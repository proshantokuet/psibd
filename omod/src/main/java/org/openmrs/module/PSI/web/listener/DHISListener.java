package org.openmrs.module.PSI.web.listener;

import java.util.Date;
import java.util.UUID;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
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
	
	public void sendData() throws Exception {
		PSIDHISMarker psidhisMarker = new PSIDHISMarker();
		psidhisMarker.setType("Patient");
		//psidhisMarker.setMarkerDate(new Date());
		psidhisMarker.setTimestamp(0l);
		psidhisMarker.setDateCreated(new Date());
		//psidhisMarker.setCreator(Context.getAuthenticatedUser());
		psidhisMarker.setUuid(UUID.randomUUID().toString());
		psidhisMarker.setVoided(false);
		Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
	}
	
}
