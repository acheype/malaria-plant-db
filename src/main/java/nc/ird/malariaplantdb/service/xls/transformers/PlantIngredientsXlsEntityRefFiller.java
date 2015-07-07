package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.entities.PlantIngredient;
import nc.ird.malariaplantdb.service.xls.dto.PlantIngredients;
import nc.ird.malariaplantdb.service.xls.exceptions.XlsEntityNotFoundException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The annotation must be called with no entityRefIdentifierProperties parameter, and entityRefType =
 * PlantIngredients.class
 * <p>
 * If identifierValues is null, then set an empty plant ingredients list.
 * <p>
 * The string format is the followed : each species of the ingredient, a coma then its part used. For several
 * ingredients, each ingredient complete name must be separated by a slash (/). The format is space insensitive.
 * For instance, this string is correct : "Cochlospermum planchonii Hook.f. ex Planch., Root /
 * Senna alata (L.) Roxb., Leaf"
 */
@Service
public class PlantIngredientsXlsEntityRefFiller extends XlsEntityRefFiller {

    static final private String PLANT_INGREDIENTS_REGEXP = "([a-zA-ZÀ-ÿ &\\.\\-\\(\\)]+),([a-zA-ZÀ-ÿ \\-]+)/?";

    @Override
    public void fillPropertyWithRef(List<String> refEntityIdentifierProperties, List<?> identifierValues,
                                    List<?> refEntities, Object entity, String outputProperty) throws
            IllegalAccessException, NoSuchMethodException, InvocationTargetException, XlsEntityNotFoundException {

        List<PlantIngredient> plantIngredientsResult = new ArrayList<>();

        // as all the species and parts are in a single string, extract them
        String plantIngredientsStr = (String) identifierValues.get(0);

        if (!StringUtils.isEmpty(plantIngredientsStr)) {
            ArrayList<String> speciesStrList = new ArrayList<>();
            ArrayList<String> partsUsedList = new ArrayList<>();

            Pattern pattern = Pattern.compile(PLANT_INGREDIENTS_REGEXP);
            Matcher matcher = pattern.matcher(plantIngredientsStr);

            while (matcher.find()) {
                String species = matcher.group(1);
                String partUsed = matcher.group(2);

                speciesStrList.add(species.trim());
                partsUsedList.add(partUsed.trim());
            }

            findValues(refEntityIdentifierProperties, refEntities, speciesStrList, partsUsedList, plantIngredientsResult);

            if (plantIngredientsResult.isEmpty())
                throw new XlsEntityNotFoundException(
                        String.format("Entity '%s' not found with properties {%s} = {%s}",
                                refEntities.isEmpty() ? "" : refEntities.get(0).getClass().getSimpleName(),
                                IntStream.range(0, Math.max(speciesStrList.size(), partsUsedList.size()))
                                        .mapToObj(i -> refEntityIdentifierProperties.get(i) + ".species, " +
                                                refEntityIdentifierProperties.get(i) + ".partUsed")
                                        .collect(Collectors.joining(", ")),
                                IntStream.range(0, Math.max(speciesStrList.size(), partsUsedList.size()))
                                        .mapToObj(i -> "'" + speciesStrList.get(i) + "', '" + partsUsedList.get(i) +
                                                "'")
                                        .collect(Collectors.joining(", "))
                        )
                );
            else {
                PropertyUtils.setProperty(entity, outputProperty, plantIngredientsResult);
            }
        }
    }

    private void findValues(List<String> refEntityIdentifierProperties, List<?> refEntities, ArrayList<String> speciesStrList, ArrayList<String> partsUsedList, List<PlantIngredient> plantIngredientsResult) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        boolean isFound = false;
        for (Iterator<?> curRefEntityIt = refEntities.iterator(); curRefEntityIt.hasNext() && !isFound; ) {
            Object curRefEntity = curRefEntityIt.next();
            assert (curRefEntity instanceof PlantIngredients);
            PlantIngredients curPlantIngredients = (PlantIngredients) curRefEntity;
            List<PlantIngredient> plantIngredientList = new ArrayList<>();

            assert (speciesStrList.size() == partsUsedList.size());
            boolean isEqual = true;
            int i = 0;
            while (isEqual && i < refEntityIdentifierProperties.size()) {
                String curSpecies = i < speciesStrList.size() ? speciesStrList.get(i) : null;
                String curParted = i < partsUsedList.size() ? partsUsedList.get(i) : null;
                PlantIngredient refPlantIngredient = ((PlantIngredient) PropertyUtils.getProperty(curPlantIngredients,
                        refEntityIdentifierProperties.get(i)));
                plantIngredientList.add(refPlantIngredient);
                String refSpecies = refPlantIngredient != null && refPlantIngredient.getSpecies() != null ?
                        refPlantIngredient.getSpecies().getSpecies() : null;
                String refParted = refPlantIngredient != null ? refPlantIngredient.getPartUsed() : null;

                isEqual = Objects.equals(curSpecies, refSpecies) && Objects.equals(curParted, refParted);
                i++;
            }
            if (isEqual) {
                isFound = true;
                plantIngredientsResult.addAll(
                        plantIngredientList.stream()
                                .filter(pi -> (pi.getSpecies() != null && pi.getPartUsed() != null))
                                .collect(Collectors.toList())
                );
            }
        }
    }

}
