package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;

public class PSIServiceProvision extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private int spid;
	
	private String item;
	
	private String description;
	
	private float unitCost;
	
	private int quantity;
	
	private float totalAmount;
	
	private float discount;
	
	private float netPayable;
	
	private Date moneyReceiptDate;
	
	private String patientUuid;
	
	private PSIMoneyReceipt psiMoneyReceiptId;
	
	private long timestamp;
	
	private String field1;
	
	private String field2;
	
	private int field3;
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	public int getSpid() {
		return spid;
	}
	
	public void setSpid(int spid) {
		this.spid = spid;
	}
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getUnitCost() {
		return unitCost;
	}
	
	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public float getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public float getDiscount() {
		return discount;
	}
	
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	
	public float getNetPayable() {
		return netPayable;
	}
	
	public void setNetPayable(float netPayable) {
		this.netPayable = netPayable;
	}
	
	public Date getMoneyReceiptDate() {
		return moneyReceiptDate;
	}
	
	public void setMoneyReceiptDate(Date moneyReceiptDate) {
		this.moneyReceiptDate = moneyReceiptDate;
	}
	
	public String getPatientUuid() {
		return patientUuid;
	}
	
	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}
	
	public PSIMoneyReceipt getPsiMoneyReceiptId() {
		return psiMoneyReceiptId;
	}
	
	public void setPsiMoneyReceiptId(PSIMoneyReceipt psiMoneyReceiptId) {
		this.psiMoneyReceiptId = psiMoneyReceiptId;
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
	
}
