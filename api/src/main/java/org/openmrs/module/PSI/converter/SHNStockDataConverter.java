package org.openmrs.module.PSI.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNStockDetails;

public class SHNStockDataConverter {
	
	
	public JSONObject toConvert(SHNStock objStock) throws JSONException {
		JSONObject shnStockObject = new JSONObject();
		shnStockObject.putOpt("stockInId", objStock.getStockInId());
		shnStockObject.putOpt("clinicName", objStock.getClinicName());
		shnStockObject.putOpt("clinicCode", objStock.getClinicCode());
		shnStockObject.putOpt("clinicId", objStock.getClinicId());
		shnStockObject.putOpt("invoiceNumber", objStock.getInvoiceNumber());
		shnStockObject.putOpt("receiveDateForSync", objStock.getReceiveDate());
		shnStockObject.putOpt("uuid", objStock.getUuid());
		
		Set<SHNStockDetails> shnStockDetailsList = objStock.getStockDetails();
		JSONArray stockDetailsArray = new JSONArray();
		for (SHNStockDetails shnStockDetails : shnStockDetailsList) {
			JSONObject stockDetailsObject = new JSONObject();
			stockDetailsObject.putOpt("debit", shnStockDetails.getDebit());
			stockDetailsObject.putOpt("credit", shnStockDetails.getCredit());
			stockDetailsObject.putOpt("expiryDateForSync", shnStockDetails.getExpiryDate());
			stockDetailsObject.putOpt("productID", shnStockDetails.getProductID());
			stockDetailsObject.putOpt("productName", shnStockDetails.getProductName());
			stockDetailsObject.putOpt("uuid", shnStockDetails.getUuid());
			stockDetailsArray.put(stockDetailsObject);
		}
		
		shnStockObject.putOpt("stockDetails", stockDetailsArray);
		return shnStockObject;
		
	}
	
	public JSONArray toConvert(List<SHNStock> shnStockList) throws JSONException {
		JSONArray stockJsonArrayFull = new JSONArray();
		for (SHNStock shnSingleStock : shnStockList) {
			stockJsonArrayFull.put(toConvert(shnSingleStock));
		}
		return stockJsonArrayFull;
		
	}
	
}
