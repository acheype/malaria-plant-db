package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.Species;
import org.apache.commons.lang.ObjectUtils;

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
        if (s1 == null && s2 == null || ObjectUtils.equals(s1, s2))
            return 0;
        else if (s1 == null)
            return -1;
        else if (s2 == null)
            return 1;
        else return BY_SPECIES.compare(s1, s2);
    }
}
