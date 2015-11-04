package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.Compiler;

import java.util.Comparator;

/**
 * Comparator for the Compiler entities
 *
 * @author acheype
 */
public class CompilerComparator implements Comparator<Compiler> {

    private final static Comparator<Compiler> BY_NAME_AND_ID = Comparator.comparing(Compiler::getFamily)
        .thenComparing(Compiler::getGiven).thenComparing(Compiler::getId);

    @Override
    public int compare(Compiler c1, Compiler c2) {
        return c1.equals(c2) ? 0 : BY_NAME_AND_ID.compare(c1, c2);
    }

}
