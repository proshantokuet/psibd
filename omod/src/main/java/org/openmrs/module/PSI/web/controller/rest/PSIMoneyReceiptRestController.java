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

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.SHNEslipNoGenerate;
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.api.PSIUniqueIdGeneratorService;
import org.openmrs.module.PSI.converter.PSIMoneyReceiptConverter;
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
	
	@RequestMapping(value = "/add-or-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addMoneyReceipt(@RequestBody String jsonStr) throws Exception {
		
		JSONObject requestBody = new JSONObject(jsonStr);
		JSONObject moneyReceipt = requestBody.getJSONObject("moneyReceipt");
		JSONArray services = requestBody.getJSONArray("services");
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
			
//			if (moneyReceipt.has("eslipNo")) {
//				psiMoneyReceipt.setEslipNo(moneyReceipt.getString("eslipNo"));
//			}
//			
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
			psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).saveOrUpdate(psiMoneyReceipt);
			if (moneyReceipt.has("mid")) {
				if (!moneyReceipt.getString("mid").equalsIgnoreCase("")) {
					Context.getService(PSIServiceProvisionService.class).deleteByPatientUuidAndMoneyReceiptIdNull(moneyReceipt.getString("patientUuid"));
				}
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
		return new ResponseEntity<String>("ok", HttpStatus.OK);
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
	
}
