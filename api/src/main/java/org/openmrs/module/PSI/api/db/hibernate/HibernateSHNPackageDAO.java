package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;
import org.openmrs.module.PSI.api.db.SHNPackageDAO;

public class HibernateSHNPackageDAO implements SHNPackageDAO {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public SHNPackage saveOrUpdate(SHNPackage shnPackage) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(shnPackage);
		return shnPackage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNPackage findById(int packageId) {
		List<SHNPackage> lists = sessionFactory.getCurrentSession().createQuery("from SHNPackage where packageId = :id")
		        .setInteger("id", packageId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SHNPackage> getAllPackageByClinicCode(String clinicCode) {
		// TODO Auto-generated method stub
		List<SHNPackage> lists = sessionFactory.getCurrentSession().createQuery("from SHNPackage where clinicCode = :id")
		        .setString("id", clinicCode).list();
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNPackageDetails findPackageDetailsById(int packageDetailsId) {
		List<SHNPackageDetails> lists = sessionFactory.getCurrentSession().createQuery("from SHNPackageDetails where packageDetailsId = :id")
		        .setInteger("id", packageDetailsId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNPackage findbyPackageCode(String packageCode,String clinicCode,int packageId) {
		List<SHNPackage> lists = sessionFactory.getCurrentSession().createQuery("from SHNPackage where packageId != :id and  packageCode = :packagecode and clinicCode = :cliniccode")
		        .setString("packagecode", packageCode)
		        .setString("cliniccode", clinicCode)
		        .setInteger("id", packageId)
		        .list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SHNPackage> getAllPackageByClinicId(int clinicId) {
		// TODO Auto-generated method stub
		List<SHNPackage> lists = sessionFactory.getCurrentSession().createQuery("from SHNPackage where clinicId = :id and voided = 0 order by packageName asc")
		        .setInteger("id", clinicId).list();
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SHNPackage> getAllPackageByClinicIdWithVoided(int clinicId) {
		// TODO Auto-generated method stub
		List<SHNPackage> lists = sessionFactory.getCurrentSession().createQuery("from SHNPackage where clinicId = :id order by packageName asc")
		        .setInteger("id", clinicId).list();
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deletePackageHavingNullPackageId() {
		List<SHNPackageDetails> lists = sessionFactory.getCurrentSession()
		        .createQuery("from SHNPackageDetails where shn_package_id is null").list();
		log.error("Package Query Result" + lists.size());
		if (lists.size() != 0) {
			for (SHNPackageDetails shnPackageDetails : lists) {
				int packageDetailsId = shnPackageDetails.getPackageDetailsId();
				deletePackageDetailsById(packageDetailsId);
			}
		}
		
	}

	@Override
	public void deletePackageDetailsById(int packageDetailsId) {
		// TODO Auto-generated method stub
		SHNPackageDetails shnPackageDetails = findPackageDetailsById(packageDetailsId);
		if (shnPackageDetails != null) {
			sessionFactory.getCurrentSession().delete(shnPackageDetails);
		} else {
			log.error("Package is null with id" + packageDetailsId);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNPackage findPackageByUuid(String uuid) {
		List<SHNPackage> lists = sessionFactory.getCurrentSession().createQuery("from SHNPackage where uuid = :packageuuid")
		        .setString("packageuuid", uuid).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNPackageDetails findPackageDetailsByUuid(String uuid) {
		List<SHNPackageDetails> lists = sessionFactory.getCurrentSession().createQuery("from SHNPackageDetails where uuid = :packagedetailsuuid")
		        .setString("packagedetailsuuid", uuid).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	
	
}