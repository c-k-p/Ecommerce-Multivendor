package com.snecha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.snecha.model.Home;
import com.snecha.model.HomeCategory;
import com.snecha.service.HomeCategoryService;
import com.snecha.service.HomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeCategoryController {
	private final HomeCategoryService homeCategoryService;
	private final HomeService homeService;

	@PostMapping("/home/categories")
	public ResponseEntity<Home> createHomeCategories(@RequestBody List<HomeCategory> homeCategories) {

		List<HomeCategory> categories = homeCategoryService.createCategories(homeCategories);
		Home home = homeService.createHomepageData(categories);

		return new ResponseEntity<>(home, HttpStatus.ACCEPTED);
	}

	@GetMapping("/admin/home-category")
	public ResponseEntity<List<HomeCategory>> getHomeCategory() throws Exception {
		List<HomeCategory> categories = homeCategoryService.getAllHomeCategories();
		return ResponseEntity.ok(categories);

	}

	@PatchMapping("/admin/home-category/{id}")
	public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable Long id,
			@RequestBody HomeCategory homecategory) throws Exception {
		HomeCategory updatedCategory = homeCategoryService.updateHomeCategory(homecategory, id);

		return ResponseEntity.ok(updatedCategory);
	}

}
