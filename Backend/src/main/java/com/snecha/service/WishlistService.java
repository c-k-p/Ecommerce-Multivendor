package com.snecha.service;

import com.snecha.model.Product;
import com.snecha.model.User;
import com.snecha.model.Wishlist;

public interface WishlistService {
	
	Wishlist createWishlist(User user);
	Wishlist getWishlistByUserId(User user);
	Wishlist addProductToWishlist(User user, Product product);
}
 