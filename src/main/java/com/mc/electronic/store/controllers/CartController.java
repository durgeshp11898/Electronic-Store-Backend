package com.mc.electronic.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.mc.electronic.store.dtos.AddItemToCartRequest;
import com.mc.electronic.store.dtos.ApiResponseMessage;
import com.mc.electronic.store.dtos.CartDTO;
import com.mc.electronic.store.services.CartService;


@RestController
@RequestMapping("/carts")
public class CartController{

	@Autowired
	private CartService cartService;

	//Add Items to Cart
	@PostMapping("/{userId}")
	public ResponseEntity<CartDTO> addItemToCart(@RequestBody AddItemToCartRequest addItemToCartRequest,@PathVariable String userId){

		CartDTO cartDTO = this.cartService.addItemToCart(userId, addItemToCartRequest);

		return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.CREATED);
	}


	//Remove Item From Cart
	@DeleteMapping("/items/{itemId}")
	public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable int itemId){

		this.cartService.removeItemFromCart(itemId);
		ApiResponseMessage apiResponseMessage= ApiResponseMessage.builder().message("Item is Removed !!..").success(true).httpStatus(HttpStatus.OK).build();


		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage,HttpStatus.OK);
	}



	//Clear Cart

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){

		this.cartService.clearCart(userId);
		ApiResponseMessage apiResponseMessage= ApiResponseMessage.builder().message("Cart is cleared!!..").success(true).httpStatus(HttpStatus.OK).build();

		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage,HttpStatus.OK);
	}

	//get cart
	@GetMapping("/{userId}")
	public ResponseEntity<CartDTO> getItemFromCart(@PathVariable String userId){

		CartDTO cartDTO = this.cartService.getCartByUser(userId);

		return new ResponseEntity<CartDTO>(cartDTO,HttpStatus.CREATED);
	}

}