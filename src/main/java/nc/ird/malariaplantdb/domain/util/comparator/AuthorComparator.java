package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.Author;
import org.apache.commons.lang.ObjectUtils;

import java.util.Comparator;

/**
 * Comparator for the Author entities
 *
 * @author acheype
 */
public class AuthorComparator implements Comparator<Author> {

    private final static Comparator<Author> BY_POSITION = Comparator.comparing(Author::getPosition).thenComparing(
        Author::getGiven);

    private final static Comparator<Author> BY_POSITION_AND_ID = BY_POSITION.thenComparing(Author::getId,
        Comparator.nullsFirst(Comparator.naturalOrder()));

    @Override
    public int compare(Author a1, Author a2) {
        if (a1 == null && a2 == null || ObjectUtils.equals(a1, a2))
            return 0;
        else if (a1 == null)
            return -1;
        else if (a2 == null)
            return 1;
        return BY_POSITION_AND_ID.compare(a1, a2);
    }
}
