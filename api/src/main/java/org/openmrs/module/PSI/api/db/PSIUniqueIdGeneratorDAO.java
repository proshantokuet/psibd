/**
 * 
 */
package org.openmrs.module.PSI.api.db;

import org.openmrs.module.PSI.PSIUniqueIdGenerator;

/**
 * @author proshanto
 */
public interface PSIUniqueIdGeneratorDAO {
	
	public PSIUniqueIdGenerator saveOrUpdate(PSIUniqueIdGenerator psiUniqueIdGenerator);
	
	public PSIUniqueIdGenerator findByClinicCodeAndDate(String date, String clinicCode);
}
