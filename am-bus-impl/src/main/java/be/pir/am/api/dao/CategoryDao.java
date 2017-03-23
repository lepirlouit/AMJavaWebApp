package be.pir.am.api.dao;

import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.FederationEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CategoryDao extends EntityDao<CategoryEntity> {
    List<CategoryEntity> findCategoriesByGenderFederationAndAge(Character gender, FederationEntity federation, short age);

    List<CategoryEntity> findAllCategoriesForDisplay(FederationEntity federation);
}
