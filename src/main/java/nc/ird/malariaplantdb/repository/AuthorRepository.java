package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Author entity.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

}
