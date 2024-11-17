package edu.fje.memorygame;

public class ScoreItem {
    private final int score;
    private final String timestamp;
    private final String playTime;

    public ScoreItem(int score, String timestamp, String playTime) {
        this.score = score;
        this.timestamp = timestamp;
        this.playTime = playTime;
    }

    public int getScore() {
        return score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPlayTime() {
        return playTime;
    }
}
