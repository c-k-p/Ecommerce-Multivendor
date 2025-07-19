package com.snecha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snecha.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long>{
	Category findByCategoryId(String categoryId);


}
