import java.util.Random;
import java.util.Scanner;

public class guessTheNumberVs {
    private static final String[] taunts = new String[] {
            "you will never beat me",
            "is that the best you can do?",
            "you are weak!"
    };

    public static void main(String[] args) {

        ComputerVoice computerVoice = new ComputerVoice();
        int countOfUserWins = 0;
        int countOfCompWins = 0;

        System.out.println("Welcome to 'Guess The Number!'");
        System.out.println("You will play against a computer, who ever has the least amount of tries wins!");
        System.out.println("Best of 3!");
        System.out.println("\n\n\n");
        Random random = new Random();
        ComputerVoice.Voice voice = ComputerVoice.Voice.values()[random.nextInt(40)/10];

        while (countOfUserWins < 3 && countOfCompWins < 3){
            int tauntPick = random.nextInt(20) / 10;
            System.out.println("Make your guess (1-100): ");
            int userNumOfTries = userCode();

            System.out.println("Ok its time to give the Computer a challenge!");
            System.out.println("Pick a Number (1-100): ");

            int compNumOfTries = computerCode();

            if (userNumOfTries < compNumOfTries){
                countOfUserWins++;
                System.out.println("User Wins that round");
            }else if(compNumOfTries < userNumOfTries){
                countOfCompWins++;
                System.out.println("Computer Wins that round");
                computerVoice.say(taunts[tauntPick], voice);
            }else{
                System.out.println("Tie");
            }
        }
        if (countOfUserWins > countOfCompWins){
            System.out.println("User Wins!");
        }else {
            System.out.println("Computer Wins!");
            computerVoice.say("hahaha you suck", voice);
        }


    }

    private static int userCode() {
        Random random = new Random();
        int theNumber = random.nextInt(100) + 1;
        Scanner scan = new Scanner(System.in);

        int numOfTries = 0;
        while (true) {
            int userInput = scan.nextInt();
            numOfTries++;

            if (userInput == theNumber) {
                System.out.println("You guessed Right");
                System.out.println("Guess Count: " + numOfTries);
                System.out.println("\n\n\n");
                break;
            }
            if (userInput < theNumber) {
                System.out.println("Too Low");
            }
            if (userInput > theNumber) {
                System.out.println("Too High");
            }
            System.out.println("________");
        }
        return numOfTries;
    }

    private static int computerCode() {

        Scanner scanner = new Scanner(System.in);
        Random compRan = new Random();

        int userNumber = scanner.nextInt();
        System.out.println("Working on it");

        int range = 100;

        boolean keepGoing = false;
        int numOfTries = 0;

        int middleOfRange = range / 2;
        double tenPercentOfRange = range * 0.1;

        int middleRangeOfRangeSub = middleOfRange - (int) tenPercentOfRange;
        numOfTries++;
        int computerGuess = compRan.nextInt((int) tenPercentOfRange * 2) + middleRangeOfRangeSub;
        int lowerBound = 1;
        while (!keepGoing) {

            int calculateNextGuess;

            if (computerGuess < userNumber) {
                lowerBound = computerGuess;
                calculateNextGuess = range - computerGuess;
                computerGuess = (calculateNextGuess / 2) + lowerBound;
            } else if (computerGuess > userNumber) {
                range = computerGuess;
                calculateNextGuess = computerGuess - lowerBound;
                computerGuess = calculateNextGuess / 2;
            }

            numOfTries++;
            if (computerGuess == userNumber) {
                System.out.println("Computer guessed in " + numOfTries + " tries");
                System.out.println("\n\n\n");
                keepGoing = true;
            }

        }
        return numOfTries;
    }

}
