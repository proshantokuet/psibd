package org.openmrs.module.PSI.web.controller;

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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCClinicType;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicSpot;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.AUHCClinicTypeService;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicSpotService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.dto.PSILocation;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PSIClinicManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	final String USER_URL = "ws/rest/v1/user";
	
	final String LOCATION_URL = "ws/rest/v1/location";
	
	@RequestMapping(value = "/module/PSI/addPSIClinic", method = RequestMethod.GET)
	public void addPSIClinic(HttpServletRequest request, HttpSession session, Model model) throws JSONException {
		/*HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + USER_URL + "/", "v=default",
		    "superman", "Admin123");
		JSONObject body = new JSONObject(op.body());
		JSONArray users = new JSONArray(body.getJSONArray("results").toString());
		JSONArray usernamesArray = new JSONArray();
		for (int i = 0; i < users.length(); i++) {
			JSONObject nameObject = new JSONObject();
			JSONObject user = (JSONObject) users.get(i);
			JSONObject person = user.getJSONObject("person");
			nameObject.put("username", user.get("username"));
			nameObject.put("display", person.get("display"));
			usernamesArray.put(nameObject);
		}
		
		session.setAttribute("users", usernamesArray.toString());*/
		List<AUHCClinicType> clinicTypeList = Context.getService(AUHCClinicTypeService.class).getAll();
		model.addAttribute("clinicTypes",clinicTypeList);
		List<PSILocation> locations = Context.getService(PSIClinicManagementService.class).findLocationByTag(
		    PSIConstants.TagName);
		model.addAttribute("locations", locations);
		model.addAttribute("pSIClinic", new PSIClinicManagement());
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
		
	}
	
	@RequestMapping(value = "/module/PSI/PSIClinicList", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model) {
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		model.addAttribute("pSIClinics", Context.getService(PSIClinicManagementService.class).getAllClinic());
	}
	
	@RequestMapping(value = "/module/PSI/editPSIClinic", method = RequestMethod.GET)
	public void editPSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		PSIClinicManagement clinic = Context.getService(PSIClinicManagementService.class).findById(id);
		
		List<PSILocation> divisions = Context.getService(PSIClinicManagementService.class).findLocationByTag(
		    PSIConstants.TagName);
		List<PSILocation> districts = Context.getService(PSIClinicManagementService.class).findByparentLocation(
		    clinic.getDivisionId());
		List<PSILocation> upazilas = Context.getService(PSIClinicManagementService.class).findByparentLocation(
		    clinic.getDistrictId());
		model.addAttribute("pSIClinic", clinic);
		model.addAttribute("divisions", divisions);
		model.addAttribute("districts", districts);
		model.addAttribute("upazilas", upazilas);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
	}
	
	@RequestMapping(value = "/module/PSI/deletePSIClinic", method = RequestMethod.GET)
	public ModelAndView deletePSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		Context.getService(PSIClinicManagementService.class).delete(id);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
		return new ModelAndView("redirect:/module/PSI/PSIClinicList.form");
	}
	
	@RequestMapping(value = "/module/PSI/locations", method = RequestMethod.GET)
	public void locations(HttpServletRequest request, HttpSession session, Model model,
	                      @RequestParam(required = true) int locationId) {
		List<PSILocation> locations = new ArrayList<PSILocation>();
		if (locationId != 0) {
			locations = Context.getService(PSIClinicManagementService.class).findByparentLocation(locationId);
		}
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		model.addAttribute("locations", locations);
	}
	
	@RequestMapping(value = "/module/PSI/districts", method = RequestMethod.GET)
	public void districts(HttpServletRequest request, HttpSession session, Model model,
	                      @RequestParam(required = true) int districtId) {
		List<PSILocation> locations = new ArrayList<PSILocation>();
		if (districtId != 0) {
			locations = Context.getService(PSIClinicManagementService.class).findByparentLocation(districtId);
		}
		model.addAttribute("locations", locations);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/addPsiClinic", method = RequestMethod.POST)
	public ModelAndView addORUpdatePSIClinic(@ModelAttribute("psiClinic") PSIClinicManagement psiClinic,
	                                         HttpSession session, ModelMap model) throws Exception {
		
		if (psiClinic != null) {
			log.info("saving new module objects...................");
			psiClinic.setDateCreated(new Date());
			psiClinic.setCreator(Context.getAuthenticatedUser());
			psiClinic.setUuid(UUID.randomUUID().toString());
			
			/*Set<PSIClinicUser> clinicUser = new HashSet<PSIClinicUser>();
			for (String user : users) {
				
				PSIClinicUser pSIClinicUser = new PSIClinicUser();
				pSIClinicUser.setUserName(user);
				pSIClinicUser.setDateCreated(new Date());
				pSIClinicUser.setCreator(Context.getAuthenticatedUser());
				pSIClinicUser.setUuid(UUID.randomUUID().toString());
				clinicUser.add(pSIClinicUser);
				
			}
			psiClinic.setpSIClinicUser(clinicUser);*/
			
			Context.getService(PSIClinicManagementService.class).saveOrUpdateClinic(psiClinic);
			
			return new ModelAndView("redirect:/module/PSI/PSIClinicList.form");
			
		}
		return null;
	}
	
	@RequestMapping(value = "/module/PSI/PSIClinicSpotList", method = RequestMethod.GET)
	public void pSIClinicSpotList(HttpServletRequest request, HttpSession session, Model model,
	                              @RequestParam(required = true) int id) {
		model.addAttribute("pSIClinicSpots", Context.getService(PSIClinicSpotService.class).findByClinicId(id));
		model.addAttribute("id", id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/addPSIClinicSpot", method = RequestMethod.GET)
	public void addPSIClinicSpot(HttpServletRequest request, HttpSession session, Model model,
	                             @RequestParam(required = true) int id) throws JSONException {
		model.addAttribute("pSIClinicSpot", new PSIClinicSpot());
		model.addAttribute("id", id);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/uploadPSIClinicSpot", method = RequestMethod.GET)
	public void uploadPSIClinicSpot(HttpServletRequest request, HttpSession session, Model model,
	                             @RequestParam(required = true) int id) throws JSONException {
		model.addAttribute("pSIClinicSpot", new PSIClinicSpot());
		model.addAttribute("id", id);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/module/PSI/uploadPSIClinicSpot", method = RequestMethod.POST)
	public void uploadPSIClinicService(@RequestParam MultipartFile file, HttpServletRequest request, ModelMap model,
	                                           @RequestParam(required = false) int id) throws Exception {
		
		String msg = "";
		if (file.isEmpty()) {
			// model.put("msg", "failed to upload file because its empty");
			model.addAttribute("msg", "failed to upload file because its empty");
			model.addAttribute("id", id);
			// return new ModelAndView("redirect:/module/PSI/uploadPSIClinicSpot.form?id=" + id);
		} else if (!"text/csv".equalsIgnoreCase(file.getContentType())) {
			model.addAttribute("msg", "file type should be '.csv'");
			model.addAttribute("id", id);
			// return new ModelAndView("redirect:/module/PSI/uploadPSIClinicSpot.form?id=" + id);
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
			model.put("msg", "failed to process file because : " + e.getMessage());
			//return new ModelAndView("redirect:/module/PSI/uploadPSIClinicSpot.form?id=" + id);
		}
		log.info("CSV FIle:" + csvFile.getName());
		
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
			model.addAttribute("pSIClinicSpot", new PSIClinicSpot());
			model.addAttribute("id", id);
			model.addAttribute("msg","Total successfully Spot uploaded: " + (index - 1));
		}
		catch (Exception e) {
			log.info("Some problem occured, please contact with admin..");
			msg = "failed to process file because : " + e.fillInStackTrace();
			e.printStackTrace();
			//return new ModelAndView("redirect:/module/PSI/uploadPSIClinicSpot.form?id=" + id);
		}
		//return new ModelAndView("redirect:/module/PSI/uploadPSIClinicSpot.form?id=" + id);
		
	}
	
	@RequestMapping(value = "/module/PSI/editPSIClinicSpot", method = RequestMethod.GET)
	public void editPSIClinicSpot(HttpServletRequest request, HttpSession session, Model model,
	                              @RequestParam(required = true) int id) throws JSONException {
		PSIClinicSpot pSIClinicSpot = Context.getService(PSIClinicSpotService.class).findById(id);
		model.addAttribute("pSIClinicSpot", pSIClinicSpot);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
	}
}
