package nc.ird.malariaplantdb.service.xls.transformers;

import nc.ird.malariaplantdb.domain.PlantIngredient;
import nc.ird.malariaplantdb.domain.Remedy;
import nc.ird.malariaplantdb.service.xls.dto.PlantIngredientsTemp;
import nc.ird.malariaplantdb.service.xls.exceptions.ImportRuntimeException;
import nc.ird.malariaplantdb.service.xls.structures.ClassMap;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Special process to transform the PlantIngredientsTemp in Remedy and refer them in the entities map.
 */
public class RemedyEntitiesTransformer extends EntitiesTransformer {

    static final private String[] PLANT_INGREDIENTS_PROPERTIES = {"plantIngredient1", "plantIngredient2", "plantIngredient3",
        "plantIngredient4", "plantIngredient5", "plantIngredient6", "plantIngredient7", "plantIngredient8",
        "plantIngredient9", "plantIngredient10"};

    @Override
    public void transformEntities(ClassMap entitiesMap) {
        List<PlantIngredientsTemp> plantIngredientsTempList = entitiesMap.getList(PlantIngredientsTemp.class);

        ArrayList<Remedy> remedies = new ArrayList<>();
        ArrayList<PlantIngredient> plantIngredients = new ArrayList<>();
        for (PlantIngredientsTemp plantIngredientsTemp : plantIngredientsTempList) {
            Remedy remedy = new Remedy();
            remedy.setPlantIngredients(buildPlantIngredientsSet(plantIngredientsTemp, remedy));
            remedies.add(remedy);
            remedy.getPlantIngredients().forEach(plantIngredients::add);
            // save the remedy for the references from other entities
            plantIngredientsTemp.setRemedy(remedy);
        }
        entitiesMap.putList(PlantIngredient.class, plantIngredients);
        entitiesMap.putList(Remedy.class, remedies);
    }

    private TreeSet<PlantIngredient> buildPlantIngredientsSet(PlantIngredientsTemp plantIngredientsTemp, Remedy
        remedy) {
        TreeSet<PlantIngredient> plantIngredientsSet = new TreeSet<>();

        for (int i = 0; i < 10; i++) {
            try {
                PlantIngredient plantIngredient = (PlantIngredient) PropertyUtils.getProperty(plantIngredientsTemp,
                    PLANT_INGREDIENTS_PROPERTIES[i]);
                // insert when the properties of the plant ingredient are defined and when an already plant ingredient
                // with the same properties doesn't exist
                if (plantIngredient.getSpecies() != null && plantIngredient.getPartUsed() != null){
//                    !plantIngredientsSet.contains(plantIngredient)) {
                    // attach the plant ingredient to the remedy
                    plantIngredient.setRemedy(remedy);
                    plantIngredientsSet.add(plantIngredient);
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                IllegalArgumentException e) {
                throw new ImportRuntimeException("An unexpected errors occurs during saving the entities " +
                    "in the database", e);
            }
        }
        return plantIngredientsSet;
    }

}
