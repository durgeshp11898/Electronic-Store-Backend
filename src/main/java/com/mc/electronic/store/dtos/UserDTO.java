package com.mc.electronic.store.dtos;

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

	private String userName;
	private String userEmail;
	private String userPassword;
	private String userGender;
	private String userDOB;
	private String aboutUser;
	private String userImage;
}


