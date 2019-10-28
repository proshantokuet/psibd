package org.openmrs.module.PSI.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.Privilege;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.dto.DashboardDTO;
import org.openmrs.module.PSI.dto.PSIReport;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PSIDashboardController {
	
	@RequestMapping(value = "/module/PSI/dashboard", method = RequestMethod.GET)
	public void dashboard(HttpServletRequest request, HttpSession session, Model model) {
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
		    Context.getAuthenticatedUser().getUsername());
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		String clinicCode = "0";
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		if (isAdmin) {
			clinicCode = "0";
		} else {
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
		}
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(today, today,
		    clinicCode, "");
//		dashboardDTO.setTotalDiscount(1);
		int val = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(today, today);
		model.addAttribute("dashbaord_discount_value",val);
		int totalServiceContact = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(today, today);
		model.addAttribute("dashboard_service_cotact_value",totalServiceContact);
		model.addAttribute("dashboard", dashboardDTO);
		
		List<PSIReport> providerWiseReports = Context.getService(PSIServiceProvisionService.class)
		        .serviceProviderWiseReport(today, today, clinicCode, "");
		model.addAttribute("providerWiseReports", providerWiseReports);
		
		List<PSIReport> servicePointWiseReports = Context.getService(PSIServiceProvisionService.class)
		        .servicePointWiseReport(today, today, clinicCode);
		model.addAttribute("servicePointWiseReports", servicePointWiseReports);
		
		if (psiClinicUser != null && !isAdmin) {
			
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);
			
			model.addAttribute("showClinic", 0);
			
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			
		} else {
			
		}
		
		model.addAttribute("hasDashboardPermission", Utils.hasPrivilige(privileges, PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission", Utils.hasPrivilige(privileges, PSIConstants.ClinicList));
		
	}
	
	@RequestMapping(value = "/module/PSI/ServicePointWise", method = RequestMethod.GET)
	public void ServicePointWiseReport(HttpServletRequest request, HttpSession session, Model model,
	                                   @RequestParam(required = true) String startDate,
	                                   @RequestParam(required = true) String endDate,
	                                   @RequestParam(required = true) String clinic_code) {
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		String ClinicCode = "";
//		if(isAdmin){
//			ClinicCode = "0";
//		}else 
		if (clinic_code.equalsIgnoreCase("-1")) { // for user who is assinged to a clinic vfor manager
			PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
			    Context.getAuthenticatedUser().getUsername());
			ClinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
			
		} else { // for selected clinic or all clinic
		
			ClinicCode = clinic_code;
		}
		
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
		    ClinicCode, "");
		model.addAttribute("dashboard", dashboardDTO);
		List<PSIReport> reports = Context.getService(PSIServiceProvisionService.class).servicePointWiseReport(startDate,
		    endDate, ClinicCode);
		model.addAttribute("reports", reports);
		
	}
	
	@RequestMapping(value = "/module/PSI/ServiceProviderWise", method = RequestMethod.GET)
	public void ServiceProviderWiseReport(HttpServletRequest request, HttpSession session, Model model,
	                                      @RequestParam(required = true) String startDate,
	                                      @RequestParam(required = true) String endDate,
	                                      @RequestParam(required = true) String dataCollector,
	                                      @RequestParam(required = true) String code) {
		
		String clinicCode = "";
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		if(isAdmin){
			clinicCode = "0";
		} else if (code.equalsIgnoreCase("-1")) { // for user who is assinged to a clinic
			PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
			    Context.getAuthenticatedUser().getUsername());
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
			
		} else { // for selected clinic or all clinic
		
			clinicCode = code;
		}
		
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
		    clinicCode, dataCollector);
		List<PSIReport> reports = Context.getService(PSIServiceProvisionService.class).serviceProviderWiseReport(startDate,
		    endDate, clinicCode, dataCollector);
		model.addAttribute("dashboard", dashboardDTO);
		model.addAttribute("reports", reports);
		
	}
	
	@RequestMapping(value = "/module/PSI/providerByClinic", method = RequestMethod.GET)
	public void providerByClinic(HttpServletRequest request, HttpSession session, Model model,
	                             @RequestParam(required = true) String code) {
		List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(code);
		model.addAttribute("psiClinicUsers", psiClinicUsers);
		
	}
}
