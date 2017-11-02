package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.domain.PlantIngredient;
import nc.ird.malariaplantdb.domain.Species;
import nc.ird.malariaplantdb.service.xls.dto.PlantIngredientsTemp;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Iterator;
import java.util.SortedSet;

/**
 * Test class for PlantIngrendientsTempToSet
 *
 * @author acheype
 */
public class PlantIngredientsTempToSetTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testTransform(){
        Species species = new Species();
        species.setFamily("family");
        species.setSpecies("species");
        PlantIngredient pi = new PlantIngredient();
        pi.setSpecies(species);
        pi.setPartUsed("part used1");

        PlantIngredient pi2 = new PlantIngredient();
        pi2.setSpecies(species);

        PlantIngredient pi3 = new PlantIngredient();
        pi3.setSpecies(species);
        pi3.setPartUsed("part used3");

        PlantIngredientsTemp plantIngredients = new PlantIngredientsTemp();
        plantIngredients.setPlantIngredient1(pi);
        plantIngredients.setPlantIngredient3(pi);
        plantIngredients.setPlantIngredient5(pi2);
        plantIngredients.setPlantIngredient6(pi3);
        plantIngredients.setPlantIngredient8(pi);

        PlantIngredientsTempToSet transformer = new PlantIngredientsTempToSet();
        SortedSet<PlantIngredient> piSet = (SortedSet<PlantIngredient>) transformer.transform(plantIngredients);

        Assertions.assertThat(piSet).hasSize(2);
        Assertions.assertThat(piSet).extracting("partUsed").contains("part used1");
        Assertions.assertThat(piSet).extracting("species").extracting("species").contains("species");
        Iterator<PlantIngredient> itPi = piSet.iterator();
        Assertions.assertThat(itPi.next().getPartUsed()).isEqualTo("part used1");
        Assertions.assertThat(itPi.next().getPartUsed()).isEqualTo("part used3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransformException() {
        PlantIngredientsTempToSet transformer = new PlantIngredientsTempToSet();
        transformer.transform("test");
    }

}
