package be.pir.am.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import be.pir.am.api.dao.EntityDao;
import be.pir.am.entities.BaseEntity;

/**
 * AbstractEntityRepository
 *
 * @author 7518305
 * @version %PR%
 * @since 21/08/13 - 10:50
 */
public abstract class AbstractEntityDao<T extends BaseEntity> implements EntityDao<T> {

	@PersistenceContext(unitName = "am-jta")
	private EntityManager entityManager;

	private Class<T> entityClass;

	/**
	 * Class constructor
	 * @param clazz the entity type
	 */
	public AbstractEntityDao(Class<T> clazz) {
		this.entityClass = clazz;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save(T entity) {

		if (entity.getSince() == null) {
			entity.setSince(new Date());
		}
		this.getEntityManager().persist(entity);

	}

	@Override
	public void delete(T entity) {
		this.getEntityManager().remove(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findById(Number identifier) {
		return this.getEntityManager().find(entityClass, identifier);
	}

	/**
	 * return all
	 *
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<T> findAll() {
		return getEntityManager().createQuery("Select e from " + entityClass.getSimpleName() + " e").getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T update(T entity) {

		return this.getEntityManager().merge(entity);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
