package com.mc.electronic.store.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mc.electronic.store.entity.Category;
import com.mc.electronic.store.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

	//Search
	Page<Product> findByproductTitleContaining(String title,Pageable pageable);

	//Get All find all Live Product
	Page<Product> findByisProductLiveTrue(Pageable pageable);
	
	
	//Find By Category
	
	Page<Product> findByCategory(Category category,Pageable pageable);
	
	
	
}
