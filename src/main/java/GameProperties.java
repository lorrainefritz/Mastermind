package main.java;

import java.io.IOException;

public class GameProperties {
    private int numbersOfTries;
    private int difficulty;
    private int gameLength;
    private boolean devMode;

    public GameProperties() {
        GameGetPropertyValues gameGetPropertyValues = new GameGetPropertyValues();
        try {
            gameGetPropertyValues.getPropValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setDifficulty(gameGetPropertyValues.getDifficulty());
        this.setGameLength(gameGetPropertyValues.getGameLength());
        this.setNumbersOfTries(gameGetPropertyValues.getNumberOfTries());
        this.setDevMode(gameGetPropertyValues.getDevMode());
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

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(String devMode) {
        if (devMode.equals("1")){
            this.devMode=true;
        } else if (devMode.equals("2")) {
            this.devMode = false;
        }
    }
}
