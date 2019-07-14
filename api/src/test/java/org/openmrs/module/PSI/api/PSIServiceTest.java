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

import org.junit.Test;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link $ PSIService} .
 */
public class PSIServiceTest extends BaseModuleContextSensitiveTest {
	
	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	public void shouldSetupContext() throws ParseException {
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
		
	}
}
