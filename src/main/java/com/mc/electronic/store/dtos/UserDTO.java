package com.mc.electronic.store.dtos;

import com.mc.electronic.store.validation.ImagenameValidator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {

	private String userId;

	@Size(min = 3,max = 15,message = "Invalid Name !!")
	private String userName;
	
	@Email
	private String userEmail;
	
	@NotBlank(message = "Password is Required")
	//@Pattern(regexp = "\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\"", message="Minimum 8 characters at least 1 Alphabet and 1 Number")
	private String userPassword;
	
	@Size(min = 4,max = 6,message = "Gender should be male & Female")
	private String userGender;
	
	@NotBlank
	private String userDOB;
	
	@NotNull
	@Size(min=10, message = "Size is gretter than 10 characters")
	private String aboutUser;
	
	@ImagenameValidator
	private String userImage;
}


