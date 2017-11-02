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
        List<PlantIngredientsTemp> plantIngredientsTemp = entitiesMap.getList(PlantIngredientsTemp.class);

        TreeSet<PlantIngredient> plantIngredients = buildPlantIngredientsSet(plantIngredientsTemp);
        entitiesMap.putList(PlantIngredient.class, new ArrayList<PlantIngredient>(plantIngredients));

        ArrayList<Remedy> remedies = new ArrayList<>();
        for (PlantIngredient pi : plantIngredients){
            Remedy remedy = new Remedy();
            remedy.setPlantIngredients(plantIngredients);
            remedies.add(remedy);
        }
        entitiesMap.putList(Remedy.class, remedies);
    }

    private TreeSet<PlantIngredient> buildPlantIngredientsSet(List<PlantIngredientsTemp> plantIngredientsTemp) {
        TreeSet<PlantIngredient> plantIngredientsSet = new TreeSet<>();
        for (PlantIngredientsTemp plantIngredients : plantIngredientsTemp) {
            for (int i = 0; i < 10; i++) {
                try {
                    PlantIngredient plantIngredient = (PlantIngredient) PropertyUtils.getProperty(plantIngredients,
                        PLANT_INGREDIENTS_PROPERTIES[i]);
                    if (plantIngredient.getSpecies() != null && plantIngredient.getPartUsed() != null)
                        plantIngredientsSet.add(plantIngredient);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                    IllegalArgumentException e) {
                    throw new ImportRuntimeException("An unexpected errors occurs during saving the entities " +
                        "in the database", e);
                }
            }
        }
        return plantIngredientsSet;
    }

}
