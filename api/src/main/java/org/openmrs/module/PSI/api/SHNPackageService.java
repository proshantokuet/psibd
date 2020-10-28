package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SHNPackageService extends OpenmrsService {

	public SHNPackage saveOrUpdate(SHNPackage shnPackage);
	
	public SHNPackage findById(int packageId);
	
	public List<SHNPackage> getAllPackageByClinicId(int clinicId);
	
	public List<SHNPackage> getAllPackageByClinicIdWithVoided(int clinicId);

	public List<SHNPackage> getAllPackageByClinicCode(String clinicCode);
	
	public SHNPackageDetails findPackageDetailsById (int packageDetailsId);
	
	public SHNPackage findbyPackageCode(String packageCode,String clinicCode,int packageId);
	
	public SHNPackage findPackageByUuid(String uuid);
	
	public SHNPackageDetails findPackageDetailsByUuid(String uuid);

	public void deletePackageHavingNullPackageId();
	
}
