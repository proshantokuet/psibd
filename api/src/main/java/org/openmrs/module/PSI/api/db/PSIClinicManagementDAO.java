package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.dto.PSILocation;
import org.openmrs.module.PSI.dto.PSILocationTag;

public interface PSIClinicManagementDAO {
	
	public PSIClinicManagement saveOrUpdateClinic(PSIClinicManagement psiClinic);
	
	public List<PSIClinicManagement> getAllClinic();
	
	public PSIClinicManagement findById(int id);
	
	public PSIClinicManagement findByClinicId(String clinicId);
	
	public PSIClinicManagement findByIdNotByClinicId(int id, String clinicId);
	
	public void delete(int id);
	
	public List<PSILocation> findLocationByTag(String tagName);
	
	public PSILocation findLocationById(int id);
	
	public List<PSILocation> findByparentLocation(int parentLocationId);
	
	public PSILocation findLocationByNameCode(String name, String code);
	
	public PSILocationTag findLocationTagByName(String name);
	
	public int save(Location location);
	
	public PSILocation findLastLocation();
	
}
