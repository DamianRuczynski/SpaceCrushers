public class ScoreEntry {
    private final String nickname;
    private final int score;
    public ScoreEntry(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }
    public String getNickname() {
        return nickname;
    }
    public int getScore() {
        return score;
    }
}