import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RSA Bob = new RSA("Bob");
        RSA Alice = new RSA("Alice");

        Scanner input = new Scanner(System.in);

        RSA current = Bob;

        while (true) {
            try {
                ArrayList<BigInteger> results = current.encrypt(input);
                for (BigInteger result : results)
                    System.out.print(new String(result.toByteArray()));
                System.out.println(")");
                System.out.println("(" + (current.equals(Bob) ? Alice.getName() : Bob.getName()) + " reads (after decoding): " + current.decrypt(results) + ")");
                if (current.equals(Bob))
                    current = Alice;
                else
                    current = Bob;
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}