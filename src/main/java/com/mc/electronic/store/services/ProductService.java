package com.mc.electronic.store.services;



import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.dtos.ProductDTO;


public interface ProductService {

	ProductDTO createProduct(ProductDTO productDTO);

	ProductDTO updateProduct(ProductDTO productDTO,String productId);

	void deleteProduct(String productId);

	ProductDTO getSingleProduct(String productId);

	PagableResponse<ProductDTO> getAllProducts(int pageNumber, int pageSize,String sortBy,String sortDir);

	//Search User by Id
	PagableResponse<ProductDTO> searchProductByTitle(String subtitle,int pageNumber, int pageSize,String sortBy,String sortDir);

	PagableResponse<ProductDTO> getAllLiveProducts(int pageNumber, int pageSize,String sortBy,String sortDir);

	//Create product with Category
	ProductDTO createProductWithCategory(ProductDTO productDTO,String categoryId);

	//update category of product
	
	ProductDTO updateProductCategory(String productId,String categoryId);
	
	//get all of categories
	
	PagableResponse<ProductDTO> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
	

}
