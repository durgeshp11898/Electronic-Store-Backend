package com.mc.electronic.store.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderDTO {

	private String orderId;
	//PENDING,DISPATCHED,DELIVERED,

	private String orderStatus="PENDING";

	private String paymentStatus="NOTPAID";

	private int orderAmount;

	private String billingAddress;

	private String billingPhone;

	private String billingName;

	private Date orderedDate= new Date();

	private Date deliveredDate;


	private UserDTO user;

	private List<OrderItemDTO> orderItems = new ArrayList<>();



}
