package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.PlantIngredient;
import org.apache.commons.lang.ObjectUtils;

import java.util.Comparator;

/**
 * Comparator for the PlantIngredient entities
 *
 * @author acheype
 */
public class PlantIngredientComparator implements Comparator<PlantIngredient> {

    private final static Comparator<PlantIngredient> BY_SPECIES = (pi1, pi2) ->
        new SpeciesComparator().compare(pi1.getSpecies(), pi2.getSpecies());

    private final static Comparator<PlantIngredient> BY_SPECIES_AND_PART = BY_SPECIES.thenComparing(
        PlantIngredient::getPartUsed, Comparator.nullsFirst(Comparator.naturalOrder()));

    @Override
    public int compare(PlantIngredient pi1, PlantIngredient pi2) {
        if (pi1 == null && pi2 == null || ObjectUtils.equals(pi1, pi2))
            return 0;
        else if (pi1 == null)
            return -1;
        else if (pi2 == null)
            return 1;
        return BY_SPECIES_AND_PART.compare(pi1, pi2);
    }

}
