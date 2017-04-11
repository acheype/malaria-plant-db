package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Species entity.
 */
public interface SpeciesRepository extends JpaRepository<Species,Long> {

}
