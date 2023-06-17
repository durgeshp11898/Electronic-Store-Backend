package com.mc.electronic.store.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import com.mc.electronic.store.dtos.OrderDTO;
import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.dtos.ProductDTO;
import com.mc.electronic.store.entity.Cart;
import com.mc.electronic.store.entity.CartItem;
import com.mc.electronic.store.entity.CreateOrderRequest;
import com.mc.electronic.store.entity.Order;
import com.mc.electronic.store.entity.OrderItem;
import com.mc.electronic.store.entity.Product;
import com.mc.electronic.store.entity.User;
import com.mc.electronic.store.exceptions.BadApiRequest;
import com.mc.electronic.store.exceptions.ResourceNotFoundException;
import com.mc.electronic.store.helper.PagableHelper;
import com.mc.electronic.store.repositories.CartRepository;
import com.mc.electronic.store.repositories.OrderRepository;
import com.mc.electronic.store.repositories.UserRepository;
import com.mc.electronic.store.services.OrderService;

@Service
public class OrderService_impl implements OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRespository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrderDTO createOrder(CreateOrderRequest createOrderRequest) {
		//Fetch the user
		User user = this.userRepository.findById(createOrderRequest.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Not found with this ID"));

		//Fetch the Cart
		Cart cart = this.cartRespository.findById(createOrderRequest.getCartId()).orElseThrow(()-> new ResourceNotFoundException("Cart item Not Found"));

		List<CartItem> cartItems = cart.getCartItems();

		if(cartItems.size()<=0) {
			throw new BadApiRequest("invalid number of items in cart");
		}

		Order order = Order.builder()
				.billingName(createOrderRequest.getBillingName())
				.billingAddress(createOrderRequest.getBillingAddress())
				.billingPhone(createOrderRequest.getBillingPhone())
				.deliveredDate(null)
				.orderStatus(createOrderRequest.getOrderStatus())
				.orderId(UUID.randomUUID().toString())
				.user(user)
				.paymentStatus(createOrderRequest.getPaymentStatus())
				.orderedDate(new Date())
				.build();

		//orderitems, amount 
		// to add item totalprice
		AtomicReference<Integer> orderAmount= new AtomicReference<>(0);

		List<OrderItem> orderItems = cartItems.stream().map(cartItem ->{
			///How to change cartItem to OrderItem;

			OrderItem orderItem = OrderItem.builder()
					.quantity(cartItem.getQuantity())
					.product(cartItem.getProduct())
					.totalPrice(cartItem.getQuantity()*cartItem.getProduct().getProductDiscountedPrice())
					.order(order)
					.build();

			orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());

			return orderItem;
		}).collect(Collectors.toList());

		//Set order Items
		order.setOrderItems(orderItems);
		order.setOrderAmount(orderAmount.get());

		cart.getCartItems().clear();

		this.cartRespository.save(cart);
		Order savedOrder = this.orderRepository.save(order);

		return this.modelMapper.map(savedOrder, OrderDTO.class);
	}

	@Override
	public void removeOrder(String orderId) {

		Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order Not found with this ID"));
		this.orderRepository.delete(order);
	}

	@Override
	public List<OrderDTO> getsOrderOfUsers(String userId) {

		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not found with this ID"));
		List<Order> orders = this.orderRepository.findByUser(user);
		List<OrderDTO> orderDtos = orders.stream().map(order-> this.modelMapper.map(order,OrderDTO.class)).collect(Collectors.toList());

		return orderDtos;
	}

	@Override
	public PagableResponse<OrderDTO> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort =(sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy)).ascending():(Sort.by(sortBy)).descending();
		Pageable pegeble = PageRequest.of(pageNumber, pageSize,sort);	
		Page<Order> page = this.orderRepository.findAll(pegeble);

		PagableResponse<OrderDTO> response = PagableHelper.getPagableResponse(page, OrderDTO.class);
		return response;
	}


	@Override
	public OrderDTO updateOrderAndPaymentStatus(String OrderId, OrderDTO orderDTO) {
		Order order = this.orderRepository.findById(OrderId).orElseThrow(()->new ResourceNotFoundException("Orderld Not Found"));

		order.setBillingAddress(orderDTO.getBillingAddress());
		order.setBillingName(orderDTO.getBillingName());
		order.setBillingPhone(orderDTO.getBillingPhone());
		order.setDeliveredDate(new Date());
		order.setOrderAmount(orderDTO.getOrderAmount());
		order.setOrderStatus(orderDTO.getOrderStatus());
		order.setPaymentStatus(orderDTO.getPaymentStatus());

		Order savedOrder = this.orderRepository.save(order);
		return this.modelMapper.map(savedOrder, OrderDTO.class);
	}
}