package com.sales.processing.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sales.processing.SaleProcessingLog;
import com.sales.processing.intf.SaleProductInterface;
import com.sales.processing.util.SalesProcessingUtility;

public class SaleProduct implements SaleProductInterface {

	private String productName;
	private List<Double> priceList = new ArrayList<>();
	private List<AdjustmentInfo> adjustmentInfoList = new ArrayList<>();

	public SaleProduct(String productName) {
		super();
		this.productName = productName;
	}

	/**
	 * @return the name
	 */
	@Override
	public String getProductName() {
		return productName;
	}

	/**
	 * @param price
	 *            the price to add
	 */
	@Override
	public void addPrice(Double price) {
		priceList.add(price);
	}

	@Override
	public Integer getNumberOfSales() {
		return priceList.size();
	}

	/**
	 * @return the adjustmentInfoList
	 */
	@Override
	public List<AdjustmentInfo> getAdjustmentInfoList() {
		return adjustmentInfoList;
	}

	/**
	 * @return the totalValue
	 */
	@Override
	public Double getTotalValue() {
		Double totalValue = 0d;
		for (Double price : this.priceList) {
			totalValue += price;
		}
		return totalValue;
	}

	@Override
	public void applyAdjustMent(Double value, String adjustMent) {

		AdjustmentInfo adjustMentInfo = new AdjustmentInfo();
		adjustMentInfo.setType(adjustMent);
		adjustMentInfo.setValue(value);
		adjustMentInfo.setTimeStamp(new Timestamp(System.currentTimeMillis()));

		if (SalesProcessingUtility.ADD_OPERATION.equals(adjustMentInfo.getType())) {
			addAdjustment(adjustMentInfo);
		}

		if (SalesProcessingUtility.MULTIPLY_OPERATION.equals(adjustMentInfo.getType())) {
			multiplyAdjustment(adjustMentInfo);
		}

		if (SalesProcessingUtility.SUBTRACT_OPERATION.equals(adjustMentInfo.getType())) {
			subtractAdjustment(adjustMentInfo);
		}
	}

	private void logAdjustMent(AdjustmentInfo adjustMentInfo) {
		adjustmentInfoList.add(adjustMentInfo);
	}

	private void addAdjustment(AdjustmentInfo adjustMentInfo) {
		logAdjustMent(adjustMentInfo);
		List<Double> tempList = new ArrayList<>();
		for (Double price : priceList) {
			tempList.add(BigDecimal.valueOf(price).add(BigDecimal.valueOf(adjustMentInfo.getValue())).doubleValue());
		}
		this.priceList = tempList;
	}

	private void multiplyAdjustment(AdjustmentInfo adjustMentInfo) {
		if (adjustMentInfo.getValue() > 0) {
			logAdjustMent(adjustMentInfo);
			List<Double> tempList = new ArrayList<>();
			for (Double price : priceList) {
				tempList.add(BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(adjustMentInfo.getValue()))
						.doubleValue());
			}
			this.priceList = tempList;
		} else {
			SaleProcessingLog.printLog("Adjustment can't be ngegative value");
		}
	}

	private void subtractAdjustment(AdjustmentInfo adjustMentInfo) {

		if (adjustMentInfo.getValue() > 0) {
			logAdjustMent(adjustMentInfo);
			List<Double> tempList = new ArrayList<>();
			for (Double price : priceList) {
				if (price > adjustMentInfo.getValue()) {
					tempList.add(BigDecimal.valueOf(price).subtract(BigDecimal.valueOf(adjustMentInfo.getValue()))
							.doubleValue());
				} else {
					SaleProcessingLog.printLog("Adjustment value is not applied since it is not greater than price");
				}
			}
			this.priceList = tempList;
		} else {
			SaleProcessingLog.printLog("Adjustment can't be negative value");
		}
	}
	
	@Override
	public List<Double> getPriceList() {
		return Collections.unmodifiableList(priceList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SaleProduct [productName=" + productName + ", priceList=" + priceList + ", adjustmentInfoList="
				+ adjustmentInfoList + ", totalValue=" + getTotalValue() + "p]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SaleProduct other = (SaleProduct) obj;
		if (productName == null) {
			if (other.productName != null) {
				return false;
			}
		} else if (!productName.equals(other.productName)) {
			return false;
		}
		return true;
	}

}
