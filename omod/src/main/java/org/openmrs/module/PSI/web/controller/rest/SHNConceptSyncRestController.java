package org.openmrs.module.PSI.web.controller.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import org.openmrs.module.PSI.api.PSIUniquePatientService;
import org.openmrs.module.PSI.converter.SHNConceptDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.ConceptAnswerDTO;
import org.openmrs.module.PSI.dto.ConceptDescriptionDTO;
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
		JSONArray conceptIdList = new JSONArray();
		int syncableConceptNo = 4899;
		log.error("ENtering save api" + syncableConceptNo);
		String conceptSyncUrl = "/rest/v1/sync/concept-data/" + syncableConceptNo;
		JSONArray conceptSyncJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "", conceptSyncUrl);
		List<SHNConceptDTO> shnConceptDto = gson.fromJson(conceptSyncJson.toString(),
			    new TypeToken<ArrayList<SHNConceptDTO>>() {}.getType());
		for (SHNConceptDTO conceptDTO : shnConceptDto) {
			Concept conceptGet = Context.getService(ConceptService.class).getConcept(conceptDTO.getConceptId());
			if(conceptGet == null) {
				Concept individualCopncept = new Concept();
				individualCopncept.setConceptId(conceptDTO.getConceptId());
				individualCopncept.setRetired(conceptDTO.getRetired());
				individualCopncept.setRetireReason(conceptDTO.getRetireReason());
				ConceptDatatype conceptDataType = Context.getService(ConceptService.class).getConceptDatatype(individualCopncept.getDatatype().getConceptDatatypeId());
				individualCopncept.setDatatype(conceptDataType);
				ConceptClass conceptClass = Context.getService(ConceptService.class).getConceptClass(individualCopncept.getConceptClass().getConceptClassId());
				individualCopncept.setConceptClass(conceptClass);
				individualCopncept.setSet(conceptDTO.getSet());
				individualCopncept.setCreator(Context.getAuthenticatedUser());
				individualCopncept.setDateCreated(new Date());
				Collection<ConceptName> listConceptNames = new ArrayList<ConceptName>();
				for (SHNConceptNameDTO conceptNameDto : conceptDTO.getNames()) {
					ConceptName individualConceptName = new ConceptName();
					individualConceptName.setConceptNameId(conceptNameDto.getConceptNameId());
					individualConceptName.setConcept(individualCopncept);
					individualConceptName.setName(conceptNameDto.getName());
					individualConceptName.setCreator(Context.getAuthenticatedUser());
					individualConceptName.setDateCreated(new Date());
					individualConceptName.setVoided(conceptNameDto.getVoided());
					individualConceptName.setConceptNameType(ConceptNameType.valueOf(conceptNameDto.getConceptNameType()));
					individualConceptName.setLocalePreferred(conceptNameDto.getLocalePreferred());
					individualConceptName.setLocale(Locale.ENGLISH);
					individualConceptName.setUuid(conceptNameDto.getUuid());
					listConceptNames.add(individualConceptName);
				}
				individualCopncept.setNames(listConceptNames);
				Collection<ConceptAnswer> listConceptAnswer = new ArrayList<ConceptAnswer>();
				for (ConceptAnswerDTO conceptAnswerDto : conceptDTO.getAnswers()) {
					
					ConceptAnswer individualConceptAnswer = new ConceptAnswer();
					individualConceptAnswer.setConceptAnswerId(conceptAnswerDto.getConceptAnswerId());
					individualConceptAnswer.setConcept(individualCopncept);
					Concept answerConcept = Context.getService(ConceptService.class).getConcept(conceptAnswerDto.getAnswerConceptId());
					individualConceptAnswer.setAnswerConcept(answerConcept);
					if(conceptAnswerDto.getAnswerDrug() != null) {
						Drug drugConceptAnswer = new Drug();
						drugConceptAnswer.setDrugId(conceptAnswerDto.getAnswerDrug().getDrugId());
						drugConceptAnswer.setCombination(conceptAnswerDto.getAnswerDrug().getCombination());
						drugConceptAnswer.setMaximumDailyDose(conceptAnswerDto.getAnswerDrug().getMaximumDailyDose());
						drugConceptAnswer.setMinimumDailyDose(conceptAnswerDto.getAnswerDrug().getMinimumDailyDose());
						drugConceptAnswer.setStrength(conceptAnswerDto.getAnswerDrug().getStrength());
						drugConceptAnswer.setConcept(individualCopncept);
						drugConceptAnswer.setUuid(conceptAnswerDto.getAnswerDrug().getUuid());
						Concept dosageConcept = Context.getService(ConceptService.class).getConcept(conceptAnswerDto.getAnswerDrug().getDosageFormConceptId());
						if(dosageConcept != null) {
							drugConceptAnswer.setDosageForm(dosageConcept);
						}
						else {
							drugConceptAnswer.setDosageForm(individualCopncept);
						}
						individualConceptAnswer.setAnswerDrug(drugConceptAnswer);
						individualConceptAnswer.setUuid(conceptAnswerDto.getUuid());
					}
					listConceptAnswer.add(individualConceptAnswer);
				}
				individualCopncept.setAnswers(listConceptAnswer);
				
				Collection<ConceptSet> listConceptSet = new ArrayList<ConceptSet>();
				
				for (SHNConceptSetDTO conceptSetDto : conceptDTO.getConceptSets()) {
					
					ConceptSet individualConceptSet = new ConceptSet();
					individualConceptSet.setConceptSetId(conceptSetDto.getConceptSetId());
					Concept conceptId = Context.getService(ConceptService.class).getConcept(conceptSetDto.getConceptId());
					individualConceptSet.setConcept(conceptId);
					individualConceptSet.setConceptSet(individualCopncept);
					individualConceptSet.setSortWeight(conceptSetDto.getSortWeight());
					individualConceptSet.setCreator(Context.getAuthenticatedUser());
					individualConceptSet.setDateCreated(new Date());
					listConceptSet.add(individualConceptSet);
				}
				individualCopncept.setConceptSets(listConceptSet);
				
				Collection<ConceptDescription> listConceptDescription = new ArrayList<ConceptDescription>();
				for (ConceptDescriptionDTO conceptDescriptionDto : conceptDTO.getDescriptions()) {
					ConceptDescription individualConceptDescription = new ConceptDescription();
					individualConceptDescription.setConceptDescriptionId(conceptDescriptionDto.getConceptDescriptionId());
					individualConceptDescription.setConcept(individualCopncept);
					individualConceptDescription.setDescription(conceptDescriptionDto.getDescription());
					individualConceptDescription.setLocale(Locale.ENGLISH);
					individualConceptDescription.setCreator(Context.getAuthenticatedUser());
					individualConceptDescription.setDateCreated(new Date());
					listConceptDescription.add(individualConceptDescription);
				}
				individualCopncept.setDescriptions(listConceptDescription);
			}
		}
		return new ResponseEntity<>(conceptSyncJson.toString(), HttpStatus.OK);
	}
	
	
}
