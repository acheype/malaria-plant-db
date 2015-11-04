package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.PlantIngredient;

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
        PlantIngredient::getPartUsed);

    @Override
    public int compare(PlantIngredient pi1, PlantIngredient pi2) {
        return pi1.equals(pi2) ? 0 : BY_SPECIES_AND_PART.compare(pi1, pi2);
    }

}
