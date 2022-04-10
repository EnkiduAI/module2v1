package com.epam.esm.bcrypt;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Bcrypt {
	
	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(10));
	}

	public static boolean checkPassword(String password, String hashedPassword) {
		return Bcrypt.checkPassword(password, hashedPassword);
	}
}
