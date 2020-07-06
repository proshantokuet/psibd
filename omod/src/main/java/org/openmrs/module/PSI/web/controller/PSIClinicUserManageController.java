/**
 * 
 */
package org.openmrs.module.PSI.web.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.PSIMapper;
import org.openmrs.module.PSI.utils.Utils;
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
	
	private static final String OPENMRS_BASE_URL = "https://localhost/openmrs";
	
	final String USER_URL = "ws/rest/v1/user";
	
	@RequestMapping(value = "/module/PSI/addPSIClinicUser", method = RequestMethod.GET)
	public ModelAndView addPSIClinic(HttpServletRequest request, HttpSession session, Model model,
	                                 @RequestParam(required = true) int id) throws JSONException {
		model.addAttribute("pSIClinicUser", new PSIClinicUser());
		
		model.addAttribute("psiClinicManagementId", id);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
		/*JSONArray usernamesArray = new JSONArray();
		List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findUsersNotInClinic(id);
		for (UserDTO user : psiClinicUsers) {
			JSONObject nameObject = new JSONObject();
			nameObject.put("username", user.getUsername());
			nameObject.put("display", user.getFullName());
			usernamesArray.put(nameObject);
		}*/
		
		//List<Role> roles = Context.getService(UserService.class).getAllRoles();
		List<Role> roles = PSIMapper.getRoles();
		model.addAttribute("roles", roles);
		//session.setAttribute("users", usernamesArray.toString());
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
		//List<PSIClinicUser> psiClinicUsers = Context.getService(PSIClinicUserService.class).findByClinicId(id);
		model.addAttribute("name", psiClinicManagement.getName());
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		
		List<UserDTO> users = Context.getService(PSIClinicUserService.class).findUserByClinicIdWithRawQuery(
		    psiClinicManagement.getCid());
		model.addAttribute("pSIClinicUsers", users);
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/editPSIClinicUser", method = RequestMethod.GET)
	public void editPSIClinicUser(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id)
	    throws JSONException {
		
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findById(id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(
		    psiClinicUser.getPsiClinicManagementId().getCid());
		/*JSONArray usernamesArray = new JSONArray();
		List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findUsersNotInClinic(id);
		for (UserDTO user : psiClinicUsers) {
			JSONObject nameObject = new JSONObject();
			nameObject.put("username", user.getUsername());
			nameObject.put("display", user.getFullName());
			usernamesArray.put(nameObject);
		}*/
		model.addAttribute("psiClinicManagementId", psiClinicManagement.getCid());
		//JSONArray userIds = new JSONArray();
		//userIds.put(psiClinicUser.getUserName());
		//session.setAttribute("userIds", userIds.toString());
		//session.setAttribute("users", usernamesArray.toString());
		model.addAttribute("pSIClinicUser", psiClinicUser);
		UserDTO user = Context.getService(PSIClinicUserService.class).findUserByIdWithRawQuery(psiClinicUser.getUserName());
		List<Role> roles = PSIMapper.getRoles();
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);
		model.addAttribute("defaultPassword", PSIConstants.DefaultPassword);
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
	}
	
	@RequestMapping(value = "/module/PSI/deletePSIClinicUser", method = RequestMethod.GET)
	public ModelAndView deletePSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		Context.getService(PSIClinicUserService.class).delete(id);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
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
