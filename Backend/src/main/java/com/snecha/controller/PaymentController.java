package com.snecha.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snecha.model.Order;
import com.snecha.model.PaymentOrder;
import com.snecha.model.Seller;
import com.snecha.model.SellerReport;
import com.snecha.model.User;
import com.snecha.response.ApiResponse;
import com.snecha.response.PaymentLinkResponse;
import com.snecha.service.PaymentService;
import com.snecha.service.SellerReportService;
import com.snecha.service.SellerService;
import com.snecha.service.TransactionService;
import com.snecha.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

	private final PaymentService paymentService;
	private final UserService userService;
	private final SellerService sellerServcie;
	private final SellerReportService sellerReportService;
	private final TransactionService transactionService;
	
	@GetMapping("/{paymentId}")
	public ResponseEntity<ApiResponse> paymentSuccesssHandler(
									@PathVariable String paymentId,
									@RequestParam String paymentLinkId,
									@RequestHeader("Authorization") String jwt
			)throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		
		PaymentLinkResponse paymentResponse;
		
		PaymentOrder paymentOrder=paymentService.getPaymentOrderByPaymentId(paymentLinkId);
		
		boolean paymentSuccess=paymentService.proceedPaymentOrder(paymentOrder, paymentId, paymentLinkId);
		
		if(paymentSuccess) {
			for(Order order:paymentOrder.getOrders()) {
				transactionService.createTransaction(order);
				Seller seller = sellerServcie.getSellerById(order.getSellerId());
				SellerReport report = sellerReportService.getSellerReport(seller);
				report.setTotalOrder(report.getTotalOrder()+1);
				report.setTotalEarnings(report.getTotalEarnings()+order.getTotalSellingPrice());
				report.setTotalSales(report.getTotalSales()+order.getOrderItems().size());
				
				sellerReportService.updateSellerReport(report);
				}
		}
				
		ApiResponse res = new ApiResponse();
		res.setMessage("Payment Successful");
		
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
}






