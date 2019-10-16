package org.openmrs.module.PSI.api;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinicChild;

public interface PSIUniquePatientService  extends OpenmrsService {
	
	public Boolean findPatientByUicandMobileNo(String patientUic, String mobileNo);
	
}
