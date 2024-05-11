import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int lineLength = scan.nextInt();
        int[] line = new int[lineLength];
        for (int i = 0; i < lineLength; i++) {
            line[i] = scan.nextInt();
        }
        insertionSort(line);
        for (int j : line) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    private static void insertionSort(int[] line) {
        for (int i = 1; i < line.length; i++) {
            int j = i - 1;
            while (j >= 0 && line[j + 1] < line[j]) {
                int temp = line[j + 1];
                line[j + 1] = line[j];
                line[j] = temp;
                j--;
            }

        }
    }
}