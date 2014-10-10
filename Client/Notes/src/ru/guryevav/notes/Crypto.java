package ru.guryevav.notes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	public static String getHash(String password, String salt) {
		String out = "";
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec secret = new SecretKeySpec(salt.getBytes(),"HmacSHA1");
		    mac.init(secret);
		    byte[] digest = mac.doFinal(password.getBytes());
		    for (byte b : digest) {
		    	out += String.format("%02x", b);
		    }
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
}
