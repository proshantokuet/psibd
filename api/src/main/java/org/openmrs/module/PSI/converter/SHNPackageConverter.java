package org.openmrs.module.PSI.converter;

import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;

public class SHNPackageConverter {
	public JSONObject toConvert(SHNPackage shnPackage) throws JSONException {
		
		JSONObject service = new JSONObject();
		service.putOpt("packageId", shnPackage.getPackageId());
		service.putOpt("packageName", shnPackage.getPackageName());
		service.putOpt("packageCode", shnPackage.getPackageCode());
		service.putOpt("clinicName", shnPackage.getClinicName());
		service.putOpt("clinicId", shnPackage.getClinicId());
		service.putOpt("clinicCode", shnPackage.getClinicCode());
		service.putOpt("accumulatedPrice", shnPackage.getAccumulatedPrice());
		service.putOpt("packagePrice", shnPackage.getPackagePrice());
		service.putOpt("voided", shnPackage.getVoided());
		service.putOpt("uuid", shnPackage.getUuid());
		

		
		Set<SHNPackageDetails> shnPackageDetails = shnPackage.getShnPackageDetails();
		JSONArray packageDetailsArray = new JSONArray();
		for (SHNPackageDetails packageDetails : shnPackageDetails) {
			JSONObject packageDetailsObject = new JSONObject();
			packageDetailsObject.putOpt("packageDetailsId", packageDetails.getPackageDetailsId());
			packageDetailsObject.putOpt("packageItemName", packageDetails.getPackageItemName());
			packageDetailsObject.putOpt("packageItemCode", packageDetails.getPackageItemCode());
			packageDetailsObject.putOpt("packageItemUnitPrice", packageDetails.getPackageItemUnitPrice());
			packageDetailsObject.putOpt("quantity", packageDetails.getQuantity());
			packageDetailsObject.putOpt("packageItemPriceInPackage", packageDetails.getPackageItemUnitPrice());
			packageDetailsArray.put(packageDetailsObject);
		}
		service.putOpt("shnPackageDetails", packageDetailsArray);
		
		return service;
		
	}
	
	public JSONArray toConvert(List<SHNPackage> shnPackages) throws JSONException {
		JSONArray packageJsonArray = new JSONArray();
		for (SHNPackage shnPackage : shnPackages) {
			packageJsonArray.put(toConvert(shnPackage));
		}
		return packageJsonArray;
		
	}
}
