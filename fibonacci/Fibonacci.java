import java.math.BigInteger;
import java.util.Scanner;

public class Fibonacci {

    private static BigInteger[] fibonacciCache;
    private static final BigInteger zero = new BigInteger(String.valueOf(0));
    private static final BigInteger one = new BigInteger(String.valueOf(1));

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Find out what fibonacci number your number is!");
        System.out.print("Enter a Number: ");
        int n = scan.nextInt();
        fibonacciCache = new BigInteger[n + 1];
        System.out.println(fibonacci(n));
    }

    private static BigInteger fibonacci(int n) {
        if (n <= 0) {
            return new BigInteger(String.valueOf(n));
        }
        if (zero.equals(fibonacciCache[n])) {
            return fibonacciCache[n];
        }

        BigInteger nthFibonacciNumber = (fibonacci(n - 1).add(fibonacci(n - 2)));
        fibonacciCache[n] = nthFibonacciNumber;

        return nthFibonacciNumber;
    }
}
