/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.PSI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.Module;
import org.openmrs.module.ModuleActivator;
import org.openmrs.module.ModuleFactory;

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
public class PSIActivator implements ModuleActivator {
	
	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * @see ModuleActivator#willRefreshContext()
	 */
	public void willRefreshContext() {
		log.info("Refreshing PSI Module");
	}
	
	/**
	 * @see ModuleActivator#contextRefreshed()
	 */
	public void contextRefreshed() {
		log.info("PSI Module refreshed");
	}
	
	/**
	 * @see ModuleActivator#willStart()
	 */
	public void willStart() {
		log.info("Starting PSI Module");
	}
	
	/**
	 * @see ModuleActivator#started()
	 */
	public void started() {
		
		try {
			Module mod = ModuleFactory.getModuleById("PSI");
			ModuleFactory.startModule(mod);
			log.info("PSI Module started");
		}
		catch (NoSuchMethodError e) {
			Module mod = ModuleFactory.getStartedModuleById("PSI");
			ModuleFactory.stopModule(mod);
			e.printStackTrace();
		}
		catch (Exception e) {
			Module mod = ModuleFactory.getModuleById("PSI");
			ModuleFactory.stopModule(mod);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @see ModuleActivator#willStop()
	 */
	public void willStop() {
		log.info("Stopping PSI Module");
	}
	
	/**
	 * @see ModuleActivator#stopped()
	 */
	public void stopped() {
		log.info("PSI Module stopped");
	}
	
}
