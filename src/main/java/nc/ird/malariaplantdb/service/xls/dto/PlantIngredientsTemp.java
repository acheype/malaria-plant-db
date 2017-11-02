package nc.ird.malariaplantdb.service.xls.dto;

import lombok.Data;
import nc.ird.malariaplantdb.domain.PlantIngredient;
import nc.ird.malariaplantdb.domain.Publication;

/**
 * Entity which represents a set of plant ingredients defined in a publication.
 * The entity will not be persist, but is useful to be referenced from the Ethnology, InVitroPharmaco, or
 * InVivoPharmaco entities.
 *
 * @author acheype
 */
@Data
public class PlantIngredientsTemp {

    private Publication publication;

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
