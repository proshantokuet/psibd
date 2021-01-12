/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.PSI.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.derby.tools.sysinfo;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.module.PSI.AUHCClinicType;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.utils.DateTimeTypeConverter;
import org.openmrs.module.PSI.utils.HttpResponse;
import org.openmrs.module.PSI.utils.HttpUtil;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

/**
 * Tests {@link $ PSIService} .
 */
public class PSIServiceTest extends BaseModuleContextSensitiveTest {
	
	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	private final String CLINIC_TYPE_ENDPOINT = "/rest/v1/clinic/type";
	private final String DHIS2BASEURL = "http://182.160.100.202:8080";
	private final String GETEVENTURL = DHIS2BASEURL + "/api/events.json";
	private final String ANALYTICS = DHIS2BASEURL + "/api/analytics";
	
	// SHN DHIS2
	private final String DHIS2BASEURLSHN = "http://192.168.19.149/";
	private final String DATAVALUESETURLSHN = DHIS2BASEURLSHN + "/api/dataValueSets";
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
	        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
	
	
	
	
	
	
	 
	
	@Ignore
	@Test
	public void shouldSetupContext() throws ParseException, JSONException {
		
	}
	
	@Test
	public void HNQSDataFetchTest() throws ParseException, JSONException {
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
		Object document = parseDocument(s+"");
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
		return Configuration.defaultConfiguration().jsonProvider().parse(IntialJsonDHISArray);
	}
	
	
}
