package com.sales.processing;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sales.processing.intf.SaleProductInterface;
import com.sales.processing.vo.AdjustmentInfo;

public class SaleProcessingLog {
	
	private SaleProcessingLog(){
		
	}
	
	public static void logSaleInformation(Map<String, SaleProductInterface> saleInformationMap) {
		StringBuilder sb = new StringBuilder("----------------Sale Details---------------\n");
		for (Entry<String, SaleProductInterface> entryObject : saleInformationMap.entrySet()) {
			sb.append("Product=" + entryObject.getKey() + ", TotalValue=" + entryObject.getValue().getTotalValue()
					+ "p, No of sales=" + entryObject.getValue().getNumberOfSales());
			sb.append("\n");
		}
		sb.append("----------------End---------------");
		printLog(sb.toString());
	}

	public static void logCompleteSaleInformation(Map<String, SaleProductInterface> saleInformationMap) {
		StringBuilder sb = new StringBuilder("----------------Complete Sale Details---------------\n");
		for (Entry<String, SaleProductInterface> entryObject : saleInformationMap.entrySet()) {
			sb.append("Product=" + entryObject.getKey() + ", TotalValue=" + entryObject.getValue().getTotalValue()
					+ "p, No of sales=" + entryObject.getValue().getNumberOfSales());
			sb.append("\n");
			sb.append("Adjustments made :\n");
			sb.append("------------------------------\n");
			List<AdjustmentInfo> adjustMentList = entryObject.getValue().getAdjustmentInfoList();
			for (AdjustmentInfo adjustmentInfo : adjustMentList) {
				sb.append(" Type=").append(adjustmentInfo.getType()).append(",Time=")
						.append(adjustmentInfo.getTimeStamp()).append(",Value=").append(adjustmentInfo.getValue()).append("p");
				sb.append("\n");
			}
			sb.append("------------------------------\n");
		}
		sb.append("-------------------------End------------------------");
		printLog(sb.toString());
	}
	
	public static void printLog(String log){
		System.out.println(log);
	}

}
