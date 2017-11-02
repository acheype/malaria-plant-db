package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import org.apache.commons.collections.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Transformer which takes a {@code PropVals} object with a string which corresponds to a list of compilers
 * identified with its family and given names and returns a list PropVals which contains for each compiler a element
 * with family and given names</p>
 *
 * <p>The compiler string format is the followed : each compiler name with the last name first, a coma (,) then the
 * given name. For several authors, each compiler complete name must be separated by a slash (/). The format is space
 * insensitive. For instance, this string is correct : "Bourdy, Genevieve / Deharo, Eric"
 */
public class CompilersStrTransformer implements Transformer {

    static final private String COMPILERS_REGEXP = "([a-zA-ZÀ-ÿ &\\-]+),([a-zA-ZÀ-ÿ \\-]+)/?";

    @Override
    public Object transform(Object o) {
        if (!(o instanceof PropVals)) {
            throw new IllegalArgumentException(String.format("The CompilersStrTransformer transformer need a " +
                    "PropVals object as input. It gots this object instead : '%s'", o));
        } else {

            List<PropVals> resultPropValsList = new ArrayList<>();

            PropVals dtoPropVals = (PropVals) o;

            Pattern pattern = Pattern.compile(COMPILERS_REGEXP);
            String compilersStr = (String) dtoPropVals.get("compilers");
            Matcher matcher = pattern.matcher(compilersStr);

            while (matcher.find()) {
                PropVals propVals = new PropVals();
                String family = matcher.group(1);
                String given = matcher.group(2);

                // use a point then an ID because for errors, it will take the part until the point to have a link to
                // the column name
                Transformer transformer = new StringNormalizer();
                propVals.put("compilers.family", transformer.transform(family));
                propVals.put("compilers.given", transformer.transform(given));
                resultPropValsList.add(propVals);
            }

            if (resultPropValsList.size() < 1) {
                throw new IllegalArgumentException(String.format("The followed 'compilers' field is not correctly " +
                                "formatted : '%s'\n",
                        dtoPropVals.get("compilers")));
            }

            return resultPropValsList;
        }
    }
}
