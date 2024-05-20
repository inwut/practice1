import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("hashes.txt"));
        try {
            testMessageDigest(writer);
            testSecureRandom(writer);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        testEqualsAndHashCode(writer);
        writer.close();
    }

    public static void testSecureRandom(BufferedWriter writer) throws NoSuchAlgorithmException, IOException {
        byte[] message = "Hello World".getBytes(StandardCharsets.UTF_8);

        long hash1 = getSecureRandomHash(message, "SHA1PRNG");
        System.out.println(hash1);
        writer.write(String.valueOf(hash1));
        writer.newLine();

        long hash2 = getSecureRandomHash(message, "DRBG");
        System.out.println(hash2);
        writer.write(String.valueOf(hash2));
        writer.newLine();

        long hash3 = getSecureRandomHash(message, "Windows-PRNG");
        System.out.println(hash3);
        writer.write(String.valueOf(hash3));
        writer.newLine();
    }

    public static long getSecureRandomHash(byte[] message, String algorithm) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance(algorithm);
        secureRandom.nextBytes(message);
        return secureRandom.nextLong();
    }

    public static void testMessageDigest(BufferedWriter writer) throws NoSuchAlgorithmException, IOException {
        byte[] message = "Hello World".getBytes(StandardCharsets.UTF_8);

        String hash1 = getMessageDigestHash(message, "SHA-256");
        System.out.println(hash1);
        writer.write(hash1);
        writer.newLine();

        String hash2 = getMessageDigestHash(message, "SHA-384");
        System.out.println(hash2);
        writer.write(hash2);
        writer.newLine();

        String hash3 = getMessageDigestHash(message, "SHA-512");
        System.out.println(hash3);
        writer.write(hash3);
        writer.newLine();
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

    private static void testEqualsAndHashCode(BufferedWriter writer) throws IOException {
        Book book1 = new Book("1984", "George Orwell", 214);
        Book book2 = new Book("The Little Prince", "Antoine de Saint-Exupery", 156);
        Book book3 = new Book("1984", "George Orwell", 214);

        System.out.println("book1 hashCode: " + book1.hashCode());
        writer.write(Integer.toString(book1.hashCode()));
        writer.newLine();

        System.out.println("book2 hashCode: " + book2.hashCode());
        writer.write(Integer.toString(book2.hashCode()));
        writer.newLine();

        System.out.println("book3 hashCode: " + book3.hashCode());
        writer.write(Integer.toString(book3.hashCode()));
        writer.newLine();

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
        writer.write(Integer.toString(movie1.hashCode()));
        writer.newLine();

        System.out.println("movie2 hashCode: " + movie2.hashCode());
        writer.write(Integer.toString(movie2.hashCode()));
        writer.newLine();

        System.out.println("movie3 hashCode: " + movie3.hashCode());
        writer.write(Integer.toString(movie3.hashCode()));

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

