package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Ethnology;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Ethnology entity.
 */
public interface EthnologyRepository extends JpaRepository<Ethnology,Long> {

    @Query("select distinct ethnology from Ethnology ethnology left join fetch ethnology.plantIngredients")
    List<Ethnology> findAllWithEagerRelationships();

    @Query("select ethnology from Ethnology ethnology  left join fetch ethnology.plantIngredients where ethnology.id =:id")
    Ethnology findOneWithEagerRelationships(@Param("id") Long id);

}
