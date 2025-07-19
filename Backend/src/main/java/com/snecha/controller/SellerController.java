package com.snecha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snecha.config.JwtProvider;
import com.snecha.domain.AccountStatus;
import com.snecha.exception.SellerException;
import com.snecha.model.Seller;
import com.snecha.model.SellerReport;
import com.snecha.model.VerificationCode;
import com.snecha.repository.VerificationCodeRepository;
import com.snecha.request.LoginRequest;
import com.snecha.response.AuthResponse;
import com.snecha.service.AuthService;
import com.snecha.service.EmailService;
import com.snecha.service.SellerReportService;
import com.snecha.service.SellerService;
import com.snecha.utils.OtpUtil;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

	private final SellerService sellerService;
	private final VerificationCodeRepository verificationCodeRepository;
	private final AuthService authService;
	private final EmailService emailService;
	private final JwtProvider jwtProvider;
	private final SellerReportService sellerReportService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequest req) throws Exception {

		String otp = req.getOtp();
		String email = req.getEmail();

		req.setEmail("seller_" + email);
		AuthResponse authResponse = authService.signing(req);

		return ResponseEntity.ok(authResponse);
	}

	@PatchMapping("/verify/{otp}")
	public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws Exception {

		VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

		if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
			throw new Exception("Wrong OTP..");
		}

		Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);

		return new ResponseEntity<>(seller, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception, MessagingException {
		Seller savedSeller = sellerService.createSeller(seller);

		String otp = OtpUtil.generateOtp();

		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setOtp(otp);
		verificationCode.setEmail(seller.getEmail());
		verificationCodeRepository.save(verificationCode);

		String subject = "Snecha Bazaar Email Verification Code";
		String text = "Welcome to Snecha Bazaar, verify your account using this link";
		String fronted_url = "http://localhost:3000/verify-seller/";
		emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject,
				text + fronted_url);

		return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Seller> getSellerByById(@PathVariable Long id) throws SellerException {
		Seller seller = sellerService.getSellerById(id);
		return new ResponseEntity<>(seller, HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws Exception {

		Seller seller = sellerService.getSellerProfile(jwt);
		return new ResponseEntity<Seller>(seller, HttpStatus.OK);
	}

	@GetMapping("/report")
	public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws Exception {

		Seller seller = sellerService.getSellerProfile(jwt);
		SellerReport report = sellerReportService.getSellerReport(seller);

		return new ResponseEntity<>(report, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false) AccountStatus status) {

		List<Seller> sellers = sellerService.getAllSellers(status);
		return ResponseEntity.ok(sellers);
	}

	@PatchMapping()
	public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt, @RequestBody Seller seller)
			throws Exception {

		Seller profile = sellerService.getSellerProfile(jwt);
		Seller updateSeller = sellerService.updateSeller(profile.getId(), seller);

		return ResponseEntity.ok(updateSeller);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Seller> deleteSeller(@PathVariable Long id) throws Exception {

		sellerService.deleteSeller(id);

		return ResponseEntity.noContent().build();
	}

}
