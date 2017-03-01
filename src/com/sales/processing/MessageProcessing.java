/**
 * 
 */
package com.sales.processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sales.processing.exception.SaleProcessingException;
import com.sales.processing.intf.MessageProcessingInterface;
import com.sales.processing.intf.SaleProductInterface;
import com.sales.processing.util.SalesProcessingUtility;

public class MessageProcessing implements MessageProcessingInterface{

	private List<String> messageList = new ArrayList<>();
	private Map<String,SaleProductInterface> saleInformationMap = new HashMap<>();
	private int maxMessagesAllowed = 50;
	private int messageOccurenceCount = 10;
	SaleProductProcessing saleProductProcessing = new SaleProductProcessing();
	
	public MessageProcessing(int maxMessagesAllowed, int messageOccurenceCount) {
		super();
		this.maxMessagesAllowed = maxMessagesAllowed;
		this.messageOccurenceCount = messageOccurenceCount;
	}

	@Override
	public void processMessage(String message) throws SaleProcessingException {
		if (SalesProcessingUtility.validateMessage(message)) {
			SaleProcessingLog.printLog("Message Recieved|" + message);
			persistMessage(message);

			if (messageList.size() > maxMessagesAllowed) {
				SaleProcessingLog.printLog("Maximum processing size reached, No more messages will be accepted ");
				SaleProcessingLog.logCompleteSaleInformation(saleInformationMap);
				return;
			}

			String messageType = SalesProcessingUtility.getTypeFromMessage(message);

			switch (messageType) {

			case SalesProcessingUtility.SALE_TYPE:
				saleProductProcessing.processSaleItem(message, saleInformationMap);
				break;

			case SalesProcessingUtility.SALE_OCCURENCE_TYPE:
				saleProductProcessing.processNumberOfSalesOccurence(message, saleInformationMap);
				break;

			case SalesProcessingUtility.SALE_ADJUSTMENT_TYPE:
				saleProductProcessing.processSalesAdjustment(message, saleInformationMap);
				break;

			default:
				SaleProcessingLog.printLog("Invalid type");
			}

			if (messageList.size() % messageOccurenceCount == 0) {
				SaleProcessingLog.logSaleInformation(saleInformationMap);
			}

		} else {
			throw new SaleProcessingException("Invalid Message: " + message);
		}
		
	}

	@Override
	public void persistMessage(String message) {
		messageList.add(message);	
	}

	@Override
	public int getNumberOfMessagesProcessed() {
		return messageList.size();
	}

	@Override
	public List<String> getMessagesRecieved() {
		SaleProcessingLog.printLog("getMessagesRecieved() function not supported");
		return Collections.emptyList();
	}
	
	@Override
	public Map<String,SaleProductInterface> getProductSales() {
		SaleProcessingLog.printLog("getProductSales() function not supported");
		return Collections.emptyMap();
	}

}
