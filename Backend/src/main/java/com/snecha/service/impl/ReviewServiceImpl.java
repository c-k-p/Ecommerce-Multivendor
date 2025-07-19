package com.snecha.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.snecha.model.Product;
import com.snecha.model.Review;
import com.snecha.model.User;
import com.snecha.repository.ReviewRepository;
import com.snecha.request.CreateReviewRequest;
import com.snecha.service.ReviewService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;

	@Override
	public Review createReview(CreateReviewRequest req, User user, Product product) {

		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReviewtext(req.getReviewText());
		review.setRating(req.getReviewRating());
		review.setProductImages(req.getProductImages());

		product.getReviews().add(review);

		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getReviewByProductId(Long productId) {
		return reviewRepository.findByProductId(productId);
	}

	@Override
	public Review updateReview(Long reviewId, String reviewtext, double rating, Long userId) throws Exception {
		Review review = getReviewByid(reviewId);
		if (review.getUser().getId().equals(userId)) {
			review.setReviewtext(reviewtext);
			review.setRating(rating);
			return reviewRepository.save(review);
		}
		throw new Exception("You Can't update this review");
	}

	@Override
	public void deleteReview(Long reviewId, Long userId) throws Exception {
		Review review = getReviewByid(reviewId);
		if (review.getUser().getId().equals(userId)) {
			throw new Exception("You can't delete this review");
		}
		reviewRepository.delete(review);
	}

	@Override
	public Review getReviewByid(Long reviewId) throws Exception {
		return reviewRepository.findById(reviewId).orElseThrow(() -> new Exception("Review Not Found"));
	}

}
