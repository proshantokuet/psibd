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
	
	private int unitCost;
	
	private int quantity;
	
	private int totalAmount;
	
	private int discount;
	
	private int netPayable;
	
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
	
	public int getUnitCost() {
		return unitCost;
	}
	
	public void setUnitCost(int unitCost) {
		this.unitCost = unitCost;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public int getDiscount() {
		return discount;
	}
	
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	public int getNetPayable() {
		return netPayable;
	}
	
	public void setNetPayable(int netPayable) {
		this.netPayable = netPayable;
	}
	
	public Date getMoneyReceiptDate() {
		return moneyReceiptDate;
	}
	
	public void setMoneyReceiptDate(Date moneyReceiptDate) {
		this.moneyReceiptDate = moneyReceiptDate;
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
	
	public int getSpid() {
		return spid;
	}
	
	public void setSpid(int spid) {
		this.spid = spid;
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
	
}
