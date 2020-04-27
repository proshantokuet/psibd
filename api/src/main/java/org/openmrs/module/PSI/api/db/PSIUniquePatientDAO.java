package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHnPrescriptionMetaData;

public interface PSIUniquePatientDAO {
	
	public Boolean findPatientByUicandMobileNo(String patientUic, String mobileNo);
	
	public List<SHnPrescriptionMetaData> getAllPrescriptionMetaData();

}
