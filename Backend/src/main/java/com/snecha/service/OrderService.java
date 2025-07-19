package com.snecha.service;

import java.util.List;
import java.util.Set;

import com.snecha.domain.OrderStatus;
import com.snecha.model.Address;
import com.snecha.model.Cart;
import com.snecha.model.Order;
import com.snecha.model.OrderItem;
import com.snecha.model.User;

public interface OrderService {
	
	Set<Order> createOrder(User user,Address shippingAddress, Cart cart);
	Order findOrderById(Long id) throws Exception;
	List<Order> usersOrderHistory(Long userId);
	List<Order> sellersOrder(Long sellerId);
	Order updateOrderStatus(Long orderId,OrderStatus orderStatus) throws Exception;
	Order cancelOrder(Long orderId,User user) throws Exception;
	OrderItem getOrderItemById(Long id) throws Exception;
	
	

}
