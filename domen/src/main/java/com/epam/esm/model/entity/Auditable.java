package com.epam.esm.model.entity;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class Auditable {
	private String operation;
	private long timestamp;

	public Auditable(String operation, long timestamp) {
		this.operation = operation;
		this.timestamp = timestamp;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@PrePersist
	public void onPrePersist() {
		audit("INSERT");
	}

	@PreUpdate
	public void onPreUpdate() {
		audit("UPDATE");
	}

	@PreRemove
	public void onPreRemove() {
		audit("REMOVE");
	}

	private void audit(String operation) {
		setOperation(operation);
		setTimestamp(new Date().getTime());
	}
}
