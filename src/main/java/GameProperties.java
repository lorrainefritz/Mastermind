package main.java;

public class GameProperties {
    private int numbersOfTries;
    private int difficulty;
    private int gameLength;

    public GameProperties() {
    }

    public int getNumbersOfTries() {
        return numbersOfTries;
    }

    public void setNumbersOfTries(int numbersOfTries) {
        this.numbersOfTries = numbersOfTries;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getGameLength() {
        return gameLength;
    }

    public void setGameLength(int gameLength) {
        this.gameLength = gameLength;
    }
}
