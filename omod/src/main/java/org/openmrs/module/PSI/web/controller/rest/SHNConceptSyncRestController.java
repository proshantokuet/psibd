package org.openmrs.module.PSI.web.controller.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.ConceptNumeric;
import org.openmrs.ConceptSet;
import org.openmrs.Drug;
import org.openmrs.api.ConceptNameType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.api.PSIUniquePatientService;
import org.openmrs.module.PSI.converter.SHNConceptDataConverter;
import org.openmrs.module.PSI.converter.SHNDrugConceptDataCOnverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.ConceptAnswerDTO;
import org.openmrs.module.PSI.dto.ConceptDescriptionDTO;
import org.openmrs.module.PSI.dto.EventReceordDTO;
import org.openmrs.module.PSI.dto.SHNConceptDTO;
import org.openmrs.module.PSI.dto.SHNConceptNameDTO;
import org.openmrs.module.PSI.dto.SHNConceptSetDTO;
import org.openmrs.module.PSI.dto.SHNDrugDTO;
import org.openmrs.module.PSI.utils.DateTimeTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RequestMapping("/rest/v1/sync")
@RestController
public class SHNConceptSyncRestController {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
	        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/concept-data/{eventId}", method = RequestMethod.GET)
	public ResponseEntity<String> syncConcept(@PathVariable int eventId) throws Exception {
		JSONArray conceptIdList = new JSONArray();
		List<EventReceordDTO> eventRecord =  Context.getService(PSIDHISMarkerService.class).getEventRecordsOfConcept(eventId);
		for (EventReceordDTO eventReceordDTO : eventRecord) {
			log.error("Entering single event " + eventReceordDTO.getId());
			String conceptUuid = eventReceordDTO.getUrl().split("/|\\?")[6];
			Concept conceptGet = Context.getService(ConceptService.class).getConceptByUuid(conceptUuid);
			ConceptNumeric conceptNumeric = null;
			if(conceptGet != null) {
				 conceptNumeric = Context.getService(ConceptService.class).getConceptNumeric(conceptGet.getConceptId());
			}
			
			log.error("Concept ID " + conceptGet.getConceptId());
			if(conceptGet != null) {
				JSONObject conceptJsonObject = new JSONObject();
				conceptJsonObject = new SHNConceptDataConverter().toConvert(conceptGet,conceptNumeric,eventReceordDTO.getId());
				 conceptIdList.put(conceptJsonObject);
			}
		}
		//List<Concept> getConceptId =  Context.getService(PSIUniquePatientService.class).getconceptListGreaterthanCurrentConcept(conceptId);
//		for (Concept concept : getConceptId) {
//			Concept conceptGet = Context.getService(ConceptService.class).getConcept(concept.getConceptId());
//			ConceptNumeric conceptNumeric = Context.getService(ConceptService.class).getConceptNumeric(concept.getConceptId());
//			log.error("Concept ID " + conceptGet.getConceptId());
//			if(conceptGet != null) {
//				JSONObject conceptJsonObject = new JSONObject();
//				conceptJsonObject = new SHNConceptDataConverter().toConvert(conceptGet,conceptNumeric);
//				 conceptIdList.put(conceptJsonObject);
//			}
//		}
		return new ResponseEntity<>(conceptIdList.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save-concept", method = RequestMethod.GET)
	public ResponseEntity<String> syncAndSaveConcept() throws Exception {
		try {
			int lastReadPatient = 4897;
			boolean isNewAdd = false;
			PSIDHISMarker getlastReadEntry = Context.getService(PSIDHISMarkerService.class).findByType("Concept");
			if (getlastReadEntry == null) {
				PSIDHISMarker psidhisMarker = new PSIDHISMarker();
				psidhisMarker.setType("Concept");
				psidhisMarker.setTimestamp(0l);
				psidhisMarker.setLastPatientId(4962);
				psidhisMarker.setDateCreated(new Date());
				psidhisMarker.setUuid(UUID.randomUUID().toString());
				psidhisMarker.setVoided(false);
				Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
			} else {
				lastReadPatient = getlastReadEntry.getLastPatientId();
			}
			String responseConceptId = "";
			log.error("ENtering save api" + lastReadPatient);
			String conceptSyncUrl = "/rest/v1/sync/concept-data/" + lastReadPatient;
			JSONArray conceptSyncJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "", conceptSyncUrl);
			log.error("conceptSyncJson Array" + conceptSyncJson.toString());
			List<SHNConceptDTO> shnConceptDto = gson.fromJson(conceptSyncJson.toString(),
				    new TypeToken<ArrayList<SHNConceptDTO>>() {}.getType());
			log.error("Fetching Concept Data" + shnConceptDto.size());
			for (SHNConceptDTO conceptDTO : shnConceptDto) {
				log.error("Entering  loop" + conceptDTO.getConceptId());
				Concept individualCopncept = Context.getService(ConceptService.class).getConcept(conceptDTO.getConceptId());
				if(individualCopncept == null) {
					log.error("Concept is null " + conceptDTO.getConceptId());
					individualCopncept = new Concept();
					individualCopncept.setConceptId(conceptDTO.getConceptId());
					individualCopncept.setCreator(Context.getAuthenticatedUser());
					individualCopncept.setDateCreated(new Date());
					individualCopncept.setUuid(conceptDTO.getUuid());
					isNewAdd = true;
				}
				individualCopncept.setRetired(conceptDTO.getRetired());
				individualCopncept.setRetireReason(conceptDTO.getRetireReason());
				log.error("goint to fetch concept datatype" + conceptDTO.getConceptId());
				log.error("goint to fetch concept datatype with datatype id" + conceptDTO.getDatatype().getConceptDatatypeId());
				ConceptDatatype conceptDataType = Context.getService(ConceptService.class).getConceptDatatype(conceptDTO.getDatatype().getConceptDatatypeId());
				individualCopncept.setDatatype(conceptDataType);
				log.error("goint to fetch concept class" + conceptDTO.getConceptClass().getConceptClassId());
				ConceptClass conceptClass = Context.getService(ConceptService.class).getConceptClass(conceptDTO.getConceptClass().getConceptClassId());
				individualCopncept.setConceptClass(conceptClass);
				individualCopncept.setSet(conceptDTO.getSet());
				log.error("concept obj creation complete" + conceptDTO.getConceptId());
				Collection<ConceptName> listConceptNames = new HashSet<ConceptName>();
				for (SHNConceptNameDTO conceptNameDto : conceptDTO.getNames()) {
					ConceptName individualConceptName = Context.getService(ConceptService.class).getConceptNameByUuid(conceptNameDto.getUuid());
					if(individualConceptName == null) {
						 individualConceptName = new ConceptName();
						 individualConceptName.setUuid(conceptNameDto.getUuid());
						 individualConceptName.setDateCreated(new Date());
						 individualConceptName.setCreator(Context.getAuthenticatedUser());
					}
					//individualConceptName.setConceptNameId(conceptNameDto.getConceptNameId());
					individualConceptName.setConcept(individualCopncept);
					individualConceptName.setName(conceptNameDto.getName());
					individualConceptName.setVoided(conceptNameDto.getVoided());
					log.error("setConceptNameType" + conceptNameDto.getConceptNameType());
					individualConceptName.setConceptNameType(ConceptNameType.valueOf(conceptNameDto.getConceptNameType()));
					log.error("setConceptNameType" + individualConceptName.getConceptNameType());
					individualConceptName.setLocalePreferred(conceptNameDto.getLocalePreferred());
					individualConceptName.setLocale(Locale.ENGLISH);
					
					listConceptNames.add(individualConceptName);
				}
				log.error("listConceptNames" + listConceptNames.size());
				log.error("concept id" + individualCopncept.getConceptId());
				if(!isNewAdd) {
				log.error("In update names" + individualCopncept.getConceptId());
				individualCopncept.getNames().clear();
				individualCopncept.getNames().addAll(listConceptNames);
				}
				else {
					individualCopncept.setNames(listConceptNames);
				}
				log.error("listConceptNames after adding to list" + individualCopncept.getNames().size());
				Collection<ConceptAnswer> listConceptAnswer = new HashSet<ConceptAnswer>();
				for (ConceptAnswerDTO conceptAnswerDto : conceptDTO.getAnswers()) {
					log.error("Entering to execute answer concept" + conceptAnswerDto.getConceptId());
					ConceptAnswer individualConceptAnswer = Context.getService(ConceptService.class).getConceptAnswerByUuid(conceptAnswerDto.getUuid());
					if(individualConceptAnswer == null) {
						individualConceptAnswer = new ConceptAnswer();
						individualConceptAnswer.setUuid(conceptAnswerDto.getUuid());
						individualConceptAnswer.setDateCreated(new Date());
						individualConceptAnswer.setSortWeight(conceptAnswerDto.getSortWeight());
					}
					//individualConceptAnswer.setConceptAnswerId(conceptAnswerDto.getConceptAnswerId());
					individualConceptAnswer.setConcept(individualCopncept);
					log.error("Getting  answer concept" + conceptAnswerDto.getAnswerConceptId());
					Concept answerConcept = Context.getService(ConceptService.class).getConcept(conceptAnswerDto.getAnswerConceptId());
					log.error("Entering to execute answer concept" + answerConcept.getConceptId());
					individualConceptAnswer.setAnswerConcept(answerConcept);
					if(conceptAnswerDto.getAnswerDrug() != null) {
						Drug drugConceptAnswer = Context.getService(ConceptService.class).getDrugByUuid(conceptAnswerDto.getAnswerDrug().getUuid());
						if(drugConceptAnswer == null) {
							drugConceptAnswer = new Drug();
							drugConceptAnswer.setUuid(conceptAnswerDto.getAnswerDrug().getUuid());
						}
						drugConceptAnswer.setCombination(conceptAnswerDto.getAnswerDrug().getCombination());
						drugConceptAnswer.setMaximumDailyDose(conceptAnswerDto.getAnswerDrug().getMaximumDailyDose());
						drugConceptAnswer.setMinimumDailyDose(conceptAnswerDto.getAnswerDrug().getMinimumDailyDose());
						drugConceptAnswer.setStrength(conceptAnswerDto.getAnswerDrug().getStrength());
						drugConceptAnswer.setConcept(individualCopncept);
						
						Concept dosageConcept = Context.getService(ConceptService.class).getConcept(conceptAnswerDto.getAnswerDrug().getDosageFormConceptId());
						if(dosageConcept != null) {
							drugConceptAnswer.setDosageForm(dosageConcept);
						}
/*							else {
								drugConceptAnswer.setDosageForm(individualCopncept);
							}*/
						individualConceptAnswer.setAnswerDrug(drugConceptAnswer);
						
					}
					
					listConceptAnswer.add(individualConceptAnswer);
				}
				log.error("listConceptAnswer" + listConceptAnswer.size());
				if(!isNewAdd) {
					individualCopncept.getAnswers().clear();
					individualCopncept.getAnswers().addAll(listConceptAnswer);
				}
				else {
					individualCopncept.setAnswers(listConceptAnswer);
				}
				
				Collection<ConceptSet> listConceptSet = new TreeSet<ConceptSet>();
				
				for (SHNConceptSetDTO conceptSetDto : conceptDTO.getConceptSets()) {
					
					ConceptSet individualConceptSet = Context.getService(ConceptService.class).getConceptSetByUuid(conceptSetDto.getUuid());
					if(individualConceptSet == null) {
						individualConceptSet = new ConceptSet();
						individualConceptSet.setCreator(Context.getAuthenticatedUser());
						individualConceptSet.setDateCreated(new Date());
						individualConceptSet.setUuid(conceptSetDto.getUuid());
					}
					//individualConceptSet.setConceptSetId(conceptSetDto.getConceptSetId());
					Concept conceptId = Context.getService(ConceptService.class).getConcept(conceptSetDto.getConceptId());
					individualConceptSet.setConcept(conceptId);
					individualConceptSet.setConceptSet(individualCopncept);
					individualConceptSet.setSortWeight(conceptSetDto.getSortWeight());
					listConceptSet.add(individualConceptSet);
				}
				log.error("listConceptSet" + listConceptSet.size());
				if(!isNewAdd) {
					individualCopncept.getConceptSets().clear();
					individualCopncept.getConceptSets().addAll(listConceptSet);
				}
				else {
					individualCopncept.setConceptSets(listConceptSet);
				}
				
				Collection<ConceptDescription> listConceptDescription = new HashSet<ConceptDescription>();
				for (ConceptDescriptionDTO conceptDescriptionDto : conceptDTO.getDescriptions()) {
					ConceptDescription individualConceptDescription = Context.getService(ConceptService.class).getConceptDescriptionByUuid(conceptDescriptionDto.getUuid());
					if(individualConceptDescription == null) {
						individualConceptDescription = new ConceptDescription();
						individualConceptDescription.setCreator(Context.getAuthenticatedUser());
						individualConceptDescription.setDateCreated(new Date());
						individualConceptDescription.setUuid(conceptDescriptionDto.getUuid());
					}
					//individualConceptDescription.setConceptDescriptionId(conceptDescriptionDto.getConceptDescriptionId());
					individualConceptDescription.setConcept(individualCopncept);
					individualConceptDescription.setDescription(conceptDescriptionDto.getDescription());
					individualConceptDescription.setLocale(Locale.ENGLISH);

					listConceptDescription.add(individualConceptDescription);
				}
				log.error("listConceptDescription" + listConceptDescription.size());
				if(!isNewAdd) {
					individualCopncept.getDescriptions().clear();
					individualCopncept.getDescriptions().addAll(listConceptDescription);
				}
				else {
					individualCopncept.setDescriptions(listConceptDescription);
				}
				log.error("GOing to save concept");
				

				Concept conceptSave = Context.getService(ConceptService.class).saveConcept(individualCopncept);
				if(conceptDTO.isNumeric()) {
					log.error("GOing to save numeric concept");
					ConceptNumeric conceptNumeric = Context.getService(ConceptService.class).getConceptNumeric(conceptSave.getConceptId());
					if(conceptNumeric == null) {
						 conceptNumeric = new ConceptNumeric(conceptSave);
					}
					conceptNumeric.setAllowDecimal(conceptDTO.getAllowDecimal());
					conceptNumeric.setHiAbsolute(conceptDTO.getHiAbsolute());
					conceptNumeric.setHiCritical(conceptDTO.getHiCritical());
					conceptNumeric.setHiNormal(conceptDTO.getHiNormal());
					conceptNumeric.setLowAbsolute(conceptDTO.getLowAbsolute());
					conceptNumeric.setLowCritical(conceptDTO.getLowCritical());
					conceptNumeric.setLowNormal(conceptDTO.getLowNormal());
					conceptNumeric.setUnits(conceptDTO.getUnits());
					conceptNumeric.setDisplayPrecision(conceptDTO.getDisplayPrecision());
					Context.getService(ConceptService.class).saveConcept(conceptNumeric);
				}
				log.error("Saved  numeric concept");
				responseConceptId = responseConceptId + " " +  conceptSave.getConceptId();
				getlastReadEntry.setLastPatientId(conceptDTO.getEventId());
				Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
			}
			if(shnConceptDto.size() < 1) {
				return new ResponseEntity<>("Concept is up-to-date with Global Server", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Sync Successfull with concept IDs" + responseConceptId, HttpStatus.OK);
			}
			
		
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage().toString(), HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value = "concept-data/drug/{eventId}", method = RequestMethod.GET)
	public ResponseEntity<String> syncDrugConcept(@PathVariable int eventId) throws Exception {
		JSONArray drugList = new JSONArray();
		List<EventReceordDTO> eventRecord =  Context.getService(PSIDHISMarkerService.class).getEventRecordsOfDrug(eventId);
		for (EventReceordDTO event : eventRecord) {
			String drugUuid = event.getUrl().split("/|\\?")[7];
			Drug drugObject = Context.getService(ConceptService.class).getDrugByUuid(drugUuid);
			log.error("Concept ID " + drugObject.getDrugId());
			if(drugObject != null) {
				JSONObject drugJsonObject = new JSONObject();
				drugJsonObject = new SHNDrugConceptDataCOnverter().toConvert(drugObject,event.getId());
				drugList.put(drugJsonObject);
			}
		}
		return new ResponseEntity<>(drugList.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save-drug", method = RequestMethod.GET)
	public ResponseEntity<String> saveDrugviaSync() throws Exception {
		try {
			int lastReadPatient = 2343;
			PSIDHISMarker getlastReadEntry = Context.getService(PSIDHISMarkerService.class).findByType("Drug");
			if (getlastReadEntry == null) {
				PSIDHISMarker psidhisMarker = new PSIDHISMarker();
				psidhisMarker.setType("Drug");
				psidhisMarker.setTimestamp(0l);
				psidhisMarker.setLastPatientId(2343);
				psidhisMarker.setDateCreated(new Date());
				psidhisMarker.setUuid(UUID.randomUUID().toString());
				psidhisMarker.setVoided(false);
				Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
			} else {
				lastReadPatient = getlastReadEntry.getLastPatientId();
			}
			String responseConceptId = "";
			log.error("ENtering save api" + lastReadPatient);
			String conceptSyncUrl = "/rest/v1/sync/concept-data/drug/" + lastReadPatient;
			JSONArray conceptSyncJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "", conceptSyncUrl);
			log.error("conceptSyncJson Array" + conceptSyncJson.toString());
			List<SHNDrugDTO> shnDrugDtos = gson.fromJson(conceptSyncJson.toString(),
				    new TypeToken<ArrayList<SHNDrugDTO>>() {}.getType());
			log.error("Fetching Concept Data" + shnDrugDtos.size());
			for (SHNDrugDTO shnDrugDTO  : shnDrugDtos) {
				log.error("Entering  loop" + shnDrugDTO.getDrugId());
				
				Drug getDrug = Context.getService(ConceptService.class).getDrugByUuid(shnDrugDTO.getUuid());
				
				if(getDrug == null) {
					getDrug = new Drug();
					
					getDrug.setCreator(Context.getAuthenticatedUser());
					getDrug.setDateCreated(new Date());
					getDrug.setUuid(shnDrugDTO.getUuid());
				}
				getDrug.setName(shnDrugDTO.getName());
				getDrug.setCombination(shnDrugDTO.getCombination());
				Concept dosageConcept = Context.getService(ConceptService.class).getConceptByUuid(shnDrugDTO.getDosageFrom());
				getDrug.setDosageForm(dosageConcept);
				getDrug.setMaximumDailyDose(Double.parseDouble(shnDrugDTO.getMaximumDailyDose()));
				getDrug.setMinimumDailyDose(Double.parseDouble(shnDrugDTO.getMinimumDailyDose()));
				getDrug.setStrength(shnDrugDTO.getStrength());
				Concept conceptDrug = Context.getService(ConceptService.class).getConceptByUuid(shnDrugDTO.getConcept());
				getDrug.setConcept(conceptDrug);
				getDrug.setRetired(shnDrugDTO.getRetired());
				getDrug.setRetireReason(shnDrugDTO.getRetireReason());
				
				Drug saveDrug = Context.getService(ConceptService.class).saveDrug(getDrug);
				responseConceptId = responseConceptId + " " +  saveDrug.getDrugId();
				getlastReadEntry.setLastPatientId(Integer.parseInt(shnDrugDTO.getEventId()));
				Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
			}
			if(shnDrugDtos.size() < 1) {
				return new ResponseEntity<>("Drug is up-to-date with Global Server", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Sync Successfull with Drug IDs" + responseConceptId, HttpStatus.OK);
			}
			
		
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage().toString(), HttpStatus.OK);
		}
	}
	
}
