package nc.ird.malariaplantdb.service.xls.transformers;

import org.apache.commons.collections.Transformer;

/**
 * Normalize a specified string by removing beginning and ending spaces (trim) and replacing consecutive whitespaces
 * (space, tab, new line, etc.) by a single space
 */
public class StringNormalizer implements Transformer {

    public static final StringNormalizer INSTANCE = new StringNormalizer();

    @Override
    public String transform(Object input) {
        if (input == null)
            return null;
        return input.toString().trim().replaceAll("\\s+", " ");
    }

    public static StringNormalizer getInstance() {
        return INSTANCE;
    }
}
