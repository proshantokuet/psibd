package org.openmrs.module.PSI.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Privilege;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCServiceCategory;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;
import org.openmrs.module.PSI.api.AUHCServiceCategoryService;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.api.SHNPackageService;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.dto.AUHCComprehensiveReport;
import org.openmrs.module.PSI.dto.AUHCDashboardCard;
import org.openmrs.module.PSI.dto.AUHCDraftTrackingReport;
import org.openmrs.module.PSI.dto.AUHCRegistrationReport;
import org.openmrs.module.PSI.dto.AUHCVisitReport;
import org.openmrs.module.PSI.dto.ApiResponseModelDTO;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;
import org.openmrs.module.PSI.dto.DashboardDTO;
import org.openmrs.module.PSI.dto.PSIReport;
import org.openmrs.module.PSI.dto.PSIReportSlipTracking;
import org.openmrs.module.PSI.dto.SHNPackageReportDTO;
import org.openmrs.module.PSI.dto.SearchFilterDraftTracking;
import org.openmrs.module.PSI.dto.SearchFilterRegistrationReport;
import org.openmrs.module.PSI.dto.SearchFilterReport;
import org.openmrs.module.PSI.dto.SearchFilterSlipTracking;
import org.openmrs.module.PSI.dto.SearchFilterVisitReport;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PSIDashboardController {
	
	protected final Log log = LogFactory.getLog(getClass());

	
	@RequestMapping(value = "/module/PSI/dashboard", method = RequestMethod.GET)
	public void dashboard(HttpServletRequest request, HttpSession session, Model model) {
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
		    Context.getAuthenticatedUser().getUsername());
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		String clinicCode = "0";
		String clinicName = "";
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		boolean isManager = Utils.hasPrivilige(privileges, PSIConstants.ClinicManager);
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		if (isAdmin) {
			clinicCode = "0";
			clinicName= "All";
		} else {
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
			clinicName = psiClinicUser.getPsiClinicManagementId().getName();
		}
//		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(today, today,
//		    clinicCode, "");
		DashboardDTO dashboardDTO = new DashboardDTO();
//		dashboardDTO.setTotalDiscount(1);
		//String val = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(today, today);
		model.addAttribute("dashbaord_discount_value",0);
		//String totalServiceContact = Context.getService(PSIServiceProvisionService.class).getTotalServiceContract(today, today);
		model.addAttribute("dashboard_service_cotact_value",0);
		dashboardDTO.setEarned(0);
		dashboardDTO.setNewPatient(0);
		dashboardDTO.setServedPatient(0);
		dashboardDTO.setTotalDiscount(0);
		
		model.addAttribute("dashboard", dashboardDTO);
		
//		List<PSIReport> providerWiseReports = Context.getService(PSIServiceProvisionService.class)
//		        .serviceProviderWiseReport(today, today, clinicCode, "");
		model.addAttribute("providerWiseReports", null);
		
		List<AUHCServiceCategory> serviceCategory = Context.getService(AUHCServiceCategoryService.class).getAll();
		model.addAttribute("services",serviceCategory);
		
//		List<PSIReport> servicePointWiseReports = Context.getService(PSIServiceProvisionService.class)
//		        .servicePointWiseReport(today, today, "");
		model.addAttribute("servicePointWiseReports", null);
//		SearchFilterSlipTracking filter = new SearchFilterSlipTracking();
//		filter.setStartDateSlip(today);
//		filter.setEndDateSlip(today);
//		filter.setClinicCode(clinicCode);
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
		
		model.addAttribute("no_slip_draft",0);
		model.addAttribute("total_payable_draft",0);
		
		model.addAttribute("service_category",
				Context.getService(AUHCServiceCategoryService.class).getAll());
		
		model.addAttribute("dashboard_new_reg",0);
		model.addAttribute("dashboard_old_clients",0);
		model.addAttribute("dashboard_new_clients",0);
		
		
		
//		SearchFilterReport regFilter = new SearchFilterReport();
//		regFilter.setStart_date(today);
//		regFilter.setEnd_date(today);
//		List<AUHCRegistrationReport> registrationReport =
//				Context.getService(PSIServiceProvisionService.class)
//				.getRegistrationReport(regFilter);
		
		model.addAttribute("regReport", null);
		model.addAttribute("clinic",clinicCode);
		
//		model.addAttribute("visitReport",
//				Context.getService(PSIServiceProvisionService.class).
//				getVisitReport(today, today,clinicCode));
//		
		model.addAttribute("visitReport",null);
//		SearchFilterDraftTracking filterdraft = new SearchFilterDraftTracking();
//		filterdraft.setStartDateSlip(today);
//		filterdraft.setEndDateSlip(today);
//		filterdraft.setClinicCode(clinicCode);
//		List<AUHCDraftTrackingReport> draftList = new ArrayList<AUHCDraftTrackingReport>();
//		draftList = Context.getService(PSIServiceProvisionService.class).getDraft(filterdraft);
		model.addAttribute("no_slip_draft",0);
		model.addAttribute("draftReport",null);
		if(psiClinicUser != null && isManager || isAdmin) {
			model.addAttribute("showSubmitDraft", 2);
		}
		
//		List<AUHCComprehensiveReport> report = new ArrayList<AUHCComprehensiveReport>();
		model.addAttribute("compReport",null);
		model.addAttribute("stockReport",null);
//		SearchFilterReport comp_filter = new SearchFilterReport();
//		comp_filter.setStart_date(today);
//		comp_filter.setEnd_date(today);
//		comp_filter.setService_category("");
//		comp_filter.setClinic_code(clinicCode);

		AUHCDashboardCard dashboardCard = new AUHCDashboardCard();
		dashboardCard.init();
		model.addAttribute("comp_dashboard",dashboardCard);
		model.addAttribute("clinic_code",clinicCode);
		model.addAttribute("clinic_name",clinicName);

	}
	
	//Comprehensive Search Report
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
		String clinicName = "";
		if (isAdmin) {
			clinicCode = code != "-1" ? code : "0";
			clinicName = "";
		} else {
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
			clinicName = psiClinicUser.getPsiClinicManagementId().getName();
		}
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
		    clinicCode, "");
		SearchFilterReport filter = new SearchFilterReport();
		filter.setStart_date(startDate);
		filter.setEnd_date(endDate);
		filter.setService_category(category);
		filter.setClinic_code(clinicCode);
		filter.setService_category(category);

		List<AUHCComprehensiveReport> reports = Context.getService(PSIServiceProvisionService.class).
				getComprehensiveReport(filter);
		
				
		if (psiClinicUser != null && !isAdmin) {
			
			List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(clinicCode);
			model.addAttribute("psiClinicUsers", psiClinicUsers);
			
			model.addAttribute("showClinic", 0);
		} else if (psiClinicUser != null && isAdmin) {
			List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
			model.addAttribute("clinics", clinics);
			model.addAttribute("showClinic", 1);
			
		}

		AUHCDashboardCard dashboardCard = Context.getService(
				PSIServiceProvisionService.class).getComprehensiveDashboardCard(reports,filter);
		
		model.addAttribute("comp_dashboard",dashboardCard);
		model.addAttribute("dashboard", dashboardDTO);
		model.addAttribute("compReports", reports);
		model.addAttribute("clinic_code",clinicCode);
		model.addAttribute("clinic_name",clinicName);
	}
	
	@RequestMapping(value = "/module/PSI/ServiceProviderWise", method = RequestMethod.GET)
	public void ServiceProviderWiseReport(HttpServletRequest request, HttpSession session, Model model,
	                                      @RequestParam(required = true) String startDate,
	                                      @RequestParam(required = true) String endDate,
	                                      @RequestParam(required = true) String dataCollector,
	                                      @RequestParam(required = true) String code) {
		
		String clinicCode = "";
		String clinicName = "";
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		if(isAdmin){
			clinicCode = code != "-1" ? code : "0";
//			clinicName = ""; 
		} else if (code.equalsIgnoreCase("-1")) { // for user who is assinged to a clinic
			PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
			    Context.getAuthenticatedUser().getUsername());
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
//			clinicName = psiClinicUser.getPsiClinicManagementId().getName();
		} 
		
		List<PSIReport> reports = Context.getService(PSIServiceProvisionService.class).serviceProviderWiseReport(startDate,
		    endDate, clinicCode, dataCollector);
		model.addAttribute("reports", reports);
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
		SearchFilterReport filter = new SearchFilterReport();
		filter.setStart_date(startDate);
		filter.setEnd_date(endDate);
		filter.setClinic_code(clinicCode);
		filter.setData_collector(dataCollector);
		
		AUHCDashboardCard dashboardCard = new AUHCDashboardCard();
		dashboardCard = Context.getService(PSIServiceProvisionService.class).
				getProviderDashboardCard(reports, filter);
		model.addAttribute("provider_dashbaord",dashboardCard);
		model.addAttribute("clinic_code",clinicCode);
//		model.addAttribute("clinic_name",clinicName);
	}
	
	@RequestMapping(value = "/module/PSI/providerByClinic", method = RequestMethod.GET)
	public void providerByClinic(HttpServletRequest request, HttpSession session, Model model,
	                             @RequestParam(required = true) String code) {
		List<UserDTO> psiClinicUsers = Context.getService(PSIClinicUserService.class).findAllactiveAndInactiveUserByCode(code);
		model.addAttribute("psiClinicUsers", psiClinicUsers);
		
	}
	
	//Money Receipt
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
		String clinicCode = "";
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		if (isAdmin) {
			clinicCode = code != "-1" ? code : "0";
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
		filter.setClinicCode(clinicCode);
		List<PSIReportSlipTracking> slipTrackingList = new ArrayList<PSIReportSlipTracking>();
		
		
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		String val = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(filter);
		model.addAttribute("dashbaord_discount_value",val);
//		model.addAttribute("dashbaord_discount_value",Context.getService(PSIServiceProvisionService.class).);
		String totalServiceContact = Context.getService(PSIServiceProvisionService.class).getTotalServiceContact(filter);
		model.addAttribute("dashboard_service_cotact_value",totalServiceContact);
			
			 slipTrackingList = Context.getService
						(PSIServiceProvisionService.class).getSlipTrackingReport(filter);

		model.addAttribute("dashboard_patients_served",Context.getService(
				PSIServiceProvisionService.class).getPatientsServed(filter));
		model.addAttribute("dashboard_revenue",Context.getService(
				PSIServiceProvisionService.class).getRevenueEarned(filter));
//		model.addAttribute("dashboard", dashboardDTO);
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
		model.addAttribute("clinic_code",clinicCode);
		
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
		boolean isManager = Utils.hasPrivilige(privileges, PSIConstants.ClinicManager);
		if (isAdmin) {
			clinicCode = code != "-1" ? code : "0";
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
		filter.setClinicCode(clinicCode);
		
		
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
		if(psiClinicUser != null && isManager || isAdmin) {
			model.addAttribute("showSubmitDraft", 2);
		}
		model.addAttribute("clinic_code",clinicCode);
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
		model.addAttribute("clinic_code",clinicCode);
		
	}
	
	@RequestMapping(value="/module/PSI/registrationReport",method=RequestMethod.GET)
	public void registrationReport(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(required = true) String startDate,
            @RequestParam(required = true) String endDate,
            @RequestParam String gender,
            @RequestParam(required = true) String code,
            @RequestParam String wlthPoor,
            @RequestParam String wlthPop,
            @RequestParam String wlthAbleToPay){
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
			    Context.getAuthenticatedUser().getUsername());
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		String clinicCode = "";
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		if (isAdmin) {
			clinicCode = code != "-1" ? code : "0";
		} else {
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
		}
		SearchFilterRegistrationReport filter = new SearchFilterRegistrationReport();
		filter.setStartDate(startDate);
		filter.setEndDate(endDate);
		filter.setGender(gender);
		filter.setWlthAbleToPay(wlthAbleToPay);
		filter.setWlthPoor(wlthPoor);
		filter.setWlthPop(wlthPop);
		filter.setClinicCode(clinicCode);
		
		model.addAttribute("dashboard_old_clients",
				Context.getService(PSIServiceProvisionService.class).
				getDashboardOldClients(filter));
		model.addAttribute("dashboard_new_clients",
				Context.getService(PSIServiceProvisionService.class)
				.getDashboardNewClients(filter));
		
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
			    clinicCode, "");
		model.addAttribute("dashboard",dashboardDTO);
		
		
		
		List<AUHCRegistrationReport> registrationReport =
				Context.getService(PSIServiceProvisionService.class)
				.getRegistrationReport(filter);
		model.addAttribute("dashboard_new_reg",registrationReport.size());
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
			clinicCode = code != "-1" ? code : "0";
		} else {
			clinicCode = psiClinicUser.getPsiClinicManagementId().getClinicId();
		}
		SearchFilterVisitReport filter = new SearchFilterVisitReport();
		filter.setStartDateSlip(startDate);
		filter.setEndDateSlip(endDate);
		filter.setSpCsp(spCsp);
		filter.setSpSatelite(spSatelite);
		filter.setSpStatic(spStatic);
		filter.setWlthAbleToPay(wlthAbleToPay);
		filter.setWlthPoor(wlthPoor);
		filter.setWlthPop(wlthPop);
		filter.setClinicCode(clinicCode);
		model.addAttribute("service_category",
				Context.getService(AUHCServiceCategoryService.class).getAll());
		
		model.addAttribute("dashboard_new_reg",
				Context.getService(PSIServiceProvisionService.class).
				newRegistration(filter));
		model.addAttribute("dashboard_old_clients",
				Context.getService(PSIServiceProvisionService.class)
				.oldClientCount(filter));
		model.addAttribute("dashboard_new_clients",
				Context.getService(PSIServiceProvisionService.class)
				.newClientCount(filter));
		
		DashboardDTO dashboardDTO = Context.getService(PSIServiceProvisionService.class).dashboardReport(startDate, endDate,
			    clinicCode, "");
		model.addAttribute("dashboard",dashboardDTO);
		
		//
		
		List<AUHCVisitReport> visitReport = Context.getService(PSIServiceProvisionService.class)
				.getVisitReport(filter);
		String val = Context.getService(PSIServiceProvisionService.class).getTotalDiscount(startDate, endDate);
		model.addAttribute("dashbaord_discount_value",val);
//		model.addAttribute("dashbaord_discount_value",Context.getService(PSIServiceProvisionService.class).);
		String totalServiceContact = Context.getService(PSIServiceProvisionService.class).totalServiceContact(filter);
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
//		model.addAttribute("clinic_code",clinicCode);
	}
	
	
	@RequestMapping(value = "/module/PSI/submitAllDraft", method = RequestMethod.GET)
	public void submitAllDraft(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(required = true) String startDate,
            @RequestParam(required = true) String endDate,
            @RequestParam	String dataCollector,
            @RequestParam String wlthPoor,
            @RequestParam String wlthPop,
            @RequestParam String wlthAbleToPay,
            @RequestParam String spSatelite,
            @RequestParam String spStatic,
            @RequestParam String spCsp,
            @RequestParam(required = true) String code) {
		
		ApiResponseModelDTO apiResponseModelDTO = new ApiResponseModelDTO();
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
			    Context.getAuthenticatedUser().getUsername());
		Collection<Privilege> privileges = Context.getAuthenticatedUser().getPrivileges();
		String clinicCode = "0";
		boolean isAdmin = Utils.hasPrivilige(privileges, PSIConstants.AdminUser);
		boolean isManager = Utils.hasPrivilige(privileges, PSIConstants.ClinicManager);
		if (isAdmin) {
			clinicCode = code != "-1" ? code : "0";
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
		filter.setClinicCode(clinicCode);
		
		List<AUHCDraftTrackingReport> draftListForSubmitting = new ArrayList<AUHCDraftTrackingReport>();
		draftListForSubmitting = Context.getService(PSIServiceProvisionService.class).getDraft(filter);
		try {
			for (AUHCDraftTrackingReport auhcDraftTrackingReport : draftListForSubmitting) {
				PSIMoneyReceipt psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).findById(auhcDraftTrackingReport.getMid());
				if(psiMoneyReceipt.getDueAmount() == 0) {

					log.error("going to extract package if available " + psiMoneyReceipt.getServices().size());
					Set<PSIServiceProvision> draftServiceProvision = extractPackageItems(psiMoneyReceipt.getServices());
					apiResponseModelDTO = checkAllProductStockStatus(draftServiceProvision,psiMoneyReceipt.getClinicCode());
					log.error("apiResponseModelDTO status " + apiResponseModelDTO.getStatus());
					log.error("going to save service " + draftServiceProvision.size());
	                if(apiResponseModelDTO.getStatus()) {
	                	log.error("in the block of saving data " + apiResponseModelDTO.getStatus());
						psiMoneyReceipt.setIsComplete(1);
						psiMoneyReceipt.setTimestamp(System.currentTimeMillis());
						for (PSIServiceProvision serviceProvisions : draftServiceProvision) {
							log.error("serviceProvisions code " + serviceProvisions.getCode());
							serviceProvisions.setIsComplete(1);
							serviceProvisions.setTimestamp(System.currentTimeMillis());
							
						}
						psiMoneyReceipt.getServices().clear();
						psiMoneyReceipt.setServices(draftServiceProvision);
						PSIMoneyReceipt afterSave = Context.getService(PSIMoneyReceiptService.class).saveOrUpdate(psiMoneyReceipt);
						log.error("save complete  code " + afterSave.getIsComplete());

						Context.getService(PSIServiceProvisionService.class).deleteByPatientUuidAndMoneyReceiptIdNull(psiMoneyReceipt.getPatientUuid());
						if(afterSave.getIsComplete() == 1) {
							Context.getService(SHNStockService.class).updateStockByEarliestExpiryDate(psiMoneyReceipt.getEslipNo(), psiMoneyReceipt.getClinicCode());
						}

	                }
	                else {
	                	model.addAttribute("msg", apiResponseModelDTO.getErrorMessage());
	                }
				}
			}
			
		} catch (Exception e) {
			model.addAttribute("msg", "An error Occurred, please Try again later");
		}
		
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
		model.addAttribute("clinic_code",clinicCode);
		if(psiClinicUser != null && isManager || isAdmin) {
			model.addAttribute("showSubmitDraft", 2);
		}
	}
	
	
	private Set<PSIServiceProvision> extractPackageItems(Set<PSIServiceProvision> psiServiceProvisions) {
		Set<PSIServiceProvision> afterExtractingPackage = new HashSet<PSIServiceProvision>();
		for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
			log.error("service type " + psiServiceProvision.getServiceType());
			if(psiServiceProvision.getServiceType().equalsIgnoreCase("PACKAGE")) {
				SHNPackage shnPackage = Context.getService(SHNPackageService.class).findpackageByPackageCodeAndClinic(psiServiceProvision.getCode(), psiServiceProvision.getPsiMoneyReceiptId().getClinicCode());
				if(shnPackage != null) {
					log.error("packageDetails size " + shnPackage.getShnPackageDetails().size());
				}
				
				for (SHNPackageDetails shnPackageDetails : shnPackage.getShnPackageDetails()) {
					PSIServiceManagement psiServiceManagement = Context.getService(PSIServiceManagementService.class).findById(shnPackageDetails.getServiceProductId());
					if(psiServiceManagement != null) {
						PSIServiceProvision psiProvision = new PSIServiceProvision();
						psiProvision.setUuid(UUID.randomUUID().toString());
						psiProvision.setDateCreated(new Date());
						psiProvision.setIsSendToDHIS(PSIConstants.DEFAULTERRORSTATUS);
						psiProvision.setDateCreated(new Date());
						psiProvision.setCreator(Context.getAuthenticatedUser());
						psiProvision.setSendToDhisFromGlobal(PSIConstants.SUCCESSSTATUS);
						psiProvision.setItem(psiServiceManagement.getName());
						psiProvision.setCode(psiServiceManagement.getCode());
						psiProvision.setCategory(psiServiceManagement.getCategory());
						psiProvision.setUnitCost(shnPackageDetails.getUnitPriceInPackage());
						int totalQuantity = shnPackageDetails.getQuantity() * psiServiceProvision.getQuantity();
						psiProvision.setQuantity(totalQuantity);
						psiProvision.setTotalAmount(shnPackageDetails.getUnitPriceInPackage() * totalQuantity);
						psiProvision.setNetPayable(shnPackageDetails.getUnitPriceInPackage() * totalQuantity);
						psiProvision.setDiscount(0);
						psiProvision.setServiceType(psiServiceManagement.getType());	
						psiProvision.setPackageUuid(shnPackageDetails.getShnPackage().getUuid());
						psiProvision.setMoneyReceiptDate(psiServiceProvision.getMoneyReceiptDate());
						psiProvision.setPatientUuid(psiServiceProvision.getPatientUuid());
						psiProvision.setFinancialDiscount(psiServiceProvision.getFinancialDiscount());
						log.error("Successfully bind data to service provision " + psiServiceManagement.getSid());
						afterExtractingPackage.add(psiProvision);
					}
				}
				
			}
		}
		log.error("afterExtractingPackage size "+ afterExtractingPackage.size());
		if(afterExtractingPackage.size() > 0) {
			log.error("adding extracted items to servie provision list "+ psiServiceProvisions.size());
			//psiServiceProvisions.addAll(afterExtractingPackage);
			afterExtractingPackage.addAll(psiServiceProvisions);
			log.error("After added items to servie afterExtractingPackage list "+ afterExtractingPackage.size());
			afterExtractingPackage.removeIf(obj -> obj.getServiceType().equalsIgnoreCase("PACKAGE"));
			log.error("After removing package items to servie provision list "+ afterExtractingPackage.size());
			log.error("Actual service provision size "+ psiServiceProvisions.size());

			
		}
		return afterExtractingPackage;
	}
	
	
	private ApiResponseModelDTO checkAllProductStockStatus (Set<PSIServiceProvision> psiServiceProvisions,String clinicCode) {
		String stockOutProducts = "Stock not available. "; 
		log.error("ENtered to check stock " + psiServiceProvisions.size());
		ApiResponseModelDTO apiResponseModelDTO = new ApiResponseModelDTO();
		apiResponseModelDTO.setStatus(true);
		try {
			Set<PSIServiceProvision> productSet = new HashSet<PSIServiceProvision>();
	
			for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
				
				if(psiServiceProvision.getServiceType().equalsIgnoreCase("PRODUCT")) {
					productSet.add(psiServiceProvision);
				}
			}
	        Map<String, Integer> productQuantity = productSet.stream().collect(
	        	    Collectors.groupingBy(PSIServiceProvision::getCode,
	        	      Collectors.summingInt(PSIServiceProvision::getQuantity)));
			log.error("After grouping column " + productQuantity.toString());
			
	        for (Map.Entry<String, Integer> entry : productQuantity.entrySet()) {
	        	PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findByClinicId(clinicCode);
	        	if(psiClinicManagement !=null) {
	        		log.error("clinic id " + psiClinicManagement.getCid());
	        		log.error("product code " + entry.getKey());
				PSIServiceManagement psiServiceManagement = Context.getService(PSIServiceManagementService.class).findProductByCodeAndClinicId(entry.getKey(), psiClinicManagement.getCid());
	            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				if(psiServiceManagement != null) {
	        		log.error("product id " + psiServiceManagement.getSid());
					List<ClinicServiceDTO> productStock = Context.getService(PSIServiceManagementService.class).getProductListAll(psiClinicManagement.getCid(),psiServiceManagement.getSid());
					log.error("productStock size " + productStock.size());
					if(productStock.size() > 0) {
						Integer availableStock =   (int) productStock.get(0).getStock();
						log.error("availableStock " + availableStock);
						Integer providedStock = entry.getValue();
						log.error("providedStock " + providedStock);
						if(providedStock > availableStock) {
							apiResponseModelDTO.setStatus(false);
							stockOutProducts = stockOutProducts + productStock.get(0).getName() +"("+productStock.get(0).getCode()+ ")"+",";
						}
					}
				  }
	        	}
	        }
	        
			if (stockOutProducts.endsWith(",")) {
				stockOutProducts = stockOutProducts.substring(0, stockOutProducts.length() - 1);
			}
		
			apiResponseModelDTO.setErrorMessage(stockOutProducts);
		} 
		catch (Exception e) {
			apiResponseModelDTO.setStatus(false);
			apiResponseModelDTO.setErrorMessage(e.getMessage());
		}
		return apiResponseModelDTO;
	}
	
}
