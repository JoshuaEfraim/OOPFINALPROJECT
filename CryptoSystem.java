import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public interface CryptoSystem {
    ArrayList<BigInteger> encrypt(Scanner input);
    String decrypt(ArrayList<BigInteger> encryptedMessage);
}
