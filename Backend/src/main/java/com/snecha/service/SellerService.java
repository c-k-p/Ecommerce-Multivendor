package com.snecha.service;

import java.util.List;

import com.snecha.domain.AccountStatus;
import com.snecha.exception.SellerException;
import com.snecha.model.Seller;

public interface SellerService  {
	
	Seller getSellerProfile(String jwt) throws Exception;
	Seller createSeller(Seller seller) throws Exception;
	Seller getSellerById(Long id)throws SellerException;
	Seller getSellerByEmail(String email) throws Exception;
	List<Seller> getAllSellers(AccountStatus status);
	Seller updateSeller(Long id,Seller seller) throws Exception;
	void deleteSeller(long id) throws Exception;
	Seller verifyEmail(String email,String otp) throws Exception;
	Seller updateSellerAccountStatus(Long selerId,AccountStatus status) throws Exception;
}
