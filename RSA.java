import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RSA implements CryptoSystem {

    private final String name;
    private final BigInteger n;
    private final BigInteger privateKey;
    private final BigInteger publicKey;

    /**
     * Constructor to initialize the RSA object with a name.
     * Generates public and private keys.
     *
     * @param name the name of the RSA object (e.g., "Alice", "Bob")
     */
    public RSA(String name) {
        this.name = name;
        Random random = new Random(System.currentTimeMillis());
        
        // Generate two distinct 32-bit prime numbers p and q
        BigInteger p = BigInteger.probablePrime(32, random);
        BigInteger q;
        do {
            q = BigInteger.probablePrime(32, random);
        } while (q.equals(p));
        
        BigInteger one = BigInteger.ONE;
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        // Generate a public key e such that 1 < e < phi and gcd(e, phi) == 1
        BigInteger e;
        do {
            e = BigInteger.probablePrime(16, random);
        } while (!e.gcd(phi).equals(one) || e.compareTo(phi) >= 0);

        n = p.multiply(q);
        publicKey = e;
        privateKey = publicKey.modInverse(phi);
        System.out.println(name + " says: Hello world, my public key is N=" + n + " and e=" + publicKey);
    }

    /**
     * Gets the name of the RSA object.
     *
     * @return the name of the RSA object
     */
    public String getName() {
        return this.name;
    }

    /**
     * Encrypts a message input by the user.
     *
     * @param input the Scanner object to read the user input
     * @return the encrypted message as a list of BigInteger values
     */
    @Override
    public ArrayList<BigInteger> encrypt(Scanner input) {
        System.out.println(name + " says: ");
        String message = input.nextLine();
        byte[] bytes = message.getBytes();
        ArrayList<BigInteger> encryptedMessage = new ArrayList<>();
        for (byte c : bytes)
            encryptedMessage.add(BigInteger.valueOf(c).modPow(publicKey, n));
        return encryptedMessage;
    }

    /**
     * Decrypts an encrypted message.
     *
     * @param encryptedMessage the encrypted message as a list of BigInteger values
     * @return the decrypted message as a string
     */
    @Override
    public String decrypt(ArrayList<BigInteger> encryptedMessage) {
        StringBuilder result = new StringBuilder();
        for (BigInteger bigInteger : encryptedMessage)
            result.append((char) bigInteger.modPow(privateKey, n).intValue());
        return result.toString();
    }
}
