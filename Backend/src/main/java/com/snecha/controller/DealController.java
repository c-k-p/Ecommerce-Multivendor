package com.snecha.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snecha.model.Deal;
import com.snecha.response.ApiResponse;
import com.snecha.service.DealService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deals")
public class DealController {

	private final DealService dealService;

	@PostMapping
	public ResponseEntity<Deal> createDeals(@RequestBody Deal deals) {
		Deal createdDeal = dealService.createDeal(deals);
		return new ResponseEntity<>(createdDeal, HttpStatus.ACCEPTED);
	}
	
	
	// ✅ This method calls the service method to get all deals
@GetMapping
	public ResponseEntity<List<Deal>> getDeals() {
	    List<Deal> deals = dealService.getDeals();
	    return new ResponseEntity<>(deals, HttpStatus.OK);
	}


	@PatchMapping("/{id}")
	public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal deal) throws Exception {
		Deal updatedDeal = dealService.updateDeal(deal, id);
		return ResponseEntity.ok(updatedDeal);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteDeals(@PathVariable Long id) throws Exception {

		dealService.deleteDeal(id);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Deal Deleted");

		return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);

	}

}
