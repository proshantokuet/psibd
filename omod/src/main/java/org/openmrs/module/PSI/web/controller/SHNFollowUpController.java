package org.openmrs.module.PSI.web.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.Privilege;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.api.SHNFollowUpActionService;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.dto.SHNFollowUPReportDTO;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SHNFollowUpController {

	
	@RequestMapping(value = "/module/PSI/follow-Up-dashboard", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model) {
		List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
		model.addAttribute("clinics", clinics);
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
			    Context.getAuthenticatedUser().getUsername());
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		String clinicCode = "0";
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		if (isAdmin) {
			clinicCode = "0";
		} else {
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
		}
		List<SHNFollowUPReportDTO> followUpReport = Context.getService(SHNFollowUpActionService.class).getfollowUpReprt("", "", "", "", "", "",clinicCode,"");
		model.addAttribute("followUpReport",followUpReport);
		if (psiClinicUser != null && !isAdmin) {			
			model.addAttribute("showClinic", 0);
			model.addAttribute("clinicInfo", psiClinicUser.getPsiClinicManagementId().getName() + " Clinic ID-" + psiClinicUser.getPsiClinicManagementId().getClinicId());
			
		} else if (psiClinicUser != null && isAdmin) {
			model.addAttribute("showClinic", 1);
			model.addAttribute("clinicInfo", "All");
		} else {
			
		}
		model.addAttribute("hasFollowUpPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.followUp));
	}
	
	@RequestMapping(value = "/module/PSI/follow-Up-report", method = RequestMethod.GET)
	public void getStockReport(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam String visitStartDate,
			@RequestParam String visitEndDate,
			@RequestParam String followUpStartDate,
			@RequestParam String followUpEndDate,
			@RequestParam String mobileNo,
			@RequestParam String patientHid,
			@RequestParam String clinicid,
			@RequestParam String patientName) {
		
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
			    Context.getAuthenticatedUser().getUsername());
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		String clinicCode = "0";
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		if (isAdmin) {
			clinicCode = clinicid;
		} else {
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
		}
		
		List<SHNFollowUPReportDTO> followUpReport = Context.getService(SHNFollowUpActionService.class).getfollowUpReprt(visitStartDate,visitEndDate,followUpStartDate,followUpEndDate,mobileNo,patientHid,clinicCode,patientName);
		model.addAttribute("followUpReport",followUpReport);
		

	}
}
