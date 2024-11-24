package edu.fje.memorygame;

public class GameData {
    private int previewImage;
    private String clueForIncorrectGuess;

    // Constructor
    public GameData(int previewImage, String clueForIncorrectGuess) {
        this.previewImage = previewImage;
        this.clueForIncorrectGuess = clueForIncorrectGuess;
    }

    // Getters and setters
    public int getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(int previewImage) {
        this.previewImage = previewImage;
    }

    public String getClueForIncorrectGuess() {
        return clueForIncorrectGuess;
    }

    public void setClueForIncorrectGuess(String clueForIncorrectGuess) {
        this.clueForIncorrectGuess = clueForIncorrectGuess;
    }
}
