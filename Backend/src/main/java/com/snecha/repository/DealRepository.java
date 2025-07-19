package com.snecha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snecha.model.Deal;

public interface DealRepository extends JpaRepository<Deal, Long> {

}
