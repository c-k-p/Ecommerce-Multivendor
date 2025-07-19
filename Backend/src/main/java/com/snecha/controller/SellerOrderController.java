package com.snecha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snecha.domain.OrderStatus;
import com.snecha.model.Order;
import com.snecha.model.Seller;
import com.snecha.service.OrderService;
import com.snecha.service.SellerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller/orders")
public class SellerOrderController {

	private final OrderService orderService;
	private final SellerService sellerService;

	@GetMapping()
	public ResponseEntity<List<Order>> getAllOrderHandler(@RequestHeader("Authorization") String jwt) throws Exception {
		Seller seller = sellerService.getSellerProfile(jwt);
		List<Order> orders = orderService.sellersOrder(seller.getId());

		return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
	}

	@PatchMapping("/{orderId}/status/{orderStatus}")
	public ResponseEntity<Order> updateOrderHandler(@RequestHeader("Authrization") String jwt,
			@PathVariable Long orderId, @PathVariable OrderStatus orderStatus) throws Exception {

		Order order = orderService.updateOrderStatus(orderId, orderStatus);

		return new ResponseEntity<>(order, HttpStatus.ACCEPTED);

	}

}
