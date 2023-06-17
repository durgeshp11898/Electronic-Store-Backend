package com.mc.electronic.store.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {


	//PENDING,DISPATCHED,DELIVERED,

	private String cartId;
	private String userId;
	private String orderStatus="PENDING";
	private String paymentStatus="NOTPAID";
	private String billingAddress;
	private String billingPhone;
	private String billingName;


}
