package com.mc.electronic.store.services.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mc.electronic.store.dtos.AddItemToCartRequest;
import com.mc.electronic.store.dtos.CartDTO;
import com.mc.electronic.store.entity.Cart;
import com.mc.electronic.store.entity.CartItem;
import com.mc.electronic.store.entity.Product;
import com.mc.electronic.store.entity.User;
import com.mc.electronic.store.exceptions.BadApiRequest;
import com.mc.electronic.store.exceptions.ResourceNotFoundException;
import com.mc.electronic.store.repositories.CartItemRespository;
import com.mc.electronic.store.repositories.CartRepository;
import com.mc.electronic.store.repositories.ProductRepository;
import com.mc.electronic.store.repositories.UserRepository;
import com.mc.electronic.store.services.CartService;



@Service
public class CartService_impl implements CartService{

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CartItemRespository  cartItemRespository;

	@Override
	public CartDTO addItemToCart(String userId, AddItemToCartRequest addItemToCartRequest) {

		int quantity=addItemToCartRequest.getQuantity();
		String productId=addItemToCartRequest.getProductId();

		if(quantity<=0) {
			throw new BadApiRequest("Request Quantity is not Valid");		
		}

		//Fetch the Product
		Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found"));

		//Fetch the user
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));

		//Find the user from cart //Custom Finder Method

		Cart cart = null;

		try {
			//if availble for user then use this else create new cart
			cart=this.cartRepository.findByUser(user).get();

		}catch(NoSuchElementException e) {
			//create a new cart for user
			cart= new Cart();
			cart.setCartId(UUID.randomUUID().toString()	);
			cart.setCreatedAt(new Date());
		}

		//Perfrom cart operation

		//List<CartItem> cartItems = cart.getCartItems();
		//boolean updated=false;
		AtomicReference<Boolean> updated = new AtomicReference<>(false);
		//if cart item already availble then update 
		List<CartItem> cartItems = cart.getCartItems();
		List<CartItem> updatedItems = cartItems.stream().map(item->{

			if(item.getProduct().getProductId().equals(productId)) {
				//Item Already Present in cart
				item.setQuantity(quantity);
				item.setTotalPrice(quantity*product.getProductDiscountedPrice());
				updated.set(true);
			}
			return item;
		}).collect(Collectors.toList());

		cart.setCartItems(updatedItems);


		//add items to cart
		//When we have create a new item
		if(!updated.get()) {
			CartItem cartItem = CartItem.builder()
					.quantity(quantity)
					.totalPrice(quantity*product.getProductDiscountedPrice())
					.cart(cart)
					.product(product)
					.build();
			//Add data into cart arrayList
			cart.getCartItems().add(cartItem);
		}


		cart.setUser(user);

		Cart updatedCart = this.cartRepository.save(cart);

		return this.modelMapper.map(updatedCart, CartDTO.class);
	}

	@Override
	public void removeItemFromCart(int cartItem) {

		CartItem cartItemObj = this.cartItemRespository.findById(cartItem).orElseThrow(()-> new ResourceNotFoundException("Cart item Not Found"));

		this.cartItemRespository.delete(cartItemObj);

	}

	@Override
	public void clearCart(String userId) {
		//fetch the user
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));

		Cart cart = this.cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cat\rt of Given user Notfound"));

		cart.getCartItems().clear();

		this.cartRepository.save(cart);
	}

	@Override
	public CartDTO getCartByUser(String userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));

		Cart cart = this.cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cat\rt of Given user Notfound"));

		return this.modelMapper.map(cart, CartDTO.class);
	}

}
