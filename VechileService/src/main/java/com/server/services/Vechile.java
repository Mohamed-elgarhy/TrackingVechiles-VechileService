package com.server.services;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vechile {

	@Id
	private Long id;

	@Column(name = "vechile_id")
	private String vechileId;

	@Column(name = "reg_num")
	private String registerationNumber;

	@Column(name = "owner_cust_id")
	private Long ownerCustomerId;

	@Column(name = "status")

	private String status;

	public Vechile() {

	}

	public Vechile(Long id, String vechileId, String registerationNumber, Long ownerCustomerId, String status) {
		super();
		this.id = id;
		this.vechileId = vechileId;
		this.registerationNumber = registerationNumber;
		this.ownerCustomerId = ownerCustomerId;
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ownerCustomerId == null) ? 0 : ownerCustomerId.hashCode());
		result = prime * result + ((registerationNumber == null) ? 0 : registerationNumber.hashCode());
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
		Vechile other = (Vechile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ownerCustomerId == null) {
			if (other.ownerCustomerId != null)
				return false;
		} else if (!ownerCustomerId.equals(other.ownerCustomerId))
			return false;
		if (registerationNumber == null) {
			if (other.registerationNumber != null)
				return false;
		} else if (!registerationNumber.equals(other.registerationNumber))
			return false;
		if (vechileId == null) {
			if (other.vechileId != null)
				return false;
		} else if (!vechileId.equals(other.vechileId))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVechileId() {
		return vechileId;
	}

	public void setVechileId(String vechileModel) {
		this.vechileId = vechileModel;
	}

	public String getRegisterationNumber() {
		return registerationNumber;
	}

	public void setRegisterationNumber(String registerationNumber) {
		this.registerationNumber = registerationNumber;
	}

	public Long getOwnerCustomerId() {
		return ownerCustomerId;
	}

	public void setOwnerCustomerId(Long ownerCustomerId) {
		this.ownerCustomerId = ownerCustomerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Vechile [id=" + id + ", vechileId=" + vechileId + ", registerationNumber=" + registerationNumber
				+ ", ownerCustomerId=" + ownerCustomerId + ", status=" + status + "]";
	}

}