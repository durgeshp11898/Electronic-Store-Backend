package com.mc.electronic.store.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.electronic.store.entity.User;


public interface UserRepository extends JpaRepository<User,String>{
	
	//Value is availble  or not thats check optinal class
	//Find By Email 
	Optional<User> findByuserEmail(String userEmail);
	
	//List<User> FindByuserNameContaining(String keywords);
	
//	@Query("SELECT user_name FROM User WHERE user_name LIKE CONCAT('%',:user_name,'%')")
//	List<User> searchusername(@Param("user_name") String user_name);
//	
    public List<User> findByuserName(String userName);
    public List<User> findByuserNameContaining(String userName);

}
