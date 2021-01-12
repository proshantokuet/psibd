package org.openmrs.module.PSI.web.listener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.jayway.jsonpath.JsonPath;


@Service
@EnableScheduling
@Configuration
@EnableAsync
@Controller
public class HnqisToShnListener {

	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	//private final String DHIS2BASEURL = "http://dhis.mpower-social.com:1971";
	
	//private final String DHIS2BASEURL = "http://192.168.19.149";
	
	//test server psi
	//private final String DHIS2BASEURL = "http://10.100.11.2:5271";
	
	//live dhis url
	//private final String DHIS2BASEURL = "http://10.100.11.3:5271";
	

	private final String DHIS2BASEURL = "http://182.160.100.202:8080";

	private final String ANALYTICS = DHIS2BASEURL + "/api/analytics";
	
	// SHN DHIS2
	private final String DHIS2BASEURLSHN = "http://192.168.19.149/";
	
	private final String DATAVALUESETURLSHN = DHIS2BASEURLSHN + "/api/dataValueSets";
	
	private final String VERSIONAPI = DHIS2BASEURL + "/api/metadata/version";
	
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
		JSONArray orgunitMaps = new JSONArray();
		JSONObject orgunitMap = new JSONObject();
		orgunitMap.put("hnqs", "krGB0Ja929i");
		orgunitMap.put("shn", "XWDcwSGIuI5");
		orgunitMaps.put(orgunitMap);
		JSONObject orgunitMap1 = new JSONObject();
		orgunitMap1.put("hnqs", "bluq08bE2cU");
		orgunitMap1.put("shn", "cAWWacGBR8G");
		orgunitMaps.put(orgunitMap1);
		JSONArray datasets = getDataSets();
		//String s = new Gson().toJson("");
		JSONArray s = getIndicators();
		JSONArray configHnqiosJsonArray = toConvert(configMapping);
		Object document = parseDocument(configMapping.toString());
		String prog = "1";
		
		
		
		for (int i = 0; i < orgunitMaps.length(); i++) {
			for (int j = 0; j < datasets.length(); j++) {
				JSONObject dataset = datasets.getJSONObject(j);
				List<String > _indicators = JsonPath.read(document, "$.[?(@.program == '" + dataset.getString("id") + "')]");
				JSONArray indicators = new JSONArray(_indicators);
				sendData(orgunitMaps.getJSONObject(i), 202006, indicators,dataset);
			}
			
		}
	}

	private void sendData(JSONObject orgUnitJson,int period,JSONArray indicators,JSONObject dataset){
		try{
			
			
			String orgUnit = orgUnitJson.getString("hnqs");
			String shnOrgUnit =orgUnitJson.getString("shn");
			String dataSetId = "pVZqixN5A1K";
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format(new Date());
			
			JSONObject data = new JSONObject();
			
			data.put("dataSet", dataset.opt("dataSet"));
			data.put("completeDate", today);
			data.put("period", period);
			data.put("orgUnit", shnOrgUnit);
			
			JSONArray dataValues = new JSONArray();
			for (int i = 0; i < indicators.length(); i++) {
			
	           JSONObject indicator = new JSONObject(indicators.getString(i));
	            JSONObject response = psiapiServiceFactory.getAPIType("dhis2").get("", "", ANALYTICS+"?dimension=dx:"+indicator.getString("hnqs")+"&dimension=pe:"+period+"&dimension=ou:"+orgUnit+"");
	    		
	            JSONArray rows = response.getJSONArray("rows");
	            
	    		
	    		if(rows.length()!=0) {
	    			JSONArray values = rows.getJSONArray(0);
	    			double value = values.getDouble(3);
	    			JSONObject datalueaValue = new JSONObject();
	    			datalueaValue.put("dataElement", indicator.getString("shn"));
	    			datalueaValue.put("value", value);
	                dataValues.put(datalueaValue);
	    		}		
	    	}
			data.put("dataValues", dataValues);
			HttpResponse op = HttpUtil.post(DATAVALUESETURLSHN, "", data.toString(), "admin", "district");
			JSONObject res = new JSONObject(op.body());
			
			System.out.println("Data:"+data);
			System.out.println("Res:"+res);
			
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	
	private JSONArray getIndicators() throws JSONException{
		JSONArray indicators = new JSONArray();		
		JSONObject indicator = new JSONObject();
		indicator.put("hnqs", "JFIynmjXy64");
		indicator.put("shn", "VrJOcPU0fWM");
		indicator.put("program", "1");
		indicators.put(indicator);
		
		JSONObject indicator1 = new JSONObject();
		indicator1.put("hnqs", "RC3CuxE1x4e");
		indicator1.put("shn", "ofRYiwUQpQp");
		indicator1.put("program", "1");
		indicators.put(indicator1);
		
		JSONObject indicator2 = new JSONObject();
		indicator2.put("hnqs", "CSyKldUunvD");
		indicator2.put("shn", "Sk6t1ja0W3X");
		indicator2.put("program", "1");
		indicators.put(indicator2);
		
		JSONObject indicator3 = new JSONObject();
		indicator3.put("hnqs", "VAcX88W6Ctc");
		indicator3.put("shn", "ccpfsR62kt5");
		indicator3.put("program", "1");
		indicators.put(indicator3);
		
		JSONObject indicator4 = new JSONObject();
		indicator4.put("hnqs", "Mqbr0YQDhce");
		indicator4.put("shn", "CuAb7KtQqHa");
		indicator4.put("program", "1");
		indicators.put(indicator4);
		
		JSONObject indicator5 = new JSONObject();
		indicator5.put("hnqs", "iIrDNhckG2x");
		indicator5.put("shn", "LRLpIM5wrq3");
		indicator5.put("program", "1");
		indicators.put(indicator5);
		
		JSONObject indicator6 = new JSONObject();
		indicator6.put("hnqs", "a3SCh6VUMTh");
		indicator6.put("shn", "xPP6UvbhoUj");
		indicator6.put("program", "1");
		indicators.put(indicator6);
		return indicators;
		
		
	}
	
	private JSONArray getDataSets() throws JSONException{
		JSONArray programs = new JSONArray();	
		JSONObject program = new JSONObject();
		program.put("id", "1");
		program.put("dataSet", "pVZqixN5A1K");
		programs.put(program);
		return programs;
		
		
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
