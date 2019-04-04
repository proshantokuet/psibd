package org.openmrs.module.PSI.web.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PSIMoneyReceiptManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/PSI/addPSIMoneyReceipt", method = RequestMethod.GET)
	public void addPSIMoneyReceipt(HttpServletRequest request, HttpSession session, Model model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
		
		model.addAttribute("pSIMoneyReceipt", new PSIMoneyReceipt());
	}
	
	@RequestMapping(value = "/module/PSI/addPSIMoneyReceipt", method = RequestMethod.POST)
	public ModelAndView addORUpdatePSIMoneyReceipt(@ModelAttribute("psiMoneyReceipt") PSIMoneyReceipt psiMoneyReceipt,
	                                               HttpSession session) throws Exception {
		
		psiMoneyReceipt.setDateCreated(new Date());
		psiMoneyReceipt.setCreator(Context.getAuthenticatedUser());
		psiMoneyReceipt.setUuid(UUID.randomUUID().toString());
		psiMoneyReceipt.setDob(new Date());
		psiMoneyReceipt.setPatientName("Xahin");
		PSIServiceProvision psiServiceProvision = new PSIServiceProvision();
		psiServiceProvision.setDateCreated(new Date());
		psiServiceProvision.setCreator(Context.getAuthenticatedUser());
		psiServiceProvision.setUuid(UUID.randomUUID().toString());
		Set<PSIServiceProvision> services = new HashSet<PSIServiceProvision>();
		services.add(psiServiceProvision);
		
		psiMoneyReceipt.setServices(services);
		Context.getService(PSIMoneyReceiptService.class).saveOrUpdate(psiMoneyReceipt);
		
		return null;
	}
	
}
