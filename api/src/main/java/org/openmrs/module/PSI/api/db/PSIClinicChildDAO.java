package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIClinicChild;

public interface PSIClinicChildDAO {
	
	public PSIClinicChild saveOrUpdate(PSIClinicChild psiClinicAddChild);
	
	public PSIClinicChild findById(int id);
	
	public List<PSIClinicChild> findByMotherUuid(String motherUuid);
	
	public void delete(int id);
}
