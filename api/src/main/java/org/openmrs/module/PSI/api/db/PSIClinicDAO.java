package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIClinic;

public interface PSIClinicDAO {
	
	public PSIClinic saveOrUpdateClinic(PSIClinic psiClinic);
	
	public List<PSIClinic> getAllClinic();
	
	public PSIClinic findById(int id);
	
	public void delete(PSIClinic psiClinic);
	
}
