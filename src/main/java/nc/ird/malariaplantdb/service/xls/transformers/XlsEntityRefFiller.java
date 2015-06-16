package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.exceptions.XlsEntityNotFoundException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XlsEntityRefFiller implements EntityRefFiller {

    @Override
    public void fillPropertyWithRef(List<String> refEntityIdentifierProperties, List<?> identifierValues,
                                    List<?> refEntities, Object entity, String outputProperty) throws
            IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, XlsEntityNotFoundException {

        List<Object> result = new ArrayList<>();

        for (Object curRefEntity : refEntities) {
            List<Object> refIdentifierValues = new ArrayList<>();

            for (String refId : refEntityIdentifierProperties)
                refIdentifierValues.add(PropertyUtils.getProperty(curRefEntity, refId));

            if (identifierValues.equals(refIdentifierValues)) {
                result.add(curRefEntity);
            }
        }

        if (result.isEmpty())
            throw new XlsEntityNotFoundException(
                    String.format("Entity '%s' not found with propert%s {%s} = {%s}",
                            refEntities.isEmpty() ? "" : refEntities.get(0).getClass().getSimpleName(),
                            refEntityIdentifierProperties.size() > 1 ? "ies" : "y",
                            refEntityIdentifierProperties.stream().collect(Collectors.joining(", ")),
                            identifierValues.stream()
                                    .map(Object::toString)
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
