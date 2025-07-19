package com.snecha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snecha.domain.AccountStatus;
import com.snecha.model.Seller;


public interface SellerRepository extends JpaRepository<Seller, Long> {
	
	Seller findByEmail(String email);
	
	List<Seller>  findByAccountStatus(AccountStatus accountStatus);

}
