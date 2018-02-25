package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.InVivoPharmaco;
import org.apache.commons.lang.ObjectUtils;

import java.util.Comparator;

/**
 * Comparator for the InVivoPharmaco entities
 *
 * @author acheype
 */
public class InVivoPharmacoComparator implements Comparator<InVivoPharmaco> {

    private final static Comparator<InVivoPharmaco> BY_REMEDY_AND_ID = Comparator.comparing(
        InVivoPharmaco::getRemedy, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(
            InVivoPharmaco::getId, Comparator.nullsFirst(Comparator.naturalOrder()));

    @Override
    public int compare(InVivoPharmaco i1, InVivoPharmaco i2) {
        if (i1 == null && i2 == null || ObjectUtils.equals(i1, i2))
            return 0;
        else if (i1 == null)
            return -1;
        else if (i2 == null)
            return 1;
        return BY_REMEDY_AND_ID.compare(i1, i2);
    }
}
