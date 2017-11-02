package nc.ird.malariaplantdb.service.xls.infos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nc.ird.malariaplantdb.service.xls.fillers.EntityRefFiller;
import nc.ird.malariaplantdb.service.xls.fillers.util.EqualsStrategy;
import nc.ird.malariaplantdb.service.xls.transformers.EntitiesTransformer;
import org.apache.commons.collections.Transformer;

/**
 * Information for a Xls entity reference
 *
 * @author acheype
 */
@Getter
@Setter
@ToString
public class XlsEntityRefInfo extends EntityRefInfo {

    /**
     * The class of the already loaded entity which represents a Xls worksheet
     */
    private Class xlsEntityRefType;

    /**
     * The property names of the worksheet entity which will be compared to the {@code dtoProperties}
     * values. If not, a default array will be set.
     */
    private String[] xlsEntityRefProperties;

    /**
     * The column names in the referenced sheet of the properties compared to the{@code dtoProperties}  (used
     * to write eventual understandable error messages)
     */
    private String[] xlsEntityRefPropertiesLabels;


    /**
     * The equals strategy used to compare the{@code dtoProperties} values and the{@code xlsEntityRefProperties}
     * ones. If no equals strategy is supplied, the default strategy consists of comparing one by one in the same order
     * each{@code dtoProperties} values and{@code xlsEntityRefType} ones.
     */
    private Class<? extends EqualsStrategy> fillerEqualsStrategy;

    public XlsEntityRefInfo(String[] dtoProperties, Class<? extends Transformer> dtoPropertiesTransformer, Class
            xlsEntityRefType, String[] xlsEntityRefProperties, String[] xlsEntityRefPropertiesLabels, Class<? extends
        EntityRefFiller> filler, Class<? extends EqualsStrategy> fillerEqualsStrategy, String outputProperty,
        Class<? extends Transformer> outputTransformer, Class<? extends EntitiesTransformer> afterFillingTransformer) {
        super(dtoProperties, dtoPropertiesTransformer, filler, outputProperty, outputTransformer, afterFillingTransformer);
        this.xlsEntityRefType = xlsEntityRefType;
        this.xlsEntityRefProperties = xlsEntityRefProperties;
        this.xlsEntityRefPropertiesLabels = xlsEntityRefPropertiesLabels;
        this.fillerEqualsStrategy = fillerEqualsStrategy;
    }
}
