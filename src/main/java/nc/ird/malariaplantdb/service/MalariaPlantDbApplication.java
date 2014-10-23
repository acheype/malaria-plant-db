package nc.ird.malariaplantdb.service;

import nc.ird.malariaplantdb.repositories.PublicationRestRepository;
import nc.ird.malariaplantdb.util.CORSResponseFilter;
import nc.ird.malariaplantdb.util.LoggingResponseFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Registers the components to be used by the JAX-RS application
 *
 * @author acheype
 */
public class MalariaPlantDbApplication extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public MalariaPlantDbApplication() {
        register(RequestContextFilter.class);
        register(PublicationRestRepository.class);
        register(JacksonFeature.class);
        register(LoggingResponseFilter.class);
        register(CORSResponseFilter.class);
    }
}
