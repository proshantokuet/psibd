package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNFormPdfDetails;
import org.openmrs.module.PSI.SHnPrescriptionMetaData;
import org.openmrs.module.PSI.api.PSIUniquePatientService;
import org.openmrs.module.PSI.api.db.PSIUniquePatientDAO;

public class PSIUniquePatientServiceImpl extends BaseOpenmrsService implements PSIUniquePatientService {
	
	private PSIUniquePatientDAO dao;
	
	public PSIUniquePatientDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIUniquePatientDAO dao) {
		this.dao = dao;
	}
	@Override
	public Boolean findPatientByUicandMobileNo(String patientUic,
			String mobileNo) {
		// TODO Auto-generated method stub
		return dao.findPatientByUicandMobileNo(patientUic, mobileNo);
	}

	@Override
	public List<SHnPrescriptionMetaData> getAllPrescriptionMetaData() {
		// TODO Auto-generated method stub
		return dao.getAllPrescriptionMetaData();
	}

	@Override
	public List<SHNFormPdfDetails> getDischargeInformationByVisit(
			String patientUuid, String visitUuid) {
		// TODO Auto-generated method stub
		return dao.getDischargeInformationByVisit(patientUuid, visitUuid);
	}

	@Override
	public List<SHNFormPdfDetails> getbirthInformationByVisit(
			String patientUuid, String visitUuid) {
		// TODO Auto-generated method stub
		return dao.getbirthInformationByVisit(patientUuid, visitUuid);
	}

	@Override
	public String getLastProviderName(String visitUuid) {
		// TODO Auto-generated method stub
		return dao.getLastProviderName(visitUuid);
	}

	public Boolean findPatientByUicandMobileNoWhileEdit(String patientUic,
			String mobileNo, String patientUuid) {
		// TODO Auto-generated method stub
		return dao.findPatientByUicandMobileNoWhileEdit(patientUic, mobileNo, patientUuid);
	}

	public List<Concept> getconceptListGreaterthanCurrentConcept(int conceptId) {
		// TODO Auto-generated method stub
		return dao.getconceptListGreaterthanCurrentConcept(conceptId);

	}

}
