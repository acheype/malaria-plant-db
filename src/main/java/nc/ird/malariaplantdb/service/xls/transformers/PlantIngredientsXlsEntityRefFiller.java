package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.entities.PlantIngredient;
import nc.ird.malariaplantdb.service.xls.dto.PlantIngredients;
import nc.ird.malariaplantdb.service.xls.exceptions.XlsEntityNotFoundException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * If identifierValues is null, then set an empty compiler list.
 * <p>
 * The string format is the followed : each species of the ingredient, a coma then its part used. For several
 * ingredients, each ingrediant complete name must be separated by a slash (/). The format is space insensitive.
 * For instance, this string is correct : "Cochlospermum planchonii Hook.f. ex Planch., Root /
 * Senna alata (L.) Roxb., Leaf"
 */
@Service
public class PlantIngredientsXlsEntityRefFiller extends XlsEntityRefFiller {

    static final private String PLANT_INGREDIENTS_REGEXP = "([a-zA-ZÀ-ÿ \\-]+),([a-zA-ZÀ-ÿ \\-]+)/?";

    static final String[] SPECIES_PROPERTIES = {"species1", "species2", "species3", "species4", "species5", "species6",
            "species7", "species8", "species9", "species10"};

    static final String[] PARTS_USED_PROPERTIES = {"partUsed1", "partUsed2", "partUsed3", "partUsed4", "partUsed5",
            "partUsed6", "partUsed7", "partUsed8", "partUsed9", "partUsed10"};

    @Override
    public void fillPropertyWithRef(List<String> refEntityIdentifierProperties, List<?> identifierValues,
                                    List<?> refEntities, Object entity, String outputProperty) throws
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, XlsEntityNotFoundException {

        List<PlantIngredient> plantIngredientsResult = new ArrayList<>();

        // as all the species, parts are in a single string, extract them
        String plantIngredientsStr = (String) identifierValues.get(0);

        if (plantIngredientsStr != null) {
            PlantIngredients identifierPlantIngredients = new PlantIngredients();
            int i = 0;

            Pattern pattern = Pattern.compile(PLANT_INGREDIENTS_REGEXP);
            Matcher matcher = pattern.matcher(plantIngredientsStr);

            while (matcher.find() && i < 10) {
                String species = matcher.group(1);
                String partUsed = matcher.group(2);

                PropertyUtils.setProperty(identifierPlantIngredients, SPECIES_PROPERTIES[i], species);
                PropertyUtils.setProperty(identifierPlantIngredients, PARTS_USED_PROPERTIES[i], partUsed);

                i++;
            }

            int resultNb = 0;
            for (Object curRefEntity : refEntities) {
                if (curRefEntity.equals(identifierPlantIngredients)) {
                    plantIngredientsResult.addAll(buildPlantIngredientListFromPlantIngredient(identifierPlantIngredients));
                    resultNb++;
                }
            }

            if (plantIngredientsResult.isEmpty())
                throw new XlsEntityNotFoundException(
                        String.format("Entity '%s' not found with propert%s {%s} = {%s}",
                                refEntities.isEmpty() ? "" : refEntities.get(0).getClass().getSimpleName(),
                                refEntityIdentifierProperties.size() > 1 ? "ies" : "y",
                                refEntityIdentifierProperties.stream().collect(Collectors.joining(", ")),
                                identifierValues.stream()
                                        .map(Object::toString)
                                        .map(s -> String.format("'%s'", s))
                                        .collect(Collectors.joining(", ")))
                );
            else {
                assert (resultNb == 1);
                PropertyUtils.setProperty(entity, outputProperty, plantIngredientsResult);
            }
        }
    }

    private List<PlantIngredient> buildPlantIngredientListFromPlantIngredient(PlantIngredients plantIngredients) {

        List<PlantIngredient> result = new ArrayList<>();

        // TODO to finish
        return result;
    }
}
