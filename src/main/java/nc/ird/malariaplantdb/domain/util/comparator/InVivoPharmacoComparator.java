package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.InVivoPharmaco;

import java.util.Comparator;

/**
 * Comparator for the InVivoPharmaco entities
 *
 * @author acheype
 */
public class InVivoPharmacoComparator implements Comparator<InVivoPharmaco> {

    private final static Comparator<InVivoPharmaco> BY_REMEDY_AND_ID = Comparator.comparing(InVivoPharmaco::getRemedy)
        .thenComparing(InVivoPharmaco::getId);

    @Override
    public int compare(InVivoPharmaco s1, InVivoPharmaco s2) {
        return s1.equals(s2) ? 0 : BY_REMEDY_AND_ID.compare(s1, s2);
    }
}
