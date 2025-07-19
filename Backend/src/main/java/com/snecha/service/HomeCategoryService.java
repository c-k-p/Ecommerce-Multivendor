package com.snecha.service;

import java.util.List;

import com.snecha.model.HomeCategory;

public interface HomeCategoryService {

	HomeCategory createHomeCategory(HomeCategory homeCategory);
	
	List<HomeCategory> createCategories(List<HomeCategory> homecategories);
	
	HomeCategory updateHomeCategory(HomeCategory homeCategory, Long id) throws Exception;
	
	List<HomeCategory> getAllHomeCategories();
	
}
