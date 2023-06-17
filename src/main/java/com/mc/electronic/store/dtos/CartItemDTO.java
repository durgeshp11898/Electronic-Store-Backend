package com.mc.electronic.store.dtos;



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
public class CartItemDTO {

	private int cartItemId;
	private ProductDTO product;
	private int quantity;
	private int totalPrice;



}
