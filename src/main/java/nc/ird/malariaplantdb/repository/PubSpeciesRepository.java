package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.PubSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PubSpecies entity.
 */
public interface PubSpeciesRepository extends JpaRepository<PubSpecies,Long> {

    List<PubSpecies> findByPublicationId(Long id);

    @Query("select ps from PubSpecies ps, PlantIngredient pi join ps.species s join pi.species s2 where ps.publication.id = :pubId and s.id = s2.id and pi.id in :piIds")
    List<PubSpecies> findByPublicationIdAndPlantIngredients(@Param("pubId") Long pubId, @Param("piIds") List<Long>
        piIds);

}
