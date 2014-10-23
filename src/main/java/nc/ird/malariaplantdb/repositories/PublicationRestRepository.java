package nc.ird.malariaplantdb.repositories;

import nc.ird.malariaplantdb.entities.Publication;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

/**
 * Exposes JAX-RS read-only operations for the Publication entities.
 * <p>
 * Uses Spring DATA and a JAX-RS annotated base interface to automatically expose the service
 */
@Component
@Path("/publications")
public interface PublicationRestRepository extends ReadOnlyService<Publication, Long> {
}

