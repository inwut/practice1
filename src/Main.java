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
        byte[] message = "Hello World".getBytes(StandardCharsets.UTF_8);
        try {
            System.out.println("..........testing Message Digest.........");
            getMessageDigestHash(message, "SHA-256", writer);
            getMessageDigestHash(message, "SHA-384", writer);
            getMessageDigestHash(message, "SHA-512", writer);
            System.out.println();
            System.out.println(".........testing Secure Random............");
            getSecureRandomHash(message, "SHA1PRNG", writer);
            getSecureRandomHash(message, "DRBG", writer);
            getSecureRandomHash(message, "Windows-PRNG", writer);
            System.out.println();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
        System.out.println(".........testing correct equals and hashCode.........");
        testCorrectEqualsAndHashCode(writer);
        System.out.println();
        System.out.println(".........testing incorrect equals and hashCode.........");
        testIncorrectEqualsAndHashCode(writer);
        writer.close();
    }


    public static void getSecureRandomHash(byte[] message, String algorithm, BufferedWriter writer) throws NoSuchAlgorithmException, IOException {
        SecureRandom secureRandom = SecureRandom.getInstance(algorithm);
        secureRandom.setSeed(1308);
        secureRandom.nextBytes(message);
        long hash = secureRandom.nextLong();
        System.out.println(hash);
        writer.write(String.valueOf(hash));
        writer.newLine();
    }


    private static void getMessageDigestHash(byte[] message, String algorithm, BufferedWriter writer) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(message);
        String hash = convertToHex(messageDigest.digest());
        System.out.println(hash);
        writer.write(hash);
        writer.newLine();
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

    private static void testCorrectEqualsAndHashCode(BufferedWriter writer) throws IOException {
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
    }

    private static void testIncorrectEqualsAndHashCode(BufferedWriter writer) throws IOException {
        Movie movie1 = new Movie("Reincarnation", "Horror", Duration.ofMinutes(120));
        Movie movie2 = new Movie("Avatar", "Science fiction", Duration.ofMinutes(200));
        Movie movie3 = new Movie("Reincarnation", "Horror", Duration.ofMinutes(120));

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

