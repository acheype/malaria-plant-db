package nc.ird.malariaplantdb.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
@Produces(MediaType.APPLICATION_JSON)
//@org.codehaus.enunciate.modules.jersey.ExternallyManagedLifecycle
public interface ReadOnlyService<T, ID extends Serializable> extends Repository<T, ID> {
    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public T findOne(@PathParam("id") ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, {@literal false} otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @HEAD
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean exists(@PathParam("id") ID id);

    /**
     * Returns a {@link org.springframework.data.domain.Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    //Page<T> findAll(@Context Pageable pageable);
    //public Iterable<T> findAll(@Context Pageable pageable);
    public List<T> findAll(@Context Pageable pageable);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    @GET
    @Path("/count")
    public long count();

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    public Iterable<T> findAll();

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    public Iterable<T> findAll(Iterable<ID> ids);

    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort
     * @return all entities sorted by the given options
     */
    public Iterable<T> findAll(Sort sort);
}
