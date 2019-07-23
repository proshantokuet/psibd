package org.openmrs.module.PSI.web.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PSIServiceManagementController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/PSI/addPSIClinicService", method = RequestMethod.GET)
	public void addPSIClinic(HttpServletRequest request, HttpSession session, Model model,
	                         @RequestParam(required = false) int id) {
		List<PSIClinicManagement> psiClinicManagements = Context.getService(PSIClinicManagementService.class).getAllClinic();
		model.addAttribute("clinics", psiClinicManagements);
		model.addAttribute("user", Context.getAuthenticatedUser());
		model.addAttribute("pSIServiceManagement", new PSIServiceManagement());
		model.addAttribute("id", id);
	}
	
	@RequestMapping(value = "/module/PSI/PSIClinicServiceList", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model,
	                          @RequestParam(required = true) int id) {
		model.addAttribute("pSIServiceManagements",
		    Context.getService(PSIServiceManagementService.class).getAllByClinicId(id));
		model.addAttribute("id", id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
	}
	
	@RequestMapping(value = "/module/PSI/editPSIClinicService", method = RequestMethod.GET)
	public void editPSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		List<PSIClinicManagement> psiClinicManagements = Context.getService(PSIClinicManagementService.class).getAllClinic();
		model.addAttribute("clinics", psiClinicManagements);
		model.addAttribute("pSIServiceManagement", Context.getService(PSIServiceManagementService.class).findById(id));
		
	}
	
	@RequestMapping(value = "/module/PSI/uploadPSIClinicService", method = RequestMethod.GET)
	public void uploadPSIClinicService(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		model.addAttribute("id", id);
		PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
		model.addAttribute("psiClinicManagement", psiClinicManagement);
		model.addAttribute("pSIServiceManagement", new PSIClinicManagement());
		
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/module/PSI/uploadPSIClinicService", method = RequestMethod.POST)
	public ModelAndView uploadPSIClinicService(@RequestParam MultipartFile file, HttpServletRequest request, ModelMap model,
	                                           @RequestParam(required = false) int id) throws Exception {
		
		String msg = "";
		if (file.isEmpty()) {
			model.put("msg", "failed to upload file because its empty");
			model.addAttribute("msg", "failed to upload file because its empty");
			return new ModelAndView("redirect:/module/PSI/uploadPSIClinicService.form?id=" + id);
		} else if (!"text/csv".equalsIgnoreCase(file.getContentType())) {
			model.addAttribute("msg", "file type should be '.csv'");
			return new ModelAndView("redirect:/module/PSI/uploadPSIClinicService.form?id=" + id);
		}
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File csvFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		
		try {
			try (InputStream is = file.getInputStream();
			        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(csvFile))) {
				int i;
				
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		}
		catch (IOException e) {
			model.put("msg", "failed to process file because : " + e.getMessage());
			return new ModelAndView("redirect:/module/PSI/uploadPSIClinicService.form?id=" + id);
		}
		log.info("CSV FIle:" + csvFile.getName());
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int index = 0;
		try {
			PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
				PSIServiceManagement psm = new PSIServiceManagement();
				String[] service = line.split(cvsSplitBy);
				
				if (index != 0) {
					PSIServiceManagement findByCodeAndClinicId = Context.getService(PSIServiceManagementService.class)
					        .findByCodeAndClinicId(service[1], id);
					if (findByCodeAndClinicId == null) {
						PSIServiceManagement pSIServiceManagement = new PSIServiceManagement();
						pSIServiceManagement.setName(service[0]);
						pSIServiceManagement.setCode(service[1]);
						pSIServiceManagement.setCategory(service[2]);
						pSIServiceManagement.setProvider(service[3]);
						pSIServiceManagement.setUnitCost(Float.parseFloat(service[4]));
						pSIServiceManagement.setPsiClinicManagement(psiClinicManagement);
						pSIServiceManagement.setDateCreated(new Date());
						pSIServiceManagement.setCreator(Context.getAuthenticatedUser());
						psiClinicManagement.setTimestamp(System.currentTimeMillis());
						pSIServiceManagement.setUuid(UUID.randomUUID().toString());
						Context.getService(PSIServiceManagementService.class).saveOrUpdate(pSIServiceManagement);
					}
				}
				
				index++;
				
			}
			
		}
		catch (Exception e) {
			log.info("Some problem occured, please contact with admin..");
			msg = "failed to process file because : " + e.fillInStackTrace();
			e.printStackTrace();
			return new ModelAndView("redirect:/module/PSI/uploadPSIClinicService.form?id=" + id);
		}
		
		return new ModelAndView("redirect:/module/PSI/PSIClinicServiceList.form");
		
	}
	
	@RequestMapping(value = "/module/PSI/deletePSIClinicService", method = RequestMethod.GET)
	public ModelAndView deletePSIClinic(HttpServletRequest request, HttpSession session, Model model, @RequestParam int id) {
		Context.getService(PSIServiceManagementService.class).delete(id);
		return new ModelAndView("redirect:/module/PSI/PSIClinicServiceList.form");
	}
	
	@RequestMapping(value = "/module/PSI/addPPSIClinicService", method = RequestMethod.POST)
	public ModelAndView addORUpdatePSIClinic(@ModelAttribute("pSIServiceManagement") PSIServiceManagement pSIServiceManagement,
	                                         HttpSession session, Model model) throws Exception {
		try {
			if (pSIServiceManagement != null) {
				log.info("saving new module objects...................");
				pSIServiceManagement.setDateCreated(new Date());
				pSIServiceManagement.setCreator(Context.getAuthenticatedUser());
				pSIServiceManagement.setUuid(UUID.randomUUID().toString());
				Context.getService(PSIServiceManagementService.class).saveOrUpdate(pSIServiceManagement);
				return new ModelAndView("redirect:/module/PSI/PSIClinicServiceList.form");
			}
			
		}
		catch (Exception e) {
			model.addAttribute("pSIServiceManagement", pSIServiceManagement);
			model.addAttribute("msg", "Some thing wrong , please contact with administrator");
			return new ModelAndView("redirect:/module/PSI/addPSIClinicService.form");
		}
		return null;
	}
	
}
