package com.mc.electronic.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mc.electronic.store.entity.User;
import com.mc.electronic.store.exceptions.ResourceNotFoundException;
import com.mc.electronic.store.repositories.UserRepository;

@Service
public class FormBasedCustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=	this.userRepository.findByuserEmail(username).orElseThrow(()-> new ResourceNotFoundException("User with this Email Not Found"));
		return user;
	}


}
