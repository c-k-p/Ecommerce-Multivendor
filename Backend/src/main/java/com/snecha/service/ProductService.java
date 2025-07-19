package com.snecha.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.snecha.exception.ProductException;
import com.snecha.model.Product;
import com.snecha.model.Seller;
import com.snecha.request.CreateProductRequest;

public interface ProductService {
	
	public Product createProduct(CreateProductRequest req,Seller seller);
	public void deleteProducts(Long productId) throws ProductException;
	public Product updateProduct(Long productId, Product product) throws ProductException;
	public Product findProductById(Long productId) throws ProductException;
	public List<Product> searchProducts(String query);
	public Page<Product> getAllProducts(
			String category,
			String brand,
			String colors,
			String sizes,
			Integer minPrice,
			Integer maxPrice,
			Integer minDiscount,
			String sort,
			String stock,
			Integer pageNumber
			);
	public List<Product> getProductBySellerId(Long sellerId);
}
