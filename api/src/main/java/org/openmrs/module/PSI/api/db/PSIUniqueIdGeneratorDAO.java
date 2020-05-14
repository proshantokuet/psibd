/**
 * 
 */
package org.openmrs.module.PSI.api.db;

import org.openmrs.module.PSI.PSIUniqueIdGenerator;
import org.openmrs.module.PSI.SHNEslipNoGenerate;

/**
 * @author proshanto
 */
public interface PSIUniqueIdGeneratorDAO {
	
	public PSIUniqueIdGenerator saveOrUpdate(PSIUniqueIdGenerator psiUniqueIdGenerator);
	
	public PSIUniqueIdGenerator findByClinicCodeAndDate(String date, String clinicCode);
	
	public SHNEslipNoGenerate saveOrUpdate (SHNEslipNoGenerate shnEslipNoGenerate);
	
	public SHNEslipNoGenerate findEslipByClinicCodeAndDate(String date, String clinicCode);
}
