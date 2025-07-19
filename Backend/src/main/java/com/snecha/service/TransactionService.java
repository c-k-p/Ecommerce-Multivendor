package com.snecha.service;

import java.util.List;

import com.snecha.model.Order;
import com.snecha.model.Seller;
import com.snecha.model.Transaction;

public interface  TransactionService {
	
	Transaction createTransaction(Order order);
	List<Transaction> getTransactionsBySellerId(Seller seller);
	List<Transaction> getAllTransactions();

}
