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
			
		}catch(Exception e){
			if(clinicTypeInfo.has("typeName")){
				clinicType.setTypeName(clinicTypeInfo.getString("typeName"));
			}
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

	
	@RequestMapping(value = "/module/PSI/clinicTypeList.form", method = RequestMethod.GET)
	public void clinicTypeList(HttpServletRequest request, HttpSession session, Model model) {
		model.addAttribute("hasDashboardPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
			model.addAttribute("hasClinicPermission",
			    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
		
	model.addAttribute("clinicTypeList",Context.getService(AUHCClinicTypeService.class).getAll());
	}
}
