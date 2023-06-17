package com.mc.electronic.store.services;

import java.util.List;


import com.mc.electronic.store.dtos.OrderDTO;
import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.entity.CreateOrderRequest;

public interface OrderService {

	//Create order
	OrderDTO createOrder(CreateOrderRequest createOrderRequest);
	
	//remove Order
	void removeOrder(String orderId);
	
	//Gets order by user
	List<OrderDTO>  getsOrderOfUsers(String userId);
	
	//get orders
	PagableResponse<OrderDTO>  getOrders(int pageNumber, int pageSize,String sortBy,String sortDir);
	
	//other logics releted to orders
	
	//update Order
	OrderDTO updateOrderAndPaymentStatus(String OrderId,OrderDTO orderDTO);
	
}
