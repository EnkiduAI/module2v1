package com.epam.esm.model.entity;

import java.time.LocalDateTime;

public interface Auditable {
	LocalDateTime getCreateDate();

	void setCreateDate(LocalDateTime createDate);

	LocalDateTime getUpdateDate();

	void setUpdateDate(LocalDateTime updateDate);
}
