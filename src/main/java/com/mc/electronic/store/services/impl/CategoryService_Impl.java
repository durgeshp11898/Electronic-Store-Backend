package com.mc.electronic.store.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mc.electronic.store.dtos.CategoryDTO;
import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.entity.Category;
import com.mc.electronic.store.exceptions.ResourceNotFoundException;
import com.mc.electronic.store.helper.PagableHelper;
import com.mc.electronic.store.repositories.CategoryRepository;
import com.mc.electronic.store.services.CategoryService;

@Service
public class CategoryService_Impl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${category.prfile.image.path}")
	private String imagePath;

	Logger logger = Logger.getLogger(CategoryService_Impl.class);

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = this.modelMapper.map(categoryDTO, Category.class);
		String categoryId=UUID.randomUUID().toString();
		category.setCategoryId(categoryId);
		Category savedCategory = this.categoryRepository.save(category);
		logger.info("Category Saved Successfully  " +savedCategory.getCategoryTitle());

		return this.modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, String categoryId) {

		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Id is Not Found"));
		category.setCategoryDesc(categoryDTO.getCategoryDesc());
		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCoverImage(categoryDTO.getCoverImage());

		Category updatedCategory = this.categoryRepository.save(category);

		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(String categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Id is Not Found"));

		if(category!=null) {

			//delete user profile image
			String fullPath=imagePath+category.getCoverImage();
			logger.info("Image full Path"+fullPath);
			Path path= Paths.get(fullPath);

			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.info("file not found Exception : IOException");
				e.printStackTrace();
			}

			logger.info("Deleted user is -->"+category.toString());
			this.categoryRepository.delete(category);
		}
	}

@Override
public PagableResponse<CategoryDTO> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {

	//Sort sort = Sort.by(sortBy);
	Sort sort =(sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy)).ascending():(Sort.by(sortBy)).descending();
	Pageable pegeble = PageRequest.of(pageNumber, pageSize,sort);	
	Page<Category> page = this.categoryRepository.findAll(pegeble);

	PagableResponse<CategoryDTO> response = PagableHelper.getPagableResponse(page, CategoryDTO.class);

	return response;

}

@Override
public CategoryDTO getSingleCategoryById(String categoryId) {
	Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Id is Not Found"));
	return this.modelMapper.map(category, CategoryDTO.class);
}

@Override
public List<CategoryDTO> searchCategory(String keyword) {
	List<Category> categorys = this.categoryRepository.findBycategoryTitleContaining(keyword);
	List<CategoryDTO> dtoList = categorys.stream().map(category -> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
	logger.info("searched Categories are --> "+dtoList);
	return dtoList;
}

}
