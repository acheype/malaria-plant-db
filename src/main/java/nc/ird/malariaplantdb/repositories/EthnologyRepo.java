package nc.ird.malariaplantdb.repositories;

import nc.ird.malariaplantdb.entities.Ethnology;
import org.springframework.stereotype.Repository;

/**
 * Repository for the entity Ethnology
 *
 * @author acheype
 */
@Repository
public interface EthnologyRepo extends CrudRepo<Ethnology, Long> {

}
