package org.openmrs.module.PSI.api.impl;
import org.openmrs.api.impl.BaseOpenmrsService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openmrs.module.PSI.AUHCClinicType;
import org.openmrs.module.PSI.api.AUHCClinicTypeService;
import org.openmrs.module.PSI.api.db.AUHCClinicTypeDAO;

public class AUHCClinicTypeServiceImpl extends BaseOpenmrsService  implements AUHCClinicTypeService{

	protected final Log log = LogFactory.getLog(getClass());
	
	private AUHCClinicTypeDAO dao;
	
	
	public AUHCClinicTypeDAO getDao(){
		return this.dao;
	}
	
	public void setDao(AUHCClinicTypeDAO dao){
		this.dao = dao;
	}
	@Override
	public AUHCClinicType saveOrUpdate(AUHCClinicType aUHCClinicType) {
		// TODO Auto-generated method stub
		dao.saveOrUpdate(aUHCClinicType);
		return aUHCClinicType;
	}

	@Override
	public List<AUHCClinicType> getAll() {
		// TODO Auto-generated method stub
		return dao.getAll();
	}

	@Override
	public AUHCClinicType findByCtId(int ctid) {
		// TODO Auto-generated method stub
		return dao.findByCtId(ctid);
	}
	
	
}
