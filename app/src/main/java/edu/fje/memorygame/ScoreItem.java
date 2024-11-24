package edu.fje.memorygame;

public class ScoreItem {
    private final int score;
    private final String date;
    private final String playTime;

    public ScoreItem(int score, String date, String playTime) {
        this.score = score;
        this.date = date;
        this.playTime = playTime;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public String getPlayTime() {
        return playTime;
    }
}
