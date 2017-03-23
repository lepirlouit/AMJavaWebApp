package be.pir.am.dao;

import be.pir.am.api.dao.CategoryDao;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.FederationEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Stateless
public class CategoryDaoImpl extends AbstractEntityDao<CategoryEntity> implements CategoryDao {

    public CategoryDaoImpl() {
        super(CategoryEntity.class);
    }

    @Override
    public List<CategoryEntity> findCategoriesByGenderFederationAndAge(Character gender, FederationEntity federation,
                                                                       short age) {
        List<Character> genders;
        if (Character.valueOf('W').equals(gender) || Character.valueOf('F').equals(gender)) {
            genders = Arrays.asList('W', 'F');
        } else {
            genders = Collections.singletonList(gender);
        }

        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<CategoryEntity> cq = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> category = cq.from(CategoryEntity.class);
        cq.where(category.get("gender").in(genders), cb.equal(category.get("federation"), federation),
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
        cq.orderBy(cb.asc(category.get("minimumAge")), cb.asc(category.get("gender")));
        return em.createQuery(cq).getResultList();
    }
}
