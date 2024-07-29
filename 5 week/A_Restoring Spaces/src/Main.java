//Ulyana Chaikouskaya

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberWords = scanner.nextInt();
        int numberSymbols = scanner.nextInt();
        scanner.nextLine();

        String[] dictionary = scanner.nextLine().split(" ");
        String corruptedText = scanner.nextLine();

        ArrayList<String> separetedText = separatedString(dictionary, numberSymbols, corruptedText);

        for (String word : separetedText){
            System.out.print(word + " ");
        }

    }

    private static ArrayList<String> separatedString(String[] dictionary,
                                        int lengthCorruptedString, String corruptedString){

        ArrayList<String>[] dp = new ArrayList[lengthCorruptedString + 1];

        dp[0] = new ArrayList<>();

        for (int i = 0; i < lengthCorruptedString; i++) {
            if (dp[i] != null) {
                for (String word : dictionary) {
                    if (i + word.length() <= lengthCorruptedString && corruptedString.substring(i, i + word.length()).equals(word)) {
                        ArrayList<String> newSeparatedWords = new ArrayList<>(dp[i]);
                        newSeparatedWords.add(word);

                        if (dp[i + word.length()] == null || newSeparatedWords.size() < dp[i + word.length()].size()) {
                            dp[i + word.length()] = newSeparatedWords;
                        }
                    }
                }
            }
        }

        return dp[lengthCorruptedString];

        /*String separetedString = "";
        ArrayList<String> separatedSubstring = new ArrayList<>();

        ArrayList<String> separatedWords = new ArrayList<>();
        int startSubstring = 0;

        for (int i = 0; i < lengthCorruptedString; i++){
            for (String word : dictionary) {
                String s = corruptedString.substring(startSubstring,
                        lengthCorruptedString-i);
                if (i + word.length()<= lengthCorruptedString &&
                        (s.equals(word))){
                    //System.out.println(word);
                    separatedWords.add(word);
                    startSubstring += word.length()-1;
                    break;
                }

            }
        }

        for (String i : separatedWords) {
            System.out.print(i + " ");
        }
*/


    }
}
