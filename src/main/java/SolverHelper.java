package main.java;

public class SolverHelper { // classe utilisée pour le mode défenseur du + ou -
    private int max;
    private int min;
    private int lastTry;
   private static int counter;// compteur qui va jouer dans la triche

    public SolverHelper() {
        max = 10;
        min = 0;
    }

    public int guessNumber() { // méthode qui permet de retourner une moyenne d'un max et min qui varient
        lastTry = Math.round((min + max) / 2);
        return lastTry;
    }

    public void analyse(String s) { // méthode qui en fonction du type de retour (+ ou -) va augmenter ou diminuer min et max
        if (s.equals("+")) {
            min = lastTry;
            if (min == lastTry && lastTry == 9) {// vérif triche
                counter++;
            }

        } else if (s.equals("-")) {
            max = lastTry;
            if (min == lastTry && lastTry == 0) { // vérif  triche
                counter++;
            }
        } else {
            min = lastTry;
            max = lastTry;

        }
    }

    public int getLastTry() {
        return lastTry;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
