package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.AUHCClinicType;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AUHCClinicTypeService extends OpenmrsService {
	
	public AUHCClinicType saveOrUpdate(AUHCClinicType aUHCClinicType);
	
	public List<AUHCClinicType> getAll();
	
	public AUHCClinicType findByCtId(int ctid);
	
	public AUHCClinicType save(AUHCClinicType clinicType);
	
	public int updatePrimaryKey(int oldId, int currentId);
}
