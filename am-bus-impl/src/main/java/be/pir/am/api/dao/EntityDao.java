package be.pir.am.api.dao;

import java.util.List;

import be.pir.am.entities.BaseEntity;
import java.lang.Number;

/**
 *
 * EntityRepository
 * @param <T> The entity type
 *
 * @author 7518305 - Suederick Ralph
 * @version %PR%
 */
public interface EntityDao<T extends BaseEntity> {

	/**
	 * Saves the entity
	 * @param entity the entity to save
	 */
	void save(T entity);

	/**
	 * find the entity by the given identifier
	 * @param identifier the identifier for the entity
	 * @return T the entity matching the given identifier
	 */
	T findById(Number identifier);

	/**
	 * updates the given entity
	 * @param entity the entity to update
	 * @return the updated entity
	 */
	T update(T entity);

	/**
	 * finds all of the entities of type T
	 * @return List of entities
	 */
	List<T> findAll();

}
