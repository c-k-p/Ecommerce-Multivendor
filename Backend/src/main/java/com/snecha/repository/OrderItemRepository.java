package com.snecha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snecha.model.OrderItem;

public interface OrderItemRepository  extends JpaRepository<OrderItem,Long>{

}
