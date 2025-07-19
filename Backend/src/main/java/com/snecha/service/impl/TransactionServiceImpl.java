package com.snecha.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.snecha.model.Order;
import com.snecha.model.Seller;
import com.snecha.model.Transaction;
import com.snecha.repository.SellerRepository;
import com.snecha.repository.TransactionRepository;
import com.snecha.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final SellerRepository sellerRepository;
	
	
	
	@Override
	public Transaction createTransaction(Order order) {

		Seller seller = sellerRepository.findById(order.getSellerId()).get();
		
		Transaction transaction =new Transaction();
		
		transaction.setSeller(seller);
		transaction.setCustomer(order.getUser());
		transaction.setOrder(order);
		
		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> getTransactionsBySellerId(Seller seller) {
		return transactionRepository.findBySellerId(seller.getId());
	}

	@Override
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

}
