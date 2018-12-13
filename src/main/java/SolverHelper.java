package main.java;

public class SolverHelper {
    private int max;
    private int min;
    private int lastTry;

    public SolverHelper() {
        max = 9;
        min = 0;
    }

    public int guessNumber() {
        lastTry = Math.round((min + max) / 2);
        return lastTry;
    }
    public void analyse (String s){
        if(s.equals("+")){
            min = lastTry;
        } else if(s.equals("-")){
            max= lastTry;
        } else {
            min = lastTry;
            max= lastTry;
        }
    }
}
