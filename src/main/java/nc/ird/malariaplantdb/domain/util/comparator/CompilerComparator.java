package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.Compiler;
import org.apache.commons.lang.ObjectUtils;

import java.util.Comparator;

/**
 * Comparator for the Compiler entities
 *
 * @author acheype
 */
public class CompilerComparator implements Comparator<Compiler> {

    private final static Comparator<Compiler> BY_NAME_AND_ID = Comparator.comparing(
        Compiler::getFamily, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(
            Compiler::getGiven, Comparator.nullsFirst(Comparator.naturalOrder())).thenComparing(
                Compiler::getId, Comparator.nullsFirst(Comparator.naturalOrder()));

    @Override
    public int compare(Compiler c1, Compiler c2) {
        if (c1 == null && c2 == null || ObjectUtils.equals(c1, c2))
            return 0;
        else if (c1 == null)
            return -1;
        else if (c2 == null)
            return 1;
        return BY_NAME_AND_ID.compare(c1, c2);
    }

}
