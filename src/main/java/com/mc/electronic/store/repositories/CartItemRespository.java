package com.mc.electronic.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.electronic.store.entity.CartItem;

public interface CartItemRespository extends JpaRepository<CartItem, Integer>{

}
