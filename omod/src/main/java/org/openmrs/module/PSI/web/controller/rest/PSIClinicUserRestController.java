package org.openmrs.module.PSI.web.controller.rest;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.converter.PSIClinicUserConverter;
import org.openmrs.module.PSI.converter.UserDataConverter;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/clinic-user/")
@RestController
public class PSIClinicUserRestController extends MainResourceController {
	
	@RequestMapping(value = "/get-by-username/{username}", method = RequestMethod.GET)
	public ResponseEntity<String> findById(@PathVariable String username) throws Exception {
		PSIClinicUser psiClinicUser = new PSIClinicUser();
		JSONObject psiClinic = new JSONObject();
		try {
			psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(username);
			if (psiClinicUser != null) {
				psiClinic = new PSIClinicUserConverter().toConvertClinic(psiClinicUser);
				psiClinic.put("status", "success");
			}
		}
		catch (Exception e) {
			psiClinic.put("msg", e.toString());
			psiClinic.put("status", "error");
			return new ResponseEntity<String>(psiClinic.toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(psiClinic.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-by-code/{code}", method = RequestMethod.GET)
	public ResponseEntity<String> findUserByCode(@PathVariable String code) throws Exception {
		JSONArray usersJson = new JSONArray();
		
		try {
			List<UserDTO> users = Context.getService(PSIClinicUserService.class).findUserByCode(code);
			if (users != null) {
				usersJson = new UserDataConverter().toConvert(users);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		return new ResponseEntity<String>(usersJson.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-by-org/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<String> findUser(@PathVariable String uuid) throws Exception {
		UserDTO userDTO = Context.getService(PSIClinicUserService.class).findOrgUnitFromOpenMRS(uuid);
		return new ResponseEntity<String>(userDTO.toString(), HttpStatus.OK);
	}
}
