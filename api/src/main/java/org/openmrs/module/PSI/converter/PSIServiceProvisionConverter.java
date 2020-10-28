package org.openmrs.module.PSI.converter;

import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.SHNMoneyReceiptPaymentLog;

public class PSIServiceProvisionConverter {
	
	public JSONObject toConvert(PSIServiceProvision psiServiceProvisions) throws JSONException {
		
		JSONObject service = new JSONObject();
		service.putOpt("spid", psiServiceProvisions.getSpid());
		service.putOpt("item", psiServiceProvisions.getItem());
		service.putOpt("description", psiServiceProvisions.getDescription());
		service.putOpt("unitCost", psiServiceProvisions.getUnitCost());
		service.putOpt("quantity", psiServiceProvisions.getQuantity());
		service.putOpt("totalAmount", psiServiceProvisions.getTotalAmount());
		service.putOpt("discount", psiServiceProvisions.getDiscount());
		service.putOpt("netPayable", psiServiceProvisions.getNetPayable());
		service.putOpt("moneyReceiptDate", psiServiceProvisions.getMoneyReceiptDate());
		service.putOpt("code", psiServiceProvisions.getCode());
		service.putOpt("provider", psiServiceProvisions.getProvider());
		service.putOpt("category", psiServiceProvisions.getCategory());
		service.putOpt("mid", psiServiceProvisions.getPsiMoneyReceiptId().getMid());
		service.putOpt("patientName", psiServiceProvisions.getPsiMoneyReceiptId().getPatientName());
		service.putOpt("patientUuid", psiServiceProvisions.getPsiMoneyReceiptId().getPatientUuid());
		service.putOpt("uic", psiServiceProvisions.getPsiMoneyReceiptId().getUic());
		service.putOpt("contact", psiServiceProvisions.getPsiMoneyReceiptId().getContact());
		service.putOpt("dob", psiServiceProvisions.getPsiMoneyReceiptId().getDob());
		service.putOpt("address", psiServiceProvisions.getPsiMoneyReceiptId().getAddress());
		service.putOpt("clinicName", psiServiceProvisions.getPsiMoneyReceiptId().getClinicName());
		service.putOpt("clinicCode", psiServiceProvisions.getPsiMoneyReceiptId().getClinicCode());
		service.putOpt("sateliteClinicId", psiServiceProvisions.getPsiMoneyReceiptId().getSateliteClinicId());
		service.putOpt("teamNo", psiServiceProvisions.getPsiMoneyReceiptId().getTeamNo());
		service.putOpt("moneyReceiptDate", psiServiceProvisions.getPsiMoneyReceiptId().getMoneyReceiptDate());
		service.putOpt("reference", psiServiceProvisions.getPsiMoneyReceiptId().getReference());
		service.putOpt("referenceId", psiServiceProvisions.getPsiMoneyReceiptId().getReferenceId());
		service.putOpt("shift", psiServiceProvisions.getPsiMoneyReceiptId().getShift());
		service.putOpt("servicePoint", psiServiceProvisions.getPsiMoneyReceiptId().getServicePoint());
		service.putOpt("wealth", psiServiceProvisions.getPsiMoneyReceiptId().getWealth());
		service.putOpt("gender", psiServiceProvisions.getPsiMoneyReceiptId().getGender());
		service.putOpt("slipNo", psiServiceProvisions.getPsiMoneyReceiptId().getSlipNo());
		service.putOpt("clinicType", psiServiceProvisions.getPsiMoneyReceiptId().getClinicType());
		service.putOpt("session", psiServiceProvisions.getPsiMoneyReceiptId().getSession());
		service.putOpt("other", psiServiceProvisions.getPsiMoneyReceiptId().getOthers());
		service.putOpt("cspId", psiServiceProvisions.getPsiMoneyReceiptId().getCspId());
		service.putOpt("dataCollector", psiServiceProvisions.getPsiMoneyReceiptId().getDataCollector());
		service.putOpt("designation", psiServiceProvisions.getPsiMoneyReceiptId().getDesignation());
		service.putOpt("isComplete", psiServiceProvisions.getIsComplete());
		service.putOpt("eslipNo", psiServiceProvisions.getPsiMoneyReceiptId().getEslipNo());
		service.putOpt("overallDiscount", psiServiceProvisions.getPsiMoneyReceiptId().getOverallDiscount());
		service.putOpt("dueAmount", psiServiceProvisions.getPsiMoneyReceiptId().getDueAmount());
		service.putOpt("netPayableAfterDiscount", psiServiceProvisions.getPsiMoneyReceiptId().getTotalAmount());
		service.putOpt("type", psiServiceProvisions.getServiceType());

		
		Set<SHNMoneyReceiptPaymentLog> moneyReceiptPaymentLog = psiServiceProvisions.getPsiMoneyReceiptId().getPayments();
		JSONArray moneyReceiptPaymentLogArray = new JSONArray();
		for (SHNMoneyReceiptPaymentLog paymentLog : moneyReceiptPaymentLog) {
			JSONObject paymentObj = new JSONObject();
			paymentObj.putOpt("receiveDate", paymentLog.getReceiveDate());
			paymentObj.putOpt("receiveAmount", paymentLog.getReceiveAmount());
			moneyReceiptPaymentLogArray.put(paymentObj);
		}
		service.putOpt("payments", moneyReceiptPaymentLogArray);
		
		return service;
		
	}
	
	public JSONArray toConvert(List<PSIServiceProvision> psiServiceProvisions) throws JSONException {
		JSONArray moneyReceipts = new JSONArray();
		for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
			moneyReceipts.put(toConvert(psiServiceProvision));
		}
		return moneyReceipts;
		
	}
	
}
