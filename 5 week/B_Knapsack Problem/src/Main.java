//Ulyana Chaikouskaya

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numberItems = scan.nextInt();
        int knapsackCapacity = scan.nextInt();

        int[] weightArray = new int[numberItems];
        int[] costsArray = new int[numberItems];

        for (int i = 0; i < numberItems; i++) {
            weightArray[i] = scan.nextInt();
        }
        for (int i = 0; i < numberItems; i++) {
            costsArray[i] = scan.nextInt();
        }

        int solutionKnapsackProblem[][] = solutionKnapsackProblem(numberItems, knapsackCapacity, weightArray, costsArray);
        selectedItemIndexes(solutionKnapsackProblem, numberItems, knapsackCapacity, weightArray, costsArray);
    }

    private static void selectedItemIndexes(int[][] dp, int numberItems, int knapsackCapacity, int[] weight, int[] cost) {
        int currentValue = dp[numberItems][knapsackCapacity];
        int capacity = knapsackCapacity;

        ArrayList<Integer> index = new ArrayList<>();

        while (capacity >= 1 && numberItems > 0) {
            if (currentValue != dp[numberItems - 1][capacity]) {
                index.add(numberItems);
                capacity -= weight[numberItems - 1];
                currentValue -= cost[numberItems - 1];
            }
            numberItems--;
        }

        System.out.println(index.size());
        for (int i = index.size() - 1; i >= 0; i--) {
            System.out.print(index.get(i) + " ");
        }
    }

    private static int[][] solutionKnapsackProblem(int numberItems, int knapsackCapacity, int[] weight, int[] cost) {
        int[][] dp = new int[numberItems + 1][knapsackCapacity + 1];

        for (int i = 0; i < knapsackCapacity + 1; i++) {
            dp[0][i] = 0;
        }
        for (int i = 0; i < numberItems + 1; i++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i <= numberItems; i++) {
            for (int j = 1; j <= knapsackCapacity; j++) {
                if (weight[i - 1] <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j], cost[i - 1] + dp[i - 1][j - weight[i - 1]]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp;
    }
}
