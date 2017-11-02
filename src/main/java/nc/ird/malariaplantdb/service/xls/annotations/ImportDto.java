package nc.ird.malariaplantdb.service.xls.annotations;

import java.lang.annotation.*;

/**
 * Annotation used as a label to identify which dto to load with Excel data
 *
 * @author acheype
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportDto {

    /**
     * @return the Excel sheet name to tell the user the sheet source reference
     */
    public String sheetLabel();

    /**
     * @return the order number. The importation process will begin with the sheet's lowest numbers and finish
     * with the greatest ones.
     */
    public int importOrder();

    /**
     * @return the number line of the first row imported from the sheet
     */
    public int startRow() default 2;

    /**
     * @return the output entity class in which the importation data will be loaded
     */
    public Class<?> outputEntityClass();

    /**
     * @return the Xls entity references to set in the final entity
     */
    public XlsEntityRef[] xlsEntityRef() default {};

    /**
     * @return the DB entity references to set in the final entity
     */
    public DbEntityRef[] dbEntityRef() default {};

}
