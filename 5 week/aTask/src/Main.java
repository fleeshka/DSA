//Ulyana Chaikouskaya

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberWords = scanner.nextInt();
        int numberSymbols = scanner.nextInt();


        String[] dictionary = new String[numberWords];
        for (int i = 0; i < numberWords; i++) {
            dictionary[i] = scanner.next();
        }

        String corruptedText = scanner.next();

        String result = separatingString(dictionary, corruptedText);

        System.out.println(result);
    }

    private static String separatingString(String[] dictionary, String
            corruptedText ) {
        int K = corruptedText.length();
        boolean[] dp = new boolean[K + 1];
        dp[0] = true;

        for (int i = 1; i <= K; i++) {
            for (String word : dictionary) {
                int wordLength = word.length();
                if (i >= wordLength && dp[i - wordLength] && corruptedText.substring(i - wordLength, i).equals(word)) {
                    dp[i] = true;
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        int i = K;
        while (i > 0) {
            for (String word : dictionary) {
                int wordLength = word.length();
                if (i >= wordLength && dp[i - wordLength] && corruptedText.substring(i - wordLength, i).equals(word)) {
                    result.insert(0, word).insert(0, " ");
                    i -= wordLength;
                    break;
                }
            }
        }

        return result.toString().trim();
    }
}
