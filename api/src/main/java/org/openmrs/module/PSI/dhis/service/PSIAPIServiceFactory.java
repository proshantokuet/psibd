package org.openmrs.module.PSI.dhis.service;

import org.openmrs.module.PSI.dhis.service.impl.PSIDHISAPIServiceImpl;
import org.openmrs.module.PSI.dhis.service.impl.PSIOpenmrsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PSIAPIServiceFactory {
	
	private PSIAPIService psiapiService;
	
	@Autowired
	private PSIDHISAPIServiceImpl psidhisapiServiceImpl;
	
	@Autowired
	private PSIOpenmrsServiceImpl psiOpenmrsServiceImpl;
	
	public PSIAPIService getAPIType(String type) {
		if ("dhis2".equalsIgnoreCase(type)) {
			psiapiService = psidhisapiServiceImpl;
			
		} else if ("openmrs".equalsIgnoreCase(type)) {
			psiapiService = psiOpenmrsServiceImpl;
		} else {
			psiapiService = null;
		}
		return psiapiService;
		
	}
}
