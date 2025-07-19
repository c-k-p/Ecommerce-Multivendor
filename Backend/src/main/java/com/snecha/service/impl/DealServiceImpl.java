package com.snecha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.snecha.model.Deal;
import com.snecha.model.HomeCategory;
import com.snecha.repository.DealRepository;
import com.snecha.repository.HomeCategoryRepository;
import com.snecha.service.DealService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

	private final DealRepository dealRepository;
	private final HomeCategoryRepository homeCategoryRepository;

	@Override
	public List<Deal> getDeals() {
		return dealRepository.findAll();
	}

	@Override
	public Deal createDeal(Deal deal) {
		HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);
		Deal newDeal = dealRepository.save(deal);
		newDeal.setCategory(category);
		newDeal.setDiscount(deal.getDiscount());
		return dealRepository.save(newDeal);
	}

	@Override
	public Deal updateDeal(Deal deal, Long id) throws Exception {
		Deal existingDeal = dealRepository.findById(id).orElse(null);
		HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);

		if (existingDeal != null) {
			if (deal.getDiscount() != null) {
				existingDeal.setDiscount(deal.getDiscount());
			}
			if (category != null) {
				existingDeal.setCategory(category);
			}
			return dealRepository.save(existingDeal);
		}
		throw new Exception("Deal Not Found");
	}

	@Override
	public void deleteDeal(Long id) throws Exception {
		Deal deal = dealRepository.findById(id).orElseThrow(() -> new Exception("Deal ot Found"));
		dealRepository.delete(deal);
	}

}
