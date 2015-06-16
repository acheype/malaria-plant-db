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
     * @return the output property in which the imported value will be loaded
     */
    public String outputProperty();

    /**
     * @return the transformer which transforms the importation value into the corresponding one for the
     * outputProperty (by default an no-operation transformer)
     */
    public Class<? extends Transformer> transformer() default NOPTransformer.class;

    /**
     * Null value used for outputProperty to define there is no property to load
     */
    public final static String NULL_OUTPUT = "";

}
