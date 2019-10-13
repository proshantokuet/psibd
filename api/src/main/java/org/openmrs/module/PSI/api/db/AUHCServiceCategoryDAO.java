package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.AUHCServiceCategory;

public interface AUHCServiceCategoryDAO {
	public AUHCServiceCategory saveOrUpdate(AUHCServiceCategory aUHCServiceCategory);
	
	public AUHCServiceCategory findBySctId(int sctid);
	
//	public List<AUHCServiceCategory> findByMotherUuid(String motherUuid);
//	
//	public void delete(int id);
	public List<AUHCServiceCategory> getAll();
//	public void delete(int id);

}
