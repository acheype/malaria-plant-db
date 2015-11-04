package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.PlantIngredient;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PlantIngredient entity.
 */
public interface PlantIngredientRepository extends JpaRepository<PlantIngredient,Long> {

}
