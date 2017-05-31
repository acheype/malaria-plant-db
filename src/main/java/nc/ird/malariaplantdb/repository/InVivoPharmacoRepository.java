package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.InVivoPharmaco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InVivoPharmaco entity.
 */
public interface InVivoPharmacoRepository extends JpaRepository<InVivoPharmaco,Long> {

    List<InVivoPharmaco> findByPublicationId(Long id);

    // Other method to fetch the authors and compilers relationships, but as it's by a join, the firsResult and
    // maxResults are applied in memory
    //@Query("select distinct inVivoPharmaco from InVivoPharmaco inVivoPharmaco left join fetch inVivoPharmaco" +
    //    ".plantIngredients")
    //List<InVivoPharmaco> findAllWithEagerRelationships();
    //
    //@Query("select inVivoPharmaco from InVivoPharmaco inVivoPharmaco  left join fetch inVivoPharmaco" +
    //    ".plantIngredients where inVivoPharmaco.id =:id")
    //InVivoPharmaco findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select iv from InVivoPharmaco iv join iv.remedy r where iv.publication.id = :pubId " +
        "and r.id = :remId")
    List<InVivoPharmaco> findByPublicationIdAndRemedy(@Param("pubId") Long pubId, @Param("remId") Long remId);

}
