package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Ethnology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Ethnology entity.
 */
public interface EthnologyRepository extends JpaRepository<Ethnology,Long> {

    List<Ethnology> findByPublicationId(Long id);

    // Other method to fetch the authors and compilers relationships, but as it's by a join, the firsResult and
    // maxResults are applied in memory
    //@Query("select distinct ethnology from Ethnology ethnology left join fetch ethnology.plantIngredients")
    //List<Ethnology> findAllWithEagerRelationships();
    //@Query("select ethnology from Ethnology ethnology  left join fetch ethnology.plantIngredients where ethnology
    // .id =:id")
    //Ethnology findOneWithEagerRelationships(@Param("id") Long id);

}
