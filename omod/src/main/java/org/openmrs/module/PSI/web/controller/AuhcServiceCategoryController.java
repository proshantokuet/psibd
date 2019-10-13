package org.openmrs.module.PSI.web.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCServiceCategory;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.AUHCServiceCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Controller
public class AuhcServiceCategoryController {

	protected final Log log = LogFactory.getLog(getClass());
	
	
	@RequestMapping(value = "/module/PSI/addServiceCategory.form", method = RequestMethod.GET)
	public void addServiceCategory(HttpServletRequest request, HttpSession session, Model model){
		model.addAttribute("serviceCategory", new AUHCServiceCategory());
		//Context.getService(AUHCServiceCategoryService.class).saveOrUpdate(pSIClinicUser);
		
		
	}
	@RequestMapping(value = "/module/PSI/addServiceCategory.form", method = RequestMethod.POST)
	public  ResponseEntity<String> addNewServiceCategory(@RequestBody String jsonStr, ModelMap model) throws Exception{
//		
		AUHCServiceCategory serviceCategory = new AUHCServiceCategory();
		JSONObject categoryInfo = new JSONObject(jsonStr);
		try{
			if(categoryInfo.has("categoryName")){
				serviceCategory.setCategoryName(categoryInfo.getString("categoryName"));
			}
			
		}catch(Exception e){
			serviceCategory.setCategoryName("Name Invalid");
		}
		
		serviceCategory.setDateCreated(new Date());
		serviceCategory.setCreator(Context.getAuthenticatedUser());
		serviceCategory.setVoided(false);
		serviceCategory.setUuid(UUID.randomUUID().toString());
		Context.openSession();
		 Context.getService(AUHCServiceCategoryService.class).saveOrUpdate(serviceCategory);
		Context.closeSession();
		// return new ResponseEntity<String>(new Gson().toJson(""), HttpStatus.OK);
		return new ResponseEntity<>(new Gson().toJson(""), HttpStatus.OK);
//		return new ResponseEntity;

	}
	@RequestMapping(value = "/module/PSI/servicecategoryList.form", method = RequestMethod.GET)
	public void seviceCategoryList(HttpServletRequest request, HttpSession session, Model model) {
		model.addAttribute("serviceCategories",Context.getService(AUHCServiceCategoryService.class).getAll());
	}

	@RequestMapping(value = "/module/PSI/editServiceCategory.form", method = RequestMethod.GET)
	public void editServiceCategory(HttpServletRequest request, HttpSession session, Model model, @RequestParam(value="sctid",required=true) int sctid) 
	{
//		Context.openSession();
		AUHCServiceCategory serviceCategory = Context.getService(AUHCServiceCategoryService.class).findBySctId(sctid);
		
		serviceCategory.setSctid(sctid);
		
		model.addAttribute("serviceCategory", serviceCategory);
	}
	
// serviceCategory	 
	@RequestMapping(value = "/module/PSI/updateServiceCategory.form", method = RequestMethod.POST)
	public ModelAndView updateServiceCategory(@ModelAttribute("serviceCategory") 
									AUHCServiceCategory serviceCategory,
						HttpSession session,ModelMap model){
		if(serviceCategory != null){
			AUHCServiceCategory auhcServiceCategory = new AUHCServiceCategory();
			auhcServiceCategory =  Context.getService(AUHCServiceCategoryService.class).
					findBySctId(serviceCategory.getSctid());
			 auhcServiceCategory.setCategoryName(serviceCategory.getCategoryName());
			 auhcServiceCategory.setId(serviceCategory.getId());
			 auhcServiceCategory.setDateCreated(new Date());
			 auhcServiceCategory.setCreator(Context.getAuthenticatedUser());
			 auhcServiceCategory.setUuid(UUID.randomUUID().toString());
			 auhcServiceCategory = Context.getService(AUHCServiceCategoryService.class).saveOrUpdate(auhcServiceCategory);			
			
			return new ModelAndView("redirect:module/PSI/servicecategorylist.form");
		}
		return null;
	}
	
}
