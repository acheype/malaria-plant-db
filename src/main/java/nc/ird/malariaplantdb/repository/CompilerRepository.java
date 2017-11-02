package nc.ird.malariaplantdb.repository;

import nc.ird.malariaplantdb.domain.Compiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Compiler entity.
 */
@Repository
public interface CompilerRepository extends JpaRepository<Compiler,Long> {

    List<Compiler> findByFamilyAndGivenAllIgnoreCase(String family, String given);

}
