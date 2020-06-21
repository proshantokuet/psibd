package org.openmrs.module.PSI.web.controller.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
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
import org.openmrs.module.PSI.AUHCClinicType;
import org.openmrs.module.PSI.PSIClinicSpot;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicSpotService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.converter.PSIClinicUserConverter;
import org.openmrs.module.PSI.converter.UserDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.DateTimeTypeConverter;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.openmrs.util.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RequestMapping("/rest/v1/clinic-user/")
@RestController
public class PSIClinicUserRestController extends MainResourceController {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	protected final Log log = LogFactory.getLog(getClass());

	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
	        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
	
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
	
	@RequestMapping(value = "/getUserForSync/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> findUserForSync(@PathVariable int id) throws Exception {
		String User = "";
		JSONArray userJsonArray = null;
		try {
			List<UserDTO> users = Context.getService(PSIClinicUserService.class).findUserByClinicIdWithRawQuery(id);
			users.sort(Comparator.comparing(UserDTO::getCuid));

			if (users != null) {
				User = gson.toJson(users);
				userJsonArray = new JSONArray(User);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		return new ResponseEntity<String>(userJsonArray.toString(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/syncUser/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> syncUser(@PathVariable int id) throws Exception {
		log.error("Hit in the api id is " + id);
		JSONArray userListJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "",
				"/rest/v1/clinic-user/getUserForSync" + "/" + id);
		log.error("api result of clinic from global" + userListJson.toString());
		List<UserDTO> clinicUserDTOs = gson.fromJson(userListJson.toString(),
			    new TypeToken<ArrayList<UserDTO>>() {}.getType());
		log.error("Converting to class dto" + clinicUserDTOs.size());
		try {
			  for (UserDTO clinicUserDTO : clinicUserDTOs) {
					log.error("in the loop" + clinicUserDTO.getFirstName());
			//for (int i = 0; i < userListJson.length(); i++) {
				//JSONObject userObject = userListJson.getJSONObject(i);
				//Gson gson= new Gson();
				//UserDTO clinicUserDTO = gson.fromJson(userObject.toString(),UserDTO.class);
				String firstName = clinicUserDTO.getFirstName();
				String lastName = clinicUserDTO.getLastName();
				String password = "Shn@1234";
				String userName = clinicUserDTO.getUserName();
				String rolesNames = clinicUserDTO.getRole().trim();
				log.error("rolesNames" + rolesNames);
				String gender = clinicUserDTO.getGender();
				int clinicId = id;
				int cuid = clinicUserDTO.getCuid();
				String email = clinicUserDTO.getEmail();
				String mobile = clinicUserDTO.getMobile();
				log.error("person name creation" + mobile);

				PersonName personName = new PersonName();
				personName.setGivenName(firstName);
				personName.setFamilyName(lastName);
				log.error("person name created" + personName.getGivenName());
				Set<PersonName> names = new TreeSet<PersonName>();
				names.add(personName);
				Person person = new Person();
				person.setNames(names);
				person.setGender(gender);
				log.error("person object created" + person.getNames());
				List<String> roleList = new ArrayList<String>(Arrays.asList(rolesNames.split(",")));
				log.error("roleList split" + roleList.toString());
				Set<Role> roles = new HashSet<Role>();
				for (String roleName : roleList) {
					Role role = Context.getService(UserService.class).getRole(roleName.trim());
					roles.add(role);
				}
				log.error("roles assigned" + roles.toString());
				User user = new User();
				user.setRoles(roles);
				User findUser = Context.getService(PSIClinicUserService.class).getbyUsernameIcludedRetiure(userName);
				log.error("search by username" + findUser);
				if (findUser == null) {
					log.error("attempt for saving" + person);
					person = Context.getService(PersonService.class).savePerson(person);
					log.error("person is saving" + person);
					user.setPerson(person);
					user.setUsername(userName);
					Provider provider = new Provider();
					provider.setPerson(person);
					provider.setIdentifier(userName);
					//provider.setUuid(clinicUserDTO.getProvider_uuid());
					Context.getService(ProviderService.class).saveProvider(provider);
					user = Context.getService(UserService.class).createUser(user, password);
					log.error("user save complete" + user.getUsername());
					PSIClinicUser pSIClinicUser = new PSIClinicUser();
					pSIClinicUser.setUserName(user.getUsername());
					pSIClinicUser.setEmail(email);
					pSIClinicUser.setMobile(mobile);
//					if (cuid != 0) {
//						pSIClinicUser.setCuid(cuid);
//					}
					pSIClinicUser.setDateCreated(new Date());
					pSIClinicUser.setCreator(Context.getAuthenticatedUser());
					pSIClinicUser.setUuid(UUID.randomUUID().toString());
					log.error("saving psi_clinic" + pSIClinicUser.getCuid());
					pSIClinicUser.setPsiClinicManagementId(Context.getService(PSIClinicManagementService.class).findById(
					    clinicId));
					log.error("getting psi_clinic management id" + pSIClinicUser.getPsiClinicManagementId());
					PSIClinicUser afterUpdate = Context.getService(PSIClinicUserService.class).saveOrUpdate(pSIClinicUser);
					Context.getService(PSIClinicUserService.class).updatePrimaryKey(cuid, afterUpdate.getCuid());

				} else {
					log.error("i am in update" + findUser.getPerson().getPersonId());
					boolean isRetired = clinicUserDTO.isRetired(); 
		
					Person personUpdate = Context.getService(PersonService.class).getPerson(findUser.getPerson().getPersonId());
					log.error("personUpdate success" + personUpdate.getNames().toString());
					int personNameId = personUpdate.getPersonName().getPersonNameId();
					log.error("personNameId update" + personNameId);

					PersonName personNameUpdate = Context.getService(PersonService.class).getPersonName(personNameId);
					personNameUpdate.setGivenName(firstName);
					personNameUpdate.setFamilyName(lastName);
					Set<PersonName> namesUpdate = personUpdate.getNames();
					namesUpdate.add(personNameUpdate);
					personUpdate.setNames(namesUpdate);
					personUpdate.setGender(gender);
					log.error("saving updated person" + personUpdate.getNames().toString());
					personUpdate = Context.getService(PersonService.class).savePerson(personUpdate);
					findUser.setPerson(personUpdate);
					findUser.setUsername(userName);
					if (isRetired) {
						findUser.setRetired(true);
						findUser.setRetiredBy(Context.getAuthenticatedUser());
						findUser.setRetireReason("reason in global");
					}
					else {
						findUser.setRetired(false);
						findUser.setRetiredBy(null);
						findUser.setRetireReason(null);
						findUser.setDateRetired(null);
					}
					findUser.setRoles(roles);
					log.error("saving updated user" + findUser.getPerson().getPersonId());
					findUser = Context.getService(UserService.class).saveUser(findUser);
		
					PSIClinicUser pSIClinicUser = new PSIClinicUser();
					pSIClinicUser.setUserName(findUser.getUsername());
					pSIClinicUser.setEmail(email);
					pSIClinicUser.setMobile(mobile);
					if (cuid != 0) {
						pSIClinicUser.setCuid(cuid);
					}
					pSIClinicUser.setDateCreated(new Date());
					pSIClinicUser.setCreator(Context.getAuthenticatedUser());
					pSIClinicUser.setUuid(UUID.randomUUID().toString());
					pSIClinicUser.setPsiClinicManagementId(Context.getService(PSIClinicManagementService.class).findById(clinicId));
					Context.getService(PSIClinicUserService.class).saveOrUpdate(pSIClinicUser);
				}
			  }
			//}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("Success", HttpStatus.OK);
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
			String isRetired = clinicUserDTO.getRetireStatus(); 
			String deactivateReasonString = "";
			if(!StringUtils.isBlank(clinicUserDTO.getDeactivateReason())) {
			   deactivateReasonString = clinicUserDTO.getDeactivateReason();
			}
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
				if (!StringUtils.isBlank(deactivateReasonString)) {
					user.setRetired(true);
					user.setRetiredBy(Context.getAuthenticatedUser());
					user.setRetireReason(deactivateReasonString);
				}
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
			}

			else if (StringUtils.isBlank(deactivateReasonString)
					&& !StringUtils.isBlank(isRetired)) {
				List<User> retiredUsersList = Context.getService(UserService.class).getUsersByPerson(person, true);
				User retiredUser = retiredUsersList.get(0);
				Context.getService(UserService.class).unretireUser(retiredUser);
			}

			else {
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
