package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.service.xls.structures.PropVals;
import org.apache.commons.collections.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Transformer which takes a {@code PropVals} object with a publication title and a string which corresponds to a
 * list of plant ingredients (until 10) and returns a list of PropVals with an unique element which contains the
 * publication title and 10 pairs of (species, plantPartUsed)</p>
 *
 * <p>The string format for the plant ingredients species is the followed : each species of the ingredient, a coma then
 * its part used. For several ingredients, each ingredient complete name must be separated by a slash (/). The format is
 * space insensitive. For instance, this string is correct : "Cochlospermum planchonii Hook.f. ex Planch., Root /
 * Senna alata (L.) Roxb., Leaf"</p>
 */
public class PlantIngredientsStrTransformer implements Transformer {

    static final private String PLANT_INGREDIENTS_REGEXP = "([a-zA-ZÀ-ÿ &\\.\\-\\(\\)']+),([a-zA-ZÀ-ÿ \\-]+)/?";

    @Override
    public Object transform(Object o) {
        if (!(o instanceof PropVals)) {
            throw new IllegalArgumentException(String.format("The PlantIngredientsStrTransformer transformer need a " +
                    "PropVals object as input. It gots this object instead : '%s'", o));
        } else {
            PropVals resultPropVals = new PropVals();
            Transformer transformer = new StringNormalizer();

            PropVals dtoPropVals = (PropVals) o;
            resultPropVals.put("publication.title", transformer.transform(dtoPropVals.get("publication")));

            Pattern pattern = Pattern.compile(PLANT_INGREDIENTS_REGEXP);
            String plantIngredientStr = (String) dtoPropVals.get("plantIngredients");
            Matcher matcher = pattern.matcher(plantIngredientStr);

            int i = 1;
            while (matcher.find()) {
                String species = matcher.group(1);
                String partUsed = matcher.group(2);

                resultPropVals.put("plantIngredient" + i + ".species.species", transformer.transform(species));
                resultPropVals.put("plantIngredient" + i + ".part", transformer.transform(partUsed));
                i++;
            }
            if (i == 1){
                throw new IllegalArgumentException(String.format("The followed 'PlantIngredients' field is not correctly " +
                        "formatted : '%s'\n",
                    dtoPropVals.get("plantIngredients")));
            }

            for (int j = i; j <= 10; j++){
                resultPropVals.put("plantIngredient" + j + ".species.species", null);
                resultPropVals.put("plantIngredient" + j + ".part", null);
            }

            return new ArrayList<PropVals>(Arrays.asList(resultPropVals));
        }
    }
}
