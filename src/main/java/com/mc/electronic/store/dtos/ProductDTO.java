package com.mc.electronic.store.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {


	private String productId;

	@Size(min=3,max = 100,message = "Product title in between 3 to 100 characters")
	private String productTitle;

	@Size(min = 5, max = 20000, message = "Product Description in between 5 to 20000")
	private String productDesc;

	
	private int productOriginolPrice;
	
	private int productDiscountedPrice;
	
	private int productQuantity;
	
	private Date productAddedDate;
	
	private boolean  isProductLive;
	private boolean isStockAvailble;
	
	private String productImage;


}
