package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.entities.Author;
import org.apache.commons.collections.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Transform a specific formatted string into a list of Author objects
 * <p>
 * The string format is the followed : each author name with the last name first, a coma (,
 * ) then the given name initials (with comas). For several authors, each author complete
 * name must be separated by a slash (/). The format is space insensitive.
 * For instance, this string is correct : "Krettli, A.U. / Brandao, M.G.L. / Grandi, T.S.M./
 * Rocha, E.M.M. / Sawyer D.R."
 */
public class StringToAuthorsList implements Transformer {

    static final private String AUTHORS_REGEXP = "([a-zA-ZÀ-ÿ \\-]+),([a-zA-ZÀ-ÿ \\-.]+)/?";

    @Override
    public Object transform(Object o) {
        List<Author> authors = new ArrayList<>();

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
                author.setFamily(TrimStringTransformer.getInstance().transform(lastName));
                author.setGiven(TrimStringTransformer.getInstance().transform(givenName));
                authors.add(author);
            }

            if (authors.isEmpty()) {
                throw new IllegalArgumentException(String.format("The followed 'Author(s)' field is not correctly " +
                        "formated : '%s'", authorsStr));
            }

        }
        return authors;
    }
}
