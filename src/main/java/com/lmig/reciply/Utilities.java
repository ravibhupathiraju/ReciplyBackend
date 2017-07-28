package com.lmig.reciply;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities {

	public String hashPassword(String password) {
		final String SALT = "GOFORCODE";
		String hashedPassword = password + SALT;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException ex) {
			System.out.println(ex);
		}
		md.update(password.getBytes());
		String digest = new String(md.digest());
		hashedPassword = digest;

		return (String) hashedPassword;
	}
	
}