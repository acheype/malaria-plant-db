package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Ethnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Ethnology entity.
 */
@Repository
public interface EthnologyRepository extends JpaRepository<Ethnology,Long> {

    List<Ethnology> findByPublicationId(Long id);

    // Other method to fetch the authors and compilers relationships, but as it's by a join, the firsResult and
    // maxResults are applied in memory
    //@Query("select distinct ethnology from Ethnology ethnology left join fetch ethnology.plantIngredients")
    //List<Ethnology> findAllWithEagerRelationships();
    //@Query("select ethnology from Ethnology ethnology  left join fetch ethnology.plantIngredients where ethnology
    // .id =:id")
    //Ethnology findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select e from Ethnology e join e.remedy r where e.publication.id = :pubId and r.id = :remId")
    Ethnology findByPublicationIdAndAndRemedyId(@Param("pubId") Long pubId, @Param("remId") Long remId);

    List<Ethnology> findByRemedyId(Long remedyId);

}
