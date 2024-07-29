import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numberItemsSlots = scan.nextInt();
        Integer[][] inputArray = new Integer[numberItemsSlots][3];

        // Input array initialization skipped for brevity...

        System.out.println("Counting sort");
        Integer[][] countingSortResult = UlyanaChaikouskaya_count_srt(inputArray);

        System.out.println("Radix sort");
        Integer[][] radixSortResult = UlyanaChaikouskaya_radix_srt(inputArray);

        // Display results...
        System.out.println("Counting Sort Result:");
        displayResult(countingSortResult);

        System.out.println("Radix Sort Result:");
        displayResult(radixSortResult);
    }

    private static <T extends Number> int findMaxItem(T[][] array, int rowNumber) {
        int temp = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i][rowNumber].intValue() > temp) {
                temp = array[i][rowNumber].intValue();
            }
        }
        return temp;
    }

    private static <T extends Number> T[][] UlyanaChaikouskaya_count_srt(T[][] inputArray) {
        int maxItem = findMaxItem(inputArray, 1);
        int[] countArray = new int[maxItem + 1];

        for (int i = 0; i < inputArray.length; i++) {
            countArray[inputArray[i][1].intValue()]++;
        }

        for (int i = 1; i <= maxItem; i++) {
            countArray[i] += countArray[i - 1];
        }

        Integer[][] outputArray = new Integer[countArray.length][3];

        for (int i = inputArray.length - 1; i >= 0; i--) {
            T currentValue = inputArray[i][1];
            int indexCountArray = currentValue.intValue();
            int indexOutputArray = countArray[indexCountArray] - 1;
            outputArray[indexOutputArray][0] = (Integer) inputArray[i][0];
            outputArray[indexOutputArray][1] = (Integer) inputArray[i][1];
            outputArray[indexOutputArray][2] = (Integer) inputArray[i][2];
            countArray[indexCountArray]--;
        }

        handleCoincidingBids(outputArray);

        return (T[][]) outputArray;
    }

    private static <T extends Number> void handleCoincidingBids(T[][] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i][2].intValue() == array[i + 1][2].intValue()) {
                if (array[i][1].intValue() > array[i + 1][1].intValue()) {
                    swapRows(array, i, i + 1);
                }
            }
        }
    }

    private static <T> void swapRows(T[][] array, int i, int j) {
        T[] temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    private static void displayResult(Integer[][] result) {
        for (int i = 0; i < result.length; i++) {
            System.out.println("index: " + result[i][0] + " min: " + result[i][1] + " max: " + result[i][2]);
        }
    }

    private static <T extends Number> T[][] UlyanaChaikouskaya_radix_srt(T[][] inputArray) {
        int maxItem = findMaxItem(inputArray, 2);

        // Perform Radix Sort on the inputArray here...
        int exp = 1;
        while (maxItem / exp > 0) {
            countingSortByDigit(inputArray, exp);
            exp *= 10;
        }

        return inputArray;
    }

    private static <T extends Number> void countingSortByDigit(T[][] inputArray, int exp) {
        int n = inputArray.length;
        int[] countArray = new int[10];
        Object[][] outputArray = new Object[n][3];

        for (int i = 0; i < n; i++) {
            countArray[(inputArray[i][2].intValue() / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int indexCountArray = (inputArray[i][2].intValue() / exp) % 10;
            int indexOutputArray = countArray[indexCountArray] - 1;
            outputArray[indexOutputArray][0] = inputArray[i][0];
            outputArray[indexOutputArray][1] = inputArray[i][1];
            outputArray[indexOutputArray][2] = inputArray[i][2];
            countArray[indexCountArray]--;
        }

        handleCoincidingBids((T[][])outputArray);

        for (int i = 0; i < n; i++) {
            inputArray[i][0] = (T) outputArray[i][0];
            inputArray[i][1] = (T) outputArray[i][1];
            inputArray[i][2] = (T) outputArray[i][2];
        }
    }
}
