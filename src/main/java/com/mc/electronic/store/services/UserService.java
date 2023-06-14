package com.mc.electronic.store.services;

import java.util.List;

import com.mc.electronic.store.dtos.UserDTO;


public interface UserService {

	//CRUD
	//Create a User
	UserDTO createUser(UserDTO userDTO);
	
	//Delete a User
	void deleteUser(String userId);
	
	//Update a User
	UserDTO updateUser(UserDTO userDTO, String userId);
	
	//Get Single User by Id
	UserDTO getUserById(String userId);
	
	//Get Single User by Email
	UserDTO getUserByEmail(String userEmail);
	
	//Get All Users
	List<UserDTO> getAllUsers();
	
	//Search User by Id
	List<UserDTO> searchUser(String keyword);
	
}
