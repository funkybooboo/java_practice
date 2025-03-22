import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    public static void main(String[] args) {

        System.out.println("Rock Paper Scissors!");
        System.out.println();
        System.out.println("Rock = 1");
        System.out.println("Paper = 2");
        System.out.println("Scissors = 3");
        System.out.println("End = 5");
        System.out.println();



        Scanner scan = new Scanner(System.in);


        Random random = new Random();


        int userWins = 0;

        int computerWins = 0;



        while(userWins + computerWins < 3){

            int userInput = scan.nextInt();
            int computerInput = random.nextInt(3)+1;

            //Tie
            if(userInput == 1 && computerInput == 1){
                System.out.println("Tie ");
            }
            if(userInput == 2 && computerInput == 2){
                System.out.println("Tie ");
            }
            if(userInput == 3 && computerInput == 3){
                System.out.println("Tie ");
            }

            //Win
            if(userInput == 1 && computerInput == 3){
                System.out.println("Win " + computerInput);
                userWins++;
            }if(userInput == 2 && computerInput == 1){
                System.out.println("Win " + computerInput);
                userWins++;
            }if(userInput == 3 && computerInput == 2){
                System.out.println("Win " + computerInput);
                userWins++;
            }


            if(userInput == 1 && computerInput == 2){
                System.out.println("Loose " + computerInput);
                computerWins++;
            }
            if(userInput == 3 && computerInput == 1){
                System.out.println("Loose " + computerInput);
                computerWins++;
            }
            if(userInput == 2 && computerInput == 3) {
                System.out.println("Loose " + computerInput);
                computerWins++;
            }

            if(userInput == 5){
                break;
            }

        }
        System.out.println("Winner is " + (userWins > computerWins ? "User!" : "Computer!"));





    }
}
