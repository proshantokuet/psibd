package org.openmrs.module.PSI.web.controller.rest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNStockDetails;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.converter.ClinicServiceConverter;
import org.openmrs.module.PSI.converter.PSIServiceManagementConverter;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;
import org.openmrs.module.PSI.dto.SHNStockDTO;
import org.openmrs.module.PSI.dto.SHNStockDetailsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

@RequestMapping("/rest/v1/stock")
@RestController
public class SHNStockRestController {
	protected final Log log = LogFactory.getLog(getClass());
	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
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
			
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		
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
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/stock-upload", method = RequestMethod.POST)
	public ResponseEntity<String> uploadProduct(@RequestParam MultipartFile file, HttpServletRequest request,
	                                                  ModelMap model, @RequestParam(required = false) int id)
	    throws Exception {
		
		String msg = "";
		String failedMessage = "";
		if (file.isEmpty()) {
			msg = "failed to upload file because its empty";
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
			
		}
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File csvFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		
		try {
			try (InputStream is = file.getInputStream();
			        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(csvFile))) {
				int i;
				
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		}
		catch (IOException e) {
			msg = "failed to process file because : " + e.getMessage();
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		}
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int index = 0;
		int notUploded = 0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			SHNStock stock = null;
			Set<SHNStockDetails> _stockDetails = new HashSet<>();
			while ((line = br.readLine()) != null) {
				String[] service = line.split(cvsSplitBy);
				log.error("Index Size" + index);
				if (index != 0) {
					log.error("Querying psiservice management " + index);
					PSIServiceManagement psiServiceManagement = Context.getService(PSIServiceManagementService.class)
					        .findByCodeAndClinicId(service[3], id);
					log.error("Querying psiservice management result " + psiServiceManagement);
					List<SHNStockDTO> findStockByProductInvoiceAndExpiryDate = new ArrayList<SHNStockDTO> ();
					if(psiServiceManagement != null) {
						findStockByProductInvoiceAndExpiryDate = Context.getService(SHNStockService.class)
					        .findStockByPrductIdInvoiceAndExpiryDate(psiServiceManagement.getSid(), service[1], service[8]);
					}
					log.error("Querying findStockByProductInvoiceAndExpiryDate result " + findStockByProductInvoiceAndExpiryDate.size());
					if (psiServiceManagement != null && findStockByProductInvoiceAndExpiryDate.size() == 0) {
					log.error("Entered to create stock Object" + stock);
					if(stock == null) {
						log.error(" stock invice is null " + stock);
						stock = new SHNStock();
						stock.setUuid(UUID.randomUUID().toString());
						if (!StringUtils.isBlank(service[3])) {
						int number = new Random().nextInt(9999);
						String randomNo = String.format("%04d", number);
						Calendar cal = Calendar.getInstance();
						int day = cal.get(Calendar.DATE);
						int month = cal.get(Calendar.MONTH) + 1;
						int year = cal.get(Calendar.YEAR);
						String dayS = day >= 10 ? "" + day : "0" + day;
						String monthS = month >= 10 ? "" + month : "0" + month;
						String clinicCode = psiServiceManagement.getPsiClinicManagement().getClinicId();
						String stockInId = "" + year + monthS + dayS + clinicCode.substring(0, 3) + randomNo;
						stock.setStockInId(stockInId);
						}
						stock.setDateCreated(new Date());
						String invoiceNo = "";
						if (!StringUtils.isBlank(service[1])) {
							invoiceNo = service[1];
						}
						stock.setInvoiceNumber(invoiceNo);
						stock.setClinicName(psiServiceManagement.getPsiClinicManagement().getName());
						stock.setClinicCode(psiServiceManagement.getPsiClinicManagement().getClinicId());
						Date recieveDate = null;
						if (!StringUtils.isBlank(service[7])) {
							recieveDate = yyyyMMdd.parse(service[7]);
						}
						stock.setReceiveDate(recieveDate);
						stock.setCreator(Context.getAuthenticatedUser());
					}
					log.error(" trying to create stockdetails " + _stockDetails.size());

					SHNStockDetails stockDetails = new SHNStockDetails();
					int debit = 0;
					if (!StringUtils.isBlank(service[6])) {
						debit = Integer.parseInt(service[6]) ;
					}
					stockDetails.setDebit(debit);
					stockDetails.setCredit(0);
					Date expiryDate = null;
					if (!StringUtils.isBlank(service[8])) {
						expiryDate = yyyyMMdd.parse(service[8]);
					}
					stockDetails.setExpiryDate(expiryDate);
					stockDetails.setStockId(stock);
					stockDetails.setProductID(psiServiceManagement.getSid());
					stockDetails.setProductName(psiServiceManagement.getName());
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
			}
				index++;
				
			}
			log.error(" Adding stock details list array  " + _stockDetails.size());
			if(_stockDetails.size() > 0) {
			stock.setStockDetails(_stockDetails);
			Context.getService(SHNStockService.class).saveOrUpdate(stock);
			}
			msg = "Total successfully stock uploaded: " + (_stockDetails.size());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			failedMessage = "failed to process file because : " + e;
			return new ResponseEntity<>(new Gson().toJson(msg + ", and  got error at column : " + (index + 1) + " due to "
			        + failedMessage), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
}
