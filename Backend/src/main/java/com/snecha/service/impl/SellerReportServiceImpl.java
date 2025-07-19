package com.snecha.service.impl;

import org.springframework.stereotype.Service;

import com.snecha.model.Seller;
import com.snecha.model.SellerReport;
import com.snecha.repository.SellerReportRepository;
import com.snecha.service.SellerReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {

	private final SellerReportRepository sellerReportRepository;

	@Override
	public SellerReport getSellerReport(Seller seller) {

		SellerReport sr = sellerReportRepository.findBySellerId(seller.getId());
		if (sr == null) {
			SellerReport newReport = new SellerReport();
			newReport.setSeller(seller);
			return sellerReportRepository.save(newReport);
		}

		return sr;
	}

	@Override
	public SellerReport updateSellerReport(SellerReport sellerReport) {
		return sellerReportRepository.save(sellerReport);
	}

}
