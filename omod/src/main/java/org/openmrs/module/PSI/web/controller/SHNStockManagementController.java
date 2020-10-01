package org.openmrs.module.PSI.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCServiceCategory;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.api.AUHCServiceCategoryService;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SHNStockManagementController {
	@RequestMapping(value = "/module/PSI/add-stock", method = RequestMethod.GET)
	public void addPSIClinic(HttpServletRequest request, HttpSession session, Model model,
	                         @RequestParam(required = false) int id) {
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		model.addAttribute("productList",
			    Context.getService(PSIServiceManagementService.class).getProductListAll(id,0));
		model.addAttribute("id", id);
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
/*	@RequestMapping(value = "/module/PSI/get-product-stock/{clinicId}/{productId}", method = RequestMethod.GET)
	@ResponseBody
	public String userByBranch(Model model,@PathVariable("clinicId") int clinicId,@PathVariable("productid") int productId) {
		List<ClinicServiceDTO> productStock = Context.getService(PSIServiceManagementService.class).getProductListAll(clinicId,productId);
		String stockAvailable = String.valueOf( productStock.get(0).getStock());
		return stockAvailable;
	}*/
	
	@RequestMapping(value = "/module/PSI/stock-invoice-list", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model,
	                          @RequestParam(required = true) int id) {
		model.addAttribute("id", id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		model.addAttribute("stockList",Context.getService(SHNStockService.class).getAllStockByClinicCode(psiClinicManagement.getClinicId()));
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/edit-stock", method = RequestMethod.GET)
	public void editPSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		
		List<AUHCServiceCategory> serviceCategory = Context.getService(AUHCServiceCategoryService.class).getAll();
		model.addAttribute("services",serviceCategory);

		model.addAttribute("pSIServiceManagement", Context.getService(PSIServiceManagementService.class).findById(id));
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/view-stock", method = RequestMethod.GET)
	public void getStockDetailsByStockId(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id,@RequestParam int clinicid) {
		
		model.addAttribute("id", clinicid);
		SHNStock stock = Context.getService(SHNStockService.class).findById(id);
		model.addAttribute("invoiceNo", stock.getInvoiceNumber());
		model.addAttribute("stockDetailsList", Context.getService(SHNStockService.class).getStockDetailsByStockId(id));
		
		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
	
	@RequestMapping(value = "/module/PSI/upload-stock", method = RequestMethod.GET)
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
	
}
