package br.ueg.openodonto.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class ShaUtil {

    public static String hash(String str) {
	StringBuilder stb = new StringBuilder();
	try {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] data = str.getBytes("UTF-8");
	    byte[] md5sum = md.digest(data);
	    BigInteger hash = new BigInteger(1, md5sum);
	    stb.append(hash.toString(16));
	} catch (Exception e) {
	}
	while (stb.length() != 32) {
	    stb.insert(0,"0");
	}
	return stb.toString();
    }

}
