package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;
import org.openmrs.module.PSI.api.SHNPackageService;
import org.openmrs.module.PSI.api.db.SHNPackageDAO;
import org.openmrs.module.PSI.dto.SHNPackageDTO;
import org.openmrs.module.PSI.dto.SHNPackageReportDTO;

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
	public List<SHNPackage> getAllPackageByClinicCode(String clinicCode) {
		// TODO Auto-generated method stub
		return dao.getAllPackageByClinicCode(clinicCode);
	}

	@Override
	public SHNPackageDetails findPackageDetailsById(int packageDetailsId) {
		// TODO Auto-generated method stub
		return dao.findPackageDetailsById(packageDetailsId);
	}

	@Override
	public SHNPackage findbyPackageCode(String packageCode, int clinicId, int packageId) {
		// TODO Auto-generated method stub
		return dao.findbyPackageCode(packageCode,clinicId,packageId);
	}

	@Override
	public List<SHNPackage> getAllPackageByClinicId(int clinicId) {
		// TODO Auto-generated method stub
		return dao.getAllPackageByClinicId(clinicId);
	}

	@Override
	public void deletePackageHavingNullPackageId() {
		// TODO Auto-generated method stub
		dao.deletePackageHavingNullPackageId();
	}

	@Override
	public SHNPackage findPackageByUuid(String uuid) {
		// TODO Auto-generated method stub
		return dao.findPackageByUuid(uuid);
	}

	@Override
	public SHNPackageDetails findPackageDetailsByUuid(String uuid) {
		// TODO Auto-generated method stub
		return dao.findPackageDetailsByUuid(uuid);
	}

	@Override
	public List<SHNPackage> getAllPackageByClinicIdWithVoided(int clinicId) {
		// TODO Auto-generated method stub
		return dao.getAllPackageByClinicIdWithVoided(clinicId);
	}

	@Override
	public List<SHNPackageReportDTO> getPackageListForViewByCLinic(int clinicId) {
		// TODO Auto-generated method stub
		return dao.getPackageListForViewByCLinic(clinicId);
	}

	@Override
	public List<SHNPackageReportDTO> getPackageByPackageIdForEdit(int packageId) {
		// TODO Auto-generated method stub
		return dao.getPackageByPackageIdForEdit(packageId);
	}

	@Override
	public List<SHNPackageReportDTO> getstockStatusFromPackage(int clinicId,
			int quantity, int packageId) {
		// TODO Auto-generated method stub
		return dao.getstockStatusFromPackage(clinicId, quantity, packageId);
	}

}
