package com.snecha.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.snecha.config.JwtProvider;
import com.snecha.domain.USER_ROLE;
import com.snecha.model.Cart;
import com.snecha.model.Seller;
import com.snecha.model.User;
import com.snecha.model.VerificationCode;
import com.snecha.repository.CartRepository;
import com.snecha.repository.SellerRepository;
import com.snecha.repository.UserRepository;
import com.snecha.repository.VerificationCodeRepository;
import com.snecha.request.LoginRequest;
import com.snecha.response.AuthResponse;
import com.snecha.response.SignupRequest;
import com.snecha.service.AuthService;
import com.snecha.service.EmailService;
import com.snecha.utils.OtpUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final CartRepository cartRepository;
	private final VerificationCodeRepository verificationCodeRepository;
	private final JwtProvider jwtProvider;
	private final EmailService emailService;
	private final CustomeUserServiceImpl customeUserServiceImpl;
	private final SellerRepository sellerRepository;

	@Override
	public String createUser(SignupRequest req) throws Exception {

		VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());
		if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())) {
			throw new Exception("Wrong Otp...");
		}

		User user = userRepository.findByEmail(req.getEmail());

		if (user == null) {
			User createdUser = new User();

			createdUser.setEmail(req.getEmail());
			createdUser.setFullName(req.getFullName());
			createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
			createdUser.setMobile("9798513763");
			createdUser.setPassword(passwordEncoder.encode(req.getOtp()));
			user = userRepository.save(createdUser);

			Cart cart = new Cart();
			cart.setUser(user);

			cartRepository.save(cart);
		}
		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));
		Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return jwtProvider.generaateToken(authentication);
	}

	@Override
	public void sentLoginOtp(String email, USER_ROLE role) throws Exception {
		String SIGNING_PREFIX = "signing_";

		if (email.startsWith(SIGNING_PREFIX)) {
			email = email.substring(SIGNING_PREFIX.length());

			if (role.equals(USER_ROLE.ROLE_SELLER)) {
				Seller seller = sellerRepository.findByEmail(email);
				if (seller == null) {
					throw new Exception("Seller Not Found ");
				}

			} else {
				System.out.println(email+"===@@@@@@@@@@@@@@@@");
				User user = userRepository.findByEmail(email);
				if (user == null) {
					throw new Exception("user not exist with provided email ");
				}

			}

		}
		VerificationCode isExist = verificationCodeRepository.findByEmail(email);

		if (isExist != null) {
			verificationCodeRepository.delete(isExist);
		}
		String otp = OtpUtil.generateOtp();

		VerificationCode verificationCode = new VerificationCode();

		verificationCode.setOtp(otp);
		verificationCode.setEmail(email);
		verificationCodeRepository.save(verificationCode);

		String subject = "Snecha bazaar Login/Signup otp";

		String text = "Your Login/Signinup otp is  -  " + otp;

		emailService.sendVerificationOtpEmail(email, otp, subject, text);

	}

	@Override
	public AuthResponse signing(LoginRequest req) throws Exception {

		String username = req.getEmail();
		String otp = req.getOtp();

		Authentication authentication = authenticate(username, otp);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generaateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwtToken(token);
		authResponse.setMessage("Login success");

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

		authResponse.setRole(USER_ROLE.valueOf(roleName));

		return authResponse;
	}

	private Authentication authenticate(String username, String otp) throws Exception {

		UserDetails userDetails = customeUserServiceImpl.loadUserByUsername(username);
		
		String SELLER_PREFIX="seller_";
		
		if(username.startsWith(SELLER_PREFIX)) {
			username=username.substring(SELLER_PREFIX.length());
		}

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Username");
		}
		VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

		if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
			throw new Exception("Wrong OTP ");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
