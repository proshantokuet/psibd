package org.openmrs.module.PSI.converter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.SHNStockAdjust;

public class SHNStockAdjustDataConverter {
public JSONObject toConvert(SHNStockAdjust shnStockAdjust) throws JSONException {
		
		JSONObject shnStockAdjustObject = new JSONObject();
		shnStockAdjustObject.putOpt("productId", shnStockAdjust.getProductId());
		shnStockAdjustObject.putOpt("clinicId", shnStockAdjust.getClinicId());
		shnStockAdjustObject.putOpt("clinicCode", shnStockAdjust.getClinicCode());
		shnStockAdjustObject.putOpt("adjustDateForSync", shnStockAdjust.getAdjustDate());
		shnStockAdjustObject.putOpt("previousStock", shnStockAdjust.getPreviousStock());
		shnStockAdjustObject.putOpt("changedStock", shnStockAdjust.getChangedStock());
		shnStockAdjustObject.putOpt("adjustReason", shnStockAdjust.getAdjustReason());
		shnStockAdjustObject.putOpt("uuid", shnStockAdjust.getUuid());
	
		return shnStockAdjustObject;
		
	}
	
	public JSONArray toConvert(List<SHNStockAdjust> stockAdjusts) throws JSONException {
		JSONArray stockJsonArray = new JSONArray();
		for (SHNStockAdjust shnStock : stockAdjusts) {
			stockJsonArray.put(toConvert(shnStock));
		}
		return stockJsonArray;
		
	}
}
