// Ulyana Chaikouskaya

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numberItemsSlots = scan.nextInt();

        Integer [][] inputArray = new Integer[numberItemsSlots][3];

        int tempMin, tempMax;
        for (int i = 0; i < numberItemsSlots; i++) {

            tempMin = scan.nextInt();
            tempMax = scan.nextInt();
            inputArray[i][0] = i+1;
            inputArray[i][1] = tempMin;
            inputArray[i][2] = tempMax;

        }

        Integer[][]test = UlyanaChaikouskaya_radix_srt(inputArray);
        test = UlyanaChaikouskaya_count_srt(test);

        for (int i = 0; i < test.length; i++) {
            System.out.print(test[i][0] + " ");
        }

    }



    private static <T extends Number> int findMaxItem (T [][] array, int rowNumber){
        //rowNumber = 1 -- min, = 2 - max, 0 -- index
        int temp = 0;
        for (int i = 0; i < array.length; i++) {
            if ((int)array[i][rowNumber] > temp){
                temp = (int)array[i][rowNumber];
            }
        }
        return temp;
    }

    private static <T extends Number> T[][] UlyanaChaikouskaya_count_for_radix_srt
            (T [][] inputArray,int arrayLength, int exp){

        //create countArray
        int [] countArray = new int [10];

        // Store count of occurrences in count[]
        for (int i = 0; i < arrayLength; i++) {
            countArray[((int)inputArray[i][2]/exp)%10]++;
        }

        //accum results
        for (int i = 1; i < 10; i++) {
            countArray[i] += countArray[i - 1];
        }

        Integer[][] outputArray = new Integer[inputArray.length][3];

        for (int i = inputArray.length-1; i >= 0 ; i--) {
            int indexCountArray = ((int) inputArray[i][2]/exp)%10;
            int indexOutputArray =  countArray[indexCountArray] - 1;
            outputArray[indexOutputArray][0] = (Integer) inputArray[i][0];
            outputArray[indexOutputArray][1] = (Integer) inputArray[i][1];
            outputArray[indexOutputArray][2] = (Integer) inputArray[i][2];
            countArray[indexCountArray]--;
        }
        return (T[][]) outputArray;
    }

    private static <T extends Number> T[][] UlyanaChaikouskaya_radix_srt (T [][] inputArray){
        int maxItem = findMaxItem(inputArray, 2);
        for (int exp = 1; maxItem / exp > 0; exp *= 10){
            inputArray = UlyanaChaikouskaya_count_for_radix_srt(inputArray, inputArray.length, exp);
        }
        return inputArray;
    }

    private static <T extends Number> T[][] UlyanaChaikouskaya_count_srt
            (T [][] inputArray){

        //find max item in row
        int maxItem = findMaxItem(inputArray, 1);

        //create countArray
        int [] countArray = new int [maxItem+1];

        // store the count of each unique element of the input array
        for (int i = 0; i < inputArray.length; i++) {
            countArray[(int)inputArray[i][1]]++;
        }

        //accum results
        for (int i = maxItem-1; i >= 0; i--) {
            countArray[i] += countArray[i + 1];
        }

        Integer[][] outputArray = new Integer[inputArray.length][3];

        for (int i = inputArray.length-1; i >= 0 ; i--) {
            T currentValue = inputArray[i][1];
            int indexCountArray = (int)currentValue;
            int indexOutputArray =  countArray[indexCountArray] - 1;
            outputArray[indexOutputArray][0] = (Integer) inputArray[i][0];
            outputArray[indexOutputArray][1] = (Integer) inputArray[i][1];
            outputArray[indexOutputArray][2] = (Integer) inputArray[i][2];
            countArray[indexCountArray]--;
        }
        return (T[][]) outputArray;
    }
}


