
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Recursion {
    public static void main(String[] args) {

        int[] sumMe = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 };
        System.out.printf("Array Sum: %d\n", arraySum(sumMe, 0));

        int[] minMe = { 0, 1, 1, 2, 3, 5, 8, -42, 13, 21, 34, 55, 89 };
        System.out.printf("Array Min: %d\n", arrayMin(minMe, 0));

        String[] amISymmetric =  {
                "You can cage a swallow can't you but you can't swallow a cage can you",
                "I still say cS 1410 is my favorite class"
        };
        for (String test : amISymmetric) {
            String[] words = test.toLowerCase().split(" ");
            System.out.println();
            System.out.println(test);
            System.out.printf("Is word symmetric: %b\n", isWordSymmetric(words, 0, words.length - 1));
        }

        double[][] masses = {
                { 51.18 },
                { 55.90, 131.25 },
                { 69.05, 133.66, 132.82 },
                { 53.43, 139.61, 134.06, 121.63 }
        };
        System.out.println();
        System.out.println("--- Weight Pyramid ---");
        for (int row = 0; row < masses.length; row++) {
            for (int column = 0; column < masses[row].length; column++) {
                double weight = computePyramidWeights(masses, row, column);
                System.out.printf("%.2f ", weight);
            }
            System.out.println();
        }

        char[][] image = {
                {'*','*',' ',' ',' ',' ',' ',' ','*',' '},
                {' ','*',' ',' ',' ',' ',' ',' ','*',' '},
                {' ',' ',' ',' ',' ',' ','*','*',' ',' '},
                {' ','*',' ',' ','*','*','*',' ',' ',' '},
                {' ','*','*',' ','*',' ','*',' ','*',' '},
                {' ','*','*',' ','*','*','*','*','*','*'},
                {' ',' ',' ',' ',' ',' ',' ',' ','*',' '},
                {' ',' ',' ',' ',' ',' ',' ',' ','*',' '},
                {' ',' ',' ','*','*','*',' ',' ','*',' '},
                {' ',' ',' ',' ',' ','*',' ',' ','*',' '}
        };
        int howMany = howManyOrganisms(image);
        System.out.println();
        System.out.println("--- Labeled Organism Image ---");
        for (char[] line : image) {
            for (char item : line) {
                System.out.print(item);
            }
            System.out.println();
        }
        System.out.printf("There are %d organisms in the image.\n", howMany);

    }

    public static boolean isWordSymmetric(String[] words, int start, int end) {

        // Description: Similar to a palindrome, a phrase is symmetric if the words placed forward or backwards say the same thing.
        // For example, "Fall leaves as soon as leaves fall" is word symmetric.
        // Write a recursive method that determines if the phrase is word symmetric.
        // To solve this, you'll need to split it into the individual words before calling the recursive method.
        // Inside the recursive method be sure to do the word comparison while ignoring the case of the string.
        // Please note that the end argument represents the last array index to check.

        if (start >= end) {
            return true;
        }
        if (Objects.equals(words[start].toLowerCase(), words[end].toLowerCase())) {
            return isWordSymmetric(words, start + 1, end - 1);
        }
        return false;
    }

    public static long arraySum(int[] data, int position) {

        // Description: Write a recursive method that returns the total of all elements in the array.

        if (data.length == 0) {
            return 0;
        }
        if (data.length == 1){
            return data[0];
        }
        if (position == data.length - 1) {
            return data[position];
        }
        if (position + 1 > data.length) {
            return 0;
        }
        return data[position] + arraySum(data, position + 1);

    }

    public static int arrayMin(int[] data, int position) {

        // Description: Write a recursive function that returns the minimum value of the elements in the array.
        // You may assume there is at least one value in the array.

        if (data.length == 0) {
            return 0;
        }
        if (data.length == 1){
            return data[0];
        }
        if (position == data.length - 1) {
            return data[position];
        }
        if (position + 1 > data.length) {
            return 0;
        }
        return Math.min(data[position], arrayMin(data, position + 1));
    }

    public static double computePyramidWeights(double[][] masses, int row, int column) {

        // Description:  Consider the following pyramid of objects...
        //   A
        //  B C
        // D E F
        //G H I J
        //The weight supported at each object is the mass of the object itself, plus half of the supported weight of the objects above it.
        //The weight supported at location A is the mass of A itself.
        //The weight supported at location B is the mass of B plus 1/2 the weight supported at location A.
        //The weight supported at location C is the mass of C plus 1/2 the weight supported at location A.
        //The weight supported at location E is the mass of E itself, plus 1/2 of the weight supported at location B and 1/2 of the weight supported at location C.
        //The method must be able to handle incorrect row or column arguments
        //The method should never leave the bounds of each row or column
        //The data is given to you in the following format (there may be any number of rows, this example shows only 4).
        //       0  1  2  3 (col)
        //    0  A
        //    1  B, C
        //    2  D, E, F
        //    3  G, H, I, J
        //  (row)

        if(row < 0 || column < 0 || row > masses.length - 1 || column > masses[row].length-1){
            return 0.0;
        }
        else if (row == 0 && column == 0){
            return masses[0][0];
        } else if (column == 0) {
            return masses[row][column] + .5 * (computePyramidWeights(masses, row - 1, column));
        }
        return masses[row][column] + .5 * (computePyramidWeights(masses, row - 1, column - 1));
    }

    public static int howManyOrganisms(char[][] image) {

        // Description: Given a 2d array of characters, where an asterisk (*) marks a non-empty cell.
        // An organism is defined as all cells connected directly to other cells in the up/down/left/right (not diagonal) directions.
        // The method signature above is not a recursive method.
        // Instead, it iterates through the image and as soon as it encounters a new organism,
        // it calls a recursive method (that you define) that labels the newly found organism.
        // Look at the provided driver code to see how the organism is stored and compare that with the output of the labeled organisms below.
        // In the original image, the organisms are identified by asterisks (*), but the resulting image has each organism labeled with a different character in the alphabet.
        // The image is NOT guaranteed to be rectangular (i.e. it may be ragged).
        int character = 97;
        int count = 0;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                if (image[i][j] == '*') {
                    labelOrganisms(image, i, j, character++);
                    count++;
                }
            }
        }
        return count;
    }

    public static void labelOrganisms(char[][] image, int row, int col, int character) {
        if (row < 0 || row >= image.length) return;
        if (col < 0 || col >= image[row].length) return;
        if (image[row][col] != '*') return;
        image[row][col] = (char) character;
        labelOrganisms(image, row, col + 1, character);
        labelOrganisms(image, row, col - 1, character);
        labelOrganisms(image, row + 1, col, character);
        labelOrganisms(image, row - 1, col, character);
    }

}
