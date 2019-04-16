package org.openmrs.module.PSI.web.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Configuration
@EnableAsync
@Controller
public class DHISListener {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		
		/*PSIDHISMarker psidhisMarker = new PSIDHISMarker();
		psidhisMarker.setType("Patient");
		//psidhisMarker.setMarkerDate(new Date());
		psidhisMarker.setTimestamp(0l);
		psidhisMarker.setDateCreated(new Date());
		//psidhisMarker.setCreator(Context.getAuthenticatedUser());
		psidhisMarker.setUuid(UUID.randomUUID().toString());
		psidhisMarker.setVoided(false);
		Context.openSession();
		Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
		Context.clearSession();
		
		*/
		List<String> datas = new ArrayList<String>();
		datas = Context.getService(PSIDHISMarkerService.class).rawQuery(0);
		for (String url : datas) {
			
			try {
				JSONObject patient = psiapiServiceFactory.getAPiObject("openmrs").get("", "", url);
				PSIDHISMarker psidhisMarker = new PSIDHISMarker();
				psidhisMarker.setType(patient.getString("uuid"));
				//psidhisMarker.setMarkerDate(new Date());
				psidhisMarker.setTimestamp(0l);
				psidhisMarker.setDateCreated(new Date());
				//psidhisMarker.setCreator(Context.getAuthenticatedUser());
				psidhisMarker.setUuid(UUID.randomUUID().toString());
				psidhisMarker.setVoided(false);
				Context.openSession();
				Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
				Context.clearSession();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
