package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.domain.PlantIngredient;
import nc.ird.malariaplantdb.service.xls.dto.PlantIngredientsTemp;
import org.apache.commons.collections.Transformer;

import java.util.Set;
import java.util.TreeSet;

/**
 * Transformer which take a{@code PlantIngredients} and return a list of {@code PlantIngredient}
 */
public class PlantIngredientsTempToSet implements Transformer {

    @Override
    public Object transform(Object o) {

        if (!(o instanceof PlantIngredientsTemp)) {
            throw new IllegalArgumentException(String.format("The PlantIngredientsTempToList transformer need a " +
                    "PlantIngredientsTemp object as input. It received this object instead : '%s'", o));
        } else {
            PlantIngredientsTemp plantsIngredients = (PlantIngredientsTemp) o;

            Set<PlantIngredient> piSet = new TreeSet<>();
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient1());
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient2());
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient3());
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient4());
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient5());
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient6());
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient7());
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient8());
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient9());
            addPlantIngredient(piSet, plantsIngredients.getPlantIngredient10());

            return piSet;
        }
    }

    private void addPlantIngredient(Set<PlantIngredient> piSet, PlantIngredient plantIngredient){
        if (plantIngredient != null && plantIngredient.getSpecies() != null & plantIngredient.getPartUsed() != null){
            piSet.add(plantIngredient);
        }
    }

}
