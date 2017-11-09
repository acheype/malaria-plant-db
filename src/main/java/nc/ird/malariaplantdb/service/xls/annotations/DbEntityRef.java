package nc.ird.malariaplantdb.service.xls.annotations;

import nc.ird.malariaplantdb.service.xls.fillers.DbEntityRefFiller;
import nc.ird.malariaplantdb.service.xls.transformers.EntitiesTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.NOPEntitiesTransformer;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.NOPTransformer;

/**
 * TODO Update comments
 * Annotation used to fill a property with an other entity already loaded in the Xls
 * worksheet or can be persist in a storage (file, database).
 *
 * The entity will be set by a {@code filler} in the {@code outputProperty} specified. The {@code filler} defines a
 * strategy to find an entity by comparing some dto properties values defined in {@code dtoProperties}.
 *
 * <p>If a {@code dtoPropertiesTransformer} is specified, the dto properties values will be transform before to be
 * compared.</p>
 * @author acheype
 */
public @interface DbEntityRef {

    /**
     * @return the dto property names used to identify the referenced entity
     */
    String[] dtoProperties();

    /**
     * @return the transformer which applies on all {@code dtoIdentifierProperties} values before to be find a
     * matching entity (by default an no-operation transformer). The transformer have to receive a{@code PropVals}
     * object and return a list of{@code PropVals} (if the list contains several{@code PropVals}, the filler will search for
     * a collection of entity). For error messages, to refer as a correct column label in the source sheet in case of
     * errors, the PropVals keys used in the transformer must be as 'dtoProperty.name1', 'dtoProperty.names2'...
     * (dtoProperty corresponding to this source sheet column)
     */
    public Class<? extends Transformer> dtoPropertiesTransformer() default NOPTransformer.class;

    /**
     * @return the filler which finds, then set the corresponding entity
     */
    Class<? extends DbEntityRefFiller> filler();

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
