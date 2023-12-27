import java.util.Scanner;

public class theBinomaryCardTrick {
    public static void main(String[] args) {
        System.out.println("Pick a number (1-63) and keep it in your head");
        System.out.print("Hit enter when ready: ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int guess = 0;
        System.out.println();
        System.out.println("There are six cards");
        printFirstCard();
        guess += check(scanner, 16);
        printSecondCard();
        guess += check(scanner, 4);
        printThirdCard();
        guess += check(scanner, 8);
        printFourthCard();
        guess += check(scanner, 1);
        printFifthCard();
        guess += check(scanner, 2);
        printSixthCard();
        guess += check(scanner, 32);
        System.out.println();
        System.out.println("Your number is: " + guess);
    }

    private static int check(Scanner scanner, int value) {
        System.out.println();
        System.out.print("Is your number on this card? [Y/n]: ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y") || choice.isEmpty()){
            System.out.println("y");
            return value;
        }
        System.out.println("n");
        return 0;
    }

    private static void printFirstCard() {
        System.out.println();
        System.out.println("Card 1");
        System.out.println("16 17 18 19 20 21 22 23");
        System.out.println("24 25 26 27 28 29 30 31");
        System.out.println("48 49 50 51 52 53 54 55");
        System.out.println("56 57 58 59 60 61 62 63");
    }

    private static void printSecondCard() {
        System.out.println();
        System.out.println("Card 2");
        System.out.println("4 5 6 7 12 13 14 15");
        System.out.println("20 21 22 23 28 29 30 31");
        System.out.println("36 37 38 39 44 45 46 47");
        System.out.println("52 53 54 55 60 61 62 63");
    }

    private static void printThirdCard() {
        System.out.println();
        System.out.println("Card 3");
        System.out.println("8 9 10 11 12 13 14 15");
        System.out.println("24 25 26 27 28 29 30 31");
        System.out.println("40 41 42 43 44 45 46 47");
        System.out.println("56 57 58 59 60 61 62 63");
    }

    private static void printFourthCard() {
        System.out.println();
        System.out.println("Card 4");
        System.out.println("1 3 5 7 9 11 13 15");
        System.out.println("17 19 21 23 25 27 29 31");
        System.out.println("33 35 37 39 41 43 45 47");
        System.out.println("49 51 53 55 57 59 61 63");
    }

    private static void printFifthCard() {
        System.out.println();
        System.out.println("Card 5");
        System.out.println("2 3 6 7 10 11 14 15");
        System.out.println("18 19 22 23 26 27 30 31");
        System.out.println("34 35 38 39 42 43 46 47");
        System.out.println("50 51 54 55 58 59 62 63");
    }

    private static void printSixthCard() {
        System.out.println();
        System.out.println("Card 6");
        System.out.println("32 33 34 35 36 37 38 39");
        System.out.println("40 41 42 43 44 45 46 47");
        System.out.println("48 49 50 51 52 53 54 55");
        System.out.println("56 57 58 59 60 61 62 63");
    }

}
