package nc.ird.malariaplantdb.service.xls.annotations;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.NOPTransformer;

import java.lang.annotation.*;

/**
 * Annotation used as a label to identify in a DTO which properties to import
 *
 * @author acheype
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportProperty {

    /**
     * @return the Excel column reference (one or several letters) to keep a map between the ref and the label
     */
    String columnLetterRef();

    /**
     * @return the Excel column name to tell the user the column source reference
     */
    String columnLabel();

    /**
     * @return the output property in which the imported value will be loaded
     */
    String outputProperty();

    /**
     * @return the transformer which transforms the importation value into the corresponding one for the
     * outputProperty (by default an no-operation transformer)
     */
    Class<? extends Transformer> propertyTransformer() default NOPTransformer.class;

}