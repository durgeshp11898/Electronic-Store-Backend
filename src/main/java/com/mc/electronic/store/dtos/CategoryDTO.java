package com.mc.electronic.store.dtos;


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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

	private String categoryId;
	
	@NotBlank(message = "Title should not be blank")
	@Size(min = 3,max = 100,message = "Size of Desc between 3 to 100")
	private String categoryTitle;
	
	
	@NotBlank
	@Size(min = 3,max = 1000,message = "Size of Desc between 3 to 1000")
	private String categoryDesc;
	
	@NotNull(message = "Cover image Required")
	private String coverImage;
}
