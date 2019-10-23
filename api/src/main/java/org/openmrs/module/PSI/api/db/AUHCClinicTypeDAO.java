package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.AUHCClinicType;

public interface AUHCClinicTypeDAO {
	public AUHCClinicType saveOrUpdate(AUHCClinicType clinicType);
	
	public AUHCClinicType findByCtId(int ctid);
	
	public void deleteByCtId(int ctid);
	
	public List<AUHCClinicType> getAll();
}
