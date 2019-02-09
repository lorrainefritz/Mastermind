package main.java;

public class ResultMastermindDefender {
    int writePlacedNumbers;
    int presentNumbers;

    public ResultMastermindDefender(int writePlacedNumbers, int presentNumbers) {
        this.writePlacedNumbers = writePlacedNumbers;
        this.presentNumbers = presentNumbers;
    }

    public boolean equals(ResultMastermindDefender r) {
        return r.presentNumbers == presentNumbers && r.writePlacedNumbers == writePlacedNumbers;
    }

    public String toString() {
        return writePlacedNumbers + ";" + presentNumbers;
    }

    public int getWritePlacedNumbers() {
        return writePlacedNumbers;
    }

    public void setWritePlacedNumbers(int writePlacedNumbers) {
        this.writePlacedNumbers = writePlacedNumbers;
    }

    public int getPresentNumbers() {
        return presentNumbers;
    }

    public void setPresentNumbers(int presentNumbers) {
        this.presentNumbers = presentNumbers;
    }
}



