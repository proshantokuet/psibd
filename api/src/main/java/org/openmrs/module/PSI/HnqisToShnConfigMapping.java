package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class HnqisToShnConfigMapping extends BaseOpenmrsData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int configId;
	
	private String shnConfigId;
	
	private String hnqisConfigId;
	
	private String configType;
	
	private String datasetId;

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public String getShnConfigId() {
		return shnConfigId;
	}

	public void setShnConfigId(String shnConfigId) {
		this.shnConfigId = shnConfigId;
	}

	public String getHnqisConfigId() {
		return hnqisConfigId;
	}

	public void setHnqisConfigId(String hnqisConfigId) {
		this.hnqisConfigId = hnqisConfigId;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
}
