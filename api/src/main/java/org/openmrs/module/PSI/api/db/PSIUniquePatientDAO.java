package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHNFormPdfDetails;
import org.openmrs.module.PSI.SHnPrescriptionMetaData;

public interface PSIUniquePatientDAO {
	
	public Boolean findPatientByUicandMobileNo(String patientUic, String mobileNo);
	
	public List<SHnPrescriptionMetaData> getAllPrescriptionMetaData();
	
	public List <SHNFormPdfDetails> getDischargeInformationByVisit(String patientUuid, String visitUuid);
	
	public List <SHNFormPdfDetails> getbirthInformationByVisit(String patientUuid, String visitUuid);

	public String getLastProviderName(String visitUuid);
	
	public Boolean findPatientByUicandMobileNoWhileEdit(String patientUic, String mobileNo,String patientUuid);



}
