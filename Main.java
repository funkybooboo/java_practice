import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("(E)ncryption");
        System.out.println("(D)ecryption");
        System.out.print("Enter: ");
        String userChoice = scanner.next();
        System.out.println();
        if (Objects.equals(userChoice, "E") || Objects.equals(userChoice, "e") || Objects.equals(userChoice, "D") || Objects.equals(userChoice, "d")) {
            try {
                System.out.print("filepath-space-offset: ");
                String filePath = scanner.next();
                int offset = scanner.nextInt();
                System.out.println();
                File file = new File(filePath);
                Scanner scan = new Scanner(file);
                getOutput(offset, scan, userChoice);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(1);
            }
        }
        else System.out.println("invalid input");
    }

    private static void getOutput(int offset, Scanner scan, String userChoice) {
        System.out.println();
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            char[] listOfChars = line.toCharArray();
            for (char letter : listOfChars) {
                if (Objects.equals(userChoice, "E") || Objects.equals(userChoice, "e")) {
                    encryption(offset, (int) letter);
                } else if (Objects.equals(userChoice, "D") || Objects.equals(userChoice, "d")) {
                    decryption(offset, (int) letter);
                }
            }
        }
        System.out.println();
    }

    private static void decryption(int offset, int letter) {
        System.out.print(((char) (letter - offset)));
    }

    private static void encryption(int offset, int letter) {
        System.out.print(((char) (letter + offset)));
    }

}
