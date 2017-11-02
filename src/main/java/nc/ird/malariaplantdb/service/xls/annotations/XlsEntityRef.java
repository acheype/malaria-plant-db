package nc.ird.malariaplantdb.service.xls.annotations;

import nc.ird.malariaplantdb.service.xls.fillers.XlsEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.fillers.util.DefaultEqualsStrategy;
import nc.ird.malariaplantdb.service.xls.fillers.util.EqualsStrategy;
import nc.ird.malariaplantdb.service.xls.transformers.EntitiesTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.NOPEntitiesTransformer;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.NOPTransformer;

/**
 * <p>Annotation used to fill a property with an other entity already loaded from a Xls worksheet.</p>
 * <p>The{@code filler} use the{@code fillerEqualsStrategy} to find in the already loaded entities of class{@code
 * xlsEntityRefType} a matching entity. Then, the{@code filler} will set this entity into the specified{@code
 * outputProperty}. If an output transformer is defined, it will be applied on the entity and the result will be
 * set.</p>
 * <p>If a {@code dtoPropertiesTransformer} is specified, the dto properties values will be transform before to be
 * compared.</p>
 *
 * @author acheype
 */
public @interface XlsEntityRef {

    /**
     * @return the dto property names used to identify the referenced entity
     */
    String[] dtoProperties();

    /**
     * @return the transformer which applies on all{@code dtoIdentifierProperties} values before to be find a
     * matching entity (by default an no-operation transformer). The transformer have to receive a{@code PropVals}
     * and return a list of{@code PropVals} (if the list contains several{@code PropVals}, the filler will search for
     * a collection of entity). However, an exception is made for the{@code nc.ird.malariaplantdb.service.xls
     * .transformers.StringNormalizer} transformer where no{@code PropVals} is needed.
     */
    public Class<? extends Transformer> dtoPropertiesTransformer() default NOPTransformer.class;

    /**
     * @return the class of the already loaded entity which represents a Xls worksheet
     */
    Class xlsEntityRefType();

    /**
     * @return the property names of the worksheet entity which will be compared to the{@code dtoProperties}
     * values. If not, a default array will be set. Conventionally, it must have the same property number than the{@code
     * dtoProperties}. But if a{@code dtoPropertiesTransformer} will be applied, it must have the same property
     * number than the{@code PropVals} returned by the transformer.
     */
    String[] xlsEntityRefProperties() default {};

    /**
     * @return the column names in the referenced sheet of the properties compared to the{@code dtoProperties} (used
     * to write eventual understandable error messages)
     */
    String[] xlsEntityRefPropertiesLabels();


    /**
     * @return the filler which finds, then set the corresponding entity
     */
    Class<? extends XlsEntityRefFiller> filler();

    /**
     * @return the equals strategy used to compare the{@code dtoProperties} values and the{@code xlsEntityRefProperties}
     * ones. If no equals strategy is supplied, the default strategy consists of comparing one by one in the same order
     * each{@code dtoProperties} values and{@code xlsEntityRefType} ones.
     */
    Class<? extends EqualsStrategy> fillerEqualsStrategy() default DefaultEqualsStrategy.class;

    /**
     * @return the output property in which the entity reference will be filled
     */
    String outputProperty();

    /**
     * In situation where the outputProperty can't extends the{@code xlsEntityRefType} class, a transformer can be
     * defined.
     * @return the transformer which transforms the matching entity into an object of the{@code outputProperty} class
     */
    Class<? extends Transformer> outputTransformer() default NOPTransformer.class;

    /**
     * @return the transformer which modify the entities map after all the filling of this property
     */
    Class<? extends EntitiesTransformer> afterFillingTransformer() default NOPEntitiesTransformer.class;


}
