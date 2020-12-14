package org.openmrs.module.PSI.web.controller.rest;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.api.PSIUniquePatientService;
import org.openmrs.module.PSI.converter.SHNConceptDataConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/sync")
@RestController
public class SHNConceptSyncRestController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/concept-data", method = RequestMethod.GET)
	public ResponseEntity<String> syncConcept() throws Exception {
		JSONArray conceptIdList = new JSONArray();
		List<Concept> getConceptId =  Context.getService(PSIUniquePatientService.class).getconceptListGreaterthanCurrentConcept(3600);
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
}
