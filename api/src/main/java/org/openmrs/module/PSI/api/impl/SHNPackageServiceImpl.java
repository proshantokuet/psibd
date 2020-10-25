package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;
import org.openmrs.module.PSI.api.SHNPackageService;
import org.openmrs.module.PSI.api.db.SHNPackageDAO;

public class SHNPackageServiceImpl extends BaseOpenmrsService implements SHNPackageService {
	
	private SHNPackageDAO dao;

	public SHNPackageDAO getDao() {
		return dao;
	}

	public void setDao(SHNPackageDAO dao) {
		this.dao = dao;
	}

	@Override
	public SHNPackage saveOrUpdate(SHNPackage shnPackage) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(shnPackage);
	}

	@Override
	public SHNPackage findById(int packageId) {
		// TODO Auto-generated method stub
		return dao.findById(packageId);
	}

	@Override
	public List<SHNPackage> getAllPackageByClinic(String clinicCode) {
		// TODO Auto-generated method stub
		return dao.getAllPackageByClinic(clinicCode);
	}

	@Override
	public SHNPackageDetails finPackageDetailsById(int packageDetailsId) {
		// TODO Auto-generated method stub
		return dao.finPackageDetailsById(packageDetailsId);
	}

	@Override
	public SHNPackage findbyPackageCode(String packageCode,String clinicCode,int packageId) {
		// TODO Auto-generated method stub
		return dao.findbyPackageCode(packageCode,clinicCode,packageId);
	}

}
