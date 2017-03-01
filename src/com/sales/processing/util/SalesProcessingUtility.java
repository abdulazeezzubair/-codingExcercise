package com.sales.processing.util;

public class SalesProcessingUtility {

	/* Message format 1 - type=Sales^apple^10p
	 * Message format 2 - type=SalesOccurence^apple^10p^20
	 * Message format 3 - type=SalesAdjustment^apple^10p^add
	 */
	
	public static final String DELIMITER = "\\^";
	public static final String DELIMITER_TYPE = "\\=";
	public static final String SALE_TYPE = "sales";
	public static final String SALE_ADJUSTMENT_TYPE = "salesAdjustment";
	public static final String SALE_OCCURENCE_TYPE = "salesOccurence";
	public static final String ADD_OPERATION = "add";
	public static final String MULTIPLY_OPERATION = "multiply";
	public static final String SUBTRACT_OPERATION = "subtract";
	public static final String CURRENCY_DENOMINATION = "p";
	
	private SalesProcessingUtility(){
		
	}
	
	public static boolean validateMessage(String message) {
		if (null != message && message.split(DELIMITER).length >= 3) {
			return validateMessageType(message) ? true : false;
		}
		return false;
	}
	
	public static boolean validateMessageType(String message) {
		String messageType = getTypeFromMessage(message);
		if (SALE_TYPE.equals(messageType) || SALE_OCCURENCE_TYPE.equals(messageType)
				|| SALE_ADJUSTMENT_TYPE.equals(messageType)) {
			return true;
		}
		return false;
	}
	
	public static Double getPrice(String price){
		 return Double.valueOf(price.replace(CURRENCY_DENOMINATION, ""));
	}
	
	public static String getProductName(String message){
		 return message.split(DELIMITER)[1];
	}
	
	public static Double getPriceFromMessage(String message){
		return getPrice(message.split(DELIMITER)[2]);
	}

	public static String getTypeFromMessage(String message) {
		return message.split(DELIMITER)[0].split(DELIMITER_TYPE)[1];
	}

	public static Integer getSaleOccurences(String message) {
		return Integer.parseInt(message.split(DELIMITER)[3]);
	}

	public static String getAdjustmentType(String message) {
		return message.split(DELIMITER)[3];
	}
}
