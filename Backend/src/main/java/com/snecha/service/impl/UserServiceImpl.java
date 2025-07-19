package com.snecha.service.impl;

import org.springframework.stereotype.Service;

import com.snecha.config.JwtProvider;
import com.snecha.model.User;
import com.snecha.repository.UserRepository;
import com.snecha.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	@Override
	public User findUserByJwtToken(String jwt) throws Exception  {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
	
		return this.findUserByEmail(email);
	}

	@Override
	public User findUserByEmail(String email) throws Exception {

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new Exception("User Not Found with email  " + email);

		}

		return user;
	}

}
