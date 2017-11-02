package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test class for PlantIngredientsStrTransformer
 *
 * @author acheype
 */
public class PlantIngredientsStrTransformerTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testTransform() throws Exception {
        PlantIngredientsStrTransformer transformer = new PlantIngredientsStrTransformer();

        PropVals dtoPropVals = new PropVals();
        String pubStr = "Ethnobotanical study of antimalarial plants in Shinile District, Somali" +
                " Region, Ethiopia, and in vivo evaluation of selected ones against Plasmodium berghei";
        dtoPropVals.put("publication", pubStr);
        dtoPropVals.put("plantIngredients", "Halothamnus somalensis (N.E.Br.) Botsch., Root / Halothamnus somalensisA (N.E.Br.) Botsch., Bark");

        ArrayList<PropVals> resultPropValsList = (ArrayList<PropVals>) transformer.transform(dtoPropVals);
        Assertions.assertThat(resultPropValsList).hasSize(1);
        PropVals resultPropVals = resultPropValsList.get(0);
        Assertions.assertThat(resultPropVals.get("sp1")).isEqualTo("Halothamnus somalensis (N.E.Br.) Botsch.");
        Assertions.assertThat(resultPropVals.get("part1")).isEqualTo("Root");
        Assertions.assertThat(resultPropVals.get("sp2")).isEqualTo("Halothamnus somalensisA (N.E.Br.) Botsch.");
        Assertions.assertThat(resultPropVals.get("part2")).isEqualTo("Bark");
        Assertions.assertThat(resultPropVals).hasSize(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransformException() throws Exception {
        PlantIngredientsStrTransformer transformer = new PlantIngredientsStrTransformer();

        PropVals dtoPropVals = new PropVals();
        String pubStr = "Ethnobotanical study of antimalarial plants in Shinile District, Somali" +
                " Region, Ethiopia, and in vivo evaluation of selected ones against Plasmodium berghei";
        dtoPropVals.put("publication", pubStr);
        dtoPropVals.put("plantIngredients", "Aaaa / Bbbbb");

        transformer.transform(dtoPropVals);
    }
}
