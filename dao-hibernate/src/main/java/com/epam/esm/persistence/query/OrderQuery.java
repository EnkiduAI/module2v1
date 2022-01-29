package com.epam.esm.persistence.query;

public class OrderQuery {
	public static final String FIND_ALL_USER_ORDERS = """
			from Order where idUser = :id
			""";
	public static final String FIND_USER_ORDER_BY_ID = """
			from Order where idUser = :idUser and idOrder = :idOrder
			""";
	public static final String FIND_ORDER_BY_ID = """
			from Order where orderId = :id
			""";
}
