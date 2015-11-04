package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.InVivoPharmaco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the InVivoPharmaco entity.
 */
public interface InVivoPharmacoRepository extends JpaRepository<InVivoPharmaco,Long> {

    // Other method to fetch the authors and compilers relationships, but as it's by a join, the firsResult and
    // maxResults are applied in memory
    //@Query("select distinct inVivoPharmaco from InVivoPharmaco inVivoPharmaco left join fetch inVivoPharmaco" +
    //    ".plantIngredients")
    //List<InVivoPharmaco> findAllWithEagerRelationships();
    //
    //@Query("select inVivoPharmaco from InVivoPharmaco inVivoPharmaco  left join fetch inVivoPharmaco" +
    //    ".plantIngredients where inVivoPharmaco.id =:id")
    //InVivoPharmaco findOneWithEagerRelationships(@Param("id") Long id);

}
