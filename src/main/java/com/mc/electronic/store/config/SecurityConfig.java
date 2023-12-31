package com.mc.electronic.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration 	
public class SecurityConfig  {

	@Autowired
	private UserDetailsService userDetailsService;
	
	
	/* InMememry form based Authntication*/
	/*@Bean
	public UserDetailsService userDetailsService() {

		UserDetails normal = User.builder()
				.username("Ankit")
				.password(passwordEncoder().encode("ankit"))
				.roles("NORMAL")
				.build();

		UserDetails admin = User.builder()
				.username("Durgesh")
				.password(passwordEncoder().encode("durgesh"))
				.roles("ADMIN")
				.build();
		// users create
		//InMemoryUserDetailsManager- is implementation class of UserDetailService
		return new InMemoryUserDetailsManager(normal, admin);
	}
	 */

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}