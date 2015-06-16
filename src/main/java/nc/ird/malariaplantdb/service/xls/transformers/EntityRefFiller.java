package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.exceptions.DbEntityNotFoundException;
import nc.ird.malariaplantdb.service.xls.exceptions.XlsEntityNotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface EntityRefFiller {

    public void fillPropertyWithRef(List<String> refEntityIdentifierProperties, List<?> identifierValues,
                                    List<?> refEntities, Object entity,
                                    String outputProperty) throws IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, XlsEntityNotFoundException, DbEntityNotFoundException;

}
