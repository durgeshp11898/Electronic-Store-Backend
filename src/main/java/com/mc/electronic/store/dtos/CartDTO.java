package com.mc.electronic.store.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class CartDTO {

	private String cartId;

	private Date createdAt;

	private UserDTO user;
	//MApping cart item Entties
	//One cart has many items
	private List<CartItemDTO> cartItems= new ArrayList<>();
}
