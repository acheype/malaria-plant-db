package nc.ird.malariaplantdb.service.xls;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.Transformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adri on 20/04/15.
 */
@Slf4j
@AllArgsConstructor
public class ExcelLoader {

    private List<SheetInfo> sheetInfos;

    public ClassMap loadEntities(ClassMap dtosMap) {
        ClassMap entitiesMap = new ClassMap();


        for (SheetInfo sheetInfo : sheetInfos) {
            try {
                entitiesMap.putList(sheetInfo.getOutputEntityClass(), new ArrayList<>());

                for (Object dto : dtosMap.getList(sheetInfo.getDtoClass())) {
                    Object entity = sheetInfo.getOutputEntityClass().newInstance();

                    for (ColumnInfo columnInfo : sheetInfo.getColumnInfos()) {
                        setEntityPropertyValue(columnInfo, dto, entity);
                    }

                    entitiesMap.getList(sheetInfo.getOutputEntityClass()).add(entity);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
            NoSuchMethodException e) {
                throw new ImportRuntimeException(String.format("An unexpected error occurs during the entity '%s' " +
                        "loading process", sheetInfo.getOutputEntityClass().getSimpleName()), e);
            }
        }

        return entitiesMap;
    }


    private void setEntityPropertyValue(ColumnInfo columnInfo, Object dto, Object entity) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Constructor constructor = columnInfo.getPropertyTransformer().getDeclaredConstructor();
        constructor.setAccessible(true);
        Transformer transformer = (Transformer) constructor.newInstance();

        Object propertyValue = BeanUtils.getProperty(dto, columnInfo.getDtoPropertyName());

        BeanUtils.setProperty(entity, columnInfo.getOutputProperty(),
                transformer.transform(propertyValue));
    }
}
