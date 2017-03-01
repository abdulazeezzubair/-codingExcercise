package com.sales.processing.intf;

import java.util.List;
import java.util.Map;

import com.sales.processing.exception.SaleProcessingException;

public interface MessageProcessingInterface {

	public void processMessage(String message) throws SaleProcessingException;
	public void persistMessage(String message);
	public int getNumberOfMessagesProcessed();
	public List<String> getMessagesRecieved();
	public Map<String,SaleProductInterface> getProductSales();
}
