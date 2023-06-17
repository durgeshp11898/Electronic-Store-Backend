package com.mc.electronic.store.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	private List<Order> orders=new ArrayList<>();
}
