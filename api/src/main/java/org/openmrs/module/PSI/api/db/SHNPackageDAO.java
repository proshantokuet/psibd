package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;

public interface SHNPackageDAO {
	
	public SHNPackage saveOrUpdate(SHNPackage shnPackage);
	
	public SHNPackage findById(int packageId);
	
	public List<SHNPackage> getAllPackageByClinicId(int clinicId);
	
	public List<SHNPackage> getAllPackageByClinicIdWithVoided(int clinicId);

	public List<SHNPackage> getAllPackageByClinicCode(String clinicCode);
	
	public SHNPackageDetails findPackageDetailsById (int packageDetailsId);
	
	public SHNPackage findbyPackageCode(String packageCode, int clinicId, int packageId);
	
	public SHNPackage findPackageByUuid(String uuid);
	
	public SHNPackageDetails findPackageDetailsByUuid(String uuid);
	
	public void deletePackageHavingNullPackageId();
	
	public void deletePackageDetailsById(int packageDetailsId);

}
