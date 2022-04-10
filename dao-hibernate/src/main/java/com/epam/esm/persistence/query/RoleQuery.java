package com.epam.esm.persistence.query;

public class RoleQuery {

	public static final String FIND_ROLE_BY_NAME = """
			from Role where roleName = :roleName
			""";
}
