import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class eightBall {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random random = new Random();

        getRandomPhrase(scan, random);


    }

    private static void getRandomPhrase(Scanner scan, Random random) {

        String phrase1 = "Not Technically.";
        String phrase2 = "Please Just Stop.";
        String phrase3 = "Nailed It.";
        String phrase4 = "No. No. No.";
        String phrase5 = "This Is True.";
        String phrase6 = "Seems Controversial.";
        String phrase7 = "Wait. What?";
        String phrase8 = "Yes. Of Course.";
        String phrase9 = "Oh Definitely Not.";
        String phrase10 = "That's A Fact.";
        String phrase11 = "I Highly Doubt That.";
        String phrase12 = "Yes. More Of That.";
        String phrase13 = "Can You Repeat That?";
        String phrase14 = "Do More Research.";
        String phrase15 = "Do Owls Exist?";
        String phrase16 = "I'm Not Convinced.";
        String phrase17 = "Go On...";
        String phrase18 = "Definitely Almost.";
        String phrase19 = "Seriously?";
        String phrase20 = "You Must Be Joking.";

        System.out.println("Eight Ball!");
        System.out.println("Roll 1: ");
        System.out.println("Stop 2: ");
        System.out.println("---------");

        while (true){

            int userInput = scan.nextInt();
            if (userInput == 1) {
                int randNum = random.nextInt(20) + 1;

                if (randNum == 1){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase1);
                }
                if (randNum == 2){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase2);
                }
                if (randNum == 3){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase3);
                }
                if (randNum == 4){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase4);
                }
                if (randNum == 5){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase5);
                }
                if (randNum == 6){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase6);
                }
                if (randNum == 7){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase7);
                }
                if (randNum == 8){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase8);
                }
                if (randNum == 9){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase9);
                }
                if (randNum == 10){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase10);
                }
                if (randNum == 11){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase11);
                }
                if (randNum == 12){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase12);
                }
                if (randNum == 13){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase13);
                }
                if (randNum == 14){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase14);
                }
                if (randNum == 15){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase15);
                }
                if (randNum == 16){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase16);
                }
                if (randNum == 17){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase17);
                }
                if (randNum == 18){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase18);
                }
                if (randNum == 19){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase19);
                }
                if (randNum == 20){
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println(phrase20);
                }
                System.out.println("\n\n");
                System.out.println("How about another go?");
            }else{
                System.out.println("Bye");
                break;
            }



        }
    }
}
