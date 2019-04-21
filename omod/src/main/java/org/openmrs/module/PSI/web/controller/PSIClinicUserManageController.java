/**
 * 
 */
package org.openmrs.module.PSI.web.controller;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.utils.ClinicUser;
import org.openmrs.module.PSI.utils.HttpResponse;
import org.openmrs.module.PSI.utils.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author proshanto
 */
@Controller
public class PSIClinicUserManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private static final String OPENMRS_BASE_URL = "https://192.168.33.10/openmrs";
	
	final String USER_URL = "ws/rest/v1/user";
	
	@RequestMapping(value = "/module/PSI/addPSIClinicUser", method = RequestMethod.GET)
	public ModelAndView addPSIClinic(HttpServletRequest request, HttpSession session, Model model,
	                                 @RequestParam(required = true) int id) throws JSONException {
		model.addAttribute("pSIClinicUser", new PSIClinicUser());
		model.addAttribute("psiClinicManagementId", id);
		
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + USER_URL + "/", "v=default",
		    "superman", "Admin123");
		JSONObject body = new JSONObject(op.body());
		JSONArray users = new JSONArray(body.getJSONArray("results").toString());
		JSONArray usernamesArray = new JSONArray();
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		
		Set<PSIClinicUser> psiClinicUsers = psiClinicManagement.getpSIClinicUser();
		for (int i = 0; i < users.length(); i++) {
			JSONObject nameObject = new JSONObject();
			JSONObject user = (JSONObject) users.get(i);
			JSONObject person = user.getJSONObject("person");
			if (!ClinicUser.isExists(psiClinicUsers, user.get("username").toString())) {
				nameObject.put("username", user.get("username"));
				nameObject.put("display", person.get("display"));
				usernamesArray.put(nameObject);
			}
			
		}
		
		session.setAttribute("users", usernamesArray.toString());
		if (id == 0) {
			return new ModelAndView("redirect:/module/PSI/PSIClinicList.form");
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/module/PSI/PSIClinicUserList", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model,
	                          @RequestParam(required = true) int id) {
		model.addAttribute("id", id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		Set<PSIClinicUser> psiClinicUsers = psiClinicManagement.getpSIClinicUser();
		model.addAttribute("name", psiClinicManagement.getName());
		model.addAttribute("pSIClinicUsers", psiClinicUsers);
	}
	
	@RequestMapping(value = "/module/PSI/editPSIClinicUser", method = RequestMethod.GET)
	public void editPSIClinicUser(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id)
	    throws JSONException {
		
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + USER_URL + "/", "v=default",
		    "superman", "Admin123");
		JSONObject body = new JSONObject(op.body());
		JSONArray users = new JSONArray(body.getJSONArray("results").toString());
		JSONArray usernamesArray = new JSONArray();
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findById(id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(
		    psiClinicUser.getPsiClinicManagementId().getCid());
		
		Set<PSIClinicUser> psiClinicUsers = psiClinicManagement.getpSIClinicUser();
		for (int i = 0; i < users.length(); i++) {
			JSONObject nameObject = new JSONObject();
			JSONObject user = (JSONObject) users.get(i);
			JSONObject person = user.getJSONObject("person");
			if (!ClinicUser.isExists(psiClinicUsers, user.get("username").toString(), psiClinicUser.getUserName())) {
				nameObject.put("username", user.get("username"));
				nameObject.put("display", person.get("display"));
				usernamesArray.put(nameObject);
			}
			
		}
		
		model.addAttribute("psiClinicManagementId", psiClinicManagement.getCid());
		JSONArray userIds = new JSONArray();
		userIds.put(psiClinicUser.getUserName());
		session.setAttribute("userIds", userIds.toString());
		session.setAttribute("users", usernamesArray.toString());
		model.addAttribute("pSIClinicUser", psiClinicUser);
		
	}
	
	@RequestMapping(value = "/module/PSI/deletePSIClinicUser", method = RequestMethod.GET)
	public ModelAndView deletePSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		Context.getService(PSIClinicUserService.class).delete(id);
		return new ModelAndView("redirect:/module/PSI/PSIClinicUserList.form");
	}
	
	@RequestMapping(value = "/module/PSI/addPSIClinicUser", method = RequestMethod.POST)
	public ModelAndView addORUpdatePSIClinic(@ModelAttribute("pSIClinicUser") PSIClinicUser pSIClinicUser,
	                                         HttpSession session,
	                                         @RequestParam(value = "usernames[]", required = false) String[] users,
	                                         @RequestParam(required = false) int clinicId) throws Exception {
		if (pSIClinicUser != null) {
			for (String user : users) {
				pSIClinicUser.setUserName(user);
				pSIClinicUser.setDateCreated(new Date());
				pSIClinicUser.setCreator(Context.getAuthenticatedUser());
				pSIClinicUser.setUuid(UUID.randomUUID().toString());
				pSIClinicUser.setPsiClinicManagementId(Context.getService(PSIClinicManagementService.class).findById(
				    clinicId));
				Context.openSession();
				Context.getService(PSIClinicUserService.class).saveOrUpdate(pSIClinicUser);
				Context.clearSession();
			}
			return new ModelAndView("redirect:/module/PSI/PSIClinicUserList.form?id=" + clinicId);
		}
		return null;
	}
	
}
