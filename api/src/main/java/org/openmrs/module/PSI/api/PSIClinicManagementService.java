package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.Location;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.dto.PSILocation;
import org.openmrs.module.PSI.dto.PSILocationTag;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIClinicManagementService extends OpenmrsService {
	
	public PSIClinicManagement saveOrUpdateClinic(PSIClinicManagement psiClinic);
	
	public List<PSIClinicManagement> getAllClinic();
	
	public PSIClinicManagement findById(int id);
	
	public PSIClinicManagement findByClinicId(String clinicId);
	
	public PSIClinicManagement findByIdNotByClinicId(int id, String clinicId);
	
	public void delete(int id);
	
	public List<PSILocation> findLocationByTag(String tagName);
	
	public PSILocation findLocationById(int id);
	
	public List<PSILocation> findByparentLocation(int parentLocationId);
	
	public PSILocation findLocationByNameCodeLocationTag(String name, String code, int location_tag_id);
	
	public PSILocation findLocationByNameCodeLocationTagParent(String name, String code, int location_tag_id,
	                                                           int parentLocation);
	
	public PSILocationTag findLocationTagByName(String name);
	
	public int save(Location location);
	
	public PSILocation findLastLocation();
	
	public int updateClinicPrimaryKey(int oldId, int currentId);
	
}
