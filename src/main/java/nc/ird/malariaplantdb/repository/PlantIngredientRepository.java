package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.PlantIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlantIngredient entity.
 */
@Repository
public interface PlantIngredientRepository extends JpaRepository<PlantIngredient,Long> {

}
