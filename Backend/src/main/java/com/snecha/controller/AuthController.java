package com.snecha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snecha.domain.USER_ROLE;
import com.snecha.model.VerificationCode;
import com.snecha.request.LoginOtpRequest;
import com.snecha.request.LoginRequest;
import com.snecha.response.ApiResponse;
import com.snecha.response.AuthResponse;
import com.snecha.response.SignupRequest;
import com.snecha.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception{
		
		String jwt = authService.createUser(req);
				
		AuthResponse res=new AuthResponse();
		res.setJwtToken(jwt);
		res.setMessage("Register Success...");
		res.setRole(USER_ROLE.ROLE_CUSTOMER);
		
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/sent/login-signup-otp")
	public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception{
		
		 authService.sentLoginOtp(req.getEmail(),req.getRole());
				
		ApiResponse res=new ApiResponse();
		
		res.setMessage("otp sent successfully");
		
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/signing")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception{
		
		 AuthResponse authResponse = authService.signing(req);
				
		return ResponseEntity.ok(authResponse);
	}
	
	
	
	
	
}
