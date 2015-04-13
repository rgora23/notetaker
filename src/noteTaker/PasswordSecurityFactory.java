package noteTaker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * This class handles the methods for the creation of a salt and hash during the
 * registration and authentication process. The method used to generate a hash
 * implements the SHA1 algorithm created by the NSA, and the method used to
 * generate the salt uses a SecureRandom object found in the java.security
 * package. Both methods can produce a NoSuchAlgorithmException.
 * 
 * Source:
 * http://en.tekstenuitleg.net/articles/software/database-design-tutorial
 * /intro.html
 * 
 */
public class PasswordSecurityFactory {

	// These are the functions used to create password salts and hashes
	public static String getSHA1Hash(String passwordToHash, String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(salt.getBytes());
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}

}




