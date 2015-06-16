package nc.ird.malariaplantdb.service.xls.annotations;

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
    public String columnLetterRef();

    /**
     * @return the Excel column name to tell the user the column source reference
     */
    public String columnLabel();

    /**
     * @return the loader which uses the field to load the final entity property
     */
    public PropertyLoader propertyLoader() default @PropertyLoader(outputProperty = PropertyLoader.NULL_OUTPUT);

}