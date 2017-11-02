package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Species entity.
 */
@Repository
public interface SpeciesRepository extends JpaRepository<Species,Long> {

    Optional<Species> findByFamilyAndSpeciesAllIgnoreCase(String family, String species);

}
