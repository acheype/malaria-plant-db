package nc.ird.malariaplantdb.service.xls.fillers;

import nc.ird.malariaplantdb.service.xls.ExcelLoader;
import nc.ird.malariaplantdb.service.xls.fillers.errors.FillerError;
import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

/**
 * Fill the{@code outputProperty} with an already loaded entity searched with the{@code propValsSearch} values.
 *
 * If a{@code dtoPropertiesTransformer} has initialized the{@code PropValsSearched} list with several objects, the
 * result objects will be added to an already constructed collection set in the {@code outputProperties}.
 *
 * @author acheype
 */
public abstract class EntityRefFiller {

    /**
     * List of{@code PropVals} which identify the values to be searched. Each {@code PropVals} object contains a set
     * of parameterNames/values in order to find one result object.
     */
    List<PropVals> propValsSearched;

    /**
     * The entity which contains the property to be set
     */
    Object outputEntity;

    /**
     * The entity property name which will be set with the result object.
     */
    String outputProperty;

    /**
     * <p>In situation where the outputProperty can't extends the{@code xlsEntityRefType} class, a transformer can be
     * defined.</p>
     * <p>Returns the transformer which transforms the matching entity into an object of the{@code
     * outputProperty} class.</p>
     */
    private Class<? extends Transformer> outputTransformer;

    /**
     * The error which could occur during the{@code resultObject} search
     */
    FillerError resultError;

    /**
     * Search a referenced entity with the value defined in the{@code propValsSearched}
     *
     * @param propValsSearched the{@code PropVal} searched
     */
    public abstract Object findEntityRef(PropVals propValsSearched) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalArgumentException;

    @SuppressWarnings("unchecked")
    public void fillPropertyWithRef() throws IllegalAccessException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalArgumentException {

        if (CollectionUtils.isEmpty(propValsSearched))
            throw new IllegalArgumentException(String.format("The propValsSearched used to find a reference for the " +
                    "'%s' property of the '%s' class must not be empty or null", outputProperty, outputEntity
                    .getClass().getSimpleName()));

        else if (propValsSearched.size() == 1) {
            Object resultObject = findEntityRef(propValsSearched.get(0));

            Object outputPropertyVal = PropertyUtils.getProperty(outputEntity, outputProperty);
            if (outputPropertyVal instanceof Collection) {
                // if the output property is a collection, add the unique object found
                Collection<Object> outputPropertyCol = (Collection<Object>) outputPropertyVal;
                if (resultObject != null)
                    outputPropertyCol.add(ExcelLoader.applyTransformer(outputTransformer, resultObject));
            } else {
                // if it's a simple object, set it
                try {
                    if (resultObject != null)
                        PropertyUtils.setProperty(outputEntity, outputProperty,
                            ExcelLoader.applyTransformer(outputTransformer, resultObject));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("The outputProperty type is not matching with the found object " +
                        "type. If a Collection object is expected, the collection must be initialized before.", e);
                }
            }
        } else {
            // case where several results objects will be set in a collection
            Object outputPropertyVal = PropertyUtils.getProperty(outputEntity, outputProperty);
            if (outputPropertyVal == null || !(outputPropertyVal instanceof Collection))
                throw new IllegalArgumentException(String.format("As several objects are searched by the " +
                    "entityRefFiller, the '%s' property of the '%s' class must be an already constructed collection",
                    outputProperty, outputEntity.getClass().getSimpleName()));
            else {
                Collection<Object> outputPropertyCol = (Collection<Object>) outputPropertyVal;
                for (PropVals propVals : propValsSearched) {
                    Object resultObject = findEntityRef(propVals);
                    if (resultObject != null)
                        outputPropertyCol.add(ExcelLoader.applyTransformer(outputTransformer, resultObject));
                }
            }
        }
    }

    public List<PropVals> getPropValsSearched() {
        return propValsSearched;
    }

    public void setPropValsSearched(List<PropVals> propValsSearched) {
        this.propValsSearched = propValsSearched;
    }

    public Object getOutputEntity() {
        return outputEntity;
    }

    public void setOutputEntity(Object outputEntity) {
        this.outputEntity = outputEntity;
    }

    public String getOutputProperty() {
        return outputProperty;
    }

    public void setOutputProperty(String outputProperty) {
        this.outputProperty = outputProperty;
    }

    public Class<? extends Transformer> getOutputTransformer() {
        return outputTransformer;
    }

    public void setOutputTransformer(Class<? extends Transformer> outputTransformer) {
        this.outputTransformer = outputTransformer;
    }

    public FillerError getResultError() {
        return resultError;
    }

    public void setResultError(FillerError resultError) {
        this.resultError = resultError;
    }
}
