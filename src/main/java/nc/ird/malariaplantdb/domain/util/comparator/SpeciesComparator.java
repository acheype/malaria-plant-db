package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.Species;

import java.util.Comparator;

/**
 * Comparator for the Species entities
 *
 * @author acheype
 */
public class SpeciesComparator implements Comparator<Species> {

    private final static Comparator<Species> BY_SPECIES = Comparator.comparing(Species::getSpecies);

    @Override
    public int compare(Species s1, Species s2) {
        return s1.equals(s2) ? 0 : BY_SPECIES.compare(s1, s2);
    }
}
