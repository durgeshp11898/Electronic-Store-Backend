package com.mc.electronic.store.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

	@Id
	private String cartId;
	
	private Date createdAt;
	
	@OneToOne
	private User user;
	
	//MApping cart item Entties
	
	//One cart has many items
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "cart",orphanRemoval = true)
	private List<CartItem> cartItems= new ArrayList<>();
	
	
}
