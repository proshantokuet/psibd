package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class PSIServiceManagement extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private int sid;
	
	private String name;
	
	private String code;
	
	private String category;
	
	private String provider;
	
	private float unitCost;
	
	private long timestamp;
	
	private PSIClinicManagement psiClinicManagement;
	
	private String field1;
	
	private String field2;
	
	private int field3;
	
	private String eligible;
	
	private int ageTo; // calculative field from yearTo,monthTo,daysTo. Starting range
	
	private int ageFrom; // calculative field from yearFrom,monthFrom,daysFrom. Ending range
	
	private int yearTo;
	
	private int monthTo;
	
	private int daysTo;
	
	private int yearFrom;
	
	private int monthFrom;
	
	private int daysFrom;
	
	private String gender;
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	public int getSid() {
		return sid;
	}
	
	public void setSid(int sid) {
		this.sid = sid;
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
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public float getUnitCost() {
		return unitCost;
	}
	
	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getField1() {
		return field1;
	}
	
	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	public String getField2() {
		return field2;
	}
	
	public void setField2(String field2) {
		this.field2 = field2;
	}
	
	public int getField3() {
		return field3;
	}
	
	public void setField3(int field3) {
		this.field3 = field3;
	}
	
	public PSIClinicManagement getPsiClinicManagement() {
		return psiClinicManagement;
	}
	
	public void setPsiClinicManagement(PSIClinicManagement psiClinicManagement) {
		this.psiClinicManagement = psiClinicManagement;
	}
	
	public String getEligible() {
		return eligible;
	}
	
	public void setEligible(String eligible) {
		this.eligible = eligible;
	}
	
	public int getAgeTo() {
		return ageTo;
	}
	
	public void setAgeTo(int ageTo) {
		this.ageTo = ageTo;
	}
	
	public int getAgeFrom() {
		return ageFrom;
	}
	
	public void setAgeFrom(int ageFrom) {
		this.ageFrom = ageFrom;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public int getYearTo() {
		return yearTo;
	}
	
	public void setYearTo(int yearTo) {
		this.yearTo = yearTo;
	}
	
	public int getMonthTo() {
		return monthTo;
	}
	
	public void setMonthTo(int monthTo) {
		this.monthTo = monthTo;
	}
	
	public int getDaysTo() {
		return daysTo;
	}
	
	public void setDaysTo(int daysTo) {
		this.daysTo = daysTo;
	}
	
	public int getYearFrom() {
		return yearFrom;
	}
	
	public void setYearFrom(int yearFrom) {
		this.yearFrom = yearFrom;
	}
	
	public int getMonthFrom() {
		return monthFrom;
	}
	
	public void setMonthFrom(int monthFrom) {
		this.monthFrom = monthFrom;
	}
	
	public int getDaysFrom() {
		return daysFrom;
	}
	
	public void setDaysFrom(int daysFrom) {
		this.daysFrom = daysFrom;
	}
	
}
