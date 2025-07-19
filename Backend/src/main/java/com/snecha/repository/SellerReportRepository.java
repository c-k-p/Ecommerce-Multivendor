package com.snecha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snecha.model.SellerReport;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
	
	SellerReport findBySellerId(Long sellerId);

}
