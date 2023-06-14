package com.mc.electronic.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user")
public class User {

	@Id //Primary key
	//@GeneratedValue(strategy = GenerationType.AUTO) //Auto Increment value
	private String userId;
	
	
	private String userName;
	
	@Column(unique = true) // value is not same 
	private String userEmail;
	private String userPassword;
	private String userGender;
	private String userDOB;
	
	@Column(length = 1000)
	private String aboutUser;
	
	private String userImage;
}
