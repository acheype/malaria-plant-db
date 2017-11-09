package nc.ird.malariaplantdb.service.xls.dto;

import groovy.transform.ToString;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nc.ird.malariaplantdb.domain.PlantIngredient;
import nc.ird.malariaplantdb.domain.Publication;
import nc.ird.malariaplantdb.domain.Species;

/**
 * Entity which represents a set of plant ingredients defined in a publication.
 * The entity will not be persist, but is useful to be referenced from the Ethnology, InVitroPharmaco, or
 * InVivoPharmaco entities.
 *
 * @author acheype
 */
@Getter
@Setter
@ToString
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

    public PlantIngredientsTemp() {
        plantIngredient1 = new PlantIngredient();
        plantIngredient1.setSpecies(new Species());
        plantIngredient2 = new PlantIngredient();
        plantIngredient2.setSpecies(new Species());
        plantIngredient3 = new PlantIngredient();
        plantIngredient3.setSpecies(new Species());
        plantIngredient4 = new PlantIngredient();
        plantIngredient4.setSpecies(new Species());
        plantIngredient5 = new PlantIngredient();
        plantIngredient5.setSpecies(new Species());
        plantIngredient6 = new PlantIngredient();
        plantIngredient6.setSpecies(new Species());
        plantIngredient7 = new PlantIngredient();
        plantIngredient7.setSpecies(new Species());
        plantIngredient8 = new PlantIngredient();
        plantIngredient8.setSpecies(new Species());
        plantIngredient9 = new PlantIngredient();
        plantIngredient9.setSpecies(new Species());
        plantIngredient10 = new PlantIngredient();
        plantIngredient10.setSpecies(new Species());
    }
}
