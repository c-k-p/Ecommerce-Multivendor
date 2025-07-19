package com.snecha.service.impl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.snecha.domain.USER_ROLE;
import com.snecha.model.User;
import com.snecha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public void run(String... args) throws Exception {
initializeAdminUser();		
	}


	private void initializeAdminUser() {

		String adminUsername="ckpandey0423@gmail.com";
		String adminPassword="Ckp123@";
		
		if(userRepository.findByEmail(adminUsername)==null) {
			User adminUser=new User();
			
			adminUser.setPassword(passwordEncoder.encode(adminPassword));
			adminUser.setFullName("Stylish Rony");
			adminUser.setEmail(adminUsername);
			adminUser.setRole(USER_ROLE.ROLE_ADMIN);
			
		userRepository.save(adminUser);
			
		}
		
		
		
		
		
	}

}
