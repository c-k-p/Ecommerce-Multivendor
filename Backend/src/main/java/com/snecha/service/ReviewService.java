package com.snecha.service;

import java.util.List;

import com.snecha.model.Product;
import com.snecha.model.Review;
import com.snecha.model.User;
import com.snecha.request.CreateReviewRequest;

public interface ReviewService {
	Review createReview(CreateReviewRequest req, User user, Product product);

	List<Review> getReviewByProductId(Long productId);

	Review updateReview(Long reviewId, String reviewtext, double rating, Long userId) throws Exception;

	void deleteReview(Long reviewId, Long userId) throws Exception;
	Review getReviewByid(Long reviewId) throws Exception;

}
