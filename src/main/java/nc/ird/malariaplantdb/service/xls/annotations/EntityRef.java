package nc.ird.malariaplantdb.service.xls.annotations;

import nc.ird.malariaplantdb.service.xls.transformers.EntityRefFiller;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.NOPTransformer;

/**
 * Annotation used to fill a property with a entity reference in the Xls worksheet
 *
 * @author acheype
 */
public @interface EntityRef {

    /**
     * @return the dto properties that the values will be searched
     */
    public String[] dtoIdentifierProperties();

    /**
     * @return the transformer which transforms the dto properties values before to be compared (by default an
     * no-operation transformer)
     */
    public Class<? extends Transformer> dtoIdentifierTransformer() default NOPTransformer.class;

    /**
     * @return the referenced entity properties which will be compared with the dto values (usually not used for
     * database references)
     */
    public String[] entityRefIdentifierProperties() default {};

    /**
     * @return the entity ref class used to do the comparison. Usually this class is not specified,
     * and inferred from the outputProperty. May be useful per example if the outputProperty refer to a list.
     */
    public Class entityRefType() default NULL_TYPE.class;

    /**
     * @return the output property in which the entity reference will be filled
     */
    public String outputProperty();

    /**
     * @return the filler which finds, then set the corresponding entity
     */
    public Class<? extends EntityRefFiller> filler();

    /**
     * Null value used for entityRefType to define the class must be inferred from the outputProperty
     */
    public final static class NULL_TYPE {
    }

}
