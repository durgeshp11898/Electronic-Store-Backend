package com.mc.electronic.store.controllers;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.electronic.store.dtos.UserDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserDetailsService detailsService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/current")
	public ResponseEntity<UserDTO> getCurrentUser(Principal principal){
		
		String userName= principal.getName();
		UserDTO userDto = this.modelMapper.map(this.detailsService.loadUserByUsername(userName),UserDTO.class);
		return new ResponseEntity<UserDTO>(userDto,HttpStatus.OK);
	}
}
