import java.util.Scanner;

public class theBinomaryCardTrick {
    public static void main(String[] args) {
        System.out.println("Pick a number (1-63) and keep it in your head");
        System.out.print("Enter 1 when you ready: ");
        Scanner scan = new Scanner(System.in);
        int userIsReady = scan.nextInt();
        int myGuess = 0;
        System.out.println();
        System.out.println("There are six cards");
        printFirstCard();
        int userChoice1 = scan.nextInt();
        if(userChoice1 == 1){
            myGuess += 16;
        }
        printSecondCard();
        int userChoice2 = scan.nextInt();
        if(userChoice2 == 1){
            myGuess += 4;
        }
        printThirdCard();
        int userChoice3 = scan.nextInt();
        if(userChoice3 == 1){
            myGuess += 8;
        }
        printFourthCard();
        int userChoice4 = scan.nextInt();
        if(userChoice4 == 1){
            myGuess += 1;
        }
        printFifthCard();
        int userChoice5 = scan.nextInt();
        if(userChoice5 == 1){
            myGuess += 2;
        }
        printSixthCard();
        int userChoice6 = scan.nextInt();
        if(userChoice6 == 1){
            myGuess += 32;
        }
        System.out.println();
        System.out.println("Your number is: " + myGuess);
    }

    private static void printFirstCard(){
        System.out.println();
        System.out.println("Card 1");
        System.out.println("16 17 18 19 20 21 22 23");
        System.out.println("24 25 26 27 28 29 30 31");
        System.out.println("48 49 50 51 52 53 54 55");
        System.out.println("56 57 58 59 60 61 62 63");
        System.out.print("Is your number on this card? (yes = 1 / no = 2): ");
    }

    private static void printSecondCard(){
        System.out.println();
        System.out.println("Card 2");
        System.out.println("4 5 6 7 12 13 14 15");
        System.out.println("20 21 22 23 28 29 30 31");
        System.out.println("36 37 38 39 44 45 46 47");
        System.out.println("52 53 54 55 60 61 62 63");
        System.out.print("Is your number on this card? (yes = 1 / no = 2): ");
    }

    private static void printThirdCard(){
        System.out.println();
        System.out.println("Card 3");
        System.out.println("8 9 10 11 12 13 14 15");
        System.out.println("24 25 26 27 28 29 30 31");
        System.out.println("40 41 42 43 44 45 46 47");
        System.out.println("56 57 58 59 60 61 62 63");
        System.out.print("Is your number on this card? (yes = 1 / no = 2): ");
    }

    private static void printFourthCard(){
        System.out.println();
        System.out.println("Card 4");
        System.out.println("1 3 5 7 9 11 13 15");
        System.out.println("17 19 21 23 25 27 29 31");
        System.out.println("33 35 37 39 41 43 45 47");
        System.out.println("49 51 53 55 57 59 61 63");
        System.out.print("Is your number on this card? (yes = 1 / no = 2): ");
    }

    private static void printFifthCard(){
        System.out.println();
        System.out.println("Card 5");
        System.out.println("2 3 6 7 10 11 14 15");
        System.out.println("18 19 22 23 26 27 30 31");
        System.out.println("34 35 38 39 42 43 46 47");
        System.out.println("50 51 54 55 58 59 62 63");
        System.out.print("Is your number on this card? (yes = 1 / no = 2): ");
    }

    private static void printSixthCard(){
        System.out.println();
        System.out.println("Card 6");
        System.out.println("32 33 34 35 36 37 38 39");
        System.out.println("40 41 42 43 44 45 46 47");
        System.out.println("48 49 50 51 52 53 54 55");
        System.out.println("56 57 58 59 60 61 62 63");
        System.out.print("Is your number on this card? (yes = 1 / no = 2): ");
    }

}
