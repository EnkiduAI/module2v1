package com.epam.esm.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@EntityListeners(Auditable.class)
@Entity
@Table(name = "tag")
public class Tag implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tag")
	private int id;
	@Column(name = "tag_name")
	private String name;
	@ManyToMany(mappedBy = "tags")
	private Set<GiftCertificate> giftCertificates = new HashSet<>();

	public Tag() {
		
	}
	
	public Tag(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void addCertificate(GiftCertificate certificate) {
		this.giftCertificates.add(certificate);
		certificate.getTags().add(this);
	}
	
	public void removeCertificate(GiftCertificate certificate) {
		this.giftCertificates.remove(certificate);
		certificate.getTags().remove(this);
	}
	
	public Set<GiftCertificate> getGiftCertificates() {
		return giftCertificates;
	}

	public void setGiftCertificates(Set<GiftCertificate> giftCertificates) {
		this.giftCertificates = giftCertificates;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Tag other = (Tag) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tag [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
