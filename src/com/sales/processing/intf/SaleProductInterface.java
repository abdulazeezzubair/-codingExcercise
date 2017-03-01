package com.sales.processing.intf;

import java.util.List;

import com.sales.processing.vo.AdjustmentInfo;

public interface SaleProductInterface {

	public String getProductName();
	public void addPrice(Double price);
	public Integer getNumberOfSales();
	public List<AdjustmentInfo> getAdjustmentInfoList();
	public List<Double> getPriceList();
	public Double getTotalValue();
	public void applyAdjustMent(Double value, String adjustMent);
	
}
