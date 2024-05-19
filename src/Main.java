import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            testMessageDigest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    public static void testMessageDigest() throws NoSuchAlgorithmException {
        byte[] message = "Hello World".getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(message);
        System.out.println(convertToHex(messageDigest.digest()));
        messageDigest = MessageDigest.getInstance("SHA-384");
        messageDigest.update(message);
        System.out.println(convertToHex(messageDigest.digest()));
        messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(message);
        System.out.println(convertToHex(messageDigest.digest()));
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

