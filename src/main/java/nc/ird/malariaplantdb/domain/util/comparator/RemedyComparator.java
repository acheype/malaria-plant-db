package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.PlantIngredient;
import nc.ird.malariaplantdb.domain.Remedy;
import org.apache.commons.lang.ObjectUtils;

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
        Remedy::getId, Comparator.nullsFirst(Comparator.naturalOrder()));

    @Override
    public int compare(Remedy r1, Remedy r2) {
        if (r1 == null && r2 == null || ObjectUtils.equals(r1, r2))
            return 0;
        else if (r1 == null)
            return -1;
        else if (r2 == null)
            return 1;
        return BY_PLANT_INGREDIENTS_SET_AND_ID.compare(r1, r2);
    }

}
