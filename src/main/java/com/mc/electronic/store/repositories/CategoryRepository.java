package com.mc.electronic.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mc.electronic.store.entity.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

	 public List<Category> findBycategoryTitle(String categoryTitle);
	 public List<Category> findBycategoryTitleContaining(String categoryTitle);
}
