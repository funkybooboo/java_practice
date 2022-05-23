import java.util.Scanner;

public class Palendrone {
    public static void main(String[] args) {
        System.out.println("Enter a word to see if its a Palendrone");
        Scanner scan = new Scanner(System.in);
        while(true){
            String userInput = scan.nextLine();

            int i = 0;
            int j = userInput.length()-1;

            while(i < j){
                while(userInput.charAt(i) == ' '){
                    i++;
                }
                while(userInput.charAt(j) == ' '){
                    j--;
                }
                if(userInput.charAt(i) == userInput.charAt(j)){
                    i++;
                    j--;
                }else{
                    break;
                }

            }
            if(i >= j){
                System.out.println("YES");
            }else{
                System.out.println("NO");
            }

        }


    }
}
