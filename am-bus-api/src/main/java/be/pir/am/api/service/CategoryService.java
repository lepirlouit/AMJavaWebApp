package be.pir.am.api.service;

import java.util.List;

import javax.ejb.Local;

import be.pir.am.api.dto.CategoryDto;

@Local
public interface CategoryService {
	List<CategoryDto> getCategoriesForLbfa();

}
