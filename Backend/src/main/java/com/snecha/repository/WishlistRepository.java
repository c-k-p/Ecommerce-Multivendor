package com.snecha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snecha.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
	Wishlist findByUserId(Long userId);

}
