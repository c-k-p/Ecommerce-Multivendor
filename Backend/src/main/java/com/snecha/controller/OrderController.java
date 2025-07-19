package com.snecha.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.PaymentLink;
import com.snecha.domain.PaymentMethod;
import com.snecha.model.Address;
import com.snecha.model.Cart;
import com.snecha.model.Order;
import com.snecha.model.OrderItem;
import com.snecha.model.PaymentOrder;
import com.snecha.model.Seller;
import com.snecha.model.SellerReport;
import com.snecha.model.User;
import com.snecha.repository.PaymentOrderRepository;
import com.snecha.response.PaymentLinkResponse;
import com.snecha.service.CartService;
import com.snecha.service.OrderService;
import com.snecha.service.PaymentService;
import com.snecha.service.SellerReportService;
import com.snecha.service.SellerService;
import com.snecha.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
	
	private final OrderService orderService;
	private final UserService userService;
	private final CartService cartService;
	private final SellerReportService sellerReportService;
	private final SellerService  sellerService;
	private final PaymentService paymentService;
	private final PaymentOrderRepository paymentOrderRepository;

	
	public ResponseEntity<PaymentLinkResponse> createOrderHandler(
			@RequestBody Address shippingAddress,
			@RequestParam PaymentMethod paymentMethod,
			@RequestHeader("Authorization") String jwt	)throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartService.findUserCart(user);
		
		Set<Order> orders=orderService.createOrder(user, shippingAddress, cart);
		
		PaymentOrder paymentOrder=paymentService.createOrder(user,orders);
		
		PaymentLinkResponse res=	new PaymentLinkResponse();
		
		if(paymentMethod.equals(PaymentMethod.RAZORPAY)) {
			PaymentLink payment=paymentService.createRazorpayPaymentLink(user,
					   paymentOrder.getAmount(),
					   paymentOrder.getId());
			
			String paymentUrl=payment.get("short_url");
			String paymentUrlId=payment.get("id");
			
			
			res.setPayment_link_url(paymentUrl);
			
			paymentOrder.setPaymentLinkId(paymentUrlId);
			
			paymentOrderRepository.save(paymentOrder);
			
		}else {
			String paymentUrl=paymentService.createStripePaymentLink(user,
					   paymentOrder.getAmount(),
					   paymentOrder.getId());
				
			res.setPayment_link_id(paymentUrl);
		}
		
				return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>> userOrderHistoryHandler(
			@RequestHeader("Authorization") String jwt)throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		List<Order> orders = orderService.usersOrderHistory(user.getId());
		
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		Order orders=orderService.findOrderById(orderId);
		
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}

	@GetMapping("/item/{orderItemId}")
	public ResponseEntity<OrderItem> getOrderItemById(
			@PathVariable Long orderItemId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		OrderItem orderItem=orderService.getOrderItemById(orderItemId);
		
		return new ResponseEntity<>(orderItem,HttpStatus.ACCEPTED);		
	}
	
	
	public ResponseEntity<Order> cancelOrder(
			@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		
		Order order = orderService.cancelOrder(orderId, user);		
		
		Seller seller=sellerService.getSellerById(order.getSellerId());
		SellerReport report=sellerReportService.getSellerReport(seller);
		
		report.setCancledOrders(report.getCancledOrders()+1);
		report.setTotalRefunds(report.getTotalRefunds()+order.getTotalSellingPrice());
		sellerReportService.updateSellerReport(report);
		
		return ResponseEntity.ok(order);

	}

}
