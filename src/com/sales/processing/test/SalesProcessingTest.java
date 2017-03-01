package com.sales.processing.test;

import com.sales.processing.MessageProcessing;
import com.sales.processing.exception.SaleProcessingException;
import com.sales.processing.intf.MessageProcessingInterface;

public class SalesProcessingTest {

	public static void main(String[] args) {
		
		/* Message format 1 - type=Sales^apple^10p
		 * Message format 2 - type=SalesOccurence^apple^10p^20
		 * Message format 3 - type=SalesAdjustment^apple^10p^add
		 */

		String[] product = {"apple","mango","orange","lemon","papaya"};
		String[] messageType = {"sales","salesOccurence","salesAdjustment"};
		String[] value = {"10p","20p","25p","5p","15p"};
		String[] occurrence = {"10","20","15"};
		String[] adjustment = {"multiply","subtract","add","subtract","add"};
		
		MessageProcessingInterface messageProcessing = new MessageProcessing(50,10);
		
		String message = "";
		for(int i=0;i<51;i++){
			int rand = 0 + (int)(Math.random() * ((2) + 1));
			int randPrice = 0 + (int)(Math.random() * ((4) + 1));
			if("sales".equals(messageType[rand])){
				message = "type="+messageType[rand]+"^"+ product[randPrice] +"^"+value[randPrice];
			}
			
			if("salesOccurence".equals(messageType[rand])){
				message = "type="+messageType[rand]+"^"+ product[randPrice] +"^"+value[randPrice]+"^"+occurrence[rand];
			}
			
			if("salesAdjustment".equals(messageType[rand])){
				message = "type="+messageType[rand]+"^"+ product[randPrice] +"^"+value[randPrice]+"^"+adjustment[randPrice];
			}
			
			try {
				messageProcessing.processMessage(message);
			} catch (SaleProcessingException e) {
				e.printStackTrace();
			}
		}	
		
	}

}
