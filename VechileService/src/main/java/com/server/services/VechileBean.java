package com.server.services;

public class VechileBean {

	private String vechileId;
	private String customerName;
	private String status;
	private String registerationNumber;
	private String customerAdress;

	public String getVechileId() {
		return vechileId;
	}

	public void setVechileId(String vechileId) {
		this.vechileId = vechileId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public VechileBean(String vechileId, String customerName) {
		super();
		this.vechileId = vechileId;
		this.customerName = customerName;
	}

	public VechileBean(String vechileId, String customerName, String status) {
		super();
		this.vechileId = vechileId;
		this.customerName = customerName;
		this.status = status;
	}

	public VechileBean() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegisterationNumber() {
		return registerationNumber;
	}

	public void setRegisterationNumber(String registerationNumber) {
		this.registerationNumber = registerationNumber;
	}

	public String getCustomerAdress() {
		return customerAdress;
	}

	public void setCustomerAdress(String customerAdress) {
		this.customerAdress = customerAdress;
	}

	public VechileBean(String vechileId, String customerName, String status, String registerationNumber,
			String customerAdress) {
		super();
		this.vechileId = vechileId;
		this.customerName = customerName;
		this.status = status;
		this.registerationNumber = registerationNumber;
		this.customerAdress = customerAdress;
	}

	@Override
	public String toString() {
		return "VechileBean [vechileId=" + vechileId + ", customerName=" + customerName + ", status=" + status
				+ ", registerationNumber=" + registerationNumber + ", customerAdress=" + customerAdress + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerAdress == null) ? 0 : customerAdress.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((registerationNumber == null) ? 0 : registerationNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((vechileId == null) ? 0 : vechileId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VechileBean other = (VechileBean) obj;
		if (customerAdress == null) {
			if (other.customerAdress != null)
				return false;
		} else if (!customerAdress.equals(other.customerAdress))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (registerationNumber == null) {
			if (other.registerationNumber != null)
				return false;
		} else if (!registerationNumber.equals(other.registerationNumber))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (vechileId == null) {
			if (other.vechileId != null)
				return false;
		} else if (!vechileId.equals(other.vechileId))
			return false;
		return true;
	}
	
	
	

}
