package be.pir.am.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import be.pir.am.api.dao.CategoryDao;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.FederationEntity;

@Stateless
public class CategoryDaoImpl extends AbstractEntityDao<CategoryEntity> implements CategoryDao {

	public CategoryDaoImpl() {
		super(CategoryEntity.class);
	}

	@Override
	public List<CategoryEntity> findCategoriesByGenderFederationAndAge(Character gender, FederationEntity federation,
			short age) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<CategoryEntity> cq = cb.createQuery(CategoryEntity.class);
		Root<CategoryEntity> category = cq.from(CategoryEntity.class);
		cq.where(cb.equal(category.get("gender"), gender), cb.equal(category.get("federation"), federation),
				cb.lessThanOrEqualTo(category.get("minimumAge"), age),
				cb.greaterThanOrEqualTo(category.get("maximumAge"), age), cb.notEqual(category.get("maximumAge"), 0));
		return em.createQuery(cq).getResultList();
	}

	@Override
	public List<CategoryEntity> findAllCategoriesForDisplay(FederationEntity federation) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<CategoryEntity> cq = cb.createQuery(CategoryEntity.class);
		Root<CategoryEntity> category = cq.from(CategoryEntity.class);
		cq.where(cb.equal(category.get("federation"), federation),
				cb.or(cb.like(category.get("name"), "% (M)"), cb.like(category.get("name"), "% (F)")),
				cb.notLike(category.get("abbreviation"), "TC%"));
		return em.createQuery(cq).getResultList();
	}
}
