package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.Ethnology;
import nc.ird.malariaplantdb.domain.PlantIngredient;

import java.util.Comparator;

/**
 * Comparator for the Ethnology entities
 *
 * @author acheype
 */
public class EthnologyComparator implements Comparator<Ethnology> {

    private final static Comparator<Ethnology> BY_PLANT_INGREDIENT_SET = ((et1, et2) ->
        new CollectionComparator<PlantIngredient>(PlantIngredientComparator::new).compare(et1.getPlantIngredients(),
            et2.getPlantIngredients()));

    private final static Comparator<Ethnology> BY_PLANT_INGREDIENT_SET_AND_ID = BY_PLANT_INGREDIENT_SET.thenComparing(
        Ethnology::getId);

    @Override
    public int compare(Ethnology s1, Ethnology s2) {
        return s1.equals(s2) ? 0 : BY_PLANT_INGREDIENT_SET_AND_ID.compare(s1, s2);
    }
}
