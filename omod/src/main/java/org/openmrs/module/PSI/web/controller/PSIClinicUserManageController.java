/**
 * 
 */
package org.openmrs.module.PSI.web.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicUserService;
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
	
	@RequestMapping(value = "/module/PSI/addPSIClinicUser", method = RequestMethod.GET)
	public ModelAndView addPSIClinic(HttpServletRequest request, HttpSession session, Model model,
	                                 @RequestParam(value = "0", required = false) int id) {
		model.addAttribute("pSIClinicUser", new PSIClinicUser());
		model.addAttribute("psiClinicManagementId", id);
		if (id == 0) {
			return new ModelAndView("redirect:/module/PSI/PSIClinicList.form");
		}
		return null;
	}
	
	@RequestMapping(value = "/module/PSI/PSIClinicUserList", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model) {
		model.addAttribute("pSIClinicUsers", Context.getService(PSIClinicUserService.class).getAll());
	}
	
	@RequestMapping(value = "/module/PSI/editPSIClinicUser", method = RequestMethod.GET)
	public void editPSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		model.addAttribute("pSIClinicUser", Context.getService(PSIClinicUserService.class).findById(id));
		
	}
	
	@RequestMapping(value = "/module/PSI/deletePSIClinicUser", method = RequestMethod.GET)
	public ModelAndView deletePSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		Context.getService(PSIClinicUserService.class).delete(id);
		return new ModelAndView("redirect:/module/PSI/PSIClinicUserList.form");
	}
	
	@RequestMapping(value = "/module/PSI/addPSIClinicUser", method = RequestMethod.POST)
	public ModelAndView addORUpdatePSIClinic(@ModelAttribute("pSIClinicUser") PSIClinicUser pSIClinicUser,
	                                         HttpSession session) throws Exception {
		if (pSIClinicUser != null) {
			pSIClinicUser.setDateCreated(new Date());
			pSIClinicUser.setCreator(Context.getAuthenticatedUser());
			pSIClinicUser.setUuid(UUID.randomUUID().toString());
			Context.getService(PSIClinicUserService.class).saveOrUpdate(pSIClinicUser);
			return new ModelAndView("redirect:/module/PSI/PSIClinicUserList.form");
		}
		return null;
	}
	
}
