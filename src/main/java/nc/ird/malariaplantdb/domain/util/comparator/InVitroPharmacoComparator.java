package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.InVitroPharmaco;

import java.util.Comparator;

/**
 * Comparator for the InVitroPharmaco entities
 *
 * @author acheype
 */
public class InVitroPharmacoComparator implements Comparator<InVitroPharmaco> {

    private final static Comparator<InVitroPharmaco> BY_REMEDY_AND_ID = Comparator.comparing(InVitroPharmaco::getRemedy)
        .thenComparing(InVitroPharmaco::getId);

    @Override
    public int compare(InVitroPharmaco s1, InVitroPharmaco s2) {
        return s1.equals(s2) ? 0 : BY_REMEDY_AND_ID.compare(s1, s2);
    }
}
