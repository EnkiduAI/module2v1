package com.epam.esm.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@EntityListeners(AuditListner.class)
@Entity
@Table(name = "gift_certificate")
public class GiftCertificate implements Serializable, Auditable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="cert_id")
	private int certificateId;
	private String name;
	private String description;
	private int price;
	private String duration;
	@Column(name = "create_date")
	private LocalDateTime createDate;
	@Column(name = "last_update_date")
	private LocalDateTime lastUpdateDate;
	@ManyToMany
	@JoinTable(
			name = "gift_certificate_has_tag",
			joinColumns = @JoinColumn(name = "gift_certificate_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();

	public GiftCertificate() {

	}

	public GiftCertificate(int id, String name, String description, int price, String duration,
			LocalDateTime createDate, LocalDateTime lastUpdateDate) {
		this.certificateId = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.duration = duration;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
		tag.getGiftCertificates().add(this);
	}
	
	public void removeTag(Tag tag) {
		this.tags.remove(tag);
		tag.getGiftCertificates().remove(this);
	}
	
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public int getId() {
		return certificateId;
	}

	public void setId(int id) {
		this.certificateId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	@Override
	public LocalDateTime getUpdateDate() {
		return lastUpdateDate;
	}

	@Override
	public void setUpdateDate(LocalDateTime updateDate) {
		this.lastUpdateDate = updateDate;		
	}

	@Override
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + certificateId;
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + price;
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
		GiftCertificate other = (GiftCertificate) obj;
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
		if (certificateId != other.certificateId)
			return false;
		if (lastUpdateDate == null) {
			if (other.lastUpdateDate != null)
				return false;
		} else if (!lastUpdateDate.equals(other.lastUpdateDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price != other.price)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GiftCertificate [id=");
		builder.append(certificateId);
		builder.append(", name=");
		builder.append(name);
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
