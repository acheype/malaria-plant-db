package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Publication entity.
 */
public interface PublicationRepository extends JpaRepository<Publication,Long> {

    // Other method to fetch the authors and compilers relationships, but as it's by a join, the firsResult and
    // maxResults are applied in memory
    //@Query(value = "select distinct publication from Publication publication left join fetch publication.authors " +
    //    "left join fetch publication.compilers",
    //        countQuery = "select count(publication) from Publication publication")
    //Page<Publication> findAllWithEagerRelationships(Pageable page);

}
