package com.mc.electronic.store.services;

import java.util.List;

import com.mc.electronic.store.dtos.CategoryDTO;
import com.mc.electronic.store.dtos.PagableResponse;

public interface CategoryService {

	//create
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	//update
	CategoryDTO updateCategory(CategoryDTO categoryDTO,String categoryId);
	
	//delete
	void deleteCategory(String categoryId);
	
	//getall
	PagableResponse<CategoryDTO> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//get single 
	CategoryDTO getSingleCategoryById(String categoryId);
	
	//search via name
	List<CategoryDTO> searchCategory(String keyword);
	
	
}
