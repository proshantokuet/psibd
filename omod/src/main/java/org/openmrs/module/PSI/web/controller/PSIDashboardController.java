package org.openmrs.module.PSI.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.Privilege;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCServiceCategory;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.AUHCServiceCategoryService;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.dto.AUHCComprehensiveReport;
import org.openmrs.module.PSI.dto.AUHCDraftTrackingReport;
import org.openmrs.module.PSI.dto.AUHCRegistrationReport;
import org.openmrs.module.PSI.dto.AUHCVisitReport;
import org.openmrs.module.PSI.dto.DashboardDTO;
import org.openmrs.module.PSI.dto.PSIReport;
import org.openmrs.module.PSI.dto.PSIReportSlipTracking;
import org.openmrs.module.PSI.dto.SearchFilterDraftTracking;
import org.openmrs.module.PSI.dto.SearchFilterReport;
import org.openmrs.module.PSI.dto.SearchFilterSlipTracking;
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
		String val = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(today, today);
		model.addAttribute("dashbaord_discount_value",val);
		String totalServiceContact = Context.getService(PSIServiceProvisionService.class).getTotalServiceContract(today, today);
		model.addAttribute("dashboard_service_cotact_value",totalServiceContact);
		model.addAttribute("dashboard", dashboardDTO);
		
		List<PSIReport> providerWiseReports = Context.getService(PSIServiceProvisionService.class)
		        .serviceProviderWiseReport(today, today, clinicCode, "");
		model.addAttribute("providerWiseReports", providerWiseReports);
		
		List<AUHCServiceCategory> serviceCategory = Context.getService(AUHCServiceCategoryService.class).getAll();
		model.addAttribute("services",serviceCategory);
		
		List<PSIReport> servicePointWiseReports = Context.getService(PSIServiceProvisionService.class)
		        .servicePointWiseReport(today, today, clinicCode);
		model.addAttribute("servicePointWiseReports", servicePointWiseReports);
		SearchFilterSlipTracking filter = new SearchFilterSlipTracking();
		filter.setStartDateSlip(today);
		filter.setEndDateSlip(today);
//		List<PSIReportSlipTracking> trackingList = Context.getService(PSIServiceProvisionService.class)
//							.getSlipTrackingReport(filter);
//		List<Object[]> slipList = Context.getService(PSIServiceProvisionService.class).getSlip(filter);
		model.addAttribute("slipReport",null);
		
		
		
		
		if (psiClinicUser != null && !isAdmin) {
			
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);
			
			model.addAttribute("showClinic", 0);
			
			model.addAttribute("showServiceCategory",1);
			
			model.addAttribute("serviceCategory",
					Context.getService(PSIServiceManagementService.class).getCategoryList(
							Integer.parseInt(clinicCode)));
			
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			model.addAttribute("showServiceCategory",0);
			
		} else {
			
		}
		
		model.addAttribute("hasDashboardPermission", Utils.hasPrivilige(privileges, PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission", Utils.hasPrivilige(privileges, PSIConstants.ClinicList));
		
		model.addAttribute("no_slip_draft",Context.getService(PSIServiceProvisionService.class)
				.getNoOfDraft(today, today));
		model.addAttribute("total_payable_draft",Context.getService(PSIServiceProvisionService.class)
				.getTotalPayableDraft(today, today));
		
		model.addAttribute("service_category",
				Context.getService(AUHCServiceCategoryService.class).getAll());
		
		model.addAttribute("dashboard_new_reg",0);
		model.addAttribute("dashboard_old_clients",0);
		model.addAttribute("dashboard_new_clients",0);
		
		List<AUHCComprehensiveReport> report = new ArrayList<AUHCComprehensiveReport>();
		model.addAttribute("compReport",report);
		
		SearchFilterReport regFilter = new SearchFilterReport();
		regFilter.setStart_date(today);
		regFilter.setEnd_date(today);
		List<AUHCRegistrationReport> registrationReport =
				Context.getService(PSIServiceProvisionService.class)
				.getRegistrationReport(regFilter);
		
		model.addAttribute("regReport", registrationReport);
		model.addAttribute("clinic",clinicCode);
		model.addAttribute("visitReport",
				Context.getService(PSIServiceProvisionService.class).
				getVisitReport(today, today));
		
		
		SearchFilterDraftTracking filterdraft = new SearchFilterDraftTracking();
		filterdraft.setStartDateSlip(today);
		filterdraft.setEndDateSlip(today);
		List<AUHCDraftTrackingReport> draftList = new ArrayList<AUHCDraftTrackingReport>();
		draftList = Context.getService(PSIServiceProvisionService.class).getDraft(filterdraft);
		model.addAttribute("no_slip_draft",draftList.size());
		model.addAttribute("draftReport",draftList);
	}
	
	@RequestMapping(value = "/module/PSI/ServicePointWise", method = RequestMethod.GET)
	public void ServicePointWiseReport(HttpServletRequest request, HttpSession session, Model model,
	                                   @RequestParam(required = true) String startDate,
	                                   @RequestParam(required = true) String endDate,
	                                   @RequestParam String category,
	                                   @RequestParam(required = true) String code) {
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
			    Context.getAuthenticatedUser().getUsername());
		String clinicCode = "";
		if (isAdmin) {
			clinicCode = code != "-1" ? code : "0";
//			clinicCode = "109";
		} else {
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
//			clinicCode = "291";
		}
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
		    clinicCode, "");
		SearchFilterReport filter = new SearchFilterReport();
		filter.setStart_date(startDate);
		filter.setEnd_date(endDate);
		filter.setService_category(category);
		filter.setClinic_code(clinicCode);
		filter.setService_category(category);
//		dashboardDTO.setEarned();
		List<AUHCComprehensiveReport> reports = Context.getService(PSIServiceProvisionService.class).
				getComprehensiveReport(filter);
		
		double discount = 0;
		for(int i = 0; i < reports.size();i++)
			discount += reports.get(i).getDiscount_total();
		dashboardDTO.setEarned((int)reports.get(0).getTotal_revenue());
//		dashboardDTO.setEarned(0);
		model.addAttribute("dashbaord_discount_value",discount);
		model.addAttribute("dashboard_service_cotact_value",(long)reports.get(0).getTotal_service_contact());
//		List<PSIReport> reports = Context.getService(PSIServiceProvisionService.class).servicePointWiseReport(startDate,
//		    endDate, ClinicCode);
		
		
		if (psiClinicUser != null && !isAdmin) {
			
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);
			
			model.addAttribute("showClinic", 0);
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			
		}
		model.addAttribute("dashboard_old_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardOldClients(startDate, endDate));
		model.addAttribute("dashboard_new_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardNewClients(startDate, endDate));
		
		model.addAttribute("dashboard", dashboardDTO);
		model.addAttribute("compReports", reports);
		
		
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
		
		
		
		model.addAttribute("dashboard_old_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardOldClients(startDate, endDate));
		model.addAttribute("dashboard_new_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardNewClients(startDate, endDate));
		
		String val = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(startDate, endDate);
		model.addAttribute("dashbaord_discount_value",val);
//		model.addAttribute("dashbaord_discount_value",Context.getService(PSIServiceProvisionService.class).);
		String totalServiceContact = Context.getService(PSIServiceProvisionService.class).getTotalServiceContract(startDate, endDate);
		model.addAttribute("dashboard_service_cotact_value",totalServiceContact);
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
			    Context.getAuthenticatedUser().getUsername());
		if (psiClinicUser != null && !isAdmin) {
			
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);
			
			model.addAttribute("showClinic", 0);
			
		
			
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			
		}
	}
	
	@RequestMapping(value = "/module/PSI/providerByClinic", method = RequestMethod.GET)
	public void providerByClinic(HttpServletRequest request, HttpSession session, Model model,
	                             @RequestParam(required = true) String code) {
		List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(code);
		model.addAttribute("psiClinicUsers", psiClinicUsers);
		
	}
	
	@RequestMapping(value = "/module/PSI/slipTracking", method = RequestMethod.GET)
	public void slipTrackingWise(HttpServletRequest request, HttpSession session, Model model,
	                                      @RequestParam(required = true) String startDate,
	                                      @RequestParam(required = true) String endDate,
	                                      @RequestParam	String dataCollector,
	                                      @RequestParam String wlthPoor,
	                                      @RequestParam String wlthPop,
	                                      @RequestParam String wlthAbleToPay,
	                                      @RequestParam String spSatelite,
	                                      @RequestParam String spStatic,
	                                      @RequestParam String spCsp,
	                                      @RequestParam(required = true) String code){
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
		
		SearchFilterSlipTracking filter = new SearchFilterSlipTracking();
		filter.setStartDateSlip(startDate);
		filter.setEndDateSlip(endDate);
		filter.setCollector(dataCollector);
		filter.setWlthPoor(wlthPoor);
		filter.setWlthPop(wlthPop);
		filter.setWlthAbleToPay(wlthAbleToPay);
		filter.setSpSatelite(spSatelite);
		filter.setSpStatic(spStatic);
		filter.setSpCsp(spCsp);
		List<PSIReportSlipTracking> slipTrackingList = new ArrayList<PSIReportSlipTracking>();
	
		
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		String val = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(filter);
		model.addAttribute("dashbaord_discount_value",val);
//		model.addAttribute("dashbaord_discount_value",Context.getService(PSIServiceProvisionService.class).);
		String totalServiceContact = Context.getService(PSIServiceProvisionService.class).getTotalServiceContact(filter);
		model.addAttribute("dashboard_service_cotact_value",totalServiceContact);
			
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
			    clinicCode, dataCollector);
		
//		List<Object[]> slipList = null;
//		List<Object[]> slipList = Context.getService(PSIServiceProvisionService.class).getSlip(filter);
//		if(isAdmin){
			 slipTrackingList = Context.getService
						(PSIServiceProvisionService.class).getSlipTrackingReport(filter);
//		 slipList = Context.getService(PSIServiceProvisionService.class).getSlip(filter);
//		}else {
			
//		}
//		slipTrackingList = null;
		model.addAttribute("dashboard_patients_served",Context.getService(
				PSIServiceProvisionService.class).getPatientsServed(filter));
		model.addAttribute("dashboard_revenue",Context.getService(
				PSIServiceProvisionService.class).getRevenueEarned(filter));
		model.addAttribute("dashboard", dashboardDTO);
		model.addAttribute("slipReport",slipTrackingList);
		
		
		if (psiClinicUser != null && !isAdmin) {
			
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);
			
			model.addAttribute("showClinic", 0);
			
		
			
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			
		}
		
	}
	
	@RequestMapping(value = "/module/PSI/draftTracking", method = RequestMethod.GET)
	public void draftTrackingWise(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(required = true) String startDate,
            @RequestParam(required = true) String endDate,
            @RequestParam	String dataCollector,
            @RequestParam String wlthPoor,
            @RequestParam String wlthPop,
            @RequestParam String wlthAbleToPay,
            @RequestParam String spSatelite,
            @RequestParam String spStatic,
            @RequestParam String spCsp,
            @RequestParam(required = true) String code){
		
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
		SearchFilterDraftTracking filter = new SearchFilterDraftTracking();
		filter.setStartDateSlip(startDate);
		filter.setEndDateSlip(endDate);
		filter.setCollector(dataCollector);
		filter.setWlthPoor(wlthPoor);
		filter.setWlthPop(wlthPop);
		filter.setWlthAbleToPay(wlthAbleToPay);
		filter.setSpSatelite(spSatelite);
		filter.setSpStatic(spStatic);
		filter.setSpCsp(spCsp);
		
		
		model.addAttribute("total_payable_draft",Context.getService(PSIServiceProvisionService.class)
				.getTotalPayableDraft(filter));
		List<AUHCDraftTrackingReport> draftList = new ArrayList<AUHCDraftTrackingReport>();
		draftList = Context.getService(PSIServiceProvisionService.class).getDraft(filter);
		model.addAttribute("no_slip_draft",draftList.size());
		model.addAttribute("draftReport",draftList);
		
		if (psiClinicUser != null && !isAdmin) {
			
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);
			
			model.addAttribute("showClinic", 0);
			
		
			
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			
		}
	}
	
	@RequestMapping(value="/module/PSI/compServiceReporting",method=RequestMethod.GET)
	public void compServiceReporting(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(required = true) String startDate,
            @RequestParam(required = true) String endDate,
            @RequestParam String serviceCategory,
            @RequestParam String searchString,
            @RequestParam(required = true) String code){
//		List<AUHCServiceCategory> serviceCategory = Context.getService(AUHCServiceCategoryService.class).getAll();
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
		model.addAttribute("service_category",
				Context.getService(AUHCServiceCategoryService.class).getAll());
		
		model.addAttribute("dashboard_new_reg",
				Context.getService(PSIServiceProvisionService.class).
				getDashboardNewReg(startDate, endDate));
		model.addAttribute("dashboard_old_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardOldClients(startDate, endDate));
		model.addAttribute("dashboard_new_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardNewClients(startDate, endDate));
		
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
			    clinicCode, "");
		model.addAttribute("dashboard",dashboardDTO);
		
		String val = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(startDate, endDate);
		model.addAttribute("dashbaord_discount_value",val);
//		model.addAttribute("dashbaord_discount_value",Context.getService(PSIServiceProvisionService.class).);
		String totalServiceContact = Context.getService(PSIServiceProvisionService.class).getTotalServiceContract(startDate, endDate);
		model.addAttribute("dashboard_service_cotact_value",totalServiceContact);
		
		SearchFilterReport filter = new SearchFilterReport();
		filter.setStart_date(startDate);
		filter.setEnd_date(endDate);
		filter.setService_category(serviceCategory);
		filter.setSearch_string(searchString);
		
		List<AUHCComprehensiveReport> report =  Context.getService(
				PSIServiceProvisionService.class).getComprehensiveReport(filter);
		model.addAttribute("compReport",report);
		
		if (psiClinicUser != null && !isAdmin) {
			
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);
			
			model.addAttribute("showClinic", 0);
			
		
			
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			
		}
		
	}
	
	@RequestMapping(value="/module/PSI/registrationReport",method=RequestMethod.GET)
	public void registrationReport(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(required = true) String startDate,
            @RequestParam(required = true) String endDate,
            @RequestParam String gender,
            @RequestParam(required = true) String code){
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
		
		
		model.addAttribute("dashboard_old_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardOldClients(startDate, endDate));
		model.addAttribute("dashboard_new_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardNewClients(startDate, endDate));
		
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
			    clinicCode, "");
		model.addAttribute("dashboard",dashboardDTO);
		
		
		
		List<AUHCRegistrationReport> registrationReport =
				Context.getService(PSIServiceProvisionService.class)
				.getRegistrationReport(startDate,endDate,gender);
		
		model.addAttribute("regReport", registrationReport);
		
		if (psiClinicUser != null && !isAdmin) {
			
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);
			
			model.addAttribute("showClinic", 0);
			
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			
		}
	}
	
	@RequestMapping(value="/module/PSI/visitReport",method=RequestMethod.GET)
	public void visitReport(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(required=true) String startDate,
			@RequestParam(required=true) String endDate,
			@RequestParam(required = true) String code){
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
		model.addAttribute("service_category",
				Context.getService(AUHCServiceCategoryService.class).getAll());
		
		model.addAttribute("dashboard_new_reg",
				Context.getService(PSIServiceProvisionService.class).
				getDashboardNewReg(startDate, endDate));
		model.addAttribute("dashboard_old_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardOldClients(startDate, endDate));
		model.addAttribute("dashboard_new_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardNewClients(startDate, endDate));
		
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
			    clinicCode, "");
		model.addAttribute("dashboard",dashboardDTO);
		List<AUHCVisitReport> visitReport = Context.getService(PSIServiceProvisionService.class)
				.getVisitReport(startDate, endDate);
		String val = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(startDate, endDate);
		model.addAttribute("dashbaord_discount_value",val);
//		model.addAttribute("dashbaord_discount_value",Context.getService(PSIServiceProvisionService.class).);
		String totalServiceContact = Context.getService(PSIServiceProvisionService.class).getTotalServiceContract(startDate, endDate);
		model.addAttribute("dashboard_service_cotact_value",totalServiceContact);
		
		model.addAttribute("visitReport",visitReport);
		
		if (psiClinicUser != null && !isAdmin) {	
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);			
			model.addAttribute("showClinic", 0);			
			
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			
		}
	}
}
