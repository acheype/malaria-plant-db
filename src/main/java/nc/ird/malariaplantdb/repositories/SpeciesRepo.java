package nc.ird.malariaplantdb.repositories;

import nc.ird.malariaplantdb.entities.Compiler;
import nc.ird.malariaplantdb.entities.Species;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for the entity Species
 *
 * @author acheype
 */
@Repository
public interface SpeciesRepo extends CrudRepo<Species, Long> {

    List<Compiler> findByFamilyAndSpeciesAllIgnoreCase(String family, String species);

}
