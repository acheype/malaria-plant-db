package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.PubSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the PubSpecies entity.
 */
@Repository
public interface PubSpeciesRepository extends JpaRepository<PubSpecies,Long> {

    List<PubSpecies> findByPublicationId(Long id);

    @Query("select ps from PubSpecies ps, Remedy r join ps.species s join r.plantIngredients pi join pi.species s2 " +
        "where ps.publication.id = :pubId and s.id = s2.id and r.id = :remId")
    List<PubSpecies> findByPublicationIdAndRemedy(@Param("pubId") Long pubId, @Param("remId") Long remId);

    List<PubSpecies> findBySpeciesId(Long speciesId);

}
