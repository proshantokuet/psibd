package org.openmrs.module.PSI.web.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinic;
import org.openmrs.module.PSI.api.PSIClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PSIClinicManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/PSI/addPSIClinic", method = RequestMethod.GET)
	public void addPSIClinic(HttpServletRequest request, HttpSession session, Model model) {
		model.addAttribute("pSIClinic", new PSIClinic());
	}
	
	@RequestMapping(value = "/module/PSI/PSIClinicList", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model) {
		model.addAttribute("pSIClinics", Context.getService(PSIClinicService.class).getAllClinic());
	}
	
	@RequestMapping(value = "/module/PSI/editPSIClinic", method = RequestMethod.GET)
	public void editPSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		model.addAttribute("psiClinic", Context.getService(PSIClinicService.class).findById(id));
		
	}
	
	@RequestMapping(value = "/module/PSI/deletePSIClinic", method = RequestMethod.GET)
	public ModelAndView deletePSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		Context.getService(PSIClinicService.class).delete(id);
		return new ModelAndView("redirect:/module/PSI/PSIClinicList.form");
	}
	
	@RequestMapping(value = "/module/PSI/addPsiClinic", method = RequestMethod.POST)
	public ModelAndView addORUpdatePSIClinic(@ModelAttribute("encounterMarker") PSIClinic psiClinic, HttpSession session)
	    throws Exception {
		if (psiClinic != null) {
			log.info("saving new module objects...................");
			psiClinic.setDateCreated(new Date());
			psiClinic.setCreator(Context.getAuthenticatedUser());
			psiClinic.setUuid(UUID.randomUUID().toString());
			Context.getService(PSIClinicService.class).saveOrUpdateClinic(psiClinic);
			return new ModelAndView("redirect:/module/PSI/PSIClinicList.form");
		}
		return null;
	}
	
}
