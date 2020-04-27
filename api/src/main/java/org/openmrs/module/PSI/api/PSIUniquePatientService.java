package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinicChild;
import org.openmrs.module.PSI.SHnPrescriptionMetaData;

public interface PSIUniquePatientService  extends OpenmrsService {
	
	public Boolean findPatientByUicandMobileNo(String patientUic, String mobileNo);
	
	public List<SHnPrescriptionMetaData> getAllPrescriptionMetaData();
	
}
