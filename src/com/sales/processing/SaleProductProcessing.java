package com.sales.processing;

import java.util.Map;

import com.sales.processing.intf.SaleProductInterface;
import com.sales.processing.util.SalesProcessingUtility;
import com.sales.processing.vo.SaleProduct;

public class SaleProductProcessing {
	
	private SaleProductInterface parseSaleInformation(String message,Integer numberOfOccurences){
		String[] productInfo  = message.split(SalesProcessingUtility.DELIMITER);
		String name = productInfo[1];
		Double price  = SalesProcessingUtility.getPrice(productInfo[2]);
		SaleProductInterface saleProduct = new SaleProduct(name);
 		saleProduct.addPrice(price*numberOfOccurences);
		return saleProduct;
	}

	public void processSaleItem(String message,Map<String,SaleProductInterface> saleInformationMap){
		String productName = SalesProcessingUtility.getProductName(message);
		Double price = SalesProcessingUtility.getPriceFromMessage(message);
		
		if(saleInformationMap.containsKey(productName)){
			SaleProductInterface saleProduct = saleInformationMap.get(productName);
			saleProduct.addPrice(price);
		}else{
			SaleProductInterface saleProduct = parseSaleInformation(message,1);
			saleInformationMap.put(saleProduct.getProductName(), saleProduct);
		}
	
	}

	public void processNumberOfSalesOccurence(String message, Map<String, SaleProductInterface> saleInformationMap) {
		String productName = SalesProcessingUtility.getProductName(message);
		Double price = SalesProcessingUtility.getPriceFromMessage(message);
		Integer numberOfItems = SalesProcessingUtility.getSaleOccurences(message);
		
		if(saleInformationMap.containsKey(productName)){
			SaleProductInterface saleProduct = saleInformationMap.get(productName);
			saleProduct.addPrice(price*numberOfItems);
		}else{
			SaleProductInterface saleProduct = parseSaleInformation(message,numberOfItems);
			saleInformationMap.put(saleProduct.getProductName(), saleProduct);
		}
	
		
	}

	public void processSalesAdjustment(String message, Map<String, SaleProductInterface> saleInformationMap) {
		String productName = SalesProcessingUtility.getProductName(message);
		Double price = SalesProcessingUtility.getPriceFromMessage(message);
		String adjustMent = SalesProcessingUtility.getAdjustmentType(message);
		
		if(saleInformationMap.containsKey(productName)){
			SaleProductInterface saleProduct = saleInformationMap.get(productName);
			saleProduct.applyAdjustMent(price,adjustMent);
		}else{
			SaleProcessingLog.printLog("Dropping adjustment for "+message);
		}
	}
}
