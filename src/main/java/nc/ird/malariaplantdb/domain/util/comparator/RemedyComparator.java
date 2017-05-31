package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.PlantIngredient;
import nc.ird.malariaplantdb.domain.Remedy;

import java.util.Comparator;

/**
 * Comparator for the Remedy entities
 *
 * @author acheype
 */
public class RemedyComparator implements Comparator<Remedy> {

    private final static Comparator<Remedy> BY_PLANT_INGREDIENTS_SET = (r1, r2) ->
        new CollectionComparator<PlantIngredient>(PlantIngredientComparator::new).compare(r1.getPlantIngredients(),
            r2.getPlantIngredients());

    private final static Comparator<Remedy> BY_PLANT_INGREDIENTS_SET_AND_ID = BY_PLANT_INGREDIENTS_SET.thenComparing(
        Remedy::getId);

    @Override
    public int compare(Remedy r1, Remedy r2) {
        return r1.equals(r2) ? 0 : BY_PLANT_INGREDIENTS_SET_AND_ID.compare(r1, r2);
    }

}
