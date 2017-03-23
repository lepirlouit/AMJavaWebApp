package be.pir.am.service;

import be.pir.am.api.dao.CategoryDao;
import be.pir.am.api.dao.FederationDao;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.api.service.CategoryService;
import be.pir.am.entities.CategoryEntity;
import be.pir.am.entities.FederationEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CategoryServiceImpl implements CategoryService {
    @EJB
    private CategoryDao categoryDao;
    @EJB
    private FederationDao federationDao;

    @Override
    public List<CategoryDto> getCategoriesForLbfa() {
        List<CategoryDto> returnedList = new ArrayList<>();
        FederationEntity lbfa = federationDao.getByCode("BEL_LBFA");
        List<CategoryEntity> findAllCategoriesForDisplay = categoryDao.findAllCategoriesForDisplay(lbfa);
        for (CategoryEntity categoryEntity : findAllCategoriesForDisplay) {
            CategoryDto category = createCategoryDto(categoryEntity);
            returnedList.add(category);
        }
        return returnedList;
    }

    CategoryDto createCategoryDto(CategoryEntity categoryEntity) {
        CategoryDto category = new CategoryDto();
        category.setAbbreviation(categoryEntity.getAbbreviation());
        category.setName(categoryEntity.getName());
        category.setId(categoryEntity.getId());
        category.setGender(categoryEntity.getGender());
        category.setMinimumAge(categoryEntity.getMinimumAge());
        category.setMaximumAge(categoryEntity.getMaximumAge());
        return category;
    }

}
