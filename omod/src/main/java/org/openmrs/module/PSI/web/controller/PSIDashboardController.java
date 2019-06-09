package org.openmrs.module.PSI.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.dto.PSIReport;
import org.openmrs.module.PSI.dto.UserDTO;
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
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(
		    psiClinicUser.getPsiClinicManagementId().getCid());
		Set<PSIClinicUser> psiClinicUsers = psiClinicManagement.getpSIClinicUser();
		
		model.addAttribute("psiClinicUsers", psiClinicUsers);
		/*select code, item, category, sum(Clilic) as Clilic, sum(Satellite) as Satellite,
			sum(CSP) as CSP , sum(Clilic)+sum(Satellite)+sum(CSP) as total
		from (
			select code,item ,category,service_point, sum(net_payable) as ttt,count(*),
			CASE WHEN service_point = 'Clinic' THEN sum(net_payable) ELSE 0 END Clilic,
			CASE WHEN service_point = 'Satellite' THEN sum(net_payable) ELSE 0 END Satellite,
			CASE WHEN service_point = 'CSP' THEN sum(net_payable)  ELSE 0 END CSP
				from openmrs.psi_service_provision as sp left join
				openmrs.psi_money_receipt as mr on  sp.psi_money_receipt_id =mr.mid
				where DATE(sp.money_receipt_date)  between '2019-03-31' and '2019-05-31' 
		        and clinic_code = 'mouha84s'
			group by code ,item,service_point,category order  by code) 
		as Report  group by code,item



		select code,item ,category, count(*) as serviceCount ,sum(net_payable) as total
		from openmrs.psi_service_provision as sp left join
			openmrs.psi_money_receipt as mr on  sp.psi_money_receipt_id =mr.mid
			where DATE(sp.money_receipt_date)  between '2019-03-31' and '2019-05-31' 
		    and clinic_code = 'mouha84s' and sp.creator = 4
		group by code ,item,category order  by code
		
		
		SELECT count(*) FROM openmrs.patient as p left join openmrs.person_attribute  as
		pa on p.patient_id = pa.person_id where person_attribute_type_id = 38 
		and value = 'mouha84s' and DATE(p.date_created) = '2019-05-26';

		SELECT count(*)  FROM openmrs.psi_money_receipt where clinic_code = 'mouha84s' 
		and money_receipt_date = '2019-05-05' ;

		SELECT sum(net_payable) FROM openmrs.psi_service_provision as sp left join 
		openmrs.psi_money_receipt as mr  on sp.psi_money_receipt_id = mr.mid  
		where sp.money_receipt_date = '2019-05-05 ' and mr.clinic_code = 'mouha84s';
		*/
	}
	
	@RequestMapping(value = "/module/PSI/ServicePointWise", method = RequestMethod.GET)
	public void ServicePointWiseReport(HttpServletRequest request, HttpSession session, Model model,
	                                   @RequestParam(required = true) String startDate,
	                                   @RequestParam(required = true) String endDate) {
		PSIClinicUser psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(
		    Context.getAuthenticatedUser().getUsername());
		List<PSIReport> reports = Context.getService(PSIServiceProvisionService.class).servicePointWiseReport(startDate,
		    endDate, psiClinicUser.getPsiClinicManagementId().getClinicId());
		model.addAttribute("reports", reports);
		/*model.addAttribute("reportr",
		    Context.getService(PSIServiceProvisionService.class).servicePointWiseRepor(startDate, endDate, "mouha84s"));
		*/
	}
	
	@RequestMapping(value = "/module/PSI/ServiceProviderWise", method = RequestMethod.GET)
	public void ServiceProviderWiseReport(HttpServletRequest request, HttpSession session, Model model,
	                                      @RequestParam(required = true) String startDate,
	                                      @RequestParam(required = true) String endDate,
	                                      @RequestParam(required = true) String provider) {
		/*Context.getAuthenticatedUser()*/
		UserDTO userDTO = Context.getService(PSIClinicUserService.class).findByUserNameFromOpenmrs(provider);
		List<PSIReport> reports = Context.getService(PSIServiceProvisionService.class).serviceProviderWiseReport(startDate,
		    endDate, "", userDTO.getId());
		model.addAttribute("reports", reports);
		
	}
}
