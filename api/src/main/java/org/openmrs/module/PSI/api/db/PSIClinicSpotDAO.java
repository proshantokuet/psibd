package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIClinicSpot;

public interface PSIClinicSpotDAO {
	
	public PSIClinicSpot saveOrUpdate(PSIClinicSpot psiClinicSpot);
	
	public List<PSIClinicSpot> findAll();
	
	public PSIClinicSpot findById(int id);
	
	public PSIClinicSpot findDuplicateSpot(int id, String code, int clinicCode);
	
	public List<PSIClinicSpot> findByClinicId(int id);
	
	public void delete(int id);
	
	public int updatePrimaryKey(int oldId, int currentId);
	
}
