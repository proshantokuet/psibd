package org.openmrs.module.PSI.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.ConceptSet;

public class SHNConceptDataConverter {
	
	protected final Log log = LogFactory.getLog(getClass());

	public JSONObject toConvert(Concept conceptObejct) throws JSONException {
		log.error("ENtering to create concept with id" + conceptObejct.getConceptId());
		JSONObject shnConceptDataObject = new JSONObject();
		shnConceptDataObject.putOpt("conceptId", conceptObejct.getConceptId());
		shnConceptDataObject.putOpt("retired", conceptObejct.getRetired());
		shnConceptDataObject.putOpt("retireReason", conceptObejct.getRetireReason());
		shnConceptDataObject.putOpt("set", conceptObejct.getSet());
		shnConceptDataObject.putOpt("uuid", conceptObejct.getUuid());
		
		JSONObject dataTypeObject = new JSONObject();
		dataTypeObject.putOpt("conceptDatatypeId", conceptObejct.getDatatype().getConceptDatatypeId());
		dataTypeObject.putOpt("hl7Abbreviation", conceptObejct.getDatatype().getHl7Abbreviation());
		shnConceptDataObject.putOpt("datatype", dataTypeObject);
		log.error("COmpelted to create dataTypeObject" + dataTypeObject.toString());
		
		JSONObject conceptClassObject = new JSONObject();
		conceptClassObject.putOpt("conceptClassId", conceptObejct.getConceptClass().getConceptClassId());
		shnConceptDataObject.putOpt("conceptClass", conceptClassObject);
		log.error("COmpelted to create conceptClassObject" + conceptClassObject.toString());
		
		
		JSONArray conceptNamesArray = new JSONArray();
		for (ConceptName conceptName : conceptObejct.getNames()) {
			JSONObject conceptNameObject = new JSONObject();
			conceptNameObject.putOpt("conceptNameId", conceptName.getConceptNameId());
			conceptNameObject.putOpt("name", conceptName.getName());
			conceptNameObject.putOpt("locale", conceptName.getLocale());
			conceptNameObject.putOpt("conceptNameType", conceptName.getConceptNameType());
			conceptNameObject.putOpt("localePreferred", conceptName.getLocalePreferred());
			conceptNameObject.putOpt("voided", conceptName.getLocalePreferred());
			conceptNameObject.putOpt("uuid", conceptName.getUuid());
			conceptNamesArray.put(conceptNameObject);
		}
		shnConceptDataObject.put("names", conceptNamesArray);
		log.error("COmpelted to create conceptNamesArray" + conceptNamesArray.toString());
		JSONArray conceptAnswerArray = new JSONArray();
		for (ConceptAnswer conceptAnswer : conceptObejct.getAnswers()) {
			JSONObject conceptAnswerObject = new JSONObject();
			conceptAnswerObject.putOpt("conceptAnswerId", conceptAnswer.getConceptAnswerId());
			conceptAnswerObject.putOpt("answerConceptId", conceptAnswer.getAnswerConcept().getConceptId());
			conceptAnswerObject.putOpt("sortWeight", conceptAnswer.getSortWeight());
			conceptAnswerObject.putOpt("uuid", conceptAnswer.getUuid());
			
			if(conceptAnswer.getAnswerDrug() != null) {
			JSONObject answerDrugObject = new JSONObject();
			answerDrugObject.putOpt("drugId", conceptAnswer.getAnswerDrug().getDrugId());
			answerDrugObject.putOpt("combination", conceptAnswer.getAnswerDrug().getCombination());
			answerDrugObject.putOpt("dosageFormConceptId", conceptAnswer.getAnswerDrug().getDosageForm().getConceptId());
			answerDrugObject.putOpt("maximumDailyDose", conceptAnswer.getAnswerDrug().getMaximumDailyDose());
			answerDrugObject.putOpt("minimumDailyDose", conceptAnswer.getAnswerDrug().getMinimumDailyDose());
			answerDrugObject.putOpt("strength", conceptAnswer.getAnswerDrug().getStrength());
			answerDrugObject.putOpt("uuid", conceptAnswer.getAnswerDrug().getUuid());
			conceptAnswerObject.putOpt("answerDrug", answerDrugObject);
			}
			conceptAnswerArray.put(conceptAnswerObject);
		}
		shnConceptDataObject.put("answers", conceptAnswerArray);
		log.error("COmpelted to create conceptAnswerArray" + conceptAnswerArray.toString());
		JSONArray conceptSetArray = new JSONArray();
		for (ConceptSet conceptSet : conceptObejct.getConceptSets()) {
			JSONObject conceptSetObject = new JSONObject();
			conceptSetObject.putOpt("conceptSetId", conceptSet.getConceptSetId());
			conceptSetObject.putOpt("parentConceptSet", conceptSet.getConceptSet().getConceptId());
			conceptSetObject.putOpt("sortWeight", conceptSet.getSortWeight());
			conceptSetObject.putOpt("uuid", conceptSet.getUuid());
			conceptSetArray.put(conceptSetObject);
		}
		shnConceptDataObject.put("conceptSets", conceptSetArray);
		log.error("COmpelted to create conceptSetArray" + conceptSetArray.toString());
		JSONArray conceptDescriptionArray = new JSONArray();
		for (ConceptDescription conceptDescription : conceptObejct.getDescriptions()) {
			JSONObject conceptDescriptionObject = new JSONObject();
			conceptDescriptionObject.putOpt("conceptDescriptionId", conceptDescription.getConceptDescriptionId());
			conceptDescriptionObject.putOpt("description", conceptDescription.getDescription());
			conceptDescriptionObject.putOpt("locale", conceptDescription.getLocale());
			conceptDescriptionObject.putOpt("uuid", conceptDescription.getUuid());
			conceptDescriptionArray.put(conceptDescriptionObject);
		}
		shnConceptDataObject.put("descriptions", conceptDescriptionArray);
		log.error("COmpelted to create conceptDescriptionArray" + conceptDescriptionArray.toString());
		return shnConceptDataObject;
	}
}
