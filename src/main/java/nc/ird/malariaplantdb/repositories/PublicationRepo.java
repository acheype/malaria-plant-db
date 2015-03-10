package nc.ird.malariaplantdb.repositories;

import nc.ird.malariaplantdb.entities.Publication;
import org.springframework.stereotype.Repository;

/**
 * Repository for the entity Publication
 *
 * @author acheype
 */
@Repository
public interface PublicationRepo extends CrudRepo<Publication, Long> {

}
