import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            testMessageDigest();
            testSecureRandom();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        testEqualsAndHashCode();
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
        for (byte datum : data) {
            String hex = Integer.toHexString(0xff & datum);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static void testEqualsAndHashCode() {
        Book book1 = new Book("1984", "George Orwell", 214);
        Book book2 = new Book("The Little Prince", "Antoine de Saint-Exupery", 156);
        Book book3 = new Book("1984", "George Orwell", 214);
        System.out.println("book1 hashCode: " + book1.hashCode());
        System.out.println("book2 hashCode: " + book2.hashCode());
        System.out.println("book3 hashCode: " + book3.hashCode());
        System.out.println("book1 equals book2: " + book1.equals(book2));
        System.out.println("book1 equals book3: " + book1.equals(book3));
        Map<Book, Integer> map = new HashMap<>();
        map.put(book1, 5);
        map.put(book2, 7);
        map.put(book3, 8);
        for(Map.Entry<Book, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        Movie movie1 = new Movie("Я, побєда і Берлін", "Comedy", Duration.ofMinutes(120));
        Movie movie2 = new Movie("Avatar", "Science fiction", Duration.ofMinutes(200));
        Movie movie3 = new Movie("Я, побєда і Берлін", "Comedy", Duration.ofMinutes(120));
        System.out.println("movie1 hashCode: " + movie1.hashCode());
        System.out.println("movie2 hashCode: " + movie2.hashCode());
        System.out.println("movie3 hashCode: " + movie3.hashCode());
        System.out.println("movie1 equals movie2: " + movie1.equals(movie2));
        System.out.println("movie1 equals movie3: " + movie1.equals(movie3));
        Map<Movie, Integer> map1 = new HashMap<>();
        map1.put(movie1, 12);
        map1.put(movie2, 15);
        map1.put(movie3, 9);
        for(Map.Entry<Movie, Integer> entry : map1.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

}

