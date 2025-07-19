package com.snecha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.snecha.model.User;
import com.snecha.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserControlleer {

	private final UserService userService; 
	
	@GetMapping("/api/users/profile")
	public ResponseEntity<User>userProfileHandler(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		
		return ResponseEntity.ok(user);
	}
	
}
