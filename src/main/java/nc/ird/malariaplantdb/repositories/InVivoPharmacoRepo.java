package nc.ird.malariaplantdb.repositories;

import nc.ird.malariaplantdb.entities.InVivoPharmaco;
import org.springframework.stereotype.Repository;

/**
 * Repository for the entity InVivoPharmaco
 *
 * @author acheype
 */
@Repository
public interface InVivoPharmacoRepo extends CrudRepo<InVivoPharmaco, Long> {

}
