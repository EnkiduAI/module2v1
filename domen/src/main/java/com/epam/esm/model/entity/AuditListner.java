package com.epam.esm.model.entity;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditListner {
	
	@PrePersist
	public void onPrePersist(Auditable audit) {
		audit.setCreateDate(LocalDateTime.now());
		audit.setUpdateDate(LocalDateTime.now());
	}

	@PreUpdate
	public void onPreUpdate(Auditable audit) {
		audit.setUpdateDate(LocalDateTime.now());
	}
}
