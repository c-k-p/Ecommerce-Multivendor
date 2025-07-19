package com.snecha.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.snecha.model.HomeCategory;
import com.snecha.repository.HomeCategoryRepository;
import com.snecha.service.HomeCategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeCategoryServiceImpl implements HomeCategoryService {

	private final HomeCategoryRepository homeCategoryRepository;

	@Override
	public HomeCategory createHomeCategory(HomeCategory homeCategory) {
		return homeCategoryRepository.save(homeCategory);
	}

	@Override
	public List<HomeCategory> createCategories(List<HomeCategory> homeCategories) {

		if (homeCategoryRepository.findAll().isEmpty()) {
			return homeCategoryRepository.saveAll(homeCategories);
		}
		return homeCategoryRepository.findAll();
	}

	@Override
	public HomeCategory updateHomeCategory(HomeCategory category, Long id) throws Exception {

		HomeCategory existingCategory = homeCategoryRepository.findById(id)
				.orElseThrow(() -> new Exception("Category Not Found"));

		if (category.getImage() != null) {
			existingCategory.setImage(category.getImage());
		}
		if (category.getCategoryId() != null) {
			existingCategory.setCategoryId(category.getCategoryId());
		}

		return homeCategoryRepository.save(existingCategory);

	}

	@Override
	public List<HomeCategory> getAllHomeCategories() {
		return homeCategoryRepository.findAll();
	}

}
