package nc.ird.malariaplantdb.domain.util.comparator;

import nc.ird.malariaplantdb.domain.Author;

import java.util.Comparator;

/**
 * Comparator for the Author entities
 *
 * @author acheype
 */
public class AuthorComparator implements Comparator<Author> {

    private final static Comparator<Author> BY_ID = Comparator.comparing(Author::getId);

    @Override
    public int compare(Author a1, Author a2) {
        return a1.equals(a2) ? 0 : BY_ID.compare(a1, a2);
    }
}
