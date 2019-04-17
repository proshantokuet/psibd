package org.openmrs.module.PSI.web.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.EventReceordDTO;
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
	
	private final String trackerUrl = "http://192.168.19.148:1971/api/trackedEntityInstances";
	
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
		int lastReadPatient = 0;
		PSIDHISMarker getlastReadEntry = Context.getService(PSIDHISMarkerService.class).findByType("Patient");
		if (getlastReadEntry == null) {
			PSIDHISMarker psidhisMarker = new PSIDHISMarker();
			psidhisMarker.setType("Patient");
			psidhisMarker.setTimestamp(0l);
			psidhisMarker.setLastPatientId(0);
			psidhisMarker.setDateCreated(new Date());
			psidhisMarker.setUuid(UUID.randomUUID().toString());
			psidhisMarker.setVoided(false);
			Context.openSession();
			Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
			Context.clearSession();
		} else {
			lastReadPatient = getlastReadEntry.getLastPatientId();
		}
		List<EventReceordDTO> eventReceordDTOs = new ArrayList<EventReceordDTO>();
		eventReceordDTOs = Context.getService(PSIDHISMarkerService.class).rawQuery(lastReadPatient);
		for (EventReceordDTO eventReceordDTO : eventReceordDTOs) {
			try {
				JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", eventReceordDTO.getUrl());
				JSONObject patientJson = DHISDataConverter.toConvertPatient(patient);
				JSONObject response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
				getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
				Context.openSession();
				Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
				Context.clearSession();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
