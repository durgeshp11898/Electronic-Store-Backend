package com.mc.electronic.store.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.lf5.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mc.electronic.store.dtos.ApiResponseMessage;
import com.mc.electronic.store.dtos.CategoryDTO;
import com.mc.electronic.store.dtos.ImageResponse;
import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.services.CategoryService;
import com.mc.electronic.store.services.FileService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	Logger logger = Logger.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FileService fileService;

	//Value comes from aplication.properties file
	@Value("${category.prfile.image.path}")
	private String ImageUploadPath;

	//Create a Category
	@PostMapping
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO savedCategory = this.categoryService.createCategory(categoryDTO);

		return new ResponseEntity<CategoryDTO>(savedCategory,HttpStatus.CREATED);
	}


	//update Category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable("categoryId")String categoryId){

		CategoryDTO updatedCategory = this.categoryService.updateCategory(categoryDTO,categoryId);

		return new ResponseEntity<CategoryDTO>(updatedCategory,HttpStatus.OK);
	}

	//Delete Category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("categoryId") String categoryId){

		this.categoryService.deleteCategory(categoryId);
		ApiResponseMessage response = ApiResponseMessage.builder().message("Category Deleted Successfull").httpStatus(HttpStatus.OK).success(true).build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}

	//get Single Category

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable String categoryId){

		CategoryDTO categoryDTO = this.categoryService.getSingleCategoryById(categoryId);

		return new ResponseEntity<CategoryDTO>(categoryDTO,HttpStatus.FOUND);
	}

	//getAll
	@GetMapping
	public ResponseEntity<PagableResponse<CategoryDTO>> getAllCategories(
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "categoryTitle",required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir

			){
		PagableResponse<CategoryDTO> categories = this.categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(categories,HttpStatus.OK);
	}


	//Search category By Title
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<CategoryDTO>> searchUsingCategoryTitle(@PathVariable String keywords){
		List<CategoryDTO> categories = this.categoryService.searchCategory(keywords);
		return new ResponseEntity<>(categories,HttpStatus.OK);
	}


	//Upload user Image 
	@PostMapping("/image/{categoryId}")
	public ResponseEntity<ImageResponse> uploadCategoryCoverImage(
			@PathVariable("categoryId") String categoryId,
			@RequestParam("categoryCoverImage") MultipartFile categoryCoverImage
			){


		String imageName=this.fileService.uploadFile(categoryCoverImage, ImageUploadPath);
		logger.info("Image name is {}"+imageName);
		logger.info("Image upload Path -->"+ImageUploadPath);

		CategoryDTO category = this.categoryService.getSingleCategoryById(categoryId);
		category.setCoverImage(imageName);
		CategoryDTO updatedCategory = this.categoryService.updateCategory(category, categoryId);
		logger.info("updated user is {} -->"+updatedCategory);

		ImageResponse imageResponse= ImageResponse.builder().imageName(imageName).httpStatus(HttpStatus.CREATED).success(true).build();

		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);


	}

	//Serve user Image
	@GetMapping("/image/{categoryId}")
	public void serveCategoryImage(@PathVariable("categoryId") String categoryId, HttpServletResponse httpServletResponse) throws IOException {
		CategoryDTO categoryDTO = this.categoryService.getSingleCategoryById(categoryId);
		logger.info("user image name-->{} "+categoryDTO.getCoverImage());

		InputStream inputStream = this.fileService.getResourceFile(ImageUploadPath, categoryDTO.getCoverImage());

		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());



	}


}
