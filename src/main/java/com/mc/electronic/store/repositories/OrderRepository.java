package com.mc.electronic.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mc.electronic.store.entity.Order;
import com.mc.electronic.store.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order,String>{

	List<Order> findByUser(User user);
}
