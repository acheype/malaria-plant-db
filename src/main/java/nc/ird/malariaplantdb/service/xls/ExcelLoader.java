package nc.ird.malariaplantdb.service.xls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.service.xls.annotations.PropertyLoader;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import nc.ird.malariaplantdb.service.xls.fillers.EntityRefFiller;
import nc.ird.malariaplantdb.service.xls.fillers.XlsEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.fillers.errors.DbFillerError;
import nc.ird.malariaplantdb.service.xls.fillers.errors.FillerError;
import nc.ird.malariaplantdb.service.xls.fillers.errors.XlsFillerError;
import nc.ird.malariaplantdb.service.xls.infos.*;
import nc.ird.malariaplantdb.service.xls.structures.CellError;
import nc.ird.malariaplantdb.service.xls.structures.ClassMap;
import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import nc.ird.malariaplantdb.service.xls.transformers.EntitiesTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.StringNormalizer;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.expression.Resolver;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.NOPTransformer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class ExcelLoader {

    private List<SheetInfo> sheetInfos;

    public LoaderResult loadEntities(ClassMap dtosMap) {
        LoaderResult loaderResult = new LoaderResult();

        for (SheetInfo sheetInfo : sheetInfos) {
            loadProperties(sheetInfo, dtosMap, loaderResult.getEntitiesMap());
            fillXlsRefEntities(sheetInfo, dtosMap, loaderResult);
            fillDbRefEntities(sheetInfo, dtosMap, loaderResult);
        }

        return loaderResult;
    }

    @SuppressWarnings("unchecked")
    private void loadProperties(SheetInfo sheetInfo, ClassMap dtosMap, ClassMap entitiesMap) {

        try {
            entitiesMap.putList(sheetInfo.getOutputEntityClass(), new ArrayList());

            for (Object dto : dtosMap.getList(sheetInfo.getDtoClass())) {
                Object entity = sheetInfo.getOutputEntityClass().newInstance();

                for (ColumnInfo columnInfo : sheetInfo.getColumnInfos()) {
                    if (columnInfo.getOutputProperty() != null && !PropertyLoader.NO_LOAD.equals(columnInfo
                            .getOutputProperty()))
                        setEntityPropertyValue(columnInfo, dto, entity);
                }

                entitiesMap.getList(sheetInfo.getOutputEntityClass()).add(entity);
            }

            // after all the properties of this dto have been loaded, process the entitiesTransformer of
            // each property
            for (ColumnInfo columnInfo : sheetInfo.getColumnInfos()) {
                EntitiesTransformer entitiesTransformer = columnInfo.getAfterLoadingTransformer()
                    .getDeclaredConstructor().newInstance();
                entitiesTransformer.transformEntities(entitiesMap);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException | IllegalArgumentException e) {
            throw new ImportRuntimeException(String.format("An unexpected errors occurs during the '%s' entity " +
                    "loading process", sheetInfo.getOutputEntityClass().getSimpleName()), e);
        }
    }

    @SuppressWarnings("unchecked")
    private void fillXlsRefEntities(SheetInfo sheetInfo, ClassMap dtosMap, LoaderResult loaderResult) {

        List<?> dtos = dtosMap.getList(sheetInfo.getDtoClass());
        List<?> entities = loaderResult.getEntitiesMap().getList(sheetInfo.getOutputEntityClass());

        assert (dtos.size() == entities.size());

        try {
            for (XlsEntityRefInfo refInfo : sheetInfo.getXlsEntityRefInfos()) {
                for (int i = 0; i < dtos.size(); i++) {
                    EntityRefFiller filler = refInfo.getFiller().getDeclaredConstructor().newInstance();

                    Object curDto = dtos.get(i);
                    Object curEntity = entities.get(i);

                    if (!isExistProperty(curEntity, refInfo.getOutputProperty()))
                        throw new IllegalArgumentException(
                            String.format("The property '%s' (can be nested/indexed/mapped/combo) doesn't" +
                                    " exist in the '%s' bean", refInfo.getOutputProperty(),
                                curEntity.getClass().getSimpleName()));

                    filler.setPropValsSearched(initDtoPropVals(curDto, refInfo));
                    filler.setOutputEntity(curEntity);
                    filler.setOutputProperty(refInfo.getOutputProperty());
                    filler.setOutputTransformer(refInfo.getOutputTransformer());

                    List<?> refEntities = initXlsRefEntities(sheetInfo, loaderResult, curEntity, refInfo, filler);

                    XlsEntityRefFiller xlsFiller = (XlsEntityRefFiller) filler;
                    xlsFiller.setXlsRefEntities(refEntities);
                    xlsFiller.setXlsRefEntityProperties(Arrays.asList(refInfo.getXlsEntityRefProperties()));
                    xlsFiller.setXlsRefEntityPropertiesLabels(Arrays.asList(refInfo.getXlsEntityRefPropertiesLabels()));
                    xlsFiller.setFillerEqualsStrategy(refInfo.getFillerEqualsStrategy());

                    // no property filling if the dtoProperties are null
                    if (!CollectionUtils.isEmpty(filler.getPropValsSearched())) {
                        filler.fillPropertyWithRef();

                        if (filler.getResultError() != null)
                            loaderResult.getCellErrors().add(xlsFillerErrorToCellError((XlsFillerError) filler
                                    .getResultError(), sheetInfo, i));
                    }
                }
            }

            // after all the xls references of this dto have been filled, process the entitiesTransformer of each
            // reference
            for (XlsEntityRefInfo refInfo : sheetInfo.getXlsEntityRefInfos()) {
                EntitiesTransformer entitiesTransformer = refInfo.getAfterFillingTransformer().getDeclaredConstructor()
                    .newInstance();
                entitiesTransformer.transformEntities(loaderResult.getEntitiesMap());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException | IllegalArgumentException e) {
            throw new ImportRuntimeException(String.format("An unexpected errors occurs during the '%s' entity " +
                    "loading process", sheetInfo.getOutputEntityClass().getSimpleName()), e);
        }
    }

    @SuppressWarnings("unchecked")
    private void fillDbRefEntities(SheetInfo sheetInfo, ClassMap dtosMap, LoaderResult loaderResult) {

        List<?> dtos = dtosMap.getList(sheetInfo.getDtoClass());
        List<?> entities = loaderResult.getEntitiesMap().getList(sheetInfo.getOutputEntityClass());

        assert (dtos.size() == entities.size());

        try {
            for (DbEntityRefInfo refInfo : sheetInfo.getDbEntityRefInfos()) {
                for (int i = 0; i < dtos.size(); i++) {
                    EntityRefFiller filler = refInfo.getFiller().getDeclaredConstructor().newInstance();

                    Object curDto = dtos.get(i);
                    Object curEntity = entities.get(i);

                    if (!isExistProperty(curEntity, refInfo.getOutputProperty()))
                        throw new IllegalArgumentException(
                            String.format("The property '%s' (can be nested/indexed/mapped/combo) doesn't" +
                                    " exist in the '%s' bean", refInfo.getOutputProperty(),
                                curEntity.getClass().getSimpleName()));

                    filler.setPropValsSearched(initDtoPropVals(curDto, refInfo));
                    filler.setOutputEntity(curEntity);
                    filler.setOutputProperty(refInfo.getOutputProperty());
                    filler.setOutputTransformer(refInfo.getOutputTransformer());

                    // no property filling if the dtoProperties are null
                    if (!CollectionUtils.isEmpty(filler.getPropValsSearched())) {
                        filler.fillPropertyWithRef();

                        if (filler.getResultError() != null)
                            loaderResult.getCellErrors().add(dbFillerErrorToCellError((DbFillerError) filler
                                .getResultError(), sheetInfo, i));
                    }
                }

                // global processing (if defined) after all the entities have been filled for this property
                EntitiesTransformer entitiesTransformer = refInfo.getAfterFillingTransformer().getDeclaredConstructor()
                    .newInstance();
                entitiesTransformer.transformEntities(loaderResult.getEntitiesMap());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                NoSuchMethodException | IllegalArgumentException e) {
            throw new ImportRuntimeException(String.format("An unexpected errors occurs during the '%s' entity " +
                    "loading process", sheetInfo.getOutputEntityClass().getSimpleName()), e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<PropVals> initDtoPropVals(Object curDto, EntityRefInfo refInfo) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, InstantiationException {
        List<PropVals> dtoPropVals = new ArrayList<>();

        PropVals uniquePropVals = new PropVals();
        boolean oneDtoValueNull = false;

        for (String curDtoProp : Arrays.asList(refInfo.getDtoProperties())) {
            if (!isExistProperty(curDto, curDtoProp))
                throw new IllegalArgumentException(
                        String.format("The property '%s' (can be nested/indexed/mapped/combo) doesn't" +
                                        " exist in the '%s' bean", curDtoProp,
                                curDto.getClass().getSimpleName()));

            Object curDtoValue = PropertyUtils.getProperty(curDto, curDtoProp);
            if (curDtoValue != null)
                uniquePropVals.put(curDtoProp, curDtoValue);
            else
                oneDtoValueNull = true;
        }

        if (uniquePropVals.size() > 0 && !oneDtoValueNull) {
            if (refInfo.getDtoPropertiesTransformer() == NOPTransformer.class) {
                dtoPropVals.add(uniquePropVals);
            } else if (refInfo.getDtoPropertiesTransformer() == StringNormalizer.class) {
                StringNormalizer normalizer = new StringNormalizer();
                for (String propValsKey : uniquePropVals.keySet())
                    uniquePropVals.put(propValsKey, normalizer.transform(uniquePropVals.get(propValsKey)));
                dtoPropVals.add(uniquePropVals);
            } else {
                try {
                    dtoPropVals = (List<PropVals>) applyTransformer(refInfo.getDtoPropertiesTransformer(),
                        uniquePropVals);
                } catch (ClassCastException e) {
                    throw new IllegalArgumentException(String.format("The '%s' dtoPropertiesTransformer must " +
                        "return a List<PropVals> object.", refInfo.getDtoPropertiesTransformer().
                        getSimpleName()));
                }
            }
        }
        return dtoPropVals;
    }

    @SuppressWarnings("unchecked")
    private List<?> initXlsRefEntities(SheetInfo sheetInfo, LoaderResult loaderResult, Object curEntity,
                                       XlsEntityRefInfo refInfo, EntityRefFiller filler) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        Class outputPropertyClass = PropertyUtils.getPropertyDescriptor(curEntity, refInfo
                .getOutputProperty())
                .getPropertyType();

        if (refInfo.getOutputTransformer() == NOPTransformer.class && filler.getPropValsSearched()
                .size() == 1  &&  outputPropertyClass != refInfo.getXlsEntityRefType()){
            throw new IllegalArgumentException(String.format("As PropValsSearched contains an unique " +
                            "PropVals, the '%s' outputProperty of the '%s' class must extends the " +
                            "'%s' xlsEntityRefType",
                    refInfo.getOutputProperty(), curEntity.getClass().getSimpleName(),
                    outputPropertyClass.getSimpleName()));
        }

        List<?> refEntities = new ArrayList();
        if (loaderResult.getEntitiesMap().getList(refInfo.getXlsEntityRefType()) != null)
            refEntities.addAll(loaderResult.getEntitiesMap().getList(refInfo.getXlsEntityRefType()));
        if (refInfo.getXlsEntityRefType() == sheetInfo.getOutputEntityClass()) {
            // ref entity is the same class than the entity -> remove the current entity from the list
            refEntities.remove(curEntity);
        }
        return refEntities;
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

    private CellError xlsFillerErrorToCellError(XlsFillerError xlsFillerError, SheetInfo sheetInfo, int i)
            throws IllegalArgumentException {

        SheetInfo refSheetInfo = sheetInfos.stream()
                .filter(s -> s.getOutputEntityClass().equals(xlsFillerError.getRefEntityClass()))
                .findFirst()
                .orElse(null);
        if (refSheetInfo == null)
            throw new IllegalArgumentException(String.format("Impossible to find a sheet which his outputEntityClass " +
                            "is '%s'", xlsFillerError.getRefEntityClass()));

        if (xlsFillerError.getXlsEntityRefProperties().size() != xlsFillerError.getXlsEntityRefPropertiesLabels()
            .size())
            throw new IllegalArgumentException(String.format("The xlsEntityRefProperties {%s} must have the same " +
                        "length as the xlsEntityRefPropertiesLabels {%s}",
                        xlsFillerError.getXlsEntityRefProperties().stream().collect(Collectors.joining(", ")),
                        xlsFillerError.getXlsEntityRefPropertiesLabels().stream().collect(Collectors.joining(", "))));

        // xlsFillerError.getXlsEntityRefProperties().size == xlsFillerError.getPropVals().size() (verified in the
        // filler)
        StringBuilder valuesMap = new StringBuilder();
        for (int j = 0; j < xlsFillerError.getXlsEntityRefProperties().size(); j++) {
            valuesMap.append(xlsFillerError.getXlsEntityRefPropertiesLabels().get(j)).append(" = '")
                    .append(xlsFillerError.getPropVals().values().toArray()[j]).append("'");
            if (j != xlsFillerError.getPropVals().size() - 1)
                valuesMap.append(", ");
        }

        return new CellError(
                String.format(FillerError.ErrorCause.NOT_UNIQUE_MATCH.equals(xlsFillerError.getErrorCause()) ?
                                "Can't find in the '%s' sheet an unique line with %s values%s : %s" :
                                "Can't find in the '%s' sheet any line with %s value%s : %s",
                        refSheetInfo.getSheetLabel(),
                        xlsFillerError.getPropVals().size() > 1 ? "these" : "this",
                        xlsFillerError.getPropVals().size() > 1 ? "s" : "",
                        valuesMap),
                sheetInfo.getSheetLabel(),
                sheetInfo.getStartRow() + i,
                xlsFillerError.getPropVals().keySet().stream()
                        .map(dtoProp -> sheetInfo.getColumnInfoByDtoProperty(dtoProp).getColumnLabel())
                        .collect(Collectors.joining(", ")));
    }

    private CellError dbFillerErrorToCellError(DbFillerError dbFillerError, SheetInfo sheetInfo, int i) {

        PropVals propsBeforeTransformer = new PropVals();
        for (String propVal : dbFillerError.getPropVals().keySet()){
            ColumnInfo columnInfo = sheetInfo.getColumnInfoByDtoProperty(propVal);
            if (columnInfo == null)
                throw new IllegalArgumentException(String.format("Impossible to find the dtoProperty in a column of " +
                    "the '%s' sheet", sheetInfo.getSheetLabel()));
            else {
                if (!propsBeforeTransformer.containsKey(columnInfo.getColumnLabel()))
                    propsBeforeTransformer.put(columnInfo.getColumnLabel(), dbFillerError.getPropVals().get
                        (propVal));
                else {
                    StringBuilder curValue = new StringBuilder((String) propsBeforeTransformer.get(columnInfo
                        .getColumnLabel()));
                    propsBeforeTransformer.put(columnInfo.getColumnLabel(), curValue.append(", ").append(dbFillerError.getPropVals().get
                        (propVal)));
                }
            }
        }

        String valuesMap = propsBeforeTransformer.keySet().stream().map(dtoProp -> dtoProp + " = " +
            propsBeforeTransformer.get(dtoProp)).collect(Collectors.joining(", "));

        return new CellError(
                String.format(FillerError.ErrorCause.NOT_UNIQUE_MATCH.equals(dbFillerError.getErrorCause()) ?
                                "Can't find in the database an unique entity with %s value%s : %s" :
                                "Can't find in the database any entity with %s value%s : %s",
                        dbFillerError.getPropVals().size() > 1 ? "these" : "this",
                        dbFillerError.getPropVals().size() > 1 ? "s" : "",
                        valuesMap),
                sheetInfo.getSheetLabel(),
                sheetInfo.getStartRow() + i,
                propsBeforeTransformer.keySet().stream().collect(Collectors.joining(", ")));
    }

    private void setEntityPropertyValue(ColumnInfo columnInfo, Object dto, Object entity) throws InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException, IllegalArgumentException {

        Object propertyValue = PropertyUtils.getProperty(dto, columnInfo.getDtoPropertyName());

        String outputProperty = PropertyLoader.DTO_NAME.equals(columnInfo.getOutputProperty()) ? columnInfo
                .getDtoPropertyName() : columnInfo.getOutputProperty();
        if (isExistProperty(entity, outputProperty)) {
            if (propertyValue == null) {
                // if null, no transformation applied
                PropertyUtils.setProperty(entity, outputProperty, null);
            } else {
                BeanUtils.setProperty(entity, outputProperty,
                        applyTransformer(columnInfo.getPropertyTransformer(), propertyValue));
            }
        } else {
            throw new IllegalArgumentException(
                    String.format("The property '%s' (can be nested/indexed/mapped/combo) doesn't" +
                                    " exist in the '%s' bean", columnInfo.getOutputProperty(),
                            entity.getClass().getSimpleName()));
        }
    }

    public static Object applyTransformer(Class<? extends Transformer> transformerClass, Object value) throws
        NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

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
