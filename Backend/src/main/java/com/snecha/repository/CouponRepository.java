package com.snecha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snecha.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	Coupon findByCode(String code);
}
