package com.epam.esm.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
@Entity
@Table(name = "tag")
@SecondaryTable(name = "gift_certificate")
public class CertificateWithTag {
	
	@Column(name = "id", table = "tag", insertable = false, updatable = false)
	private int tagId;
	@Column(name = "tag_name", table = "tag", insertable = false, updatable = false)
	private String tagName;
	@Id
	@Column(name = "id", table = "gift_certificate", insertable = false, updatable = false)
	private int certificateId;
	@Column(name = "name", table = "gift_certificate", insertable = false, updatable = false)
	private String certificateName;
	@Column(name = "description", table = "gift_certificate", insertable = false, updatable = false)
	private String description;
	@Column(name = "price", table = "gift_certificate", insertable = false, updatable = false)
	private int price;
	@Column(name = "duration", table = "gift_certificate", insertable = false, updatable = false)
	private String duration;
	@Column(name = "create_date", table = "gift_certificate", insertable = false, updatable = false)
	private LocalDateTime createDate;
	@Column(name = "last_update_date", table = "gift_certificate", insertable = false, updatable = false)
	private LocalDateTime lastUpdateDate;
	
	public CertificateWithTag() {
		
	}

	public CertificateWithTag(int tagId, String tagName, int certificateId, 
			String certificateName, String description,
			int price, String duration, LocalDateTime createDate, 
			LocalDateTime lastUpdateDate) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.certificateId = certificateId;
		this.certificateName = certificateName;
		this.description = description;
		this.price = price;
		this.duration = duration;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + certificateId;
		result = prime * result + ((certificateName == null) ? 0 : certificateName.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + price;
		result = prime * result + tagId;
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
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
		CertificateWithTag other = (CertificateWithTag) obj;
		if (certificateId != other.certificateId)
			return false;
		if (certificateName == null) {
			if (other.certificateName != null)
				return false;
		} else if (!certificateName.equals(other.certificateName))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (lastUpdateDate == null) {
			if (other.lastUpdateDate != null)
				return false;
		} else if (!lastUpdateDate.equals(other.lastUpdateDate))
			return false;
		if (price != other.price)
			return false;
		if (tagId != other.tagId)
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CertificateWithTag [tagId=");
		builder.append(tagId);
		builder.append(", tagName=");
		builder.append(tagName);
		builder.append(", certificateId=");
		builder.append(certificateId);
		builder.append(", certificateName=");
		builder.append(certificateName);
		builder.append(", description=");
		builder.append(description);
		builder.append(", price=");
		builder.append(price);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", lastUpdateDate=");
		builder.append(lastUpdateDate);
		builder.append("]");
		return builder.toString();
	}
}
