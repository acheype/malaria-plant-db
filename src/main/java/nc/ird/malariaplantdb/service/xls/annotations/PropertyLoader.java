package nc.ird.malariaplantdb.service.xls.annotations;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.NOPTransformer;

import java.lang.annotation.*;

/**
 * Annotation used to describe how is loaded a property from the dto to the final entity
 *
 * @author acheype
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyLoader {

    /**
     * @return the transformer which transforms the importation value into the corresponding one for the
     * outputProperty (by default an no-operation transformer)
     */
    Class<? extends Transformer> transformer() default NOPTransformer.class;

    /**
     * @return the entitie's output property in which the imported value will be loaded. If null, the dtoPropertyName will
     * be used.
     */
    String outputProperty() default DTO_NAME;

    /**
     * Value used for outputProperty to define that the name of the dto property will be used
     */
    final static String DTO_NAME = "";

    /**
     * Null value used for outputProperty to define there is no property to load
     */
    final static String NO_LOAD = "null";

}
