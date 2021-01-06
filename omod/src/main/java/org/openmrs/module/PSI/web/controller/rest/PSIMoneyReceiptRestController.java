package org.openmrs.module.PSI.web.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.SHNEslipNoGenerate;
import org.openmrs.module.PSI.SHNMoneyReceiptPaymentLog;
import org.openmrs.module.PSI.SHNRefundedMoneyReceipt;
import org.openmrs.module.PSI.SHNRefundedMoneyReceiptDetails;
import org.openmrs.module.PSI.SHNRefundedMoneyReceiptPaymentLog;
import org.openmrs.module.PSI.SHNVoidedMoneyReceiptLog;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.api.PSIUniqueIdGeneratorService;
import org.openmrs.module.PSI.api.SHNPackageService;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.api.SHNVoidedMoneyReceiptLogService;
import org.openmrs.module.PSI.converter.PSIMoneyReceiptConverter;
import org.openmrs.module.PSI.converter.SHNVoidedMOneyReceiptDataConverter;
import org.openmrs.module.PSI.dto.SHNPackageReportDTO;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/money-receipt")
@RestController
public class PSIMoneyReceiptRestController extends MainResourceController {
	
	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat dateFormatTwentyFourHour = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/add-or-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addMoneyReceipt(@RequestBody String jsonStr) throws Exception {
		
		JSONObject requestBody = new JSONObject(jsonStr);
		JSONObject moneyReceipt = requestBody.getJSONObject("moneyReceipt");
		JSONArray services = null;
		int submitted = 0;
		if (moneyReceipt.has("isComplete")) {
			submitted = moneyReceipt.getInt("isComplete");
		}
		log.error("is complete " + submitted);
		if(submitted == 1) {
			boolean shouldExtractPackage = true;
			if (moneyReceipt.has("paymentStatus")) {
				String status = moneyReceipt.getString("paymentStatus");
				if(status.equalsIgnoreCase("REFUND")) {
					shouldExtractPackage = false;
				}
			}
			log.error("Check Payment Status " + shouldExtractPackage);
			if(shouldExtractPackage) {
				log.error("Trying to Extract packages " + shouldExtractPackage);
				services = extractPackageItems(requestBody.getJSONArray("services"));
			}
			else {
				services = requestBody.getJSONArray("services");
			}
		}
		else {
			services = requestBody.getJSONArray("services");
		}
		JSONArray payments = requestBody.getJSONArray("payments");
		PSIMoneyReceipt psiMoneyReceipt = null;
		int moneyReceiptId = 0;
		try {
			
			if (moneyReceipt.has("mid")) {
				if (!moneyReceipt.getString("mid").equalsIgnoreCase("")) {
					moneyReceiptId = Integer.parseInt(moneyReceipt.getString("mid"));
					psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).findById(moneyReceiptId);
					//psiMoneyReceipt.setMid(moneyReceiptId);
				}else{
					psiMoneyReceipt = new PSIMoneyReceipt();
					psiMoneyReceipt.setUuid(UUID.randomUUID().toString());
				}
			}
			boolean saveToRefundtable = false;
			
			if (moneyReceipt.has("paymentStatus")) {
				String status = moneyReceipt.getString("paymentStatus");
				if(status.equalsIgnoreCase("REFUND")) {
					saveToRefundtable = true;
				}
			}
			boolean isSaveTORefundSucess = false;
			if(saveToRefundtable) {
				if (!moneyReceipt.getString("mid").equalsIgnoreCase("")) {
					isSaveTORefundSucess = saveRefundMoneyReceiptData(psiMoneyReceipt);
					isSaveTORefundSucess = true;
				}
			}
			if (moneyReceipt.has("patientName")) {
				psiMoneyReceipt.setPatientName(moneyReceipt.getString("patientName"));
			}
			if (moneyReceipt.has("session")) {
				psiMoneyReceipt.setSession(moneyReceipt.getString("session"));
			}
			if (moneyReceipt.has("other")) {
				psiMoneyReceipt.setOthers(moneyReceipt.getString("other"));
			}
			
			if (moneyReceipt.has("patientUuid")) {
				psiMoneyReceipt.setPatientUuid(moneyReceipt.getString("patientUuid"));
			}
			
			if (moneyReceipt.has("uic")) {
				psiMoneyReceipt.setUic(moneyReceipt.getString("uic"));
			}
			
			if (moneyReceipt.has("contact")) {
				psiMoneyReceipt.setContact(moneyReceipt.getString("contact"));
			}
			
			if (moneyReceipt.has("dob")) {
				psiMoneyReceipt.setDob(yyyyMMdd.parse(moneyReceipt.getString("dob")));
			}
			
			if (moneyReceipt.has("address")) {
				psiMoneyReceipt.setAddress(moneyReceipt.getString("address"));
			}
			if (moneyReceipt.has("clinicName")) {
				psiMoneyReceipt.setClinicName(moneyReceipt.getString("clinicName"));
			}
			
			if (moneyReceipt.has("clinicCode")) {
				psiMoneyReceipt.setClinicCode(moneyReceipt.getString("clinicCode"));
			}
			if (moneyReceipt.has("sateliteClinicId")) {
				psiMoneyReceipt.setSateliteClinicId(moneyReceipt.getString("sateliteClinicId"));
			}
			
			if (moneyReceipt.has("teamNo")) {
				psiMoneyReceipt.setTeamNo(moneyReceipt.getString("teamNo"));
			}
			
			if (moneyReceipt.has("moneyReceiptDate")) {
				psiMoneyReceipt.setMoneyReceiptDate(dateFormatTwentyFourHour.parse(moneyReceipt.getString("moneyReceiptDate")));
			}
			
			if (moneyReceipt.has("reference")) {
				psiMoneyReceipt.setReference(moneyReceipt.getString("reference"));
			}
			
			if (moneyReceipt.has("referenceId")) {
				psiMoneyReceipt.setReferenceId(moneyReceipt.getString("referenceId"));
			}
			if (moneyReceipt.has("shift")) {
				psiMoneyReceipt.setShift(moneyReceipt.getString("shift"));
			}
			
			if (moneyReceipt.has("servicePoint")) {
				psiMoneyReceipt.setServicePoint(moneyReceipt.getString("servicePoint"));
			}
			
			if (moneyReceipt.has("session")) {
				psiMoneyReceipt.setSession(moneyReceipt.getString("session"));
			}
			
			if (moneyReceipt.has("other")) {
				psiMoneyReceipt.setOthers(moneyReceipt.getString("other"));
			}
			if (moneyReceipt.has("cspId")) {
				psiMoneyReceipt.setCspId(moneyReceipt.getString("cspId"));
			}
			
			if (moneyReceipt.has("wealth")) {
				psiMoneyReceipt.setWealth(moneyReceipt.getString("wealth"));
			}
			
			if (moneyReceipt.has("orgUnit")) {
				psiMoneyReceipt.setOrgUnit(moneyReceipt.getString("orgUnit"));
			}
			
			if (moneyReceipt.has("gender")) {
				psiMoneyReceipt.setGender(moneyReceipt.getString("gender"));
			}
			
			if (moneyReceipt.has("slipNo")) {
				psiMoneyReceipt.setSlipNo(moneyReceipt.getString("slipNo"));
			}
			if (moneyReceipt.has("clinicType")) {
				psiMoneyReceipt.setClinicType(moneyReceipt.getString("clinicType"));
			}
			
			if (moneyReceipt.has("dataCollector")) {
				JSONObject designationObj = new JSONObject(moneyReceipt.getString("dataCollector"));
				psiMoneyReceipt.setDesignation(designationObj.getString("designation"));
				psiMoneyReceipt.setDataCollector(designationObj.getString("username"));
			}
			if (moneyReceipt.has("totalAmount")) {
				psiMoneyReceipt.setTotalAmount(Float.parseFloat(moneyReceipt.getString("totalAmount")));
			}
			
			if (moneyReceipt.has("totalDiscount")) {
				psiMoneyReceipt.setTotalDiscount(Float.parseFloat(moneyReceipt.getString("totalDiscount")));
			}
			
			if (moneyReceipt.has("patientRegisteredDate")) {
				psiMoneyReceipt.setPatientRegisteredDate(yyyyMMdd.parse(moneyReceipt.getString("patientRegisteredDate")));
			}
			
			if (moneyReceipt.has("isComplete")) {
				psiMoneyReceipt.setIsComplete(moneyReceipt.getInt("isComplete"));
			}
			
			if (moneyReceipt.has("mid")) {
				if (!moneyReceipt.getString("mid").equalsIgnoreCase("")) {
					String generatedEslip = editGeneratedEslipNo(psiMoneyReceipt.getServicePoint(), psiMoneyReceipt.getSateliteClinicId(), psiMoneyReceipt.getCspId(), psiMoneyReceipt.getEslipNo());
					psiMoneyReceipt.setEslipNo(generatedEslip);
				}
				else{
					String generatedEslip = generateEslipNo(psiMoneyReceipt.getServicePoint(), psiMoneyReceipt.getSateliteClinicId(), psiMoneyReceipt.getCspId(), psiMoneyReceipt.getClinicCode());
					psiMoneyReceipt.setEslipNo(generatedEslip);
				}
			}
			
			if (moneyReceipt.has("dueAmount")) {
				psiMoneyReceipt.setDueAmount(Float.parseFloat(moneyReceipt.getString("dueAmount")));
			}
			
			if (moneyReceipt.has("overallDiscount")) {
				psiMoneyReceipt.setOverallDiscount(Float.parseFloat(moneyReceipt.getString("overallDiscount")));
			}
			
			psiMoneyReceipt.setDateCreated(new Date());
			psiMoneyReceipt.setCreator(Context.getAuthenticatedUser());
			
			psiMoneyReceipt.setTimestamp(System.currentTimeMillis());
			
			/*List<PSIServiceProvision> getProvisions = Context.getService(PSIServiceProvisionService.class)
			        .findAllByMoneyReceiptId(psiMoneyReceipt.getMid());
			for (PSIServiceProvision psiServiceProvision : getProvisions) {
				Context.getService(PSIServiceProvisionService.class).delete(psiServiceProvision.getSpid());
			}*/
			//psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).saveOrUpdate(psiMoneyReceipt);
			
			Set<PSIServiceProvision> serviceProvisions = new HashSet<PSIServiceProvision>();
			for (int i = 0; i < services.length(); i++) {
				PSIServiceProvision psiServiceProvision = new PSIServiceProvision();
				JSONObject service = services.getJSONObject(i);
				if (service.has("spid")) {
					if (!service.getString("spid").equalsIgnoreCase("")) {
						psiServiceProvision = Context.getService(PSIServiceProvisionService.class).findById(Integer.parseInt(service.getString("spid")));
						//psiServiceProvision.setSpid(Integer.parseInt(service.getString("spid")));
					}else{
						psiServiceProvision.setUuid(UUID.randomUUID().toString());
					}
				}
				if (service.has("item")) {
					JSONObject itemObj = new JSONObject(service.getString("item"));
					psiServiceProvision.setItem(itemObj.getString("name"));
					psiServiceProvision.setCategory(itemObj.getString("category"));
				}
				if (service.has("description")) {
					psiServiceProvision.setDescription(service.getString("description"));
				}
				if (service.has("code")) {
					JSONObject codeObj = new JSONObject(service.getString("code"));
					psiServiceProvision.setCode(codeObj.getString("code"));
				}
				if (service.has("category")) {
					psiServiceProvision.setCategory(service.getString("category"));
				}
				if (service.has("provider")) {
					psiServiceProvision.setProvider(service.getString("provider"));
				}
				if (service.has("unitCost")) {
					psiServiceProvision.setUnitCost(Float.parseFloat(service.getString("unitCost")));
				}
				if (service.has("quantity")) {
					psiServiceProvision.setQuantity(Integer.parseInt(service.getString("quantity")));
				}
				if (service.has("totalAmount")) {
					psiServiceProvision.setTotalAmount(Float.parseFloat(service.getString("totalAmount")));
				}
				
				if (service.has("discount")) {
					psiServiceProvision.setDiscount(Float.parseFloat(service.getString("discount")));
				}
				if (service.has("netPayable")) {
					psiServiceProvision.setNetPayable(Float.parseFloat(service.getString("netPayable")));
				}
				if (service.has("type")) {
					psiServiceProvision.setServiceType(service.getString("type"));
				}
				if (service.has("packageUuid")) {
					psiServiceProvision.setPackageUuid(service.getString("packageUuid"));
				}
				
				if (moneyReceipt.has("moneyReceiptDate")) {
					psiServiceProvision.setMoneyReceiptDate(dateFormatTwentyFourHour.parse(moneyReceipt.getString("moneyReceiptDate")));
				}
				if (moneyReceipt.has("patientUuid")) {
					psiServiceProvision.setPatientUuid(moneyReceipt.getString("patientUuid"));
				}
				if (moneyReceipt.has("isComplete")) {
					psiServiceProvision.setIsComplete(moneyReceipt.getInt("isComplete"));
					
				}
				psiServiceProvision.setIsSendToDHIS(PSIConstants.DEFAULTERRORSTATUS);
				psiServiceProvision.setDateCreated(new Date());
				psiServiceProvision.setCreator(Context.getAuthenticatedUser());
				
				psiServiceProvision.setTimestamp(System.currentTimeMillis());
				//psiServiceProvision.setPsiMoneyReceiptId(psiMoneyReceipt);
				
				//Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
				serviceProvisions.add(psiServiceProvision);
			}
			
			psiMoneyReceipt.setServices(serviceProvisions);
			Set<SHNMoneyReceiptPaymentLog> paymentsList = null;
			if (moneyReceipt.has("mid")) {
				if (!moneyReceipt.getString("mid").equalsIgnoreCase("")) {
					paymentsList = psiMoneyReceipt.getPayments();
				}
				else{
					paymentsList = new HashSet<SHNMoneyReceiptPaymentLog>();
				}
			}
			
			for (int i = 0; i < payments.length(); i++) {
				SHNMoneyReceiptPaymentLog paymentObject = new SHNMoneyReceiptPaymentLog();
				JSONObject paymentJsonObj = payments.getJSONObject(i);

				if (paymentJsonObj.has("receiveDate")) {

					paymentObject.setReceiveDate(dateFormatTwentyFourHour.parse(paymentJsonObj.getString("receiveDate")));
				}
				if (paymentJsonObj.has("receiveAmount")) {
					paymentObject.setReceiveAmount(Float.parseFloat(paymentJsonObj.getString("receiveAmount")));
				}
				
				paymentObject.setDateCreated(new Date());
				paymentObject.setEslipNo(psiMoneyReceipt.getEslipNo());
				paymentObject.setCreator(Context.getAuthenticatedUser());
				paymentObject.setUuid(UUID.randomUUID().toString());
				paymentObject.setPsiMoneyReceiptId(psiMoneyReceipt);
				paymentsList.add(paymentObject);
			}
			psiMoneyReceipt.setPayments(paymentsList);

			if(saveToRefundtable && isSaveTORefundSucess || !saveToRefundtable && !isSaveTORefundSucess) {
				psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).saveOrUpdate(psiMoneyReceipt);
				if (moneyReceipt.has("mid")) {
					if (!moneyReceipt.getString("mid").equalsIgnoreCase("")) {
						Context.getService(PSIServiceProvisionService.class).deleteByPatientUuidAndMoneyReceiptIdNull(moneyReceipt.getString("patientUuid"));
					}
				}
				
				if(saveToRefundtable && isSaveTORefundSucess) {
					if(psiMoneyReceipt.getIsComplete() == 1) {
						// change stock after refunded
						PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findByClinicId(psiMoneyReceipt.getClinicCode());
						Context.getService(SHNStockService.class).stockUpdateAfterRefund(psiMoneyReceipt.getEslipNo(), psiClinicManagement.getClinicId(), psiClinicManagement.getCid());
					}
				}
				if(!saveToRefundtable && !isSaveTORefundSucess) {
					if(psiMoneyReceipt.getIsComplete() == 1) {
						
						// change stock after money receipt confirmed
						Context.getService(SHNStockService.class).updateStockByEarliestExpiryDate(psiMoneyReceipt.getEslipNo(), psiMoneyReceipt.getClinicCode());
					}
				}
			}
			else{
				return new ResponseEntity<String>("Refund Table Data Could not Be saved", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		catch (Exception e) {

			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(psiMoneyReceipt.getMid() + "", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable int id) throws Exception {

		try {

			Context.getService(PSIMoneyReceiptService.class).delete(id);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Deleted Money Receipt Successfull", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/void-money-receipt-by-eslip/{eslip}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String eslip) throws Exception {

		try {
			
			PSIMoneyReceipt psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).getMoneyReceiptByESlipNo(eslip);
			if(psiMoneyReceipt !=null) {
				Context.getService(SHNStockService.class).deleteMoneyReceiptStockUpdate(psiMoneyReceipt.getEslipNo(), psiMoneyReceipt.getClinicCode());
				Context.getService(PSIMoneyReceiptService.class).delete(psiMoneyReceipt.getMid());
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No E-slip Found in server", HttpStatus.OK);
			}
			
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value = "/void-money-receipt/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> voidMOneyReceipt(@PathVariable int id) throws Exception {
		JSONObject deleteMoneyReceiptObject = new JSONObject();

		try {
			String dhisId = "";
			PSIMoneyReceipt psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).findById(id);
			if(psiMoneyReceipt !=null) {
				PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findByClinicId(psiMoneyReceipt.getClinicCode());
				String updatedStock = Context.getService(SHNStockService.class).deleteMoneyReceiptStockUpdate(psiMoneyReceipt.getEslipNo(), psiMoneyReceipt.getClinicCode());
				Context.getService(PSIMoneyReceiptService.class).delete(id);
				SHNVoidedMoneyReceiptLog shnVoidedLog = new SHNVoidedMoneyReceiptLog();
				shnVoidedLog.setMoneyReceiptId(psiMoneyReceipt.getMid());
				shnVoidedLog.setClinicName(psiMoneyReceipt.getClinicName());
				shnVoidedLog.setClinicCode(psiMoneyReceipt.getClinicCode());
				shnVoidedLog.seteSlipNo(psiMoneyReceipt.getEslipNo());
				shnVoidedLog.setSlipNo(psiMoneyReceipt.getSlipNo());
				shnVoidedLog.setPatientUuid(psiMoneyReceipt.getPatientUuid());
				shnVoidedLog.setClinicId(psiClinicManagement.getCid());
				shnVoidedLog.setDateCreated(new Date());
				shnVoidedLog.setCreator(Context.getAuthenticatedUser());
				for (PSIServiceProvision psiServiceProvision : psiMoneyReceipt.getServices()) {
					
					dhisId = dhisId + psiServiceProvision.getDhisId() + ",";
				}
				if (dhisId.endsWith(",")) {
					dhisId = dhisId.substring(0, dhisId.length() - 1);
					}
				shnVoidedLog.setDhisId(dhisId);
				Context.getService(SHNVoidedMoneyReceiptLogService.class).saveOrUpdate(shnVoidedLog);
				deleteMoneyReceiptObject.put("isSuccessfull", true);
				deleteMoneyReceiptObject.put("e-slip", psiMoneyReceipt.getEslipNo());
				deleteMoneyReceiptObject.put("updatedStock", updatedStock);
				deleteMoneyReceiptObject.put("mid", psiMoneyReceipt.getMid());
			}
			else {
				deleteMoneyReceiptObject.put("isSuccessfull", false);
				deleteMoneyReceiptObject.put("message", "No Money Receipt Found with provided E-slip No");
			}
		}
		catch (Exception e) {
			deleteMoneyReceiptObject.put("isSuccessfull", false);
			deleteMoneyReceiptObject.put("message", e.getMessage());
			return new ResponseEntity<String>(deleteMoneyReceiptObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(deleteMoneyReceiptObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> findById(@PathVariable int id) throws Exception {
		PSIMoneyReceipt psiMoneyReceipt = new PSIMoneyReceipt();
		JSONObject psiMoneyReceiptAndServicesObject = new JSONObject();
		try {
			psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).findById(id);
			if (psiMoneyReceipt != null) {
				psiMoneyReceiptAndServicesObject = new PSIMoneyReceiptConverter().toConvert(psiMoneyReceipt);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(psiMoneyReceiptAndServicesObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/geteslip/{eslipNo}", method = RequestMethod.GET)
	public ResponseEntity<String> findByEslipNo(@PathVariable String eslipNo) throws Exception {
		PSIMoneyReceipt psiMoneyReceipt = new PSIMoneyReceipt();
		JSONObject psiMoneyReceiptAndServicesObject = new JSONObject();
		try {
			psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).getMoneyReceiptByESlipNo(eslipNo);
			if (psiMoneyReceipt != null) {
				psiMoneyReceiptAndServicesObject = new PSIMoneyReceiptConverter().toConvert(psiMoneyReceipt);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(psiMoneyReceiptAndServicesObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-all-by-patient-uuid/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getAllByPatient(@PathVariable String id) throws Exception {
		List<PSIMoneyReceipt> psiMoneyReceipt = new ArrayList<PSIMoneyReceipt>();
		JSONArray psiMoneyReceiptAndServicesObject = new JSONArray();
		try {
			psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).getAllByPatient(id);
			if (psiMoneyReceipt != null) {
				psiMoneyReceiptAndServicesObject = new PSIMoneyReceiptConverter().toConvert(psiMoneyReceipt);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(psiMoneyReceiptAndServicesObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/check-existing-money-receipt/{slipNo}/{date}/{clinicCode}", method = RequestMethod.GET)
	public ResponseEntity<String> getAllByPatient(@PathVariable String slipNo,@PathVariable String date,@PathVariable String clinicCode) throws Exception {
		try {
			 Boolean isMoneyReceiptAvailable = Context.getService(PSIMoneyReceiptService.class).checkExistingMoneyReceipt(slipNo, date, clinicCode);
			 return new ResponseEntity<String>(isMoneyReceiptAvailable.toString(), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
	}
	
	private String generateEslipNo (String servicePoint, String sateliteId, String cspId, String code) {
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		
		SHNEslipNoGenerate shnEslipNoGenerate = new SHNEslipNoGenerate();
		
		SHNEslipNoGenerate getLastSlipNoByclinic = Context.getService(PSIUniqueIdGeneratorService.class)
		        .findEslipByClinicCodeAndDate(today, code);
		
		shnEslipNoGenerate.setClinicCode(code);
		shnEslipNoGenerate.setGenerateId(0);
		
		shnEslipNoGenerate.setDateCreated(new Date());
		if (getLastSlipNoByclinic.getGenerateId() == 0) {
			shnEslipNoGenerate.setGenerateId(0 + 1);
		} else {
			shnEslipNoGenerate.setGenerateId(getLastSlipNoByclinic.getGenerateId() + 1);
		}
		//Context.openSession();
		SHNEslipNoGenerate afterSaveSlip = Context.getService(PSIUniqueIdGeneratorService.class).saveOrUpdate(shnEslipNoGenerate);
		//Context.clearSession();
		String serquenceNumber = "";
		String serquenceNumberToString = afterSaveSlip.getGenerateId() + "";
		if (serquenceNumberToString.length() == 1) {
			serquenceNumber += "000" + serquenceNumberToString;
		} else if (serquenceNumberToString.length() == 2) {
			serquenceNumber += "00" + serquenceNumberToString;
		} else if (serquenceNumberToString.length() == 3) {
			serquenceNumber += "0" + serquenceNumberToString;
		} else {
			serquenceNumber = serquenceNumberToString;
		}
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		String dayS = day >= 10 ? "" + day : "0" + day;
		String monthS = month >= 10 ? "" + month : "0" + month;
		DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		String year = df.format(Calendar.getInstance().getTime());
//		String a = "value of " + servicePoint + sateliteId + cspId + code;
//		SHNEslipNoGenerate shnEslipNoGenerateagain = new SHNEslipNoGenerate();
//		shnEslipNoGenerateagain.setClinicCode(a);
//		shnEslipNoGenerateagain.setGenerateId(1005);
//		shnEslipNoGenerate.setDateCreated(new Date());
//		Context.openSession();
//		Context.getService(PSIUniqueIdGeneratorService.class).saveOrUpdate(shnEslipNoGenerateagain);
//		Context.clearSession();
		String concatenedString = "";
		if (servicePoint.equalsIgnoreCase("Static")) {
			concatenedString = "100";
		}
		else if (servicePoint.equalsIgnoreCase("Satellite")) {
			String satId = sateliteId.length() < 2 ? "0" + sateliteId : sateliteId;
			if (satId.length() > 2) satId = satId.substring(0,2);
			concatenedString = "2" + satId;
		}
		else if(servicePoint.equalsIgnoreCase("CSP")){
			String cspIdString = cspId.length() < 2 ? "0" + cspId : cspId;
			if (cspIdString.length() > 2) cspIdString = cspIdString.substring(0,2);
			concatenedString = "3" + cspIdString;
		}
		else if (servicePoint.equalsIgnoreCase("Telemedicine")) {
			concatenedString = "400";
		}
		String eslipId = "" + year + monthS + dayS + code.substring(0, 3)+ concatenedString + serquenceNumber;

		return eslipId;
	}
	
	private String editGeneratedEslipNo (String servicePoint, String sateliteId, String cspId, String eslipNo) {
		
		String concatenedString = "";
		String finalString = "";
		if (servicePoint.equalsIgnoreCase("Static")) {
			concatenedString = "100";
			finalString = replaceAtEslip(eslipNo, 9, concatenedString);
		}
		else if (servicePoint.equalsIgnoreCase("Satellite")) {
			String satId = sateliteId.length() < 2 ? "0" + sateliteId : sateliteId;
			if (satId.length() > 2) satId = satId.substring(0,2);
			concatenedString = "2" + satId;
			finalString = replaceAtEslip(eslipNo, 9, concatenedString);
		}
		else if(servicePoint.equalsIgnoreCase("CSP")){
			String cspIdString = cspId.length() < 2 ? "0" + cspId : cspId;
			if (cspIdString.length() > 2) cspIdString = cspIdString.substring(0,2);
			concatenedString = "3" + cspIdString;
			finalString = replaceAtEslip(eslipNo, 9, concatenedString);
		}
		else if(servicePoint.equalsIgnoreCase("Telemedicine")) {
			concatenedString = "400";
			finalString = replaceAtEslip(eslipNo, 9, concatenedString);
		}
		return finalString;
	}
	
	private String replaceAtEslip (String eslip, int index, String replacement) {
		return eslip.substring(0, index) + replacement+ eslip.substring(index + replacement.length());
	}
	
	
	@RequestMapping(value = "/get-voided-money-receipt/{clinicid}", method = RequestMethod.GET)
	public ResponseEntity<String> getAllVoidedMoneyReceipt(@PathVariable int clinicid) throws Exception {
		List<SHNVoidedMoneyReceiptLog> psiMoneyReceipt = new ArrayList<SHNVoidedMoneyReceiptLog>();
		JSONArray psiMoneyReceiptAndServicesObject = new JSONArray();
		try {
			psiMoneyReceipt = Context.getService(SHNVoidedMoneyReceiptLogService.class).getAllVoidedMoneyReceiptByClinic(clinicid);
			if(psiMoneyReceipt.size() > 0) {
				psiMoneyReceiptAndServicesObject = new SHNVoidedMOneyReceiptDataConverter().toConvert(psiMoneyReceipt);

			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(psiMoneyReceiptAndServicesObject.toString(), HttpStatus.OK);
	}
	@RequestMapping(value = "/save-in-global", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> globalServerMoneyReceipt(@RequestBody String jsonStr) throws Exception {
		
		JSONObject requestBody = new JSONObject(jsonStr);
		JSONObject moneyReceipt = requestBody.getJSONObject("moneyReceipt");
		JSONArray services = requestBody.getJSONArray("services");
		JSONArray payments = requestBody.getJSONArray("payments");

		PSIMoneyReceipt psiMoneyReceipt = null;
		try {
			boolean saveToRefundtable = false;
			boolean isSaveTORefundSucess = false;
			if (moneyReceipt.has("eslipNo")) {
				String eslipNo = moneyReceipt.getString("eslipNo");
				psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).getMoneyReceiptByESlipNo(eslipNo);
				
				if(psiMoneyReceipt == null) {
					psiMoneyReceipt = new PSIMoneyReceipt();
					psiMoneyReceipt.setUuid(UUID.randomUUID().toString());
				}
				else {
					saveToRefundtable = true;
					log.error("saveToRefundtable " + saveToRefundtable);
					if(saveToRefundtable) {
						isSaveTORefundSucess = saveRefundMoneyReceiptData(psiMoneyReceipt);
					}
				}
			}
			if (moneyReceipt.has("patientName")) {
				psiMoneyReceipt.setPatientName(moneyReceipt.getString("patientName"));
			}
			if (moneyReceipt.has("session")) {
				psiMoneyReceipt.setSession(moneyReceipt.getString("session"));
			}
			if (moneyReceipt.has("other")) {
				psiMoneyReceipt.setOthers(moneyReceipt.getString("other"));
			}
			
			if (moneyReceipt.has("patientUuid")) {
				psiMoneyReceipt.setPatientUuid(moneyReceipt.getString("patientUuid"));
			}
			
			if (moneyReceipt.has("uic")) {
				psiMoneyReceipt.setUic(moneyReceipt.getString("uic"));
			}
			
			if (moneyReceipt.has("contact")) {
				psiMoneyReceipt.setContact(moneyReceipt.getString("contact"));
			}
			
			if (moneyReceipt.has("dob")) {
				psiMoneyReceipt.setDob(yyyyMMdd.parse(moneyReceipt.getString("dob")));
			}
			
			if (moneyReceipt.has("address")) {
				psiMoneyReceipt.setAddress(moneyReceipt.getString("address"));
			}
			if (moneyReceipt.has("clinicName")) {
				psiMoneyReceipt.setClinicName(moneyReceipt.getString("clinicName"));
			}
			
			if (moneyReceipt.has("clinicCode")) {
				psiMoneyReceipt.setClinicCode(moneyReceipt.getString("clinicCode"));
			}
			if (moneyReceipt.has("sateliteClinicId")) {
				psiMoneyReceipt.setSateliteClinicId(moneyReceipt.getString("sateliteClinicId"));
			}
			
			if (moneyReceipt.has("teamNo")) {
				psiMoneyReceipt.setTeamNo(moneyReceipt.getString("teamNo"));
			}
			
			if (moneyReceipt.has("moneyReceiptDate")) {
				psiMoneyReceipt.setMoneyReceiptDate(dateFormatTwentyFourHour.parse(moneyReceipt.getString("moneyReceiptDate")));
			}
			
			if (moneyReceipt.has("reference")) {
				psiMoneyReceipt.setReference(moneyReceipt.getString("reference"));
			}
			
			if (moneyReceipt.has("referenceId")) {
				psiMoneyReceipt.setReferenceId(moneyReceipt.getString("referenceId"));
			}
			if (moneyReceipt.has("shift")) {
				psiMoneyReceipt.setShift(moneyReceipt.getString("shift"));
			}
			
			if (moneyReceipt.has("servicePoint")) {
				psiMoneyReceipt.setServicePoint(moneyReceipt.getString("servicePoint"));
			}
			
			if (moneyReceipt.has("session")) {
				psiMoneyReceipt.setSession(moneyReceipt.getString("session"));
			}
			
			if (moneyReceipt.has("other")) {
				psiMoneyReceipt.setOthers(moneyReceipt.getString("other"));
			}
			if (moneyReceipt.has("cspId")) {
				psiMoneyReceipt.setCspId(moneyReceipt.getString("cspId"));
			}
			
			if (moneyReceipt.has("wealth")) {
				psiMoneyReceipt.setWealth(moneyReceipt.getString("wealth"));
			}
			
			if (moneyReceipt.has("orgUnit")) {
				psiMoneyReceipt.setOrgUnit(moneyReceipt.getString("orgUnit"));
			}
			
			if (moneyReceipt.has("gender")) {
				psiMoneyReceipt.setGender(moneyReceipt.getString("gender"));
			}
			
			if (moneyReceipt.has("slipNo")) {
				psiMoneyReceipt.setSlipNo(moneyReceipt.getString("slipNo"));
			}
			if (moneyReceipt.has("clinicType")) {
				psiMoneyReceipt.setClinicType(moneyReceipt.getString("clinicType"));
			}
			
			if (moneyReceipt.has("dataCollector")) {
				
			}
			if (moneyReceipt.has("dataCollector")) {
				JSONObject designationObj = new JSONObject(moneyReceipt.getString("dataCollector"));
				psiMoneyReceipt.setDesignation(designationObj.getString("designation"));
				psiMoneyReceipt.setDataCollector(designationObj.getString("username"));
			}
			if (moneyReceipt.has("totalAmount")) {
				psiMoneyReceipt.setTotalAmount(Float.parseFloat(moneyReceipt.getString("totalAmount")));
			}
			
			if (moneyReceipt.has("totalDiscount")) {
				psiMoneyReceipt.setTotalDiscount(Float.parseFloat(moneyReceipt.getString("totalDiscount")));
			}
			
			if (moneyReceipt.has("patientRegisteredDate")) {
				psiMoneyReceipt.setPatientRegisteredDate(yyyyMMdd.parse(moneyReceipt.getString("patientRegisteredDate")));
			}
			
			if (moneyReceipt.has("isComplete")) {
				psiMoneyReceipt.setIsComplete(moneyReceipt.getInt("isComplete"));
			}
			
			
			if (moneyReceipt.has("eslipNo")) {
				psiMoneyReceipt.setEslipNo(moneyReceipt.getString("eslipNo"));
			}
			if (moneyReceipt.has("dueAmount")) {
				psiMoneyReceipt.setDueAmount(Float.parseFloat(moneyReceipt.getString("dueAmount")));
			}
			
			if (moneyReceipt.has("overallDiscount")) {
				psiMoneyReceipt.setOverallDiscount(Float.parseFloat(moneyReceipt.getString("overallDiscount")));
			}
			
			psiMoneyReceipt.setDateCreated(new Date());
			psiMoneyReceipt.setCreator(Context.getAuthenticatedUser());
			
			psiMoneyReceipt.setTimestamp(System.currentTimeMillis());

			Set<PSIServiceProvision> serviceProvisions = new HashSet<PSIServiceProvision>();
			for (int i = 0; i < services.length(); i++) {
				PSIServiceProvision psiServiceProvision = null;
				JSONObject service = services.getJSONObject(i);
				if (service.has("uuid")) {
					String uuid = service.getString("uuid");
					psiServiceProvision = Context.getService(PSIServiceProvisionService.class).findByUuid(uuid);
					if(psiServiceProvision == null) {
						psiServiceProvision = new PSIServiceProvision();
						psiServiceProvision.setDateCreated(new Date());
						psiServiceProvision.setUuid(uuid);
					}
					else {
						psiServiceProvision.setDateChanged(new Date());
					}

				}

				if (service.has("item")) {
					JSONObject itemObj = new JSONObject(service.getString("item"));
					psiServiceProvision.setItem(itemObj.getString("name"));
					psiServiceProvision.setCategory(itemObj.getString("category"));
				}
				if (service.has("description")) {
					psiServiceProvision.setDescription(service.getString("description"));
				}
				if (service.has("code")) {
					JSONObject codeObj = new JSONObject(service.getString("code"));
					psiServiceProvision.setCode(codeObj.getString("code"));
				}
				if (service.has("category")) {
					psiServiceProvision.setCategory(service.getString("category"));
				}
				if (service.has("provider")) {
					psiServiceProvision.setProvider(service.getString("provider"));
				}
				if (service.has("unitCost")) {
					psiServiceProvision.setUnitCost(Float.parseFloat(service.getString("unitCost")));
				}
				if (service.has("quantity")) {
					psiServiceProvision.setQuantity(Integer.parseInt(service.getString("quantity")));
				}
				if (service.has("totalAmount")) {
					psiServiceProvision.setTotalAmount(Float.parseFloat(service.getString("totalAmount")));
				}
				
				if (service.has("discount")) {
					psiServiceProvision.setDiscount(Float.parseFloat(service.getString("discount")));
				}
				if (service.has("netPayable")) {
					psiServiceProvision.setNetPayable(Float.parseFloat(service.getString("netPayable")));
				}
				if (service.has("type")) {
					psiServiceProvision.setServiceType(service.getString("type"));
				}
				if (service.has("packageUuid")) {
					psiServiceProvision.setPackageUuid(service.getString("packageUuid"));
				}
				
				if (moneyReceipt.has("moneyReceiptDate")) {
					psiServiceProvision.setMoneyReceiptDate(dateFormatTwentyFourHour.parse(moneyReceipt.getString("moneyReceiptDate")));
				}
				if (moneyReceipt.has("patientUuid")) {
					psiServiceProvision.setPatientUuid(moneyReceipt.getString("patientUuid"));
				}
				if (moneyReceipt.has("isComplete")) {
					psiServiceProvision.setIsComplete(moneyReceipt.getInt("isComplete"));
					
				}
				psiServiceProvision.setIsSendToDHIS(PSIConstants.DEFAULTERRORSTATUS);
				
				psiServiceProvision.setCreator(Context.getAuthenticatedUser());
				
				psiServiceProvision.setTimestamp(System.currentTimeMillis());
				//psiServiceProvision.setPsiMoneyReceiptId(psiMoneyReceipt);
				
				//Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
				serviceProvisions.add(psiServiceProvision);
			}
			
			psiMoneyReceipt.setServices(serviceProvisions);
			Set<SHNMoneyReceiptPaymentLog> paymentsList = new HashSet<SHNMoneyReceiptPaymentLog>();
			
			for (int i = 0; i < payments.length(); i++) {
				
				SHNMoneyReceiptPaymentLog paymentObject = null;
				JSONObject paymentJsonObj = payments.getJSONObject(i);
				if(paymentJsonObj.has("uuid")) {
					String uuid = paymentJsonObj.getString("uuid");
					paymentObject = Context.getService(PSIMoneyReceiptService.class).findByByuuid(uuid);
					if(paymentObject == null) {
						paymentObject = new SHNMoneyReceiptPaymentLog();
						paymentObject.setUuid(uuid);
						paymentObject.setDateCreated(new Date());
					}
				}
				if (paymentJsonObj.has("receiveDate")) {

					paymentObject.setReceiveDate(dateFormatTwentyFourHour.parse(paymentJsonObj.getString("receiveDate")));
				}
				if (paymentJsonObj.has("receiveAmount")) {
					paymentObject.setReceiveAmount(Float.parseFloat(paymentJsonObj.getString("receiveAmount")));
				}
				paymentObject.setEslipNo(psiMoneyReceipt.getEslipNo());
				paymentObject.setCreator(Context.getAuthenticatedUser());
				paymentObject.setPsiMoneyReceiptId(psiMoneyReceipt);
				paymentsList.add(paymentObject);
			}
			psiMoneyReceipt.setPayments(paymentsList);
			

			log.error("isSaveTORefundSucess " + isSaveTORefundSucess);
			if(saveToRefundtable && isSaveTORefundSucess || !saveToRefundtable && !isSaveTORefundSucess) {
				psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).saveOrUpdate(psiMoneyReceipt);
//				if (saveToRefundtable && isSaveTORefundSucess) {
//					Context.getService(PSIServiceProvisionService.class).deleteByPatientUuidAndMoneyReceiptIdNull(moneyReceipt.getString("patientUuid"));
//				}
			}
			else{
				return new ResponseEntity<String>("Refund Table Data Could not Be saved".toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch (Exception e) {

			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(psiMoneyReceipt.getMid() + "", HttpStatus.OK);
	}
	
	
	private JSONArray extractPackageItems(JSONArray serviceToExtract) {
		log.error("Entering to extract message" + serviceToExtract.length()) ;
		JSONArray extractedJsonArray = new JSONArray();
		JSONArray arrayWithoutPackage = new JSONArray();

		for (int i = 0; i < serviceToExtract.length(); i++) {
			JSONObject service;
			try {
				service = serviceToExtract.getJSONObject(i);
				log.error("get json object from first loop" + service.toString()) ;
				if (service.has("type")) {
					String serviceType = service.getString("type");
					log.error("serviceType from first loop" + serviceType) ;
					if(!serviceType.equalsIgnoreCase("PACKAGE")) {
						arrayWithoutPackage.put(service);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		
		log.error("Extract service from package" + arrayWithoutPackage.length()) ;
		
		for (int i = 0; i < serviceToExtract.length(); i++) {
			JSONObject service;
			try {
				service = serviceToExtract.getJSONObject(i);
				log.error("Entered seconD loop to extract" + service.toString()) ;
				if (service.has("type")) {
					String serviceType = service.getString("type");
					log.error("Service  type" + serviceType) ;
					if(serviceType.equalsIgnoreCase("PACKAGE")) {
						if (service.has("item")) {
							JSONObject itemObj = new JSONObject(service.getString("item"));
							int packageID = Integer.parseInt(itemObj.getString("sid"));
							log.error("packageID" + packageID) ;
							int quantity = Integer.parseInt(service.getString("quantity"));
							log.error("quantity" + quantity) ;
							float discount = Float.parseFloat(service.getString("discount"));
							List<SHNPackageReportDTO> shnPackageDetailsList = Context.getService(SHNPackageService.class).getPackageByPackageIdForEdit(packageID);
							for (SHNPackageReportDTO shnPackageDetails : shnPackageDetailsList) {
								JSONObject serviceObject = new JSONObject();
								JSONObject itemObject = new JSONObject();
								JSONObject codeObject = new JSONObject();
								serviceObject.putOpt("spid", "");
								itemObject.putOpt("name", shnPackageDetails.getItemName());
								itemObject.putOpt("category", "");
								serviceObject.putOpt("item", itemObject);
								serviceObject.putOpt("description", "");
								codeObject.putOpt("code", shnPackageDetails.getItemCode());
								serviceObject.putOpt("code", codeObject);
								serviceObject.putOpt("provider", "");
								serviceObject.putOpt("unitCost", shnPackageDetails.getUnitPriceInPackage());
								int totalQuantity = shnPackageDetails.getQuantity() * quantity;
								serviceObject.putOpt("quantity", totalQuantity);
								float totalAmount = shnPackageDetails.getUnitPriceInPackage() * totalQuantity;
								serviceObject.putOpt("totalAmount", totalAmount);
								serviceObject.putOpt("netPayable", totalAmount);
								serviceObject.putOpt("discount", 0);
								serviceObject.putOpt("type", "PACKAGE");
								serviceObject.putOpt("packageUuid", shnPackageDetails.getUuid());
								extractedJsonArray.put(serviceObject);
							}
						}
						
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		log.error("Extraction done json array" + extractedJsonArray.toString()) ;
		
		for (int i = 0; i < extractedJsonArray.length(); i++) {
			try {
				arrayWithoutPackage.put(extractedJsonArray.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arrayWithoutPackage;
		
	}
	
	private boolean saveRefundMoneyReceiptData(PSIMoneyReceipt psiMoneyReceipt) {
		log.error("now in refund money receipt table" + psiMoneyReceipt.getClinicCode());

		boolean isSaved = true;
		try {
			//SHNRefundedMoneyReceipt shnRefundedMoneyReceipt = null;
			
			SHNRefundedMoneyReceipt shnRefundedMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).getRefundMoneyReceiptByMid(psiMoneyReceipt.getMid());
			
			if(shnRefundedMoneyReceipt == null) {
				shnRefundedMoneyReceipt = new SHNRefundedMoneyReceipt();
				shnRefundedMoneyReceipt.setDateCreated(new Date());
				shnRefundedMoneyReceipt.setUuid(psiMoneyReceipt.getUuid());
			}
			shnRefundedMoneyReceipt.setMoneyReceiptId(psiMoneyReceipt.getMid());
			shnRefundedMoneyReceipt.setPatientName(psiMoneyReceipt.getPatientName());
			shnRefundedMoneyReceipt.setPatientUuid(psiMoneyReceipt.getPatientUuid());
			shnRefundedMoneyReceipt.setUic(psiMoneyReceipt.getUic());
			shnRefundedMoneyReceipt.setContact(psiMoneyReceipt.getContact());
			shnRefundedMoneyReceipt.setDob(psiMoneyReceipt.getDob());
			shnRefundedMoneyReceipt.setAddress(psiMoneyReceipt.getAddress());
			shnRefundedMoneyReceipt.setClinicName(psiMoneyReceipt.getClinicName());
			shnRefundedMoneyReceipt.setClinicCode(psiMoneyReceipt.getClinicCode());
			shnRefundedMoneyReceipt.setSateliteClinicId(psiMoneyReceipt.getSateliteClinicId());
			shnRefundedMoneyReceipt.setTeamNo(psiMoneyReceipt.getTeamNo());
			shnRefundedMoneyReceipt.setMoneyReceiptDate(psiMoneyReceipt.getMoneyReceiptDate());
			shnRefundedMoneyReceipt.setReference(psiMoneyReceipt.getReferenceId());
			shnRefundedMoneyReceipt.setReferenceId(psiMoneyReceipt.getReferenceId());
			shnRefundedMoneyReceipt.setShift(psiMoneyReceipt.getShift());
			shnRefundedMoneyReceipt.setServicePoint(psiMoneyReceipt.getServicePoint());
			shnRefundedMoneyReceipt.setWealth(psiMoneyReceipt.getWealth());
			shnRefundedMoneyReceipt.setGender(psiMoneyReceipt.getGender());
			shnRefundedMoneyReceipt.setSlipNo(psiMoneyReceipt.getSlipNo());
			shnRefundedMoneyReceipt.setClinicType(psiMoneyReceipt.getClinicType());
			shnRefundedMoneyReceipt.setOrgUnit(psiMoneyReceipt.getOrgUnit());
			shnRefundedMoneyReceipt.setSession(psiMoneyReceipt.getSession());
			shnRefundedMoneyReceipt.setOthers(psiMoneyReceipt.getOthers());
			shnRefundedMoneyReceipt.setCspId(psiMoneyReceipt.getCspId());
			shnRefundedMoneyReceipt.setDataCollector(psiMoneyReceipt.getDataCollector());
			shnRefundedMoneyReceipt.setDesignation(psiMoneyReceipt.getDesignation());
			shnRefundedMoneyReceipt.setTotalAmount(psiMoneyReceipt.getTotalAmount());
			shnRefundedMoneyReceipt.setTotalDiscount(psiMoneyReceipt.getTotalDiscount());
			shnRefundedMoneyReceipt.setPatientRegisteredDate(psiMoneyReceipt.getPatientRegisteredDate());
			shnRefundedMoneyReceipt.setIsComplete(psiMoneyReceipt.getIsComplete());
			shnRefundedMoneyReceipt.setEslipNo(psiMoneyReceipt.getEslipNo());
			shnRefundedMoneyReceipt.setDueAmount(psiMoneyReceipt.getDueAmount());
			shnRefundedMoneyReceipt.setOverallDiscount(psiMoneyReceipt.getOverallDiscount());
			shnRefundedMoneyReceipt.setField1(psiMoneyReceipt.getField1());
			shnRefundedMoneyReceipt.setField2(psiMoneyReceipt.getField2());
			shnRefundedMoneyReceipt.setField3(0);
			shnRefundedMoneyReceipt.setTimestamp(psiMoneyReceipt.getTimestamp());
			
			shnRefundedMoneyReceipt.setCreator(psiMoneyReceipt.getCreator());
			
			
			Set<SHNRefundedMoneyReceiptDetails> moneyReceiptDetails = new HashSet<SHNRefundedMoneyReceiptDetails>();
			
			for (PSIServiceProvision serviceProvision : psiMoneyReceipt.getServices()) {
				
					SHNRefundedMoneyReceiptDetails details = Context.getService(PSIMoneyReceiptService.class).getRefundMoneyReceiptDetailsByServiceId(serviceProvision.getSpid());
					if(details == null) {
						details = new SHNRefundedMoneyReceiptDetails();
						details.setDateCreated(new Date());
						details.setUuid(serviceProvision.getUuid());
					}
					details.setServiceProvisionId(serviceProvision.getSpid());
					details.setCategory(serviceProvision.getCategory());
					details.setDescription(serviceProvision.getDescription());
					details.setCode(serviceProvision.getCode());
					details.setCategory(serviceProvision.getCategory());
					details.setProvider(serviceProvision.getProvider());
					details.setUnitCost(serviceProvision.getUnitCost());
					details.setQuantity(serviceProvision.getQuantity());
					details.setTotalAmount(serviceProvision.getTotalAmount());
					details.setDiscount(serviceProvision.getDiscount());
					details.setNetPayable(serviceProvision.getNetPayable());
					details.setServiceType(serviceProvision.getServiceType());
					details.setPackageUuid(serviceProvision.getPackageUuid());
					details.setMoneyReceiptDate(serviceProvision.getMoneyReceiptDate());
					details.setPatientUuid(serviceProvision.getPatientUuid());
					details.setIsComplete(serviceProvision.getIsComplete());
					details.setIsSendToDHIS(serviceProvision.getIsSendToDHIS());
					details.setCreator(Context.getAuthenticatedUser());
					details.setTimestamp(serviceProvision.getTimestamp());
					details.setRefundedMoneyReceiptId(shnRefundedMoneyReceipt);
					moneyReceiptDetails.add(details);
			}
			shnRefundedMoneyReceipt.setServices(moneyReceiptDetails);
			log.error("SHNRefundedMoneyReceiptDetails in refund function " + moneyReceiptDetails.size());
			Set<SHNRefundedMoneyReceiptPaymentLog> paymentsList = new HashSet<SHNRefundedMoneyReceiptPaymentLog>();
			
			for (SHNMoneyReceiptPaymentLog shnMoneyReceiptPaymentLog : psiMoneyReceipt.getPayments()) {
				
				SHNRefundedMoneyReceiptPaymentLog shnRefundedMoneyReceiptPaymentLog = Context.getService(PSIMoneyReceiptService.class).getRefunMoneyReceiptPaymentLogByUuid(shnMoneyReceiptPaymentLog.getUuid());
				if(shnRefundedMoneyReceiptPaymentLog == null) {
					shnRefundedMoneyReceiptPaymentLog = new SHNRefundedMoneyReceiptPaymentLog();
					shnRefundedMoneyReceiptPaymentLog.setUuid(shnMoneyReceiptPaymentLog.getUuid());
					shnRefundedMoneyReceiptPaymentLog.setDateCreated(new Date());
				}
				shnRefundedMoneyReceiptPaymentLog.setReceiveDate(shnMoneyReceiptPaymentLog.getReceiveDate());
				shnRefundedMoneyReceiptPaymentLog.setReceiveAmount(shnMoneyReceiptPaymentLog.getReceiveAmount());
				shnRefundedMoneyReceiptPaymentLog.setEslipNo(shnMoneyReceiptPaymentLog.getEslipNo());
				shnRefundedMoneyReceiptPaymentLog.setCreator(shnMoneyReceiptPaymentLog.getCreator());
				shnRefundedMoneyReceiptPaymentLog.setRefundedMoneyReceiptId(shnRefundedMoneyReceipt);
				paymentsList.add(shnRefundedMoneyReceiptPaymentLog);
			}
			shnRefundedMoneyReceipt.setPayments(paymentsList);
			log.error("paymentsList in refund function " + paymentsList.size());

			SHNRefundedMoneyReceipt shnRefundedMoneyReceiptResponse = Context.getService(PSIMoneyReceiptService.class).saveOrUpdateRefund(shnRefundedMoneyReceipt);
		} 
		catch (Exception e) {
			
			log.error("Exception in refund function " + e.getMessage().toString());
			isSaved = false;
		}
		
		return isSaved;
	}
	
}
