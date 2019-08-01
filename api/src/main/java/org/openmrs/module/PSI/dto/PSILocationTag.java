/**
 * 
 */
package org.openmrs.module.PSI.dto;

import java.io.Serializable;

/**
 * @author proshanto
 */
public class PSILocationTag implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = -5544977791288977525L;
	
	private int id;
	
	private String name;
	
	private String uuid;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
