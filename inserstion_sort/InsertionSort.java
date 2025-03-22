import java.util.Random;

public class InsertionSort {
    public static void main(String[] args) {
        //Load array
        Random rand = new Random();
        int[] numbers = new int[1000000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(10000);
        }

        //System.out.println("Before: ");
        printArray(numbers);

        insertionSort(numbers);

        //System.out.println("\nAfter: ");
        printArray(numbers);
    }

    private static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int currentValue = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > currentValue) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = currentValue;

        }
    }

    private static void printArray(int[] array) {
        for (int j : array) {
            System.out.println(j);
        }
    }
}
