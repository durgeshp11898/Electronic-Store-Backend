package com.mc.electronic.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.electronic.store.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

	
}
