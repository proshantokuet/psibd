package org.openmrs.module.PSI.converter;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.SHNVoidedMoneyReceiptLog;

public class SHNVoidedMOneyReceiptDataConverter {

	public JSONObject toConvert(SHNVoidedMoneyReceiptLog psiMoneyReceipt) throws JSONException {
		JSONObject psiMoneyReceiptObject = new JSONObject();
		psiMoneyReceiptObject.putOpt("mid", psiMoneyReceipt.getMoneyReceiptId());
		psiMoneyReceiptObject.putOpt("patientUuid", psiMoneyReceipt.getPatientUuid());
		psiMoneyReceiptObject.putOpt("clinicName", psiMoneyReceipt.getClinicName());
		psiMoneyReceiptObject.putOpt("clinicCode", psiMoneyReceipt.getClinicCode());
		psiMoneyReceiptObject.putOpt("eslipNo", psiMoneyReceipt.geteSlipNo());
		
		return psiMoneyReceiptObject;
		
	}
	
	public JSONArray toConvert(List<SHNVoidedMoneyReceiptLog> psiMoneyReceipts) throws JSONException {
		JSONArray moneyReceipts = new JSONArray();
		for (SHNVoidedMoneyReceiptLog psiMoneyReceipt : psiMoneyReceipts) {
			moneyReceipts.put(toConvert(psiMoneyReceipt));
		}
		return moneyReceipts;
		
	}
	
}
