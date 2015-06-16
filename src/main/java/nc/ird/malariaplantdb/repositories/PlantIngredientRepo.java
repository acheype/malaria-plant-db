package nc.ird.malariaplantdb.repositories;

import nc.ird.malariaplantdb.entities.PlantIngredient;
import org.springframework.stereotype.Repository;

/**
 * Repository for the entity PlantIngredient
 *
 * @author acheype
 */
@Repository
public interface PlantIngredientRepo extends CrudRepo<PlantIngredient, Long> {

}
