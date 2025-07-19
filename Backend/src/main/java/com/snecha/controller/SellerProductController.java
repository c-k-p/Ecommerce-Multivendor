package com.snecha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.snecha.exception.ProductException;
import com.snecha.model.Product;
import com.snecha.model.Seller;
import com.snecha.request.CreateProductRequest;
import com.snecha.service.ProductService;
import com.snecha.service.SellerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers/products")
public class SellerProductController {

	private final ProductService productService;
	private final SellerService sellerService;

	// GEt PRODUCT BY SELLER

	@GetMapping
	public ResponseEntity<List<Product>> getProductBySellerId(@RequestHeader("Authorization") String jwt)
			throws Exception {

		Seller seller = sellerService.getSellerProfile(jwt);
		List<Product> products = productService.getProductBySellerId(seller.getId());

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	// CREATE PRODUCT

	@PostMapping()
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request,
			@RequestHeader("Authorization") String jwt) throws Exception {

		Seller seller = sellerService.getSellerProfile(jwt);
		Product product = productService.createProduct(request, seller);

		return new ResponseEntity<>(product, HttpStatus.CREATED);

	}

	// DELETE PRODUCT

	@DeleteMapping("{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
		try {
			productService.deleteProducts(productId);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (ProductException e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// UPDATE PRODUCT

	@PutMapping("/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product)
			throws ProductException {

		Product updatedProduct = productService.updateProduct(productId, product);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

}
