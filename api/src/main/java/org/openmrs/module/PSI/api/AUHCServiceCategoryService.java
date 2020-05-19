package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.AUHCServiceCategory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AUHCServiceCategoryService extends OpenmrsService {
	
	public AUHCServiceCategory saveOrUpdate(AUHCServiceCategory aUHCServiceCategory);
	
	public List<AUHCServiceCategory> getAll();
	
	public AUHCServiceCategory findBySctId(int sctid);
	
	//	public void delete(int id);
	public int updatePrimaryKey(int oldId, int currentId);
	
}
