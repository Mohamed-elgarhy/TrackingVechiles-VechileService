package com.server.services;

public class VechileBean {

	private Long VechileId;
	private String customerName;
	public Long getVechileId() {
		return VechileId;
	}
	public void setVechileId(Long vechileId) {
		VechileId = vechileId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public VechileBean(Long vechileId, String customerName) {
		super();
		VechileId = vechileId;
		this.customerName = customerName;
	}
	
	public VechileBean() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "VechileBean [VechileId=" + VechileId + ", customerName=" + customerName + "]";
	}
	
	
	
}
