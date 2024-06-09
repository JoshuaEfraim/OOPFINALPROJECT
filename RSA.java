import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RSA implements CryptoSystem {

    private final String name;
    private final BigInteger n;
    private final BigInteger privateKey;
    private final BigInteger publicKey;


    public RSA(String name) {
        this.name = name;
        Random random = new Random(System.currentTimeMillis());
        
        // Generate two distinct 32-bit prime numbers p and q
        BigInteger p = BigInteger.probablePrime(32, random);
        BigInteger q;
        // Will continuously generate a 32-bit prime number until q doesnt equal to p
        do {
            q = BigInteger.probablePrime(32, random);
        } while (q.equals(p));
        
        BigInteger one = BigInteger.ONE;
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        // Generate a public key e such that e is coprime with phi and 1 < e < phi
        BigInteger e;
        do {
            e = BigInteger.probablePrime(16, random);
        } while (!e.gcd(phi).equals(one) || e.compareTo(phi) >= 0);

        n = p.multiply(q);
        publicKey = e;
        privateKey = publicKey.modInverse(phi);
        System.out.println(name + " says: Hello world, my public key is N=" + n + " and e=" + publicKey);
    }


    public String getName() {
        return this.name;
    }

    // Method to encrypt
    @Override
    public ArrayList<BigInteger> encrypt(Scanner input) {
        System.out.println(name + " says: ");
        String message = input.nextLine();
        //The input is turned into bytes
        byte[] bytes = message.getBytes();
        ArrayList<BigInteger> encryptedMessage = new ArrayList<>();
        //For every bytes will be encrypted by getting powered by the publickey and modulus n 
        //and then will be added in the arraylist
        for (byte c : bytes)
            encryptedMessage.add(BigInteger.valueOf(c).modPow(publicKey, n));
        return encryptedMessage;
    }

    // Method to decrypt
    @Override
    public String decrypt(ArrayList<BigInteger> encryptedMessage) {
        //Initialise a string builder
        StringBuilder result = new StringBuilder();
        //For every encrypted character, it will be powered by the private key and modulus n
        //then turned into an integer to extract the integer value to find the ASCII code
        for (BigInteger bigInteger : encryptedMessage)
            result.append((char) bigInteger.modPow(privateKey, n).intValue());
        return result.toString();
    }
}
