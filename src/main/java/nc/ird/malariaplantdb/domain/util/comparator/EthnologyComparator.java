package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.Ethnology;

import java.util.Comparator;

/**
 * Comparator for the Ethnology entities
 *
 * @author acheype
 */
public class EthnologyComparator implements Comparator<Ethnology> {

    private final static Comparator<Ethnology> BY_REMEDY_AND_ID = Comparator.comparing(Ethnology::getRemedy)
        .thenComparing(Ethnology::getId);

    @Override
    public int compare(Ethnology s1, Ethnology s2) {
        return s1.equals(s2) ? 0 : BY_REMEDY_AND_ID.compare(s1, s2);
    }
}
