package com.snecha.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snecha.model.Product;
import com.snecha.model.User;
import com.snecha.model.Wishlist;
import com.snecha.service.ProductService;
import com.snecha.service.UserService;
import com.snecha.service.WishlistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistConttroller {

	private final WishlistService wishlistService;
	private final UserService userService;
	private final ProductService productService;

	@GetMapping()
	public ResponseEntity<Wishlist> getWishlistByUserId(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Wishlist wishlist = wishlistService.getWishlistByUserId(user);

		return ResponseEntity.ok(wishlist);
	}

	@PostMapping("/add-product/{productId}")
	public ResponseEntity<Wishlist> addProductToWishlist(@PathVariable Long productId,
			@RequestHeader("Authorization") String jwt) throws Exception {
		Product product = productService.findProductById(productId);
		User user = userService.findUserByJwtToken(jwt);
		Wishlist updatedWishlist = wishlistService.addProductToWishlist(user, product);

		return ResponseEntity.ok(updatedWishlist);

	}
}
