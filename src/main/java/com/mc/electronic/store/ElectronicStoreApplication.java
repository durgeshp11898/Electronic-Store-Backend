package com.mc.electronic.store;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class ElectronicStoreApplication  //implements CommandLineRunner{
{

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
		String log4jConfPath = "src/main/resources/log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}

//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		System.out.println(passwordEncoder.encode("Dipak@123"));
//	}

	
}
