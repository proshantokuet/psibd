package org.openmrs.module.PSI.converter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.SHNPackage;

public class SHNPackageAsServiceConverter {
	
	public JSONObject toConvert(SHNPackage shnPackage) throws JSONException {
		JSONObject service = new JSONObject();
		service.putOpt("sid", shnPackage.getPackageId());
		service.putOpt("name", shnPackage.getPackageName());
		service.putOpt("code", shnPackage.getPackageCode());
		service.putOpt("category", "");
		service.putOpt("provider", "");
		service.putOpt("unitCost", shnPackage.getPackagePrice());
		service.putOpt("type", "PACKAGE");
		service.putOpt("discountPop", 0);
		service.putOpt("discountPoor", 0);
		service.putOpt("discountAblePay", 0);
		service.putOpt("voided", shnPackage.getVoided());
		
		return service;
		
	}
	
	public JSONArray toConvert(List<SHNPackage> shnPackages) throws JSONException {
		JSONArray serviceManagements = new JSONArray();
		for (SHNPackage shnPackage : shnPackages) {
			serviceManagements.put(toConvert(shnPackage));
		}
		return serviceManagements;
		
	}
}
