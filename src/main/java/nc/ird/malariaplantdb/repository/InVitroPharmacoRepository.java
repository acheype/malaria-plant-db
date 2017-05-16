package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.InVitroPharmaco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InVitroPharmaco entity.
 */
public interface InVitroPharmacoRepository extends JpaRepository<InVitroPharmaco,Long> {

    List<InVitroPharmaco> findByPublicationId(Long id);

    // Other method to fetch the authors and compilers relationships, but as it's by a join, the firsResult and
    // maxResults are applied in memory
    //@Query("select distinct inVitroPharmaco from InVitroPharmaco inVitroPharmaco  left join fetch inVitroPharmaco" +
    //    ".plantIngredients")
    //List<InVitroPharmaco> findAllWithEagerRelationships();
    //@Query("select inVitroPharmaco from InVitroPharmaco inVitroPharmaco  left join fetch inVitroPharmaco" +
    //    ".plantIngredients where inVitroPharmaco.id =:id")
    //InVitroPharmaco findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select distinct iv from InVitroPharmaco iv join iv.plantIngredients pi where iv.publication.id = :pubId and pi.id in :piIds")
    List<InVitroPharmaco> findByPublicationIdAndPlantIngredients(@Param("pubId") Long pubId, @Param("piIds") List<Long>
        piIds);

}
