package com.snecha.service.impl;

import org.springframework.stereotype.Service;

import com.snecha.model.CartItem;
import com.snecha.model.User;
import com.snecha.repository.CartItemRepository;
import com.snecha.service.CartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

	private final CartItemRepository cartItemRepository;

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {

		CartItem item = findCartItemById(id);
		User cartItemUser = item.getCart().getUser();

		if (cartItemUser.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setMrpPrice(item.getQuantity() * item.getProduct().getMrpPrice());
			item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());
			return cartItemRepository.save(item);
		}
		throw new Exception("You can't Update this cartItem");

	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws Exception {

		CartItem item = findCartItemById(cartItemId);
		User cartItemUser = item.getCart().getUser();

		if (cartItemUser.getId().equals(userId)) {
			cartItemRepository.delete(item);
		} else {
			throw new Exception("You can't delete this cartItem");
		}
	}

	@Override
	public CartItem findCartItemById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return cartItemRepository.findById(id).orElseThrow(()-> new Exception("Cart item Not Found with id "+id));//09:28:18
	}

}
