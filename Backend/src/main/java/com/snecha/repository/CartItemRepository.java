package com.snecha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snecha.model.Cart;
import com.snecha.model.CartItem;
import com.snecha.model.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
