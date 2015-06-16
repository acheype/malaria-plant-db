package nc.ird.malariaplantdb.repositories;

import nc.ird.malariaplantdb.entities.Clinical;
import org.springframework.stereotype.Repository;

/**
 * Repository for the entity Clinical
 *
 * @author acheype
 */
@Repository
public interface ClinicalRepo extends CrudRepo<Clinical, Long> {

}
