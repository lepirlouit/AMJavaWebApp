package be.pir.am.api.dao;

import java.util.List;

import javax.ejb.Local;

import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.FederationEntity;

@Local
public interface CategoryDao extends EntityDao<CategoryEntity> {
	List<CategoryEntity> findCategoriesByGenderFederationAndAge(Character gender, FederationEntity federation, short age);
	List<CategoryEntity> findAllCategoriesForDisplay(FederationEntity federation);
}
