package com.snecha.service;

import com.snecha.model.Seller;
import com.snecha.model.SellerReport;

public interface SellerReportService {
	SellerReport getSellerReport(Seller seller);
	SellerReport updateSellerReport(SellerReport sellerReport);

}
