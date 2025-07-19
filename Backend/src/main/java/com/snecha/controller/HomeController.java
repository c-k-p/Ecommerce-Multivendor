package com.snecha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snecha.response.ApiResponse;

@RestController
public class HomeController {

	@GetMapping
	public ApiResponse HomeControlerHandler() {
		ApiResponse  apiResponse = new ApiResponse();
	
		apiResponse.setMessage("Welcome to ecommorce");
		
		return  apiResponse;
	}
}
