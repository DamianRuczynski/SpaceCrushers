import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BestPlayerList {
    private static final String FILE_PATH = "topten.txt";
    private static final int MAX_SCORES = 10;

    public static List<ScoreEntry> getTopTenScores() {
        List<ScoreEntry> topTenScores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
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
            topTenScores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
            if (topTenScores.size() > MAX_SCORES) {
                topTenScores = topTenScores.subList(0, MAX_SCORES);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (ScoreEntry entry : topTenScores) {
                    writer.write(entry.getNickname() + ":" + entry.getScore());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}


