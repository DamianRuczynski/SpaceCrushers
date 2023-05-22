import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BestPlayerList {

    public static List<ScoreEntry> getTopTenScores() {
        List<ScoreEntry> topTenScores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String nickname = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    ScoreEntry entry = new ScoreEntry(nickname, score);
                    topTenScores.add(entry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return topTenScores;
    }

        public static void addScore(String nickname, int score) {
            List<ScoreEntry> topTenScores = getTopTenScores();
            topTenScores.add(new ScoreEntry(nickname, score));
            topTenScores.sort((user, secondUser) -> Integer.compare(secondUser.getScore(), user.getScore()));
            if (topTenScores.size() > Constants.MAX_SCORES) {
                topTenScores = topTenScores.subList(0, Constants.MAX_SCORES);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.FILE_PATH))) {
                for (ScoreEntry entry : topTenScores) {
                    writer.write(entry.getNickname() + ":" + entry.getScore());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}


