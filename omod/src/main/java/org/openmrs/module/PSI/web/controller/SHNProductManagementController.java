package org.openmrs.module.PSI.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCServiceCategory;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.SHNStockAdjust;
import org.openmrs.module.PSI.api.AUHCServiceCategoryService;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;
import org.openmrs.module.PSI.dto.SHNStockAdjustDTO;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SHNProductManagementController {
	
	@RequestMapping(value = "/module/PSI/add-product", method = RequestMethod.GET)
	public void addPSIClinic(HttpServletRequest request, HttpSession session, Model model,
	                         @RequestParam(required = false) int id) {
		
		List<AUHCServiceCategory> serviceCategory = Context.getService(AUHCServiceCategoryService.class).getAll();
		model.addAttribute("services",serviceCategory);
		model.addAttribute("user", Context.getAuthenticatedUser());
		model.addAttribute("pSIServiceManagement", new PSIServiceManagement());
		model.addAttribute("id", id);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/product-list", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model,
	                          @RequestParam(required = true) int id) {
		model.addAttribute("productList",
		    Context.getService(PSIServiceManagementService.class).getProductListAll(id,0));
		model.addAttribute("id", id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/edit-product", method = RequestMethod.GET)
	public void editPSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		
		List<AUHCServiceCategory> serviceCategory = Context.getService(AUHCServiceCategoryService.class).getAll();
		model.addAttribute("services",serviceCategory);

		model.addAttribute("pSIServiceManagement", Context.getService(PSIServiceManagementService.class).findById(id));
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/upload-product", method = RequestMethod.GET)
	public void uploadPSIClinicService(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		model.addAttribute("id", id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		model.addAttribute("pSIServiceManagement", new PSIClinicManagement());
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
	}
	
	@RequestMapping(value = "/module/PSI/adjust-stock", method = RequestMethod.GET)
	public void adjustStock(HttpServletRequest request, HttpSession session, Model model,
	                         @RequestParam(required = false) int id,@RequestParam(required = false) int clinicid) {
		
		model.addAttribute("id", clinicid);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(clinicid);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		List<ClinicServiceDTO> productStock = Context.getService(PSIServiceManagementService.class).getProductListAll(clinicid,id);
		model.addAttribute("product", productStock.get(0));
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/adjust-history", method = RequestMethod.GET)
	public void adjustHistory(HttpServletRequest request, HttpSession session, Model model,
	                         @RequestParam(required = false) int id) {
		
		model.addAttribute("id", id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		List<SHNStockAdjustDTO> adjustStock = Context.getService(SHNStockService.class).getAdjustHistoryAllByClinic(id);
		model.addAttribute("adjustStockList", adjustStock);
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/view-adjust-stock", method = RequestMethod.GET)
	public void adjustHistory(HttpServletRequest request, HttpSession session, Model model,
	                         @RequestParam(required = false) int id,@RequestParam(required = false) int clinicid) {
		
		model.addAttribute("id", clinicid);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(clinicid);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		SHNStockAdjustDTO adjustStock = Context.getService(SHNStockService.class).getAdjustHistoryById(id,clinicid);
		model.addAttribute("adjustStock", adjustStock);
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
}
