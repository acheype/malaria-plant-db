package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.Ethnology;
import org.apache.commons.lang.ObjectUtils;

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
    public int compare(Ethnology i1, Ethnology i2) {
        if (i1 == null && i2 == null || ObjectUtils.equals(i1, i2))
            return 0;
        else if (i1 == null)
            return -1;
        else if (i2 == null)
            return 1;
        return BY_REMEDY_AND_ID.compare(i1, i2);
    }
}
