package nc.ird.malariaplantdb.service.xls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.service.xls.annotations.EntityRef;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.exceptions.DbEntityNotFoundException;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import nc.ird.malariaplantdb.service.xls.exceptions.XlsEntityNotFoundException;
import nc.ird.malariaplantdb.service.xls.infos.ColumnInfo;
import nc.ird.malariaplantdb.service.xls.infos.EntityRefInfo;
import nc.ird.malariaplantdb.service.xls.infos.SheetInfo;
import nc.ird.malariaplantdb.service.xls.transformers.EntityRefFiller;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.expression.Resolver;
import org.apache.commons.collections.Transformer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class ExcelLoader {

    private List<SheetInfo> sheetInfos;


    public LoaderResult loadEntities(ClassMap dtosMap) {
        LoaderResult loaderResult = new LoaderResult();

        loadProperties(dtosMap, loaderResult.getEntitiesMap());
        fillRefEntities(dtosMap, loaderResult);

        return loaderResult;
    }

    @SuppressWarnings("unchecked")
    private void loadProperties(ClassMap dtosMap, ClassMap entitiesMap) {
        for (SheetInfo sheetInfo : sheetInfos) {
            try {
                entitiesMap.putList(sheetInfo.getOutputEntityClass(), new ArrayList());

                for (Object dto : dtosMap.getList(sheetInfo.getDtoClass())) {
                    Object entity = sheetInfo.getOutputEntityClass().newInstance();

                    for (ColumnInfo columnInfo : sheetInfo.getColumnInfos()) {
                        if (columnInfo.getOutputProperty() != null && !PropertyLoader.NULL_OUTPUT.equals(columnInfo
                                .getOutputProperty()))
                            setEntityPropertyValue(columnInfo, dto, entity);
                    }

                    entitiesMap.getList(sheetInfo.getOutputEntityClass()).add(entity);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                    NoSuchMethodException | IllegalArgumentException e) {
                throw new ImportRuntimeException(String.format("An unexpected error occurs during the '%s' entity " +
                        "loading process", sheetInfo.getOutputEntityClass().getSimpleName()), e);
            }
        }
    }

    /**
     * Test if an property name exists (can be nested/indexed/mapped/combo)
     * The code was extracted from <code>BeanUtilsBean.setProperty(Object bean, String name, Object value)</code>
     *
     * @return The resulted boolean
     */
    private boolean isExistProperty(Object bean, String name) throws InvocationTargetException,
            IllegalAccessException {
        PropertyUtilsBean propertyUtils = BeanUtilsBean.getInstance().getPropertyUtils();

        // Resolve any nested expression to get the actual target bean
        Resolver resolver = propertyUtils.getResolver();
        while (resolver.hasNested(name)) {
            try {
                bean = propertyUtils.getProperty(bean, resolver.next(name));
                name = resolver.remove(name);
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
        if (log.isTraceEnabled()) {
            log.trace("    Target bean = " + bean);
            log.trace("    Target name = " + name);
        }

        // Declare local variables we will require
        String propName = resolver.getProperty(name); // Simple name of target property
        Class type;                                   // Java type of target property
        // Calculate the target property type
        PropertyDescriptor descriptor;
        try {
            descriptor = propertyUtils.getPropertyDescriptor(bean, name);
            if (descriptor == null) {
                return false; // Skip this property setter
            }
        } catch (NoSuchMethodException e) {
            return false; // Skip this property setter
        }
        type = descriptor.getPropertyType();
        if (type == null) {
            // Most likely an indexed setter on a POJB only
            if (log.isTraceEnabled()) {
                log.trace("    target type for property '" + propName + "' is null, so skipping ths setter");
            }
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private void fillRefEntities(ClassMap dtosMap, LoaderResult loaderResult) {
        for (SheetInfo sheetInfo : sheetInfos) {

            List dtos = dtosMap.getList(sheetInfo.getDtoClass());
            List entities = loaderResult.getEntitiesMap().getList(sheetInfo.getOutputEntityClass());

            assert (dtos.size() == entities.size());
            for (int i = 0; i < dtos.size(); i++) {
                Object curDto = dtos.get(i);
                Object curEntity = entities.get(i);

                try {
                    for (EntityRefInfo refInfo : sheetInfo.getEntityRefInfos()) {
                        EntityRefFiller filler = refInfo.getFiller().getDeclaredConstructor().newInstance();

                        if (!isExistProperty(curEntity, refInfo.getOutputProperty()))
                            throw new IllegalArgumentException(
                                    String.format("The property '%s' (can be nested/indexed/mapped/combo) doesn't" +
                                                    " exist in the '%s' bean", refInfo.getOutputProperty(),
                                            curEntity.getClass().getSimpleName()));

                        Class entityRefType = (refInfo.getEntityRefType() == EntityRef.NULL_TYPE.class) ?
                                PropertyUtils.getPropertyDescriptor(curEntity, refInfo.getOutputProperty())
                                        .getPropertyType() :
                                refInfo.getEntityRefType();

                        List refEntities = new ArrayList();
                        if (loaderResult.getEntitiesMap().getList(entityRefType) != null)
                            refEntities.addAll(loaderResult.getEntitiesMap().getList(entityRefType));
                        if (entityRefType == sheetInfo.getOutputEntityClass()) {
                            // ref entity is the same class than the entity -> remove the current entity from the list
                            refEntities.remove(curEntity);
                        }

                        ArrayList<Object> identifierValues = new ArrayList<>();
                        for (String identifierProp : Arrays.asList(refInfo.getDtoIdentifierProperties())) {
                            Object curValue = PropertyUtils.getProperty(curDto, identifierProp);
                            identifierValues.add(applyTransformer(refInfo.getDtoIdentifierTransformer(), curValue));
                        }

                        try {
                            // no filling when the identifier values are null
                            Optional<Object> notNulls = identifierValues.stream().filter(v -> v != null).findAny();
                            if (notNulls.isPresent()) {
                                filler.fillPropertyWithRef(Arrays.asList(refInfo.getRefIdentifierProperties()),
                                        identifierValues,
                                        refEntities,
                                        curEntity,
                                        refInfo.getOutputProperty());
                            }
                        } catch (XlsEntityNotFoundException xlsEntityNotFoundException) {
                            handleXlsEntityNotFoundException(loaderResult, sheetInfo, i, refInfo, entityRefType,
                                    identifierValues, xlsEntityNotFoundException);
                        } catch (DbEntityNotFoundException dbEntityNotFoundException) {
                            handleDbEntityNotFoundException(loaderResult, sheetInfo, i, refInfo, identifierValues,
                                    dbEntityNotFoundException);
                        }
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                        NoSuchMethodException | IllegalArgumentException e) {
                    throw new ImportRuntimeException(String.format("An unexpected error occurs during the '%s' entity " +
                            "loading process", sheetInfo.getOutputEntityClass().getSimpleName()), e);
                }
            }
        }
    }

    private void handleXlsEntityNotFoundException(LoaderResult loaderResult, SheetInfo sheetInfo, int i, EntityRefInfo
            refInfo, Class entityRefType, ArrayList<Object> identifierValues, XlsEntityNotFoundException
                                                          xlsEntityNotFoundException) {
        SheetInfo refSheetInfo = sheetInfos.stream()
                .filter(s -> s.getOutputEntityClass().equals(entityRefType))
                .findFirst()
                .orElse(null);
        assert (refSheetInfo != null);

        StringBuilder valuesMap = new StringBuilder();
        // refInfo.getRefIdentifierProperties().length != identifierValues.size() with extreme cases fillers
        for (int j = 0; j < identifierValues.size(); j++) {
            ColumnInfo columnInfo = refSheetInfo.getColumnInfoByOutputProperty(refInfo
                    .getRefIdentifierProperties()[j]);
            if (columnInfo != null)
                valuesMap.append(columnInfo.getColumnLabel()).append(" = '")
                        .append(identifierValues.get(j)).append("'");
            else
                valuesMap.append("'").append(identifierValues.get(j)).append("'");
            if (j != identifierValues.size() - 1)
                valuesMap.append(", ");
        }

        loaderResult.getCellErrors().add(
                new CellError(
                        String.format("Can't find in the '%s' sheet a line with %s value%s : %s",
                                refSheetInfo.getSheetLabel(),
                                identifierValues.size() > 1 ? "these" : "this",
                                identifierValues.size() > 1 ? "s" : "",
                                valuesMap),
                        sheetInfo.getSheetLabel(),
                        sheetInfo.getStartRow() + i,
                        Arrays.stream(refInfo.getDtoIdentifierProperties())
                                .map(dtoProp -> sheetInfo.getColumnInfoByDtoProperty(dtoProp).getColumnLabel())
                                .collect(Collectors.joining(", ")),
                        xlsEntityNotFoundException
                )
        );
    }

    private void handleDbEntityNotFoundException(LoaderResult loaderResult, SheetInfo sheetInfo, int i, EntityRefInfo
            refInfo, ArrayList<Object> identifierValues, DbEntityNotFoundException
                                                         xlsEntityNotFoundException) {

        StringBuilder valuesMap = new StringBuilder();
        assert (refInfo.getDtoIdentifierProperties().length == identifierValues.size());
        for (int j = 0; j < refInfo.getDtoIdentifierProperties().length; j++) {
            String columnLabel = sheetInfo.getColumnInfoByDtoProperty(refInfo
                    .getDtoIdentifierProperties()[j]).getColumnLabel();
            valuesMap.append(columnLabel).append(" = '").append
                    (identifierValues.get(j)).append("'");
            if (j != refInfo.getDtoIdentifierProperties().length - 1)
                valuesMap.append(", ");
        }

        loaderResult.getCellErrors().add(
                new CellError(
                        String.format("Can't find in the database an entity with %s value%s : %s",
                                identifierValues.size() > 1 ? "these" : "this",
                                identifierValues.size() > 1 ? "s" : "",
                                valuesMap),
                        sheetInfo.getSheetLabel(),
                        sheetInfo.getStartRow() + i,
                        Arrays.stream(refInfo.getDtoIdentifierProperties())
                                .map(dtoProp -> sheetInfo.getColumnInfoByDtoProperty(dtoProp).getColumnLabel())
                                .collect(Collectors.joining(", ")),
                        xlsEntityNotFoundException
                )
        );
    }

    private void setEntityPropertyValue(ColumnInfo columnInfo, Object dto,
                                        Object entity) throws InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, IllegalArgumentException {

        Object propertyValue = PropertyUtils.getProperty(dto, columnInfo.getDtoPropertyName());

        if (isExistProperty(entity, columnInfo.getOutputProperty())) {
            if (propertyValue == null) {
                PropertyUtils.setProperty(entity, columnInfo.getOutputProperty(),
                        applyTransformer(columnInfo.getPropertyTransformer(), null));
            } else {
                BeanUtils.setProperty(entity, columnInfo.getOutputProperty(),
                        applyTransformer(columnInfo.getPropertyTransformer(), propertyValue));
            }
        } else {
            throw new IllegalArgumentException(
                    String.format("The property '%s' (can be nested/indexed/mapped/combo) doesn't" +
                                    " exist in the '%s' bean", columnInfo.getOutputProperty(),
                            entity.getClass().getSimpleName()));
        }
    }

    private Object applyTransformer(Class<? extends Transformer> transformerClass, Object value) throws NoSuchMethodException,
            InstantiationException,
            IllegalAccessException, InvocationTargetException {

        Constructor constructor = transformerClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Transformer transformer = (Transformer) constructor.newInstance();

        return transformer.transform(value);
    }


    @Getter
    @ToString
    @NoArgsConstructor
    public static class LoaderResult {

        private ClassMap entitiesMap = new ClassMap();

        private List<CellError> cellErrors = new ArrayList<>();
    }

}
