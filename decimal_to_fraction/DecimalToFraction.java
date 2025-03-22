import java.util.Scanner;

/**
 * Created by danastott on 8/25/21
 */
public class DecimalToFraction {

    public static void main(String[] args) {
        DecimalToFraction dtf = new DecimalToFraction();
        dtf.execute();
        System.out.println("\nGoodbye");
        System.exit(0);
    }

    public void execute() {
        int pos;
        do {
            String userInput = promptForDecimal();
            pos = userInput.indexOf(".");
            if (pos != -1) {
                String wholeNumber = (pos == 0) ? "0" : userInput.substring(0, pos);
                int shift = userInput.length() - pos - 1;
                String decimalValue = userInput.substring(pos);
                double decimal = Double.parseDouble(decimalValue);
                String fraction = convertDecimalToFraction(decimal, shift);
                System.out.println(("0".equals(wholeNumber) ? "" : wholeNumber + " ") + fraction);
            }
        } while (pos != -1);
    }

    private String promptForDecimal() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nPlease enter a decimal: ");
        return scanner.nextLine();
    }


    private String convertDecimalToFraction(double x, int shift) {
        if (x < 0) {
            return "-" + convertDecimalToFraction(-x, shift);
        }
        double tolerance = computeTolerance(shift);
        return convertDecimalToFraction(x, tolerance);
    }

    private double computeTolerance(int shift) {
        if (shift > 2) {
            return Double.parseDouble("." + "0".repeat(Math.max(0, shift - 2)) + "1");
        }
        return .1;
    }

    /*
    See: https://jonisalonen.com/2012/converting-decimal-numbers-to-ratios/
     */
    private String convertDecimalToFraction(double x, double tolerance) {
        double h1 = 1;
        double h2 = 0;
        double k1 = 0;
        double k2 = 1;
        double b = x;
        do {
            double a = Math.floor(b);
            double aux = h1;
            h1 = a * h1 + h2;
            h2 = aux;
            aux = k1;
            k1 = a * k1 + k2;
            k2 = aux;
            b = 1 / (b - a);
        } while (Math.abs(x - h1 / k1) > x * tolerance);

        return (int) h1 + "/" + (int) k1;
    }
}
