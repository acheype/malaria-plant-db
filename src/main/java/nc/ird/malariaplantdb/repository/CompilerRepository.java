package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Compiler;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Compiler entity.
 */
public interface CompilerRepository extends JpaRepository<Compiler,Long> {

}
