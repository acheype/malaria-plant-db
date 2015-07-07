package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.entities.PlantIngredient;

/**
 * Bundle of 10 Plant Ingredients
 *
 * @author acheype
 */
@Data
public class PlantIngredients {

    private PlantIngredient plantIngredient1 = new PlantIngredient();

    private PlantIngredient plantIngredient2 = new PlantIngredient();

    private PlantIngredient plantIngredient3 = new PlantIngredient();

    private PlantIngredient plantIngredient4 = new PlantIngredient();

    private PlantIngredient plantIngredient5 = new PlantIngredient();

    private PlantIngredient plantIngredient6 = new PlantIngredient();

    private PlantIngredient plantIngredient7 = new PlantIngredient();

    private PlantIngredient plantIngredient8 = new PlantIngredient();

    private PlantIngredient plantIngredient9 = new PlantIngredient();

    private PlantIngredient plantIngredient10 = new PlantIngredient();

}
