package nc.ird.malariaplantdb.service.xls.infos;

import lombok.*;
import nc.ird.malariaplantdb.service.xls.transformers.EntityRefFiller;
import org.apache.commons.collections.Transformer;

/**
 * Information for a database entity reference
 *
 * @author acheype
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EntityRefInfo {

    /**
     * The dto properties which contains the values searched
     */
    private String[] dtoIdentifierProperties;

    /**
     * The transformer which transforms the dto properties values before to be compared (by default an no-operation
     * transformer)
     */
    private Class<? extends Transformer> dtoIdentifierTransformer;

    /**
     * The reference entity properties which will be compared with the dtoIdentifierProperties values
     */
    private String[] refIdentifierProperties;

    /**
     * The entity ref class used to do the comparison. Usually this class is not specified, and inferred from the
     * outputProperty. May be useful per example if the outputProperty refer to a list.
     */
    private Class entityRefType;

    /**
     * The output property in which the entity reference will be filled
     */
    private String outputProperty;

    /**
     * The filler which finds, then set the corresponding entity
     */
    private Class<? extends EntityRefFiller> filler;

}
