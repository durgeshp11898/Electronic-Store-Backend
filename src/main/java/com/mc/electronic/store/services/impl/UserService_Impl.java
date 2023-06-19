package com.mc.electronic.store.services.impl;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mc.electronic.store.dtos.PagableResponse;
import com.mc.electronic.store.dtos.UserDTO;
import com.mc.electronic.store.entity.User;
import com.mc.electronic.store.exceptions.ResourceNotFoundException;
import com.mc.electronic.store.helper.PagableHelper;
import com.mc.electronic.store.repositories.UserRepository;
import com.mc.electronic.store.services.UserService;

@Service
public class UserService_Impl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${user.prfile.image.path}")
	private String imagePath;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	Logger logger = Logger.getLogger(UserService_Impl.class);
	
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		
		//Generate Unique  id in string format
		logger.info("users value comes from request --> "+userDTO.toString());
		String userId= UUID.randomUUID().toString();
		userDTO.setUserId(userId);
		//Encoding password
		this.passwordEncoder.encode(userDTO.getUserPassword());
		
		User user = this.modelMapper.map(userDTO, User.class);
		User savedUser = this.userRepository.save(user);
		UserDTO newDTO = this.modelMapper.map(savedUser, UserDTO.class);
		logger.info("saved user values are --> "+newDTO.toString());
		return newDTO;
	}

	@Override
	public void deleteUser(String userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not found with this ID"));
		
	
		if(user!=null) {
			//delete user profile image
			String fullPath=imagePath+user.getUserImage();
			logger.info("Image full Path"+fullPath);
			Path path= Paths.get(fullPath);
			
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.info("file not found Exception : IOException");
				e.printStackTrace();
			}
			
			logger.info("Deleted user is -->"+user.toString());
			this.userRepository.delete(user);
		}
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, String userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not found with this ID"));
		
		user.setUserName(userDTO.getUserName());
		user.setUserPassword(userDTO.getUserPassword());
		user.setUserGender(userDTO.getUserGender());
		user.setUserDOB(userDTO.getUserDOB());
		user.setUserImage(userDTO.getUserImage());
		
		User updatedUser = this.userRepository.save(user);
		
		UserDTO updatedDTO = this.modelMapper.map(updatedUser,UserDTO.class);
		logger.info("Updated user is --> "+updatedDTO.toString());
		return updatedDTO;
	}

	@Override
	public UserDTO getUserById(String userId) {
		User findUser = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found !!"));
		UserDTO findDTO = this.modelMapper.map(findUser, UserDTO.class);
		logger.info("user by id= "+userId+" -->"+findDTO.toString());
		return findDTO;
	}

	@Override
	public UserDTO getUserByEmail(String userEmail) {
		User user=this.userRepository.findByuserEmail(userEmail).orElseThrow(()-> new ResourceNotFoundException("Email Id Found !!"));
		logger.info("Get user by Email --> "+user.toString());
		return this.modelMapper.map(user, UserDTO.class);
	}

	@Override
	public PagableResponse<UserDTO> getAllUsers(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		//Sort sort = Sort.by(sortBy);
		Sort sort =(sortDir.equalsIgnoreCase("asc"))?(Sort.by(sortBy)).ascending():(Sort.by(sortBy)).descending();
		Pageable pegeble = PageRequest.of(pageNumber, pageSize,sort);	
		Page<User> page = this.userRepository.findAll(pegeble);
		
		PagableResponse<UserDTO> response = PagableHelper.getPagableResponse(page, UserDTO.class);
		 
		return response;
	}



	@Override
	public List<UserDTO> searchUser(String keyword) {
		List<User> users = this.userRepository.findByuserNameContaining(keyword);
		List<UserDTO> dtoList = users.stream().map(user -> this.modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
		logger.info("searched users are --> "+dtoList);
		return dtoList;
	}

}
