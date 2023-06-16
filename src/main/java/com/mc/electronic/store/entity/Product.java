package com.mc.electronic.store.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

	@Id
	private String productId;
	
	@Column(unique = true)
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
