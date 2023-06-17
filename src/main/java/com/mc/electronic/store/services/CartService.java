package com.mc.electronic.store.services;

import com.mc.electronic.store.dtos.AddItemToCartRequest;
import com.mc.electronic.store.dtos.CartDTO;

public interface CartService {

	//add item to cart
	//Case1:cart for that user is not availble: we will create a cart and add that & add item
	//Case2: if cart availble: add items to the cart

	CartDTO addItemToCart(String userId,AddItemToCartRequest addItemToCartRequest);

	//Remove Item from Cart
	void removeItemFromCart(int cartItem);

	//Clear all cart

	void clearCart(String userId);

	CartDTO getCartByUser(String userId);
}
