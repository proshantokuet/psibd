package org.openmrs.module.PSI.web.controller.rest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicSpot;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicSpotService;
import org.openmrs.module.PSI.converter.PSIClinicSpotConverter;
import org.openmrs.module.PSI.dto.ClinicDTO;
import org.openmrs.module.PSI.dto.PSIClinicSpotDTO;
import org.openmrs.module.PSI.dto.PSILocation;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
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

@RequestMapping("/rest/v1/clinic/")
@RestController
public class PSIClinicRestController extends MainResourceController {
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveClinic(@RequestBody ClinicDTO clinicDTO, ModelMap model) throws Exception {
		PSILocation getDivison = Context.getService(PSIClinicManagementService.class).findLocationById(
		    clinicDTO.getDivision());
		PSILocation getDistrict = Context.getService(PSIClinicManagementService.class).findLocationById(
		    clinicDTO.getDistrict());
		
		PSILocation getUpazila = Context.getService(PSIClinicManagementService.class).findLocationById(
		    clinicDTO.getUpazila());
		
		PSIClinicManagement psiClinicManagement = new PSIClinicManagement();
		
		psiClinicManagement.setAddress(clinicDTO.getAddress());
		psiClinicManagement.setCategory(clinicDTO.getCategory());
		psiClinicManagement.setClinicId(clinicDTO.getClinicId());
		psiClinicManagement.setName(clinicDTO.getName());
		psiClinicManagement.setDhisId(clinicDTO.getDhisId());
		psiClinicManagement.setDateCreated(new Date());
		psiClinicManagement.setCreator(Context.getAuthenticatedUser());
		psiClinicManagement.setUuid(UUID.randomUUID().toString());
		psiClinicManagement.setTimestamp(System.currentTimeMillis());
		psiClinicManagement.setDivision(getDivison.getName());
		psiClinicManagement.setDivisionUuid(getDivison.getUuid());
		psiClinicManagement.setDivisionId(getDivison.getId());
		
		psiClinicManagement.setDistrict(getDistrict.getName());
		psiClinicManagement.setDistrictUuid(getDistrict.getUuid());
		psiClinicManagement.setDistrictId(getDistrict.getId());
		
		psiClinicManagement.setUpazila(getUpazila.getName());
		psiClinicManagement.setUpazilaUuid(getUpazila.getUuid());
		psiClinicManagement.setUpazilaId(getUpazila.getId());
		if (clinicDTO.getCid() != 0) {
			psiClinicManagement.setCid(clinicDTO.getCid());
		} else {
			psiClinicManagement.setCid(0);
		}
		String msg = "";
		PSIClinicManagement getClinicByClinicId = Context.getService(PSIClinicManagementService.class)
		        .findByIdNotByClinicId(clinicDTO.getCid(), psiClinicManagement.getClinicId());
		if (getClinicByClinicId != null) {
			msg = "This clinic id is already taken";
		} else {
			Context.openSession();
			Context.getService(PSIClinicManagementService.class).saveOrUpdateClinic(psiClinicManagement);
			Context.clearSession();
			msg = "";
		}
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseEntity<String> editClinic(@RequestBody ClinicDTO clinicDTO, ModelMap model) throws Exception {
		
		String msg = "";
		PSIClinicManagement getByIdAndClinicID = Context.getService(PSIClinicManagementService.class).findByIdNotByClinicId(
		    clinicDTO.getCid(), clinicDTO.getClinicId());
		if (getByIdAndClinicID == null) {
			PSIClinicManagement findById = Context.getService(PSIClinicManagementService.class).findById(clinicDTO.getCid());
			findById.setName(clinicDTO.getName());
			findById.setCategory(clinicDTO.getCategory());
			findById.setClinicId(clinicDTO.getClinicId());
			findById.setAddress(clinicDTO.getAddress());
			findById.setDhisId(clinicDTO.getDhisId());
			Context.openSession();
			Context.getService(PSIClinicManagementService.class).saveOrUpdateClinic(findById);
			Context.clearSession();
			msg = "";
		} else {
			msg = "This clinic id is already taken";
		}
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/spot/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveClinicSpot(@RequestBody PSIClinicSpotDTO psiClinicSpotDTO, ModelMap model)
	    throws Exception {
		
		String msg = "";
		PSIClinicSpot psiClinicSpot = new PSIClinicSpot();
		psiClinicSpot.setDateCreated(new Date());
		psiClinicSpot.setCreator(Context.getAuthenticatedUser());
		psiClinicSpot.setUuid(UUID.randomUUID().toString());
		
		psiClinicSpot.setName(psiClinicSpotDTO.getName());
		psiClinicSpot.setCode(psiClinicSpotDTO.getCode());
		psiClinicSpot.setAddress(psiClinicSpotDTO.getAddress());
		psiClinicSpot.setCcsid(psiClinicSpotDTO.getCsid());
		psiClinicSpot.setDhisId(psiClinicSpotDTO.getDhisId());
		PSIClinicManagement psiClinicManagementId = Context.getService(PSIClinicManagementService.class).findById(
		    psiClinicSpotDTO.getPsiClinicManagementId());
		psiClinicSpot.setPsiClinicManagement(psiClinicManagementId);
		psiClinicSpot.setCcsid(psiClinicSpotDTO.getCsid());
		PSIClinicSpot getClinicByClinicId = Context.getService(PSIClinicSpotService.class).findDuplicateSpot(
		    psiClinicSpot.getCcsid(), psiClinicSpot.getCode());
		
		if (getClinicByClinicId != null) {
			msg = "This clinic Spot code is already taken";
		} else {
			
			try {
				Context.openSession();
				Context.getService(PSIClinicSpotService.class).saveOrUpdate(psiClinicSpot);
				Context.clearSession();
				msg = "";
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<>(new Gson().toJson(e.getMessage()), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/spot/get-all-spotlist-by-id/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getAllChildByPatient(@PathVariable int id) throws Exception {
		List<PSIClinicSpot> psiClinicSpots = new ArrayList<PSIClinicSpot>();
		JSONArray psiClinicSpotObject = new JSONArray();
		try {
			psiClinicSpots = Context.getService(PSIClinicSpotService.class).findByClinicId(id);
			if (psiClinicSpots != null) {
				psiClinicSpotObject = new PSIClinicSpotConverter().toConvert(psiClinicSpots);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(psiClinicSpotObject.toString(), HttpStatus.OK);
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/spot/upload", method = RequestMethod.POST)
	public ResponseEntity<String> uploadClinicService(
			@RequestParam MultipartFile file, HttpServletRequest request,
			ModelMap model, @RequestParam(required = false) int id)
			throws Exception {
		String msg = "";
		String failedMessage = "";
		if (file.isEmpty()) {
			msg = "failed to upload file because its empty";
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);

		}

		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File csvFile = new File(dir.getAbsolutePath() + File.separator
				+ file.getOriginalFilename());

		try {
			try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(csvFile))) {
				int i;

				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		} catch (IOException e) {
			msg = "failed to process file because : " + e.getMessage();
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		}

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int index = 0;
		try {
			PSIClinicManagement psiClinicManagement = Context.getService(
					PSIClinicManagementService.class).findById(id);
			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {
				String[] spot = line.split(cvsSplitBy);
				if (index != 0) {
					PSIClinicSpot psiClinicSpot = new PSIClinicSpot();
					String name = "";
					if (!StringUtils.isBlank(spot[0])) {
						name = spot[0];
					}
					psiClinicSpot.setName(name);
					String code = "";
					if (!StringUtils.isBlank(spot[1])) {
						code = spot[1];
					}
					psiClinicSpot.setCode(code);
					String address = "";
					if (!StringUtils.isBlank(spot[2])) {
						address = spot[2];
					}
					psiClinicSpot.setAddress(address);
					psiClinicSpot.setPsiClinicManagement(psiClinicManagement);
					String orgId = "";
					if (!StringUtils.isBlank(spot[3])) {
						orgId = spot[3];
					}
					psiClinicSpot.setDhisId(orgId);

					psiClinicSpot.setDateCreated(new Date());
					psiClinicSpot.setCreator(Context
							.getAuthenticatedUser());
					psiClinicSpot.setUuid(UUID.randomUUID().toString());
					Context.getService(PSIClinicSpotService.class)
							.saveOrUpdate(psiClinicSpot);

				}
				index++;
			}
			msg = "Total successfully Spot uploaded: " + (index - 1);

		} catch (Exception e) {
			e.printStackTrace();
			failedMessage = "failed to process file because : " + e;
			return new ResponseEntity<>(new Gson().toJson(msg
					+ ", and  got error at column : " + (index + 1)
					+ " due to " + failedMessage), HttpStatus.OK);
		}

		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);

	}
}
