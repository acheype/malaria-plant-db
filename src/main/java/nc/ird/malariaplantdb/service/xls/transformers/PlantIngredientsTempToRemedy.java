package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.dto.PlantIngredientsTemp;
import org.apache.commons.collections.Transformer;

/**
 * Transformer which take a{@code PlantIngredients} and return a remedy
 */
public class PlantIngredientsTempToRemedy implements Transformer {

    @Override
    public Object transform(Object o) {

        if (!(o instanceof PlantIngredientsTemp)) {
            throw new IllegalArgumentException(String.format("The PlantIngredientsTempToList transformer need a " +
                "PlantIngredientsTemp object as input. It received this object instead : '%s'", o));
        } else {
            PlantIngredientsTemp plantsIngredients = (PlantIngredientsTemp) o;

            return plantsIngredients.getRemedy();
        }
    }
}
