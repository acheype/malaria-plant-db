package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.InVitroPharmaco;
import org.apache.commons.lang.ObjectUtils;

import java.util.Comparator;

/**
 * Comparator for the InVitroPharmaco entities
 *
 * @author acheype
 */
public class InVitroPharmacoComparator implements Comparator<InVitroPharmaco> {

    private final static Comparator<InVitroPharmaco> BY_REMEDY_AND_ID = Comparator.comparing(
        InVitroPharmaco::getRemedy, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(
            InVitroPharmaco::getId, Comparator.nullsFirst(Comparator.naturalOrder()));

    @Override
    public int compare(InVitroPharmaco i1, InVitroPharmaco i2) {
        if (i1 == null && i2 == null || ObjectUtils.equals(i1, i2))
            return 0;
        else if (i1 == null)
            return -1;
        else if (i2 == null)
            return 1;
        return BY_REMEDY_AND_ID.compare(i1, i2);
    }
}
