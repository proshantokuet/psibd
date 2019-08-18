/**
 * 
 */
package org.openmrs.module.PSI.dto;

import java.io.Serializable;

/**
 * @author proshanto
 */
public class PSILocation implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = -5544977791288977525L;
	
	private int id;
	
	private String name;
	
	private String code;
	
	private String uuid;
	
	private String address2;
	
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
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getAddress2() {
		return address2;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	@Override
	public String toString() {
		return "PSILocation [id=" + id + ", name=" + name + ", code=" + code + ", uuid=" + uuid + "]";
	}
	
}
