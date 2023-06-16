package com.mc.electronic.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Category {

	@Id
	private String categoryId;
	
	@Column(length = 100,nullable = false,unique = true)
	private String categoryTitle;
	
	@Column(length = 400)
	private String categoryDesc;
	
	private String coverImage;
	
	
}
