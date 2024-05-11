import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int numberOfEntries = scan.nextInt();
        int sizeOfLeadershipTable = scan.nextInt();

        Player[] allPlayers = new Player[numberOfEntries];

        for (int i = 0; i < numberOfEntries; i++) {
            String player = scan.next();
            int score = scan.nextInt();
            allPlayers[i] = new Player(player, score);
        }

        for (Player player : sortLeadershipTable(allPlayers, sizeOfLeadershipTable)) {
            System.out.println(player.name + " " + player.score);
        }
    }

    private static Player[] sortLeadershipTable(Player[] allPlayers, int sizeOfLeadershipTable) {
        if (sizeOfLeadershipTable > allPlayers.length) {
            sizeOfLeadershipTable = allPlayers.length;
        }
        Player[] leadershipTable = new Player[sizeOfLeadershipTable];

        for (int i = 0; i < sizeOfLeadershipTable; i++) {
            int indexMaxElement = i;
            int maxScore = allPlayers[i].score;
            String nameOfMaxScorePlayer = allPlayers[i].name;
            for (int j = i + 1; j < allPlayers.length; j++) {
                if (allPlayers[j].score > maxScore) {
                    maxScore = allPlayers[j].score;
                    nameOfMaxScorePlayer = allPlayers[j].name;
                    indexMaxElement = j;
                }
            }
            Player temp = allPlayers[i];
            allPlayers[i] = allPlayers[indexMaxElement];
            allPlayers[indexMaxElement] = temp;
            leadershipTable[i] = new Player(nameOfMaxScorePlayer, maxScore);
        }
        return leadershipTable;
    }
}

class Player {
    String name;
    int score;
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
}