package nc.ird.malariaplantdb.repositories;

import nc.ird.malariaplantdb.entities.Publication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

@Component
@Path("/publications")
public interface PubTestRepository extends CrudRepository<Publication, Long> {
}

