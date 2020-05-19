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

import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.utils.DateTimeTypeConverter;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Tests {@link $ PSIService} .
 */
public class PSIServiceTest extends BaseModuleContextSensitiveTest {
	
	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	private final String CLINIC_TYPE_ENDPOINT = "/rest/v1/clinic/type";
	
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
	        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
	
	@Test
	public void shouldSetupContext() throws ParseException, JSONException {
		/*assertNotNull(Context.getService(PSIService.class));
		String s = "1985-04-18T00:00:00.000+0000";
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);
		System.out.println("Converted String: " + strDate);
		
		int f = 2;
		String l = f + "";
		String ss = "";
		if (l.length() == 1) {
			ss += "000" + l;
		} else if (l.length() == 2) {
			ss += "00" + l;
		} else if (l.length() == 3) {
			ss += "0" + l;
		}
		System.out.println(ss);
		Date date1 = yyyyMMdd.parse(s.substring(0, 10));
		System.out.println("Converted Sdate1tring: " + date1);
		
		List<EventReceordDTO> eventReceordDTOs = new ArrayList<EventReceordDTO>();
		LocalDateTime dateTime = LocalDateTime.parse("2018-05-05T11:50:55");
		System.out.println(dateTime);*/
		
		/*Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int dow = cal.get(Calendar.DAY_OF_WEEK);
		int dom = cal.get(Calendar.DAY_OF_MONTH);
		int doy = cal.get(Calendar.DAY_OF_YEAR);
		String dayS = day > 10 ? "" + day : "0" + day;
		String monthS = month > 10 ? "" + month : "0" + month;
		System.out.println("Current Date: " + cal.YEAR);
		System.out.println("Day: " + dayS);
		System.out.println("Month: " + monthS);
		System.out.println("Year: " + year);
		System.out.println("Day of Week: " + dow);
		System.out.println("Day of Month: " + dom);
		System.out.println("Day of Year: " + doy);
		
		System.out.println("1234".substring(0, 3));*/
		//435
		// 6632
		
		/*int y = 18;
		
		int yr = (int) Math.ceil(y / 4f);
		System.out.println(yr);
		int m = 2;
		int d = 1;
		int days = y * 365 + m * 30 + d;
		int mm = (int) Math.ceil(m / 2f);
		System.out.println(mm);
		int modMonth = (int) Math.ceil(m / 2f);
		System.out.println((days + modMonth + yr));*/
		/*String stringTxt = "asd ,asdsdsdsd ,fgtt";
		List<String> list = new ArrayList<>(Arrays.asList(stringTxt.split(",")));
		System.out.println(list.contains("asd"));
		for (String string : list) {
			System.out.println(string);
		}
		String[] dd = stringTxt.split(",");
		for (String string : dd) {
			System.err.println(string);
		}*/
		
		/*for (int i = 0; i < 7; i = i + 2) {
			System.out.println(i);
		}*/
		
		/*JSONArray clinicTypeJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "",
		    CLINIC_TYPE_ENDPOINT);
		List<AUHCClinicType> auhcClinicTypes = gson.fromJson(clinicTypeJson.toString(),
		    new TypeToken<ArrayList<AUHCClinicType>>() {}.getType());
		System.err.println(auhcClinicTypes);
		*/
		//List<AUHCClinicType> clients = gson.fromJson(dd.toString(), new TypeToken<ArrayList<AUHCClinicType>>() {}.getType());
		//System.out.println(clients);
		
		String _clinicsStr = "{\"dateChanged\":\"2019-08-28 14:03:04.0\",\"clinicId\":\"177\",\"address\":\"Bashabo SH Clinic, 43,Maddyha Bashabo, Dhaka-1214\",\"dhisId\":\"yzZ2cGq8cj9\",\"districtUuid\":\"89ceb342-3578-40a0-afe8-220aa00cd986\",\"upazila\":\"DHAKA SOUTH CITY CORPORATION\",\"uuid\":\"6faffd63-6d0e-4bcb-b234-5fe0ce5a3e9f\",\"division\":\"Dhaka\",\"districtId\":85,\"dateCreated\":\"2019-08-28 14:03:04.0\",\"upazilaId\":86,\"district\":\"DHAKA\",\"name\":\"Bashabo\",\"upazilaUuid\":\"4405a4f9-92a5-44b6-86a1-109a1b49efef\",\"divisionId\":16,\"divisionUuid\":\"6bbee4e4-daeb-4f3f-bb35-0be72f982a33\",\"category\":\"Vital\",\"cid\":5,\"timestamp\":1566979384733}";
		PSIClinicManagement clinicArray = gson.fromJson(_clinicsStr, new TypeToken<PSIClinicManagement>() {}.getType());
		//System.out.println(clinicArray.toString());
	}
}
