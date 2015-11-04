package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.InVivoPharmaco;
import nc.ird.malariaplantdb.domain.PlantIngredient;

import java.util.Comparator;

/**
 * Comparator for the InVivoPharmaco entities
 *
 * @author acheype
 */
public class InVivoPharmacoComparator implements Comparator<InVivoPharmaco> {

    private final static Comparator<InVivoPharmaco> BY_PLANT_INGREDIENT_SET = ((iv1, iv2) ->
        new CollectionComparator<PlantIngredient>(PlantIngredientComparator::new).compare(iv1.getPlantIngredients(),
            iv2.getPlantIngredients()));

    private final static Comparator<InVivoPharmaco> BY_PLANT_INGREDIENT_SET_AND_ID = BY_PLANT_INGREDIENT_SET.thenComparing(
        InVivoPharmaco::getId);

    @Override
    public int compare(InVivoPharmaco iv1, InVivoPharmaco iv2) {
        return iv1.equals(iv2) ? 0 : BY_PLANT_INGREDIENT_SET_AND_ID.compare(iv1, iv2);
    }
}
