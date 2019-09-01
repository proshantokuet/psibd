package org.openmrs.module.PSI.web.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Provider;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.PersonService;
import org.openmrs.api.ProviderService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.converter.PSIClinicUserConverter;
import org.openmrs.module.PSI.converter.UserDataConverter;
import org.openmrs.module.PSI.dto.ClinicUserDTO;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.openmrs.util.Security;
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
	public ResponseEntity<String> saveUser(@RequestBody UserDTO clinicUserDTO, ModelMap model) throws Exception {
		String msg = "";
		try {
			String firstName = clinicUserDTO.getFirstName();
			String lastName = clinicUserDTO.getLastName();
			String password = clinicUserDTO.getPassword();
			String userName = clinicUserDTO.getUserName();
			String rolesNames = clinicUserDTO.getRoles();
			String gender = clinicUserDTO.getGender();
			int clinicId = clinicUserDTO.getClinicId();
			int cuid = clinicUserDTO.getCuid();
			String email = clinicUserDTO.getEmail();
			String mobile = clinicUserDTO.getMobile();
			PersonName personName = new PersonName();
			personName.setGivenName(firstName);
			personName.setFamilyName(lastName);
			Set<PersonName> names = new TreeSet<PersonName>();
			names.add(personName);
			Person person = new Person();
			person.setNames(names);
			person.setGender(gender);
			
			List<String> roleList = new ArrayList<String>(Arrays.asList(rolesNames.split(",")));
			Set<Role> roles = new HashSet<Role>();
			for (String roleName : roleList) {
				Role role = Context.getService(UserService.class).getRole(roleName);
				roles.add(role);
			}
			
			User user = new User();
			user.setRoles(roles);
			User findUser = Context.getService(UserService.class).getUserByUsername(userName);
			if (findUser == null) {
				person = Context.getService(PersonService.class).savePerson(person);
				user.setPerson(person);
				user.setUsername(userName);
				Provider provider = new Provider();
				provider.setPerson(person);
				provider.setIdentifier(userName);
				Context.getService(ProviderService.class).saveProvider(provider);
				user = Context.getService(UserService.class).createUser(user, password);
				
				PSIClinicUser pSIClinicUser = new PSIClinicUser();
				pSIClinicUser.setUserName(user.getUsername());
				pSIClinicUser.setEmail(email);
				pSIClinicUser.setMobile(mobile);
				if (cuid != 0) {
					pSIClinicUser.setCuid(cuid);
				}
				pSIClinicUser.setDateCreated(new Date());
				pSIClinicUser.setCreator(Context.getAuthenticatedUser());
				pSIClinicUser.setUuid(UUID.randomUUID().toString());
				pSIClinicUser.setPsiClinicManagementId(Context.getService(PSIClinicManagementService.class).findById(
				    clinicId));
				Context.getService(PSIClinicUserService.class).saveOrUpdate(pSIClinicUser);
				msg = "";
			} else {
				msg = "User name already exists";
			}
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(new Gson().toJson(e.getMessage()), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<String> updateUser(@RequestBody UserDTO clinicUserDTO, ModelMap model) throws Exception {
		String msg = "";
		try {
			String firstName = clinicUserDTO.getFirstName();
			String lastName = clinicUserDTO.getLastName();
			String password = clinicUserDTO.getPassword();
			String userName = clinicUserDTO.getUserName();
			String rolesNames = clinicUserDTO.getRoles();
			String gender = clinicUserDTO.getGender();
			int clinicId = clinicUserDTO.getClinicId();
			int cuid = clinicUserDTO.getCuid();
			int personId = clinicUserDTO.getPersonId();
			String email = clinicUserDTO.getEmail();
			String mobile = clinicUserDTO.getMobile();
			
			Person person = Context.getService(PersonService.class).getPerson(personId);
			
			if (person == null) {
				msg = "Person doesn't exists";
				return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
			}
			int personNameId = person.getPersonName().getPersonNameId();
			PersonName personName = Context.getService(PersonService.class).getPersonName(personNameId);
			personName.setGivenName(firstName);
			personName.setFamilyName(lastName);
			Set<PersonName> names = person.getNames();
			names.add(personName);
			person.setNames(names);
			person.setGender(gender);
			
			List<String> roleList = new ArrayList<String>(Arrays.asList(rolesNames.split(",")));
			Set<Role> roles = new HashSet<Role>();
			for (String roleName : roleList) {
				Role role = Context.getService(UserService.class).getRole(roleName);
				roles.add(role);
			}
			
			User user = Context.getService(UserService.class).getUserByUsername(userName);
			if (user != null) {
				person = Context.getService(PersonService.class).savePerson(person);
				user.setPerson(person);
				user.setUsername(userName);
				user.setRoles(roles);
				user = Context.getService(UserService.class).saveUser(user);
				if (!PSIConstants.DefaultPassword.equalsIgnoreCase(password)) {
					String salt = Security.getRandomToken();
					String hashedPassword = Security.encodeString(password + salt);
					Context.getService(UserService.class).changeHashedPassword(user, hashedPassword, salt);
				}
				PSIClinicUser pSIClinicUser = new PSIClinicUser();
				pSIClinicUser.setUserName(user.getUsername());
				pSIClinicUser.setEmail(email);
				pSIClinicUser.setMobile(mobile);
				if (cuid != 0) {
					pSIClinicUser.setCuid(cuid);
				}
				pSIClinicUser.setDateCreated(new Date());
				pSIClinicUser.setCreator(Context.getAuthenticatedUser());
				pSIClinicUser.setUuid(UUID.randomUUID().toString());
				pSIClinicUser.setPsiClinicManagementId(Context.getService(PSIClinicManagementService.class).findById(
				    clinicId));
				Context.getService(PSIClinicUserService.class).saveOrUpdate(pSIClinicUser);
				msg = "";
			} else {
				msg = "User name doesn't exists";
			}
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(new Gson().toJson(e.getMessage()), HttpStatus.OK);
		}
	}
}
