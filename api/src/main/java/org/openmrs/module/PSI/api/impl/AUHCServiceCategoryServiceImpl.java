package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.AUHCServiceCategory;
import org.openmrs.module.PSI.api.AUHCServiceCategoryService;
import org.openmrs.module.PSI.api.db.AUHCServiceCategoryDAO;

public class AUHCServiceCategoryServiceImpl extends BaseOpenmrsService implements AUHCServiceCategoryService {
	
protected final Log log = LogFactory.getLog(getClass());

private AUHCServiceCategoryDAO dao;

public AUHCServiceCategoryDAO getDao() {
	return dao;
}

public void setDao(AUHCServiceCategoryDAO dao) {
	this.dao = dao;
}

@Override
public AUHCServiceCategory saveOrUpdate(AUHCServiceCategory aUHCServiceCategory) {
	// TODO Auto-generated method stub
	return dao.saveOrUpdate(aUHCServiceCategory);
}

@Override
public List<AUHCServiceCategory> getAll() {
	// TODO Auto-generated method stub
	return dao.getAll();
}

@Override
public AUHCServiceCategory findBySctId(int sctid) {
	// TODO Auto-generated method stub
	return dao.findBySctId(sctid);
}

@Override
public void delete(int id) {
	// TODO Auto-generated method stub
	dao.deleteCategory(id);
}
	
	

}
