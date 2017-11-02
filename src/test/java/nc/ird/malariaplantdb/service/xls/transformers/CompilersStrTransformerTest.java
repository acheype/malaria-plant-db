package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test class for CompilersStrTransformer
 *
 * @author acheype
 * */
public class CompilersStrTransformerTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testTransform() throws Exception {
        CompilersStrTransformer transformer = new CompilersStrTransformer();

        PropVals dtoPropVals = new PropVals();
        dtoPropVals.put("compilers", "Bourdy, Geneviève / Deharo, Eric");

        ArrayList<PropVals> resultPropValsList = (ArrayList<PropVals>) transformer.transform(dtoPropVals);
        Assertions.assertThat(resultPropValsList).hasSize(2);
        Assertions.assertThat(resultPropValsList.get(0).get("compilers.family")).isEqualTo("Bourdy");
        Assertions.assertThat(resultPropValsList.get(0).get("compilers.given")).isEqualTo("Geneviève");
        Assertions.assertThat(resultPropValsList.get(1).get("compilers.family")).isEqualTo("Deharo");
        Assertions.assertThat(resultPropValsList.get(1).get("compilers.given")).isEqualTo("Eric");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransformException() throws Exception {
        CompilersStrTransformer transformer = new CompilersStrTransformer();

        PropVals dtoPropVals = new PropVals();
        dtoPropVals.put("compilers", "Aaaa / Bbbbb");

        transformer.transform(dtoPropVals);
    }
}
