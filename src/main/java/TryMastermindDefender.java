package main.java;

public class TryMastermindDefender {
    ListOfSolutionsMastermind guess;
    ResultMastermindDefender result;
    TryMastermindDefender previous;

    public TryMastermindDefender(ListOfSolutionsMastermind guess, ResultMastermindDefender result, TryMastermindDefender previous) {
        this.guess = guess;
        this.result = result;
        this.previous = previous;
    }
}
