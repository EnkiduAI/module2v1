package com.epam.esm.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

public class OrderDto extends RepresentationModel<OrderDto>{
	private int orderId;
	private int userId;
	private String userName;
	private String userSurname;
	private String certificateName;
	private String certificateDescription;
	private int price;
	private LocalDateTime purchaseDate;
	
	public OrderDto() {
		
	}

	public OrderDto(int orderId, int userId, String userName, String userSurname, String certificateName,
			String certificateDescription, int price, LocalDateTime purchaseDate) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.userName = userName;
		this.userSurname = userSurname;
		this.certificateName = certificateName;
		this.certificateDescription = certificateDescription;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateDescription() {
		return certificateDescription;
	}

	public void setCertificateDescription(String certificateDescription) {
		this.certificateDescription = certificateDescription;
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
		int result = 1;
		result = prime * result + ((certificateDescription == null) ? 0 : certificateDescription.hashCode());
		result = prime * result + ((certificateName == null) ? 0 : certificateName.hashCode());
		result = prime * result + orderId;
		result = prime * result + price;
		result = prime * result + ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
		result = prime * result + userId;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userSurname == null) ? 0 : userSurname.hashCode());
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
		OrderDto other = (OrderDto) obj;
		if (certificateDescription == null) {
			if (other.certificateDescription != null)
				return false;
		} else if (!certificateDescription.equals(other.certificateDescription))
			return false;
		if (certificateName == null) {
			if (other.certificateName != null)
				return false;
		} else if (!certificateName.equals(other.certificateName))
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
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userSurname == null) {
			if (other.userSurname != null)
				return false;
		} else if (!userSurname.equals(other.userSurname))
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
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", userSurname=");
		builder.append(userSurname);
		builder.append(", certificateName=");
		builder.append(certificateName);
		builder.append(", certificateDescription=");
		builder.append(certificateDescription);
		builder.append(", price=");
		builder.append(price);
		builder.append(", purchaseDate=");
		builder.append(purchaseDate);
		builder.append("]");
		return builder.toString();
	}
}
