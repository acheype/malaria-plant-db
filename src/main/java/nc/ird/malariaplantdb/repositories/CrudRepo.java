package nc.ird.malariaplantdb.repositories;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Customized repository structure for create-read-update-delete operations
 *
 * @param <T>  the domain type the repository manages
 * @param <ID> the type of the id of the entity the repository manages
 * @author acheype
 */
@NoRepositoryBean
public interface CrudRepo<T, ID extends Serializable> extends ReadOnlyRepo<T, ID> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity
     * @return the saved entity
     */
    public T save(T entity);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    public void delete(ID id);

    /**
     * Saves all given entities.
     *
     * @param entities
     * @return the saved entities
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    public <S extends T> Iterable<S> save(Iterable<S> entities);

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    public void delete(T entity);

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    public void delete(Iterable<? extends T> entities);

}
