package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.InVitroPharmaco;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InVitroPharmaco entity.
 */
public interface InVitroPharmacoRepository extends JpaRepository<InVitroPharmaco,Long> {

    @Query("select distinct inVitroPharmaco from InVitroPharmaco inVitroPharmaco  left join fetch inVitroPharmaco.plantIngredients")
    List<InVitroPharmaco> findAllWithEagerRelationships();

    @Query("select inVitroPharmaco from InVitroPharmaco inVitroPharmaco  left join fetch inVitroPharmaco.plantIngredients where inVitroPharmaco.id =:id")
    InVitroPharmaco findOneWithEagerRelationships(@Param("id") Long id);

}
