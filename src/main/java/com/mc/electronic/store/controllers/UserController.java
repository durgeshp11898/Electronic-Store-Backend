package com.mc.electronic.store.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.lf5.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mc.electronic.store.dtos.ApiResponseMessage;
import com.mc.electronic.store.dtos.ImageResponse;
import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.dtos.UserDTO;
import com.mc.electronic.store.services.FileService;
import com.mc.electronic.store.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	//Value comes from aplication.properties file
	@Value("${user.prfile.image.path}")
	private String ImageUploadPath;
	
	//Create user
	@PostMapping	
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		
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
	public ResponseEntity<PagableResponse<UserDTO>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0",required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "userName",required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir
		
			){
		PagableResponse<UserDTO> users = this.userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
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
	
	
	//Upload user Image 
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(
			@PathVariable("userId") String userId,
		@RequestParam("userImage") MultipartFile userImage
		){
		
		
		String imageName=this.fileService.uploadFile(userImage, ImageUploadPath);
		logger.info("Image name is {}"+imageName);
		logger.info("Image upload Path -->"+ImageUploadPath);
		
		UserDTO user = this.userService.getUserById(userId);
		user.setUserImage(imageName);
		UserDTO updatedUser = this.userService.updateUser(user, userId);
		logger.info("updated user is {} -->"+updatedUser);
		
		ImageResponse imageResponse= ImageResponse.builder().imageName(imageName).httpStatus(HttpStatus.CREATED).success(true).build();
		
		return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
		
		
	}
	
	//Serve user Image
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable("userId") String userId, HttpServletResponse httpServletResponse) throws IOException {
		UserDTO user = this.userService.getUserById(userId);
		logger.info("user image name-->{} "+user.getUserImage());
		
		InputStream inputStream = this.fileService.getResourceFile(ImageUploadPath, user.getUserImage());
		
		httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
		
		
		
	}
	
	
}
