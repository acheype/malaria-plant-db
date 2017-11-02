package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Remedy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Remedy entity.
 */
@Repository
public interface RemedyRepository extends JpaRepository<Remedy,Long> {

}
