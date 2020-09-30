package org.openmrs.module.PSI.web.controller.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNStockDetails;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.converter.PSIServiceManagementConverter;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;
import org.openmrs.module.PSI.dto.SHNStockDTO;
import org.openmrs.module.PSI.dto.SHNStockDetailsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RequestMapping("/rest/v1/stock")
@RestController
public class SHNStockRestController {
	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/save-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> newPatient(@RequestBody SHNStockDTO dto) throws Exception {
		
		JSONObject response = new JSONObject();
		log.error("DTO" + dto);
		try {

			Set<SHNStockDetailsDTO> stockDetailsDTOs = dto.getStockDetails();
			SHNStock stock = Context.getService(SHNStockService.class).findById(dto.getStockId());
			if (stock == null) {
				stock = new SHNStock();
				stock.setUuid(UUID.randomUUID().toString());
				int number = new Random().nextInt(9999);
				String randomNo = String.format("%04d", number);
				Calendar cal = Calendar.getInstance();
				int day = cal.get(Calendar.DATE);
				int month = cal.get(Calendar.MONTH) + 1;
				int year = cal.get(Calendar.YEAR);
				String dayS = day >= 10 ? "" + day : "0" + day;
				String monthS = month >= 10 ? "" + month : "0" + month;
				String stockInId = "" + year + monthS + dayS + dto.getClinicCode().substring(0, 3) + randomNo;
				stock.setStockInId(stockInId);
				stock.setDateCreated(new Date());
			}
/*			if (stock.getId() != null) {
				stock.setStockDetails(null);
				stock.setUpdatedBy(user);
				boolean isDelete = deleteAllByPrimaryKey(stock.getId(), "_stock_details", "stock_id");
				if (!isDelete) {
					return null;
				}
			} */
			
			stock.setClinicName(dto.getClinicName());
			stock.setClinicCode(dto.getClinicCode());
			stock.setInvoiceNumber(dto.getInvoiceNumber());
			stock.setReceiveDate(dto.getReceiveDate());
			stock.setCreator(Context.getAuthenticatedUser());
				
			log.error("STOCK Object Creating Seuccess " + dto.getClinicName());
			Set<SHNStockDetails> _stockDetails = new HashSet<>();
			for (SHNStockDetailsDTO stockDetailsDTO : stockDetailsDTOs) {
				SHNStockDetails stockDetails = new SHNStockDetails();
				stockDetails.setDebit(stockDetailsDTO.getDebit());
				stockDetails.setCredit(stockDetailsDTO.getCredit());
				stockDetails.setExpiryDate(stockDetailsDTO.getExpiryDate());
				stockDetails.setStockId(stock);
				stockDetails.setProductID(stockDetailsDTO.getProductID());
				stockDetails.setProductName(stockDetailsDTO.getProductName());
				stockDetails.setCreator(Context.getAuthenticatedUser());
				stockDetails.setDateCreated(new Date());
				boolean duplicate = false;
				for (SHNStockDetails shnStockDetails : _stockDetails) {

					if(shnStockDetails.getProductID() == stockDetails.getProductID() && shnStockDetails.getExpiryDate().equals(stockDetails.getExpiryDate())) {
						duplicate = true;
						log.error("Ignoring Duplicate" + stockDetails.getProductID() + "With Expiry Date" + stockDetails.getExpiryDate());
					}
				}
				log.error("Duplicate value " + duplicate);
				if(!duplicate) {
					_stockDetails.add(stockDetails);
				}
			}

			stock.setStockDetails(_stockDetails);
			
			SHNStock responseStock =  Context.getService(SHNStockService.class).saveOrUpdate(stock);
			response.put("message", "Stock Successfully Saved");
			response.put("stockId", responseStock.getStkid());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
			response.put("msg", e.getMessage());
			return new ResponseEntity<>(new Gson().toJson(response.toString()), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new Gson().toJson(response.toString()), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/get-product-stock/{clinicId}/{productId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStockById(@PathVariable int clinicId,@PathVariable int productId) throws Exception {

		
		JSONObject stockJsonObject = new JSONObject();
		try {
			List<ClinicServiceDTO> productStock = Context.getService(PSIServiceManagementService.class).getProductListAll(clinicId,productId);
			String stockAvailable = String.valueOf( productStock.get(0).getStock());
			stockJsonObject.put("stock", stockAvailable);
		}
		catch (Exception e) {
			stockJsonObject.put("msg", e.getMessage());
			return new ResponseEntity<String>(stockJsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(stockJsonObject.toString(), HttpStatus.OK);
	}
}
