package nc.ird.malariaplantdb.service.xls.annotations;

import nc.ird.malariaplantdb.service.xls.transformers.EntitiesTransformer;
import nc.ird.malariaplantdb.service.xls.transformers.NOPEntitiesTransformer;

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
     * @return the loader which uses the field to load the final entity property
     */
    PropertyLoader propertyLoader() default @PropertyLoader(outputProperty = PropertyLoader.NO_LOAD);

    /**
     * @return the transformer which modify the entities map after all the loadings of this property
     */
    Class<? extends EntitiesTransformer> afterLoadingTransformer() default NOPEntitiesTransformer.class;

}
