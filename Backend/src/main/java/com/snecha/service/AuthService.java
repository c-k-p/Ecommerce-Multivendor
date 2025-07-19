package com.snecha.service;

import com.snecha.domain.USER_ROLE;
import com.snecha.request.LoginRequest;
import com.snecha.response.AuthResponse;
import com.snecha.response.SignupRequest;

public interface AuthService {

	void sentLoginOtp(String email,USER_ROLE role) throws Exception;
	String createUser(SignupRequest req)throws Exception;
	
	AuthResponse signing(LoginRequest req) throws Exception;
	
}
