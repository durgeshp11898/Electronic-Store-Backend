package com.mc.electronic.store.controllers;


import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.lf5.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.mc.electronic.store.dtos.ImageResponse;
import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.dtos.ProductDTO;
import com.mc.electronic.store.services.FileService;
import com.mc.electronic.store.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private FileService fileService;

	//Value comes from aplication.properties file
	@Value("${product.prfile.image.path}")
	private String productImageUploadPath;


	//Create a Product
	@PostMapping
	public ResponseEntity<ProductDTO > createProduct(@Valid @RequestBody ProductDTO productDTO){
		ProductDTO createdProduct = this.productService.createProduct(productDTO);
		logger.info("Product Created with Title -- "+createdProduct.getProductTitle());
		return new ResponseEntity<ProductDTO>(createdProduct,HttpStatus.CREATED);
	}

	//update Product
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,@PathVariable("productId")String productId){

		ProductDTO updatedProduct = this.productService.updateProduct(productDTO, productId);
		logger.info("Product Updated with Title - "+updatedProduct.getProductTitle());
		return new ResponseEntity<ProductDTO>(updatedProduct,HttpStatus.OK);
	}

	//Delete Product
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable("productId") String productId){

		this.productService.deleteProduct(productId);
		logger.info("Product Deleted successfully");
		ApiResponseMessage response = ApiResponseMessage.builder().message("Product Deleted Successfull").httpStatus(HttpStatus.OK).success(true).build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}

	//get Single Product
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDTO> getSingleProduct(@PathVariable String productId){

		ProductDTO productDTO= this.productService.getSingleProduct(productId);
		logger.info("Get Single Product with Title - "+productDTO.getProductTitle());
		return new ResponseEntity<ProductDTO>( productDTO ,HttpStatus.FOUND);
	}

	//Get All Products
	@GetMapping
	public ResponseEntity<PagableResponse<ProductDTO>> getAllProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "productTitle",required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir

			){
		PagableResponse<ProductDTO> products = this.productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(products,HttpStatus.OK);
	}

	//Get all Live Products
	@GetMapping("/live")
	public ResponseEntity<PagableResponse<ProductDTO>> getAllLiveProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "productTitle",required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir
			){

		PagableResponse<ProductDTO> products = this.productService.getAllLiveProducts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(products,HttpStatus.OK);
	}

	//Search Product using title
	@GetMapping("/search/{subtitle}")
	public ResponseEntity<PagableResponse<ProductDTO>> getSearchUser(
			@PathVariable String subtitle, 
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "productTitle",required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir
			){

		PagableResponse<ProductDTO> products = this.productService.searchProductByTitle(subtitle,pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	//Upload user Image 
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadProductImage(
			@PathVariable("productId") String productId,
			@RequestParam("productImage") MultipartFile productImage
			){


		String imageName=this.fileService.uploadFile(productImage, productImageUploadPath);
		logger.info("Image name is {}"+imageName);
		logger.info("Image upload Path -->"+productImageUploadPath);

		ProductDTO product = this.productService.getSingleProduct(productId);

		product.setProductImage(imageName);
		ProductDTO updatedproductDTO = this.productService.updateProduct(product, productId);
		logger.info("updated Product is {} -->"+updatedproductDTO);

		ImageResponse imageResponse= ImageResponse.builder().imageName(imageName).httpStatus(HttpStatus.CREATED).success(true).build();

		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);


	}

	//Serve user Image
	@GetMapping("/image/{productId}")
	public void serveProductImage(@PathVariable("productId") String productId, HttpServletResponse httpServletResponse) throws IOException {
		ProductDTO productDTO= this.productService.getSingleProduct(productId);
		logger.info("user image name-->{} "+productDTO.getProductImage());

		InputStream inputStream = this.fileService.getResourceFile(productImageUploadPath, productDTO.getProductImage());

		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());

	}


}
