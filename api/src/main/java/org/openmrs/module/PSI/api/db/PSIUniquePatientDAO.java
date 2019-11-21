package org.openmrs.module.PSI.api.db;

public interface PSIUniquePatientDAO {
	
	public Boolean findPatientByUicandMobileNo(String patientUic, String mobileNo);
}
