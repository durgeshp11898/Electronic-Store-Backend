package com.mc.electronic.store.services.impl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.dtos.ProductDTO;
import com.mc.electronic.store.entity.Product;
import com.mc.electronic.store.exceptions.ResourceNotFoundException;
import com.mc.electronic.store.helper.PagableHelper;
import com.mc.electronic.store.repositories.ProductRepository;
import com.mc.electronic.store.services.ProductService;

@Service
public class ProductService_Impl implements ProductService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepository productRepository;

	@Value("${product.prfile.image.path}")
	private String imagePath;
	Logger logger = Logger.getLogger(ProductService_Impl.class);

	@Override
	public ProductDTO createProduct(ProductDTO productDTO) {
		Product product = this.modelMapper.map(productDTO, Product.class);
		String productId = UUID.randomUUID().toString();
		product.setProductAddedDate(new Date());
		product.setProductId(productId);
		Product savedProduct = this.productRepository.save(product);
		logger.info("Product has been saved with Title{} "+savedProduct.getProductTitle());
		return this.modelMapper.map(savedProduct, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO, String productId) {
		logger.info("Update Product Function Invoked ");
		Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

		product.setProductAddedDate(productDTO.getProductAddedDate());
		product.setProductDesc(productDTO.getProductDesc());
		product.setProductDiscountedPrice(productDTO.getProductDiscountedPrice());
		product.setProductLive(productDTO.isProductLive());
		product.setProductOriginolPrice(productDTO.getProductOriginolPrice());
		product.setProductQuantity(productDTO.getProductQuantity());
		product.setProductTitle(productDTO.getProductTitle());
		product.setStockAvailble(productDTO.isStockAvailble());
		product.setProductImage(productDTO.getProductImage());

		Product updatedProduct = this.productRepository.save(product);
		logger.info("updated product with Title-->  "+updatedProduct.getProductTitle());
		return this.modelMapper.map(updatedProduct, ProductDTO.class);
	}

	@Override
	public void deleteProduct(String productId) {
		logger.info("Deleted Product Function Invoked ");
		Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
		if(product !=null) {
			logger.info("Product Deleted with Title --> "+product.getProductTitle());

			//delete user profile image
			String fullPath=imagePath+product.getProductImage();
			logger.info("Image full Path"+fullPath);
			Path path= Paths.get(fullPath);

			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.info("file not found Exception : IOException");
				e.printStackTrace();
			}

			logger.info("Deleted user is -->"+product.toString());
			this.productRepository.delete(product);
		}

	}

	@Override
	public ProductDTO getSingleProduct(String productId) {
		logger.info("Single Product Product Function Invoked ");
		Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

		return this.modelMapper.map(product, ProductDTO.class);
	}

	@Override
	public PagableResponse<ProductDTO> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
		//Sort sort = Sort.by(sortBy);
		Sort sort =(sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy)).ascending():(Sort.by(sortBy)).descending();
		Pageable pegeble = PageRequest.of(pageNumber, pageSize,sort);	
		Page<Product> page = this.productRepository.findAll(pegeble);

		PagableResponse<ProductDTO> response = PagableHelper.getPagableResponse(page, ProductDTO.class);
		return response;

	}

	@Override
	public PagableResponse<ProductDTO> searchProductByTitle(String subtitle, int pageNumber, int pageSize,
			String sortBy, String sortDir) {

		Sort sort =(sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy)).ascending():(Sort.by(sortBy)).descending();
		Pageable pegeble = PageRequest.of(pageNumber, pageSize,sort);	
		Page<Product> page = this.productRepository.findByproductTitleContaining(subtitle,pegeble);

		PagableResponse<ProductDTO> response = PagableHelper.getPagableResponse(page, ProductDTO.class);
		return response;
	}

	@Override
	public PagableResponse<ProductDTO> getAllLiveProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort =(sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy)).ascending():(Sort.by(sortBy)).descending();
		Pageable pegeble = PageRequest.of(pageNumber, pageSize,sort);	
		Page<Product> page = this.productRepository.findByisProductLiveTrue(pegeble);

		PagableResponse<ProductDTO> response = PagableHelper.getPagableResponse(page, ProductDTO.class);
		return response;
	}



}
