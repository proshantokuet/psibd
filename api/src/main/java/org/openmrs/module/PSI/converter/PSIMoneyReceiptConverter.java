package org.openmrs.module.PSI.converter;

import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.SHNMoneyReceiptPaymentLog;

public class PSIMoneyReceiptConverter {
	
	public JSONObject toConvert(PSIMoneyReceipt psiMoneyReceipt) throws JSONException {
		JSONObject psiMoneyReceiptAndServicesObject = new JSONObject();
		JSONObject psiMoneyReceiptObject = new JSONObject();
		psiMoneyReceiptObject.putOpt("mid", psiMoneyReceipt.getMid());
		psiMoneyReceiptObject.putOpt("patientName", psiMoneyReceipt.getPatientName());
		psiMoneyReceiptObject.putOpt("patientUuid", psiMoneyReceipt.getPatientUuid());
		psiMoneyReceiptObject.putOpt("uic", psiMoneyReceipt.getUic());
		psiMoneyReceiptObject.putOpt("contact", psiMoneyReceipt.getContact());
		psiMoneyReceiptObject.putOpt("dob", psiMoneyReceipt.getDob());
		psiMoneyReceiptObject.putOpt("address", psiMoneyReceipt.getAddress());
		psiMoneyReceiptObject.putOpt("clinicName", psiMoneyReceipt.getClinicName());
		psiMoneyReceiptObject.putOpt("clinicCode", psiMoneyReceipt.getClinicCode());
		psiMoneyReceiptObject.putOpt("sateliteClinicId", psiMoneyReceipt.getSateliteClinicId());
		psiMoneyReceiptObject.putOpt("teamNo", psiMoneyReceipt.getTeamNo());
		psiMoneyReceiptObject.putOpt("moneyReceiptDate", psiMoneyReceipt.getMoneyReceiptDate());
		psiMoneyReceiptObject.putOpt("reference", psiMoneyReceipt.getReference());
		psiMoneyReceiptObject.putOpt("referenceId", psiMoneyReceipt.getReferenceId());
		psiMoneyReceiptObject.putOpt("shift", psiMoneyReceipt.getShift());
		psiMoneyReceiptObject.putOpt("servicePoint", psiMoneyReceipt.getServicePoint());
		psiMoneyReceiptObject.putOpt("wealth", psiMoneyReceipt.getWealth());
		psiMoneyReceiptObject.putOpt("gender", psiMoneyReceipt.getGender());
		psiMoneyReceiptObject.putOpt("slipNo", psiMoneyReceipt.getSlipNo());
		psiMoneyReceiptObject.putOpt("clinicType", psiMoneyReceipt.getClinicType());
		psiMoneyReceiptObject.putOpt("session", psiMoneyReceipt.getSession());
		psiMoneyReceiptObject.putOpt("other", psiMoneyReceipt.getOthers());
		psiMoneyReceiptObject.putOpt("cspId", psiMoneyReceipt.getCspId());
		psiMoneyReceiptObject.putOpt("orgUnit", psiMoneyReceipt.getOrgUnit());
		psiMoneyReceiptObject.putOpt("designation", psiMoneyReceipt.getDesignation());
		psiMoneyReceiptObject.putOpt("dataCollector", psiMoneyReceipt.getDataCollector());
		psiMoneyReceiptObject.putOpt("getDataCollectorFullname", psiMoneyReceipt.getDataCollectorFullname());
		psiMoneyReceiptObject.putOpt("totalAmount", psiMoneyReceipt.getTotalAmount());
		psiMoneyReceiptObject.putOpt("totalDiscount", psiMoneyReceipt.getTotalDiscount());
		psiMoneyReceiptObject.putOpt("patientRegisteredDate", psiMoneyReceipt.getPatientRegisteredDate());
		psiMoneyReceiptObject.putOpt("eslipNo", psiMoneyReceipt.getEslipNo());
		psiMoneyReceiptObject.putOpt("overallDiscount", psiMoneyReceipt.getOverallDiscount());
		psiMoneyReceiptObject.putOpt("dueAmount", psiMoneyReceipt.getDueAmount());

		
		psiMoneyReceiptAndServicesObject.putOpt("moneyReceipt", psiMoneyReceiptObject);
		
		Set<PSIServiceProvision> psiServiceProvisions = psiMoneyReceipt.getServices();
		JSONArray services = new JSONArray();
		for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
			JSONObject service = new JSONObject();
			service.putOpt("spid", psiServiceProvision.getSpid());
			service.putOpt("item", psiServiceProvision.getItem());
			service.putOpt("description", psiServiceProvision.getDescription());
			service.putOpt("unitCost", psiServiceProvision.getUnitCost());
			service.putOpt("quantity", psiServiceProvision.getQuantity());
			service.putOpt("totalAmount", psiServiceProvision.getTotalAmount());
			service.putOpt("discount", psiServiceProvision.getDiscount());
			service.putOpt("netPayable", psiServiceProvision.getNetPayable());
			service.putOpt("netPayable", psiServiceProvision.getNetPayable());
			service.putOpt("code", psiServiceProvision.getCode());
			service.putOpt("provider", psiServiceProvision.getProvider());
			service.putOpt("category", psiServiceProvision.getCategory());
			service.putOpt("isComplete", psiServiceProvision.getIsComplete());
			service.putOpt("moneyReceiptDate", psiServiceProvision.getMoneyReceiptDate());
			service.putOpt("type", psiServiceProvision.getServiceType());
			service.putOpt("packageUuid", psiServiceProvision.getPackageUuid());
			service.putOpt("uuid", psiServiceProvision.getUuid());
			service.putOpt("financialDiscount", psiServiceProvision.getFinancialDiscount());
			service.putOpt("sendToDhisFromGlobal", psiServiceProvision.getSendToDhisFromGlobal());
			services.put(service);
		}
		psiMoneyReceiptAndServicesObject.putOpt("services", services);
		
		Set<SHNMoneyReceiptPaymentLog> moneyReceiptPaymentLog = psiMoneyReceipt.getPayments();
		JSONArray moneyReceiptPaymentLogArray = new JSONArray();
		for (SHNMoneyReceiptPaymentLog paymentLog : moneyReceiptPaymentLog) {
			JSONObject paymentObj = new JSONObject();
			paymentObj.putOpt("receiveDate", paymentLog.getReceiveDate());
			paymentObj.putOpt("receiveAmount", paymentLog.getReceiveAmount());
			paymentObj.putOpt("uuid", paymentLog.getUuid());
			moneyReceiptPaymentLogArray.put(paymentObj);
		}
		psiMoneyReceiptAndServicesObject.putOpt("payments", moneyReceiptPaymentLogArray);
		return psiMoneyReceiptAndServicesObject;
		
	}
	
	public JSONArray toConvert(List<PSIMoneyReceipt> psiMoneyReceipts) throws JSONException {
		JSONArray moneyReceipts = new JSONArray();
		for (PSIMoneyReceipt psiMoneyReceipt : psiMoneyReceipts) {
			moneyReceipts.put(toConvert(psiMoneyReceipt));
		}
		return moneyReceipts;
		
	}
	
}
