package org.openmrs.module.PSI.web.listener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openmrs.api.context.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.HnqisToShnConfigMapping;
import org.openmrs.module.PSI.api.SHNDhisObsElementService;
import org.openmrs.module.PSI.converter.DhisObsJsonDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.utils.HttpResponse;
import org.openmrs.module.PSI.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;


@Service
@EnableScheduling
@Configuration
@EnableAsync
@Controller
public class HnqisToShnListener {

	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;

	private final String DHIS2BASEURL = "http://182.160.100.202:8080";

	private final String ANALYTICS = DHIS2BASEURL + "/api/analytics";
	
	// SHN DHIS2
	//test server psi
	//private final String DATAVALUESETURLSHN = "http://10.100.11.2:5271";
	
	//live dhis url
	//private final String DATAVALUESETURLSHN = "http://10.100.11.3:5271";
	
	private final String DHIS2BASEURLSHN = "http://192.168.19.149/";
	
	private final String DATAVALUESETURLSHN = DHIS2BASEURLSHN + "/api/dataValueSets";
	
	private final String VERSIONAPI = DHIS2BASEURLSHN + "/api/metadata/version";
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		
		JSONObject getResponse = null;
		boolean status = true;
		try {
			getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
			
		}
		catch (Exception e) {
			
			status = false;
		}
		if (status) {
			
			try {
				sendHnqisToDhis2();
			}
			catch (Exception e) {
				
			}
		}

	}
	
	
	
	private void sendHnqisToDhis2() throws ParseException, JSONException {
		
		List<HnqisToShnConfigMapping> configMapping = Context.getService(SHNDhisObsElementService.class).getAllConfigMappingData();

		JSONArray configHnqiosJsonArray = toConvert(configMapping);
		
		//log.error("config array from database" + configHnqiosJsonArray.toString());
		
		Object document = parseDocument(configHnqiosJsonArray.toString());
		
		List<String> extractConfigJSON = JsonPath.read(document, "$.[?(@.configType == 'ORGUNIT')]");
		String convertedJson = new Gson().toJson(extractConfigJSON);
		JSONArray extractOrgUnitArray = new JSONArray(convertedJson);
		
		//log.error("org unit parse array" + extractOrgUnitArray.toString());
		
		List<String> DatasetJson = JsonPath.read(document, "$.[?(@.configType == 'DATASET')]");
		String convertedDataSetJson = new Gson().toJson(DatasetJson);
		JSONArray extractDatasetArray = new JSONArray(convertedDataSetJson);
		
		log.error("Data set array" + extractDatasetArray.toString());
		
		
		boolean stop = false;
		for (int i = 0; i < extractOrgUnitArray.length() && !stop; i++) {
			for (int j = 0; j < extractDatasetArray.length() && !stop; j++) {
				JSONObject dataset = extractDatasetArray.getJSONObject(j);
				//log.error("Data set ID" + dataset.getString("datasetId"));
				List<String> indicatorJson = JsonPath.read(document, "$.[?(@.configType == 'INDICATOR' && @.datasetId == '"+dataset.getString("datasetId")+"')]");
				//log.error("indicator json with datasetid" + indicatorJson.toString());
				String convertedIndicatorJson = new Gson().toJson(indicatorJson);
				JSONArray indicators = new JSONArray(convertedIndicatorJson);
				//log.error("Indicator json array" + indicators.toString());
				//log.error("org unit json extracted array" + extractOrgUnitArray.getJSONObject(i));
				
				boolean statusOfPost =  sendData(extractOrgUnitArray.getJSONObject(i), indicators,dataset);
				if(!statusOfPost) {
					stop = true;
				}
			}
			
		}
	}

	private boolean sendData(JSONObject orgUnitJson,JSONArray indicators,JSONObject dataset){
		boolean sentFlag = false;
		try{
			
			//log.error("Entering in send data" + orgUnitJson.toString());
			String orgUnit = orgUnitJson.getString("hnqisConfigId");
			//log.error("orgUnit" + orgUnit);
			String shnOrgUnit =orgUnitJson.getString("shnConfigId");
			//log.error("orgUnit" + shnOrgUnit);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format(new Date());
			
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormatForPeriod = new SimpleDateFormat("yyyyMM");
			String period = dateFormatForPeriod.format(date);

			
			JSONObject data = new JSONObject();
			
			data.put("dataSet", dataset.opt("datasetId"));
			data.put("completeDate", today);

			data.put("period", period);
			data.put("orgUnit", shnOrgUnit);
			
			JSONArray dataValues = new JSONArray();
			for (int i = 0; i < indicators.length(); i++) {
			
	           JSONObject indicator = new JSONObject(indicators.getString(i));
	            //JSONObject response = psiapiServiceFactory.getAPIType("dhis2").get("", "", ANALYTICS+"?dimension=dx:"+indicator.getString("hnqisConfigId")+"&dimension=pe:"+period+"&dimension=ou:"+orgUnit+"");
				HttpResponse op = HttpUtil.get(ANALYTICS+"?dimension=dx:"+indicator.getString("hnqisConfigId")+"&dimension=pe:"+period+"&dimension=ou:"+orgUnit+"", "", "qisapiadmin", "Qisapiadmin@123");
				JSONObject response = new JSONObject(op.body());
	            JSONArray rows = response.getJSONArray("rows");
	            
	    		
	    		if(rows.length()!=0) {
	    			log.error("i have data" + rows.length());
	    			JSONArray values = rows.getJSONArray(0);
	    			double value = values.getDouble(3);
	    			JSONObject datalueaValue = new JSONObject();
	    			datalueaValue.put("dataElement", indicator.getString("shnConfigId"));
	    			datalueaValue.put("value", value);
	                dataValues.put(datalueaValue);
	    		}		
	    	}
			data.put("dataValues", dataValues);
			if(dataValues.length() > 0) {
				log.error("Datavalues in SHN" + dataValues.toString());
				log.error("Data Post in shn" + data.toString());
				JSONObject response = psiapiServiceFactory.getAPIType("dhis2").add("", data, DATAVALUESETURLSHN);
				if(response.has("status")) {
					String sentStatus = response.getString("status");
					if(sentStatus == "SUCCESS") {
						sentFlag = true;
					}
				}
				log.error("response after posting shn dhis2" + response);
			}
			else {
				sentFlag = true;
			}

			
			}catch(Exception e){
				sentFlag = false;
				e.printStackTrace();
			}
		return sentFlag;
	}
	
	
	public static Object parseDocument(String IntialJsonDHISArray) {
		Object document = DhisObsJsonDataConverter.parseDocument(IntialJsonDHISArray);
		return document;
	}
	
	
	public JSONObject toConvert(HnqisToShnConfigMapping configMapping) throws JSONException {
		JSONObject psiClinicChildObject = new JSONObject();
		psiClinicChildObject.putOpt("configId", configMapping.getConfigId());
		psiClinicChildObject.putOpt("shnConfigId", configMapping.getShnConfigId());
		psiClinicChildObject.putOpt("hnqisConfigId", configMapping.getHnqisConfigId());
		psiClinicChildObject.putOpt("configType", configMapping.getConfigType());
		psiClinicChildObject.putOpt("datasetId", configMapping.getDatasetId());
		psiClinicChildObject.putOpt("voided", configMapping.getVoided());
		return psiClinicChildObject;
	}
	
	
	public JSONArray toConvert(List<HnqisToShnConfigMapping> configMappingList) throws JSONException {
		JSONArray hnqisToShnConfigJsonArray = new JSONArray();
		for (HnqisToShnConfigMapping configMapping : configMappingList) {
			hnqisToShnConfigJsonArray.put(toConvert(configMapping));
		}
		return hnqisToShnConfigJsonArray;
		
	}
	
}
