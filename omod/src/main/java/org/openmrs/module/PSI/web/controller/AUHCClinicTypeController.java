package org.openmrs.module.PSI.web.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCClinicType;
import org.openmrs.module.PSI.api.AUHCClinicTypeService;
import org.openmrs.module.PSI.api.PSIClinicChildService;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.impl.AUHCClinicTypeServiceImpl;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Controller
public class AUHCClinicTypeController {
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/PSI/addClinicType.form", method = RequestMethod.GET)
	public void addClinicType(HttpServletRequest request, HttpSession session, Model model){
		model.addAttribute("hasDashboardPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
		model.addAttribute("clinics",Context.getService(PSIClinicManagementService.class).getAllClinic());
		model.addAttribute("clinicType", new AUHCClinicType());
	}
	
	@RequestMapping(value = "/module/PSI/addClinicType.form", method = RequestMethod.POST)
	public ResponseEntity<String> addNewClinicType(@RequestBody String jsonStr, ModelMap model) throws Exception{

		model.addAttribute("hasDashboardPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		AUHCClinicType clinicType = new AUHCClinicType();
		JSONObject clinicTypeInfo = new JSONObject(jsonStr);
		try{
			if(clinicTypeInfo.has("clinicTypeName")){
				clinicType.setClinicTypeName(clinicTypeInfo.getString("clinicTypeName"));
			}
		}catch(Exception e){
			
		}
		
		clinicType.setVoided(false);
		clinicType.setCreator(Context.getAuthenticatedUser());
		clinicType.setDateCreated(new Date());
		clinicType.setUuid(UUID.randomUUID().toString());
		
		Context.openSession();
		Context.getService(AUHCClinicTypeService.class).saveOrUpdate(clinicType);
		Context.closeSession();
		return new ResponseEntity<>(new Gson().toJson(""), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/module/PSI/editClinicType.form", method = RequestMethod.GET)
	public void editClinicType(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(value="ctid",required=true) int ctid){
		model.addAttribute("hasDashboardPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
		AUHCClinicType clinicType = Context.getService(AUHCClinicTypeService.class).findByCtId(ctid);
//		model.addAttribute("clinics",Context.getService(PSIClinicManagementService.class).getAllClinic());
		model.addAttribute("clinicType", clinicType);
	}
	
	@RequestMapping(value = "/module/PSI/editClinicType.form", method = RequestMethod.POST)
	public ModelAndView updateClinicType(@ModelAttribute("clinicType") 
					AUHCClinicType clinicType,
		HttpSession session,ModelMap model){
		if(clinicType != null){
			AUHCClinicType saveClnicType = Context.getService(AUHCClinicTypeService.class).
					findByCtId(clinicType.getCtid());
			//Save or Update code
			saveClnicType.setClinicTypeName(clinicType.getClinicTypeName());
			saveClnicType.setDateChanged(new Date());
			saveClnicType.setChangedBy(Context.getAuthenticatedUser());
			saveClnicType = Context.getService(AUHCClinicTypeService.class).
					saveOrUpdate(saveClnicType);
			
			return new ModelAndView("redirect:/module/PSI/clinicTypeList.form");
		}
		return null;
	}
	
	@RequestMapping(value = "/module/PSI/clinicTypeList.form", method = RequestMethod.GET)
	public void clinicTypeList(HttpServletRequest request, HttpSession session, Model model) {
		model.addAttribute("hasDashboardPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
			model.addAttribute("hasClinicPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
	model.addAttribute("clinicTypeList",Context.getService(AUHCClinicTypeService.class).getAll());
//	model.addAttribute("clinicTypeList",null);
	}
}
