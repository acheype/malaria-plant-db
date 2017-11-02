package nc.ird.malariaplantdb.service.xls.infos;

import lombok.*;
import nc.ird.malariaplantdb.service.xls.fillers.EntityRefFiller;
import nc.ird.malariaplantdb.service.xls.transformers.EntitiesTransformer;
import org.apache.commons.collections.Transformer;

/**
 * Abstract class for an entity reference
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
     * The dto property names used to identify the referenced entity.
     */
    private String[] dtoProperties;

    /**
     * The transformer which applies on all {@code dtoIdentifierProperties} values before to be find a
     * matching entity (by default an no-operation transformer). The transformer have to receive a PropVals object
     * and return an other.
     */
    private Class<? extends Transformer> dtoPropertiesTransformer;

    /**
     * The filler which finds, then set the corresponding entity
     */
    private Class<? extends EntityRefFiller> filler;

    /**
     * The output property in which the entity reference will be filled
     */
    private String outputProperty;

    /**
     * <p>In situation where the outputProperty can't extends the{@code xlsEntityRefType} class, a transformer can be
     * defined.</p>
     * <p>Returns the transformer which transforms the matching entity into an object of the{@code
     * outputProperty} class.</p>
     */
    private Class<? extends Transformer> outputTransformer;

    /**
     * The transformer which modify the entities map after all the loadings for this property
     */
    private Class<? extends EntitiesTransformer> afterFillingTransformer;

}
