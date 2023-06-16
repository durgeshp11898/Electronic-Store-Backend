package com.mc.electronic.store.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mc.electronic.store.exceptions.BadApiRequest;
import com.mc.electronic.store.services.FileService;

@Service
public class FileService_Impl implements FileService {


	Logger logger = Logger.getLogger(FileService_Impl.class);
	
	@Override
	public String uploadFile(MultipartFile multipartFile, String path) {
		
	String originalFileName=	multipartFile.getOriginalFilename();
	logger.info("originalFileName is --> "+originalFileName);
	
	String filename= UUID.randomUUID().toString();
	String extenstion= originalFileName.substring(originalFileName.lastIndexOf("."));
	String fileNameWithExtenstion = filename+extenstion;
	logger.info("creared file with extenstion --> "+fileNameWithExtenstion);
	String fullPathWithFilename=path+fileNameWithExtenstion;
	
	if(extenstion.equalsIgnoreCase(".png")|| extenstion.equalsIgnoreCase(".jpg")
			|| extenstion.equalsIgnoreCase("jpeg")) {
		
		//FileSave
		
			File folder = new File(path);
			//If folder dose not exists then create  a new folder
			if(!folder.exists()) {
				//creating a new folder
				folder.mkdirs();
				
			}
			
			
			try {
				Files.copy(multipartFile.getInputStream(),Paths.get(fullPathWithFilename));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		
	}else {
		throw new BadApiRequest("Image Extenstion should be png,jpg,jpeg");
	}
	
		return fileNameWithExtenstion;
	}

	@Override
	public InputStream getResourceFile(String path, String name) {
		
		String fullPath= path+File.separator+name;
		
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		return inputStream;
	}

}
 