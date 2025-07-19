package com.snecha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snecha.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
	
	List<Order> findByuserId(Long userId);
	List<Order> findBySellerId(Long sellerId);

}
