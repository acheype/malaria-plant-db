package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.PubSpecies;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PubSpecies entity.
 */
public interface PubSpeciesRepository extends JpaRepository<PubSpecies,Long> {

}
