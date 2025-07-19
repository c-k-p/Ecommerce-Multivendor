package com.snecha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snecha.exception.ProductException;
import com.snecha.model.Cart;
import com.snecha.model.CartItem;
import com.snecha.model.Product;
import com.snecha.model.User;
import com.snecha.request.AddItemRequest;
import com.snecha.response.ApiResponse;
import com.snecha.service.CartItemService;
import com.snecha.service.CartService;
import com.snecha.service.ProductService;
import com.snecha.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

	private final CartService cartService;
	private final CartItemService cartItemService;
	private final UserService userService;
	private final ProductService productService;

	@GetMapping
	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws Exception {
				
		User user = userService.findUserByJwtToken(jwt);
		
		Cart cart = cartService.findUserCart(user);
		
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<CartItem> additemToCart(@RequestBody AddItemRequest req,@RequestHeader("Authorization") String jwt) throws ProductException, Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		Product product = productService.findProductById(req.getProductId());
		
		CartItem item = cartService.addCartItem(user, product, req.getSize(), req.getQuantity());
		
		ApiResponse res=new ApiResponse();
		
		res.setMessage("Item Added To Cart Successfuly");
		
		return new ResponseEntity<>(item,HttpStatus.ACCEPTED);
		
	}
	
	@DeleteMapping("/item/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItemHandler(
			@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);
		
		ApiResponse res=new ApiResponse();
		
		res.setMessage("Item Remove Frome Cart");
		
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/item/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItemHandler(
			@PathVariable Long cartItemId,
			@RequestBody  CartItem cartItem,
			@RequestHeader("Authorization") String jwt) throws Exception
			{
		User user = userService.findUserByJwtToken(jwt);
		CartItem updatedCartItem=null;
		
		if(cartItem.getQuantity()>0) {
			 updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		}
		return new ResponseEntity<CartItem>(updatedCartItem,HttpStatus.ACCEPTED);
				
	}

}
