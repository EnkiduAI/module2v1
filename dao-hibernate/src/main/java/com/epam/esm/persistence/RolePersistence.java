package com.epam.esm.persistence;

import com.epam.esm.model.entity.Role;

public interface RolePersistence {
	Role findByName(String name);
}
