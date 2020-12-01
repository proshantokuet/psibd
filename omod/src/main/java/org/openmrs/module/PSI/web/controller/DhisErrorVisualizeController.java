package org.openmrs.module.PSI.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCDhisErrorVisualize;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.api.AUHCDhisErrorVisualizeService;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DhisErrorVisualizeController {
	@RequestMapping(value = "/module/PSI/dhisErrVisualize", method = RequestMethod.GET)
	public void dhisSyncreportDashboard(HttpServletRequest request, HttpSession session, Model model) {
		
		List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();

		// String patientToSync = Context.getService(AUHCDhisErrorVisualizeService.class).getPatientToDhisSyncInformation("3", "");
		
		// String patientTransferred = Context.getService(AUHCDhisErrorVisualizeService.class).getPatientToDhisSyncInformation("1", "");
		
		// String patientFailedSync = Context.getService(AUHCDhisErrorVisualizeService.class).getPatientToDhisSyncInformation("2", "");
		
		// String transferredMoneyReceipt = Context.getService(AUHCDhisErrorVisualizeService.class).getMoneyReceiptToDhisSyncInformation("money_receipt_transferred", "");
		
		// String money_receipt_to_sync = Context.getService(AUHCDhisErrorVisualizeService.class).getMoneyReceiptToDhisSyncInformation("money_receipt_to_sync", "");
		
		// String sync_failed = Context.getService(AUHCDhisErrorVisualizeService.class).getMoneyReceiptToDhisSyncInformation("sync_failed", "");
		
		// AUHCDhisErrorVisualize auhcDhisErrorVisualize = new AUHCDhisErrorVisualize();
		
		// List<AUHCDhisErrorVisualize> patientDhisReport = Context.getService(AUHCDhisErrorVisualizeService.class).getPatientDhisSyncReport(auhcDhisErrorVisualize);
		
		model.addAttribute("hasDashboardPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
			model.addAttribute("hasClinicPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
			model.addAttribute("patientDhisReport", null);
			model.addAttribute("clinics", clinics);
			model.addAttribute("patientToSync", "0");
			model.addAttribute("patientTransferred", "0");
			model.addAttribute("patientFailedSync", "0");
			model.addAttribute("transferredMoneyReceipt", "0");
			model.addAttribute("transferredServiceContact", "0");
			model.addAttribute("money_receipt_to_sync", "0");
			model.addAttribute("sync_failed", "0");						
	}
	
	@RequestMapping(value = "/module/PSI/dhisPatientReportSyncData", method = RequestMethod.GET)
	public void dhisSyncReportPatient(HttpServletRequest request, HttpSession session, Model model, @RequestParam(required = false) String clinicCode) {
		
		AUHCDhisErrorVisualize auhcDhisErrorVisualize = new AUHCDhisErrorVisualize();
		if(!StringUtils.isEmpty(clinicCode)) {
			auhcDhisErrorVisualize.setClinic_code(clinicCode);
		}
		List<AUHCDhisErrorVisualize> patientDhisReport = Context.getService(AUHCDhisErrorVisualizeService.class).getPatientDhisSyncReport(auhcDhisErrorVisualize);
		String patientToSyncInfo = Context.getService(AUHCDhisErrorVisualizeService.class).getPatientToDhisSyncInformation("3", auhcDhisErrorVisualize.getClinic_code());
		
		String[] splitAggValPatient = patientToSyncInfo.split("\\|");
		
		//String patientTransferred = Context.getService(AUHCDhisErrorVisualizeService.class).getPatientToDhisSyncInformation("1", auhcDhisErrorVisualize.getClinic_code());
		
		//String patientFailedSync = Context.getService(AUHCDhisErrorVisualizeService.class).getPatientToDhisSyncInformation("2", auhcDhisErrorVisualize.getClinic_code());
		
		model.addAttribute("hasDashboardPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
			model.addAttribute("hasClinicPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		model.addAttribute("patientDhisReport", patientDhisReport);
		model.addAttribute("patientToSync", splitAggValPatient[2]);
		model.addAttribute("patientTransferred", splitAggValPatient[0]);
		model.addAttribute("patientFailedSync", splitAggValPatient[1]);
	}
	
	@RequestMapping(value = "/module/PSI/dhisMoneyReceiptReportSyncData", method = RequestMethod.GET)
	public void dhisSyncReportMoneyReceipt(HttpServletRequest request, HttpSession session, Model model, @RequestParam(required = false) String clinicCode) {
		
		AUHCDhisErrorVisualize auhcDhisErrorVisualize = new AUHCDhisErrorVisualize();
		if(!StringUtils.isEmpty(clinicCode)) {
			auhcDhisErrorVisualize.setClinic_code(clinicCode);
		}
		List<AUHCDhisErrorVisualize> moneyReceiptDhisReport = Context.getService(AUHCDhisErrorVisualizeService.class).getMoneyReceiptDhisSyncReport(auhcDhisErrorVisualize);
		String transferredMoneyReceiptInfo = Context.getService(AUHCDhisErrorVisualizeService.class).getMoneyReceiptToDhisSyncInformation("money_receipt_info", auhcDhisErrorVisualize.getClinic_code());
		
		String[] splitAggVal = transferredMoneyReceiptInfo.split("\\|");
		//String money_receipt_to_sync = Context.getService(AUHCDhisErrorVisualizeService.class).getMoneyReceiptToDhisSyncInformation("money_receipt_to_sync", auhcDhisErrorVisualize.getClinic_code());
		
		//String sync_failed = Context.getService(AUHCDhisErrorVisualizeService.class).getMoneyReceiptToDhisSyncInformation("sync_failed", auhcDhisErrorVisualize.getClinic_code());
		model.addAttribute("hasDashboardPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
			model.addAttribute("hasClinicPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		model.addAttribute("moneyReceiptDhisReport", moneyReceiptDhisReport);
		model.addAttribute("transferredMoneyReceipt", splitAggVal[0]);
		model.addAttribute("money_receipt_to_sync", splitAggVal[3]);
		model.addAttribute("sync_failed", splitAggVal[2]);	
		model.addAttribute("transferredServiceContact", splitAggVal[1]);
	}
	
	@RequestMapping(value = "/module/PSI/dhis-data-upload", method = RequestMethod.GET)
	public void dhisHistoricalDataUpload(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		model.addAttribute("id", id);
		model.addAttribute("hasDashboardPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
}
