package com.lmig.reciply;

import java.io.UnsupportedEncodingException;
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
			System.out.println("NoSuchAlgorithm exception ");
			System.out.println(ex);
		}
		try {
			md.update(hashedPassword.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String digest = new String(md.digest());
		hashedPassword = digest;

		return (String) hashedPassword;
	}
	
}
