package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.InVitroPharmaco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the InVitroPharmaco entity.
 */
public interface InVitroPharmacoRepository extends JpaRepository<InVitroPharmaco,Long> {

    // Other method to fetch the authors and compilers relationships, but as it's by a join, the firsResult and
    // maxResults are applied in memory
    //@Query("select distinct inVitroPharmaco from InVitroPharmaco inVitroPharmaco  left join fetch inVitroPharmaco" +
    //    ".plantIngredients")
    //List<InVitroPharmaco> findAllWithEagerRelationships();
    //@Query("select inVitroPharmaco from InVitroPharmaco inVitroPharmaco  left join fetch inVitroPharmaco" +
    //    ".plantIngredients where inVitroPharmaco.id =:id")
    //InVitroPharmaco findOneWithEagerRelationships(@Param("id") Long id);

}
