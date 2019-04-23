package org.openmrs.module.PSI.web.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PSIServiceManagementController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/PSI/addPSIClinicService", method = RequestMethod.GET)
	public void addPSIClinic(HttpServletRequest request, HttpSession session, Model model) {
		List<PSIClinicManagement> psiClinicManagements = Context.getService(PSIClinicManagementService.class).getAllClinic();
		model.addAttribute("clinics", psiClinicManagements);
		model.addAttribute("user", Context.getAuthenticatedUser());
		model.addAttribute("pSIServiceManagement", new PSIServiceManagement());
	}
	
	@RequestMapping(value = "/module/PSI/PSIClinicServiceList", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model) {
		model.addAttribute("pSIServiceManagements", Context.getService(PSIServiceManagementService.class).getAll());
	}
	
	@RequestMapping(value = "/module/PSI/editPSIClinicService", method = RequestMethod.GET)
	public void editPSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		List<PSIClinicManagement> psiClinicManagements = Context.getService(PSIClinicManagementService.class).getAllClinic();
		model.addAttribute("clinics", psiClinicManagements);
		model.addAttribute("pSIServiceManagement", Context.getService(PSIServiceManagementService.class).findById(id));
		
	}
	
	@RequestMapping(value = "/module/PSI/deletePSIClinicService", method = RequestMethod.GET)
	public ModelAndView deletePSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		Context.getService(PSIServiceManagementService.class).delete(id);
		return new ModelAndView("redirect:/module/PSI/PSIClinicServiceList.form");
	}
	
	@RequestMapping(value = "/module/PSI/addPPSIClinicService", method = RequestMethod.POST)
	public ModelAndView addORUpdatePSIClinic(@ModelAttribute("pSIServiceManagement") PSIServiceManagement pSIServiceManagement,
	                                         HttpSession session, Model model) throws Exception {
		try {
			if (pSIServiceManagement != null) {
				log.info("saving new module objects...................");
				pSIServiceManagement.setDateCreated(new Date());
				pSIServiceManagement.setCreator(Context.getAuthenticatedUser());
				pSIServiceManagement.setUuid(UUID.randomUUID().toString());
				Context.getService(PSIServiceManagementService.class).saveOrUpdate(pSIServiceManagement);
				return new ModelAndView("redirect:/module/PSI/PSIClinicServiceList.form");
			}
			
		}
		catch (Exception e) {
			model.addAttribute("pSIServiceManagement", pSIServiceManagement);
			model.addAttribute("msg", "Some thing wrong , please contact with administrator");
			return new ModelAndView("redirect:/module/PSI/addPSIClinicService.form");
		}
		return null;
	}
	
}
