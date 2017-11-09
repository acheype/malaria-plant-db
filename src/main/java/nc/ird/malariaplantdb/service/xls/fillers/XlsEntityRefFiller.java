package nc.ird.malariaplantdb.service.xls.fillers;

import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import nc.ird.malariaplantdb.service.xls.fillers.errors.FillerError;
import nc.ird.malariaplantdb.service.xls.fillers.errors.XlsFillerError;
import nc.ird.malariaplantdb.service.xls.fillers.util.EqualsStrategy;
import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XlsEntityRefFiller extends EntityRefFiller {

    private List<?> xlsRefEntities;

    private List<String> xlsRefEntityProperties;

    private List<String> xlsRefEntityPropertiesLabels;

    private Class<? extends EqualsStrategy> fillerEqualsStrategy;

    /**
     * {@inheritDoc}
     */
    public Object findEntityRef(PropVals propValsSearched) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalArgumentException {

        if (getXlsRefEntityProperties().size() != propValsSearched.size())
            throw new IllegalArgumentException(String.format("The xlsEntityRefProperties must have the same number of" +
                    " properties than the dtoProperties (after its application if a dtoPropertiesTransformer is " +
                    "specified). They have respectively these values : [%s] and [%s]",
                    xlsRefEntityProperties.stream().collect(Collectors.joining(", ")),
                    propValsSearched.keySet().stream().collect(Collectors.joining(", "))));

        List<Object> result = new ArrayList<>();

        for (Object curRefEntity : getXlsRefEntities()) {
            PropVals refPropVals = new PropVals();

            for (String refProp : getXlsRefEntityProperties()){
                Object value = PropertyUtils.getProperty(curRefEntity, refProp);
                refPropVals.put(refProp, value);
            }

            if (compareWithFillerEqualsStrategy(propValsSearched, refPropVals)) {
                result.add(curRefEntity);
            }
        }

        if (result.isEmpty())
            this.setResultError(new XlsFillerError(propValsSearched, FillerError.ErrorCause.NO_ENTITY_FOUND,
                xlsRefEntities.isEmpty() ? null : xlsRefEntities.get(0).getClass(), xlsRefEntityProperties,
                xlsRefEntityPropertiesLabels));
        else {
            if (result.size() != 1)
                this.setResultError(new XlsFillerError(propValsSearched, FillerError.ErrorCause.NOT_UNIQUE_MATCH,
                    xlsRefEntities.isEmpty() ? null : xlsRefEntities.get(0).getClass(), xlsRefEntityProperties,
                    xlsRefEntityPropertiesLabels));
            else
                return result.get(0);
        }
        return null;
    }

    private boolean compareWithFillerEqualsStrategy(PropVals propVals1, PropVals propVals2) throws
            NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Constructor constructor = getFillerEqualsStrategy().getDeclaredConstructor();
        constructor.setAccessible(true);
        EqualsStrategy fillerEqualsStrategy = (EqualsStrategy) constructor.newInstance();

        return fillerEqualsStrategy.equals(propVals1, propVals2);
    }

    public List<?> getXlsRefEntities() {
        return xlsRefEntities;
    }

    public void setXlsRefEntities(List<?> xlsRefEntities) {
        this.xlsRefEntities = xlsRefEntities;
    }

    public List<String> getXlsRefEntityProperties() {
        return xlsRefEntityProperties;
    }

    public void setXlsRefEntityProperties(List<String> xlsRefEntityProperties) {
        this.xlsRefEntityProperties = xlsRefEntityProperties;
    }

    public List<String> getXlsRefEntityPropertiesLabels() {
        return xlsRefEntityPropertiesLabels;
    }

    public void setXlsRefEntityPropertiesLabels(List<String> xlsRefEntityPropertiesLabels) {
        this.xlsRefEntityPropertiesLabels = xlsRefEntityPropertiesLabels;
    }

    public Class<? extends EqualsStrategy> getFillerEqualsStrategy() {
        return fillerEqualsStrategy;
    }

    public void setFillerEqualsStrategy(Class<? extends EqualsStrategy> fillerEqualsStrategy) {
        this.fillerEqualsStrategy = fillerEqualsStrategy;
    }
}
