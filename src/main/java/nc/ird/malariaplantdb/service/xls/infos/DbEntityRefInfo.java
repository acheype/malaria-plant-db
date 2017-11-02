package nc.ird.malariaplantdb.service.xls.infos;

import nc.ird.malariaplantdb.service.xls.fillers.EntityRefFiller;
import nc.ird.malariaplantdb.service.xls.transformers.EntitiesTransformer;
import org.apache.commons.collections.Transformer;

/**
 * Information for a DB entity reference
 *
 * @author acheype
 */
public class DbEntityRefInfo extends EntityRefInfo {

    public DbEntityRefInfo(String[] dtoProperties, Class<? extends Transformer> dtoPropertiesTransformer,
                           Class<? extends EntityRefFiller> filler, String outputProperty,
                           Class<? extends Transformer> outputTransformer, Class<? extends EntitiesTransformer> afterFillingTransformer) {
        super(dtoProperties, dtoPropertiesTransformer, filler, outputProperty, outputTransformer, afterFillingTransformer);
    }
}
