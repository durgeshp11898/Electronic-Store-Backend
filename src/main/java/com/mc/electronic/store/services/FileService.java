package com.mc.electronic.store.services;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	public String uploadFile(MultipartFile multipartFile,String path);
	
	public InputStream getResourceFile(String path,String name);
}
