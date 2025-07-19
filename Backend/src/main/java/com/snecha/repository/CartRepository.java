package com.snecha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.snecha.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	
	Cart findByUserId( Long id);


}
