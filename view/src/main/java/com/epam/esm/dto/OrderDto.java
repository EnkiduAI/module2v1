package com.epam.esm.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

public class OrderDto extends RepresentationModel<OrderDto>{
	private int orderId;
	private int userId;
	private int certificateId;
	private int price;
	private LocalDateTime purchaseDate;
	
	public OrderDto() {
		
	}

	public OrderDto(int orderId, int userId, int certificateId, int price, LocalDateTime purchaseDate) {
		this.orderId = orderId;
		this.userId = userId;
		this.certificateId = certificateId;
		this.price = price;
		this.purchaseDate = purchaseDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + certificateId;
		result = prime * result + orderId;
		result = prime * result + price;
		result = prime * result + ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDto other = (OrderDto) obj;
		if (certificateId != other.certificateId)
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
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDto [orderId=");
		builder.append(orderId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", certificateId=");
		builder.append(certificateId);
		builder.append(", price=");
		builder.append(price);
		builder.append(", purchaseDate=");
		builder.append(purchaseDate);
		builder.append("]");
		return builder.toString();
	}
}
