package org.openmrs.module.PSI.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicSpot;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicSpotService;
import org.openmrs.module.PSI.dto.PSILocation;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		List<PSILocation> locations = Context.getService(PSIClinicManagementService.class).findLocationByTag(
		    PSIConstants.TagName);
		model.addAttribute("locations", locations);
		model.addAttribute("pSIClinic", new PSIClinicManagement());
	}
	
	@RequestMapping(value = "/module/PSI/PSIClinicList", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model) {
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
	}
	
	@RequestMapping(value = "/module/PSI/deletePSIClinic", method = RequestMethod.GET)
	public ModelAndView deletePSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		Context.getService(PSIClinicManagementService.class).delete(id);
		return new ModelAndView("redirect:/module/PSI/PSIClinicList.form");
	}
	
	@RequestMapping(value = "/module/PSI/locations", method = RequestMethod.GET)
	public void locations(HttpServletRequest request, HttpSession session, Model model,
	                      @RequestParam(required = true) int locationId) {
		List<PSILocation> locations = new ArrayList<PSILocation>();
		if (locationId != 0) {
			locations = Context.getService(PSIClinicManagementService.class).findByparentLocation(locationId);
		}
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
	}
	
	@RequestMapping(value = "/module/PSI/addPSIClinicSpot", method = RequestMethod.GET)
	public void addPSIClinicSpot(HttpServletRequest request, HttpSession session, Model model,
	                             @RequestParam(required = true) int id) throws JSONException {
		model.addAttribute("pSIClinicSpot", new PSIClinicSpot());
		model.addAttribute("id", id);
	}
	
	@RequestMapping(value = "/module/PSI/editPSIClinicSpot", method = RequestMethod.GET)
	public void editPSIClinicSpot(HttpServletRequest request, HttpSession session, Model model,
	                              @RequestParam(required = true) int id) throws JSONException {
		PSIClinicSpot pSIClinicSpot = Context.getService(PSIClinicSpotService.class).findById(id);
		model.addAttribute("pSIClinicSpot", pSIClinicSpot);
		
	}
}
