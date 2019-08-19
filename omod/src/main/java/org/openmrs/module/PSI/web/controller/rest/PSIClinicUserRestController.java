package org.openmrs.module.PSI.web.controller.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.PersonService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.converter.PSIClinicUserConverter;
import org.openmrs.module.PSI.converter.UserDataConverter;
import org.openmrs.module.PSI.dto.ClinicUserDTO;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

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
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveUser(@RequestBody ClinicUserDTO clinicUserDTO, ModelMap model) throws Exception {
		PersonName personName = new PersonName();
		personName.setGivenName(clinicUserDTO.getFirstName());
		personName.setFamilyName(clinicUserDTO.getLastName());
		Set<PersonName> names = new TreeSet<PersonName>();
		names.add(personName);
		Person person = new Person();
		person.setNames(names);
		person.setGender(clinicUserDTO.getGender());
		
		person = Context.getService(PersonService.class).savePerson(person);
		
		Role role = Context.getService(UserService.class).getRole("Admin");
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		User user = new User();
		user.setRoles(roles);
		user.setPerson(person);
		user.setUsername(clinicUserDTO.getUserName());
		Context.getService(UserService.class).createUser(user, clinicUserDTO.getPassword());
		return new ResponseEntity<>(new Gson().toJson(""), HttpStatus.OK);
	}
}
