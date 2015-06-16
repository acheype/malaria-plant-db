package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.exceptions.DbEntityNotFoundException;
import nc.ird.malariaplantdb.service.xls.exceptions.XlsEntityNotFoundException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class DbEntityRefFiller implements EntityRefFiller {

    protected abstract List<Object> findValues(List<String> refIdentifierProperties, List<?> identifierValues) throws
            XlsEntityNotFoundException, DbEntityNotFoundException;

    @Override
    public void fillPropertyWithRef(List<String> refEntityIdentifierProperties, List<?> identifierValues,
                                    List<?> refEntities, Object entity, String outputProperty) throws
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, DbEntityNotFoundException,
            XlsEntityNotFoundException {

        List<Object> result = findValues(refEntityIdentifierProperties, identifierValues);

        if (result.isEmpty())
            throw new DbEntityNotFoundException(
                    String.format("Entity '%s' not found with the value%s %s",
                            refEntities.isEmpty() ? "" : refEntities.get(0).getClass().getSimpleName(),
                            refEntityIdentifierProperties.size() > 1 ? "s" : "",
                            identifierValues.stream().map(Object::toString)
                                    .map(s -> String.format("'%s'", s))
                                    .collect(Collectors.joining(", ")))
            );
        else {
            // only the first element found is set
            assert (result.size() == 1);
            PropertyUtils.setProperty(entity, outputProperty, result.get(0));
        }
    }
}
