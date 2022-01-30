package com.epam.esm.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@EntityListeners(Auditable.class)
@Entity
@Table(name = "orders")
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private int orderId;
	@Column(name = "id_user")
	private int idUser;
	@Column(name = "id_certificate")
	private int idCertificate;
	private int price;
	@Column(name = "purchase_date")
	private LocalDateTime purchaseDate;

	public Order() {

	}

	public Order(int orderId, int idUser, int idCertificate, int price, LocalDateTime purchaseDate) {
		this.orderId = orderId;
		this.idUser = idUser;
		this.idCertificate = idCertificate;
		this.price = price;
		this.purchaseDate = purchaseDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdCertificate() {
		return idCertificate;
	}

	public void setIdCertificate(int idCertificate) {
		this.idCertificate = idCertificate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCertificate;
		result = prime * result + idUser;
		result = prime * result + orderId;
		result = prime * result + price;
		result = prime * result + ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
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
		Order other = (Order) obj;
		if (idCertificate != other.idCertificate)
			return false;
		if (idUser != other.idUser)
			return false;
		if (orderId != other.orderId)
			return false;
		if (price != other.price)
			return false;
		if (purchaseDate == null) {
			if (other.purchaseDate != null)
				return false;
		} else if (!purchaseDate.equals(other.purchaseDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [orderId=");
		builder.append(orderId);
		builder.append(", idUser=");
		builder.append(idUser);
		builder.append(", idCertificate=");
		builder.append(idCertificate);
		builder.append(", price=");
		builder.append(price);
		builder.append(", purchaseDate=");
		builder.append(purchaseDate);
		builder.append("]");
		return builder.toString();
	}
}
