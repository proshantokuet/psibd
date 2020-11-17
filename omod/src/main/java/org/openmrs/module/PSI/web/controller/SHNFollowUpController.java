package org.openmrs.module.PSI.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.SHNFollowUpActionService;
import org.openmrs.module.PSI.dto.SHNFollowUPReportDTO;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.PSI.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SHNFollowUpController {

	
	@RequestMapping(value = "/module/PSI/follow-Up-dashboard", method = RequestMethod.GET)
	public void pSIClinicList(HttpServletRequest request, HttpSession session, Model model) {
		List<PSIClinicManagement> clinics = Context.getService(PSIClinicManagementService.class).getAllClinic();
		model.addAttribute("clinics", clinics);
		List<SHNFollowUPReportDTO> followUpReport = Context.getService(SHNFollowUpActionService.class).getfollowUpReprt();
		model.addAttribute("followUpReport",followUpReport);

		model.addAttribute("hasDashboardPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.Dashboard));
		model.addAttribute("hasClinicPermission",
		    Utils.hasPrivilige(Context.getAuthenticatedUser().getPrivileges(), PSIConstants.ClinicList));
	}
}
