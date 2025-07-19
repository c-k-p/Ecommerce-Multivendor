package com.snecha.service;

import com.snecha.model.Cart;
import com.snecha.model.CartItem;
import com.snecha.model.Product;
import com.snecha.model.User;

public interface CartService {

	
	public CartItem addCartItem(
			User user,
			Product product,
			String size,
			int quantity
			);

	public Cart findUserCart(User user);
}
