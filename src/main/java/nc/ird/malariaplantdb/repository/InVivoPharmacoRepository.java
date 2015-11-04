package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.InVivoPharmaco;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InVivoPharmaco entity.
 */
public interface InVivoPharmacoRepository extends JpaRepository<InVivoPharmaco,Long> {

    @Query("select distinct inVivoPharmaco from InVivoPharmaco inVivoPharmaco left join fetch inVivoPharmaco.plantIngredients")
    List<InVivoPharmaco> findAllWithEagerRelationships();

    @Query("select inVivoPharmaco from InVivoPharmaco inVivoPharmaco  left join fetch inVivoPharmaco.plantIngredients where inVivoPharmaco.id =:id")
    InVivoPharmaco findOneWithEagerRelationships(@Param("id") Long id);

}