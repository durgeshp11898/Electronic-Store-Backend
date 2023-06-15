package com.mc.electronic.store.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.mc.electronic.store.dtos.PagableResponse;


public class PagableHelper {

	public static <U,V> PagableResponse<V> getPagableResponse(Page<U> page,Class<V> type){
		
		List<U> entity = page.getContent();
		
		List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object,type) ).collect(Collectors.toList());
		
		 
		PagableResponse<V> response = new PagableResponse<V>();
		 response.setContent(dtoList);
		 response.setLastPage(page.isLast());
		 response.setPageNumber(page.getNumber());
		 response.setPageSize(page.getSize());
		 response.setTotalElements(page.getNumberOfElements());
		 response.setTotalPages(page.getTotalPages());
		 
		 return response;
	}
}
