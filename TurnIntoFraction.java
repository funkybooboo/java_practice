import java.util.Scanner;

public class TurnIntoFraction {
    public static void main(String[] args) {
        Scanner Scan = new Scanner(System.in);
        String userInput = Scan.nextLine();

        int position = userInput.indexOf(".");

        String wholeNumber = position > 0? userInput.substring(0, position) : "0";

        String fraction = userInput.substring(position);

        int shift = userInput.length() - position - 1;

        StringBuilder sb = new StringBuilder("1");

        for(int i = 0; i < shift; i++){
            sb.append("0");
        }
        int demominator = Integer.parseInt(sb.toString());

        int decimal = (int)(Float.parseFloat(fraction) * demominator);

        int largestWhole = findLargest(decimal, demominator);
        int reducedNumerator = decimal / largestWhole;
        int reducedDenomitor = demominator / largestWhole;

        System.out.println(("0".equals(wholeNumber) ? "" : (wholeNumber + " ")) +  reducedNumerator + "/" + reducedDenomitor);


    }

    private static int findLargest(int numorator, int demominator) {
        int largest = 1;
        for(int i = 1; i <= Math.min(numorator, demominator); i++){
            float x = (float) numorator / i;
            float y = (float) demominator / i;
            if(x == (int)x && y == (int)y){
                largest = i;
            }
        }
        return largest;
    }
}
