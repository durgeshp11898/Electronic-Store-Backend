package com.mc.electronic.store.controllers;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.electronic.store.dtos.ApiResponseMessage;
import com.mc.electronic.store.dtos.UserDTO;
import com.mc.electronic.store.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	//Create user
	@PostMapping	
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
		
		UserDTO createdUser = this.userService.createUser(userDTO);
		logger.info("created user from user controller --> "+createdUser.toString());
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
	}
	
	//Update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO  userDTO,@PathVariable String userId){
		UserDTO updatedUser = this.userService.updateUser(userDTO, userId);
		logger.info("Updated User from user controller--> "+updatedUser.toString());
		
		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
	}
	
	//Delete Single User
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
		this.userService.deleteUser(userId);
		logger.info("user deleted succesfully"+userId);
		ApiResponseMessage message = ApiResponseMessage.builder().message("user deleted successfully").success(true).httpStatus(HttpStatus.OK).build();
		return  new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	
	//Get All users
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<UserDTO> users = this.userService.getAllUsers();
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	//Get Single User
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getSingleUsers(@PathVariable String userId){
		 UserDTO user = this.userService.getUserById(userId);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	//Email 
	@GetMapping("/email/{emailId}")
	public ResponseEntity<UserDTO> getEmailUser(@PathVariable String emailId){
		 UserDTO user = this.userService.getUserByEmail(emailId);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	//Search
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDTO>> searchusingMail(@PathVariable String keywords){
		  List<UserDTO> users = this.userService.searchUser(keywords);
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	
}
