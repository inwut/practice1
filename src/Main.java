import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        try {
            testMessageDigest();
            testSecureRandom();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    public static void testSecureRandom() throws NoSuchAlgorithmException {
        byte[] message = "Hello World".getBytes(StandardCharsets.UTF_8);
        System.out.println(getSecureRandomHash(message, "SHA1PRNG"));
        System.out.println(getSecureRandomHash(message, "DRBG"));
        System.out.println(getSecureRandomHash(message, "Windows-PRNG"));
    }

    public static long getSecureRandomHash(byte[] message, String algorithm) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance(algorithm);
        secureRandom.nextBytes(message);
        return secureRandom.nextLong();
    }

    public static void testMessageDigest() throws NoSuchAlgorithmException {
        byte[] message = "Hello World".getBytes(StandardCharsets.UTF_8);
        System.out.println(getMessageDigestHash(message, "SHA-256"));
        System.out.println(getMessageDigestHash(message, "SHA-384"));
        System.out.println(getMessageDigestHash(message, "SHA-512"));
    }

    private static String getMessageDigestHash(byte[] message, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(message);
        return convertToHex(messageDigest.digest());
    }

    private static String convertToHex(byte[] data) {
        StringBuilder hexString = new StringBuilder(2 * data.length);
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}

