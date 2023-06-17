package com.mc.electronic.store.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mc.electronic.store.dtos.ApiResponseMessage;
import com.mc.electronic.store.dtos.OrderDTO;
import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.dtos.UserDTO;
import com.mc.electronic.store.entity.CreateOrderRequest;
import com.mc.electronic.store.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;



	//Create Order
	@PostMapping
	public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest){

		OrderDTO createdOrder = this.orderService.createOrder(createOrderRequest);

		return new ResponseEntity<>(createdOrder,HttpStatus.CREATED);
	}


	///removeOrder
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable("orderId") String orderId){
		this.orderService.removeOrder(orderId);
		ApiResponseMessage apiResponse = ApiResponseMessage.builder().message("Order Removed !!").httpStatus(HttpStatus.OK).success(true).build();
		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}


	//getsOrderOfUsers
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<OrderDTO>> getOrdersOfUser(@PathVariable("userId") String userId){

		List<OrderDTO> orders = this.orderService.getsOrderOfUsers(userId);
 
		return new ResponseEntity<>(orders,HttpStatus.FOUND);

	}	

	//Get All Orders
	@GetMapping
	public ResponseEntity<PagableResponse<OrderDTO>> getAllOrders(
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "orderedDate",required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir
		
			){
		PagableResponse<OrderDTO> orders = this.orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}	

	//Update Order
	@PutMapping("/{orderId}")
	public ResponseEntity<OrderDTO> updateOrder(@Valid @RequestBody OrderDTO orderDTO,@PathVariable String orderId){

		OrderDTO updatedOrder = this.orderService.updateOrderAndPaymentStatus(orderId, orderDTO);
		return new ResponseEntity<>(updatedOrder,HttpStatus.CREATED);
	}
	
}
