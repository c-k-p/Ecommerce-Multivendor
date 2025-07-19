package com.snecha.service;

import com.snecha.model.User;

public interface UserService {
	
/// start to create email id 
User findUserByJwtToken(String jwt) throws Exception ;
 
  User findUserByEmail(String email)throws Exception;
}
