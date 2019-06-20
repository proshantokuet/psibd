package org.openmrs.module.PSI.web.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.openmrs.module.PSI.converter.PSIMoneyReceiptConverter;
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
	
	@RequestMapping(value = "/add-or-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addMoneyReceipt(@RequestBody String jsonStr) throws Exception {
		
		JSONObject requestBody = new JSONObject(jsonStr);
		JSONObject moneyReceipt = requestBody.getJSONObject("moneyReceipt");
		JSONArray services = requestBody.getJSONArray("services");
		PSIMoneyReceipt psiMoneyReceipt = new PSIMoneyReceipt();
		try {
			
			if (moneyReceipt.has("mid")) {
				if (!moneyReceipt.getString("mid").equalsIgnoreCase("")) {
					psiMoneyReceipt.setMid(Integer.parseInt(moneyReceipt.getString("mid")));
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
				psiMoneyReceipt.setUic(moneyReceipt.getString("contact"));
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
				psiMoneyReceipt.setMoneyReceiptDate(yyyyMMdd.parse(moneyReceipt.getString("moneyReceiptDate")));
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
			psiMoneyReceipt.setDateCreated(new Date());
			psiMoneyReceipt.setCreator(Context.getAuthenticatedUser());
			psiMoneyReceipt.setUuid(UUID.randomUUID().toString());
			
			psiMoneyReceipt.setTimestamp(System.currentTimeMillis());
			Set<PSIServiceProvision> serviceProvisions = new HashSet<PSIServiceProvision>();
			for (int i = 0; i < services.length(); i++) {
				PSIServiceProvision psiServiceProvision = new PSIServiceProvision();
				JSONObject service = services.getJSONObject(i);
				if (service.has("spid")) {
					if (!service.getString("spid").equalsIgnoreCase("")) {
						psiServiceProvision.setSpid(Integer.parseInt(service.getString("spid")));
					}
				}
				if (service.has("item")) {
					JSONObject itemObj = new JSONObject(service.getString("item"));
					psiServiceProvision.setItem(itemObj.getString("name"));
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
					psiServiceProvision.setMoneyReceiptDate(yyyyMMdd.parse(moneyReceipt.getString("moneyReceiptDate")));
				}
				if (moneyReceipt.has("patientUuid")) {
					psiServiceProvision.setPatientUuid(moneyReceipt.getString("patientUuid"));
				}
				
				psiServiceProvision.setDateCreated(new Date());
				psiServiceProvision.setCreator(Context.getAuthenticatedUser());
				psiServiceProvision.setUuid(UUID.randomUUID().toString());
				psiServiceProvision.setTimestamp(System.currentTimeMillis());
				serviceProvisions.add(psiServiceProvision);
			}
			psiMoneyReceipt.setServices(serviceProvisions);
			psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).saveOrUpdate(psiMoneyReceipt);
			
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
}
