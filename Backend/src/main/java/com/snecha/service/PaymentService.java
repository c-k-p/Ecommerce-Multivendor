package com.snecha.service;

import java.util.Set;

import com.razorpay.PaymentLink;
import com.snecha.model.Order;
import com.snecha.model.PaymentOrder;
import com.snecha.model.User;
import com.stripe.exception.StripeException;

public interface PaymentService {

	PaymentOrder createOrder(User user, Set<Order> orders);

	PaymentOrder getPaymentOrderById(Long orderId) throws Exception;

	PaymentOrder getPaymentOrderByPaymentId(String orderId)throws Exception;

	Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws Exception;

	PaymentLink createRazorpayPaymentLink(User user, Long amount, Long orderId) throws Exception;

	String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;

}
