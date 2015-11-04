package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.InVitroPharmaco;
import nc.ird.malariaplantdb.domain.PlantIngredient;

import java.util.Comparator;

/**
 * Comparator for the InVitroPharmaco entities
 *
 * @author acheype
 */
public class InVitroPharmacoComparator implements Comparator<InVitroPharmaco> {

    private final static Comparator<InVitroPharmaco> BY_PLANT_INGREDIENT_SET = ((iv1, iv2) ->
        new CollectionComparator<PlantIngredient>(PlantIngredientComparator::new).compare(iv1.getPlantIngredients(),
            iv2.getPlantIngredients()));

    private final static Comparator<InVitroPharmaco> BY_PLANT_INGREDIENT_SET_AND_ID = BY_PLANT_INGREDIENT_SET.thenComparing(
        InVitroPharmaco::getId);

    @Override
    public int compare(InVitroPharmaco iv1, InVitroPharmaco iv2) {
        return iv1.equals(iv2) ? 0 : BY_PLANT_INGREDIENT_SET_AND_ID.compare(iv1, iv2);
    }
}
