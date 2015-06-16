package nc.ird.malariaplantdb.repositories;

import nc.ird.malariaplantdb.entities.Compiler;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for the entity Compiler
 *
 * @author acheype
 */
@Repository
public interface CompilerRepo extends CrudRepo<Compiler, Long> {

    List<Compiler> findByFamilyAndGivenAllIgnoreCase(String family, String given);

}
