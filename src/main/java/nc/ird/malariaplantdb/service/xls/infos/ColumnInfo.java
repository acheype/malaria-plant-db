package nc.ird.malariaplantdb.service.xls.infos;

import lombok.*;
import nc.ird.malariaplantdb.service.xls.transformers.EntitiesTransformer;
import org.apache.commons.collections.Transformer;

/**
 * Information for a Excel column
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfo {

    /**
     * Excel column reference (one or several letters) to keep a map between the ref and the label
     */
    private String columnLetterRef;

    /**
     * Excel column name to tell the user the column source reference
     */
    private String columnLabel;

    /**
     * Name of the dtoPropertyName which map the column in a dto
     */
    private String dtoPropertyName;

    /**
     * The transformer which transforms the importation value into the corresponding one for the
     * outputProperty
     */
    private Class<? extends Transformer> propertyTransformer;

    /**
     * The output property in which the imported value will be loaded
     */
    private String outputProperty;

    /**
     * Transformer which modify the entities map after all the loadings of this property
     */
    private Class<? extends EntitiesTransformer> afterLoadingTransformer;

}
