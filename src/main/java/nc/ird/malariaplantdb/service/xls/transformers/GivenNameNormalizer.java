package nc.ird.malariaplantdb.service.xls.transformers;

import org.apache.commons.collections.Transformer;

/**
 * Normalize a specified string by removing beginning and ending spaces (trim), taking only the first letter of each
 * word (in case if it's not a initial) and ensuring that there is a point and no space between two initials
 */
public class GivenNameNormalizer implements Transformer {

    public static final GivenNameNormalizer INSTANCE = new GivenNameNormalizer();

    @Override
    public String transform(Object input) {
        if (input == null)
            return null;
        String firstLetters = "";
        String text = input.toString().trim().replaceAll("\\.+", " ").replaceAll("\\s+", " ");
        for (String s : text.split(" +")){
            if (s.length() > 0)
                firstLetters += s.charAt(0) + ".";
        }
        return firstLetters;
    }

    public static GivenNameNormalizer getInstance() {
        return INSTANCE;
    }
}
