package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

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
        Assertions.assertThat(resultPropVals.get("plantIngredient1.species.species")).isEqualTo("Halothamnus " +
            "somalensis (N.E.Br.) Botsch.");
        Assertions.assertThat(resultPropVals.get("plantIngredient1.part")).isEqualTo("Root");
        Assertions.assertThat(resultPropVals.get("plantIngredient2.species.species")).isEqualTo("Halothamnus " +
            "somalensisA (N.E.Br.) Botsch.");

        IntStream.range(3, 10).forEach(i -> Assertions.assertThat(resultPropVals.get("plantIngredient" + i + "" +
            ".species.species")).isNull());
        IntStream.range(3, 10).forEach(i -> Assertions.assertThat(resultPropVals.get("plantIngredient" + i + "" +
            ".part")).isNull());
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
