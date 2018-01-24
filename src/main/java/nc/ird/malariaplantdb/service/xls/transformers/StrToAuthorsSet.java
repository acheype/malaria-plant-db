package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.domain.Author;
import org.apache.commons.collections.Transformer;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Transform a specific formatted string into a sorted set of Author objects
 * <p>
 * The string format is the followed : each author name with the last name first, a coma (,
 * ) then the given name initials (with comas). For several authors, each author complete
 * name must be separated by a slash (/). The format is space insensitive.
 * For instance, this string is correct : "Krettli, A.U. / Brandao, M.G.L. / Grandi, T.S.M./
 * Rocha, E.M.M. / Sawyer D.R."
 */
public class StrToAuthorsSet implements Transformer {

    static final private String AUTHORS_REGEXP = "([a-zA-ZÀ-ÿ \\-']+),([a-zA-ZÀ-ÿ \\-.]+)/?";

    @Override
    public Object transform(Object o) {
        SortedSet<Author> authors = new TreeSet<>();
        int position = 1;

        if (!(o instanceof String)) {
            throw new IllegalArgumentException(String.format("The followed 'Author(s)' field is not a string : " +
                    "'%s'", o));
        } else {
            String authorsStr = (String) o;
            Pattern pattern = Pattern.compile(AUTHORS_REGEXP);
            Matcher matcher = pattern.matcher(authorsStr);

            while (matcher.find()) {
                String lastName = matcher.group(1);
                String givenName = matcher.group(2);

                Author author = new Author();
                author.setFamily(StringNormalizer.getInstance().transform(lastName));
                author.setGiven(GivenNameNormalizer.getInstance().transform(givenName));
                author.setPosition(position);
                authors.add(author);

                position++;
            }

            if (authors.isEmpty()) {
                throw new IllegalArgumentException(String.format("The followed 'Author(s)' field is not correctly " +
                        "formatted : '%s'\n", authorsStr));
            }

        }
        return authors;
    }
}
