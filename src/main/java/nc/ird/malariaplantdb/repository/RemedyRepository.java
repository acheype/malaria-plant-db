package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Remedy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Remedy entity.
 */
public interface RemedyRepository extends JpaRepository<Remedy,Long> {

}
