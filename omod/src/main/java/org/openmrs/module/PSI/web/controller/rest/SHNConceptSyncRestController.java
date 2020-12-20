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
	
	@RequestMapping(value = "/concept-data/{conceptId}", method = RequestMethod.GET)
	public ResponseEntity<String> syncConcept(@PathVariable int conceptId) throws Exception {
		JSONArray conceptIdList = new JSONArray();
		List<Concept> getConceptId =  Context.getService(PSIUniquePatientService.class).getconceptListGreaterthanCurrentConcept(conceptId);
		for (Concept concept : getConceptId) {
			Concept conceptGet = Context.getService(ConceptService.class).getConcept(concept.getConceptId());
			log.error("Concept ID " + conceptGet.getConceptId());
			if(conceptGet != null) {
				JSONObject conceptJsonObject = new JSONObject();
				conceptJsonObject = new SHNConceptDataConverter().toConvert(conceptGet);
				 conceptIdList.put(conceptJsonObject);
			}
		}
		return new ResponseEntity<>(conceptIdList.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save-concept", method = RequestMethod.GET)
	public ResponseEntity<String> syncAndSaveConcept() throws Exception {
		try {
			int lastReadPatient = 4897;
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
				if(individualCopncept.getConceptId() != null) {
				log.error("In update names" + individualCopncept.getConceptId());
				individualCopncept.getNames().clear();
				individualCopncept.getNames().addAll(listConceptNames);
				}
				else {
					individualCopncept.setNames(listConceptNames);
				}
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
				if(individualCopncept.getConceptId() != null) {
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
				if(individualCopncept.getConceptId() != null) {
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
				if(individualCopncept.getConceptId() != null) {
					individualCopncept.getDescriptions().clear();
					individualCopncept.getDescriptions().addAll(listConceptDescription);
				}
				else {
					individualCopncept.setDescriptions(listConceptDescription);
				}
				
				log.error("GOing to save concept");
				Concept conceptSave = Context.getService(ConceptService.class).saveConcept(individualCopncept);
				responseConceptId = responseConceptId + " " +  conceptSave.getConceptId();
				getlastReadEntry.setLastPatientId(conceptSave.getConceptId());
				Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
			}
			
			return new ResponseEntity<>("Sync Successfull with concept IDs" + responseConceptId, HttpStatus.OK);
		
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
	
}
