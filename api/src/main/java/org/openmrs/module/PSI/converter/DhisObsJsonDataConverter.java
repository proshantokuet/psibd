package org.openmrs.module.PSI.converter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.commons.lang.StringUtils;

import com.jayway.jsonpath.Configuration;

public class DhisObsJsonDataConverter {

	@SuppressWarnings("unchecked")
	public static String getObservations(JSONArray _obs) {
		JSONArray observations = new JSONArray();
		
		_obs.forEach(_ob -> {
			JSONObject ob = (JSONObject) _ob;
			String type = (String) ob.get("type");
			JSONArray groupMembers = (JSONArray) ob.get("groupMembers");
			// System.out.println("Coded..........:" + groupMembers.size() + " type:" + type);
			try {
				if (!StringUtils.isBlank(type) && type.equalsIgnoreCase("Coded")) {
					JSONObject codedConceptJsonObject = new JSONObject();
					String serviceName = (String) ob.get("formFieldPath");
					String serviceSplit[] = serviceName.split("\\.");
					serviceName = serviceSplit[0];
					JSONObject conceptJsonObject = (JSONObject) ob.get("concept");
					String questionName = (String) conceptJsonObject.get("name");
					String answerValue = (String) ob.get("valueAsString");
					String voidreason = (String) ob.get("voidReason");
					boolean isVoided = (boolean) ob.get("voided");
					codedConceptJsonObject.put("question", questionName);
					codedConceptJsonObject.put("answer", answerValue);
					codedConceptJsonObject.put("isVoided", isVoided);
					codedConceptJsonObject.put("voidReason", voidreason);
//					if(isNumeric(answerValue)) {
//						codedConceptJsonObject.put("answer", Double.parseDouble(answerValue));
//					}
//					else {
//						codedConceptJsonObject.put("answer", answerValue);
//					}
					codedConceptJsonObject.put("service", serviceName);
					observations.add(codedConceptJsonObject);

				} else if (groupMembers.size() != 0) {
					groupMembers.forEach(_groupMember -> {
						JSONObject groupMember = (JSONObject) _groupMember; 
						JSONObject codedConceptJsonObject = new JSONObject();
						String serviceName = (String) groupMember.get("formFieldPath");
						String serviceSplit[] = serviceName.split("\\.");
						serviceName = serviceSplit[0];
						JSONObject conceptJsonObject = (JSONObject) groupMember.get("concept");
						String questionName = (String) conceptJsonObject.get("name");
						String answerValue = (String) groupMember.get("valueAsString");
						String voidreason = (String) ob.get("voidReason");
						boolean isVoided = (boolean) ob.get("voided");
						codedConceptJsonObject.put("question", questionName);
						
//						if(isNumeric(answerValue)) {
//							codedConceptJsonObject.put("answer", Double.parseDouble(answerValue));
//						}
//						else {
//							codedConceptJsonObject.put("answer", answerValue);
//						}
						codedConceptJsonObject.put("answer", answerValue);
						codedConceptJsonObject.put("isVoided", isVoided);
						codedConceptJsonObject.put("voidReason", voidreason);
						codedConceptJsonObject.put("service", serviceName);
						observations.add(codedConceptJsonObject);
					});
					
				} else {
					JSONObject codedConceptJsonObject = new JSONObject();
					String serviceName = (String) ob.get("formFieldPath");
					String serviceSplit[] = serviceName.split("\\.");
					serviceName = serviceSplit[0];
					JSONObject conceptJsonObject = (JSONObject) ob.get("concept");
					String questionName = (String) conceptJsonObject.get("name");
					String answerValue = (String) ob.get("valueAsString");
					String voidreason = (String) ob.get("voidReason");
					boolean isVoided = (boolean) ob.get("voided");
					codedConceptJsonObject.put("question", questionName);
					
//					if(isNumeric(answerValue)) {
//						codedConceptJsonObject.put("answer", Double.parseDouble(answerValue));
//					}
//					else {
//						codedConceptJsonObject.put("answer", answerValue);
//					}
					codedConceptJsonObject.put("answer", answerValue);
					codedConceptJsonObject.put("isVoided", isVoided);
					codedConceptJsonObject.put("voidReason", voidreason);
					codedConceptJsonObject.put("service", serviceName);
					observations.add(codedConceptJsonObject);
				}
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				//System.out.println(ob);
				e.printStackTrace();
			}
		});
		return observations.toString();
	}
	
	
	public static Object parseDocument(String IntialJsonDHISArray){
		 return Configuration.defaultConfiguration().jsonProvider().parse(IntialJsonDHISArray);
	}
	
	
}
