package main.java;

public class SolverHelperMastermind {
    private int max;
    private int min;
    private int position;
    private static int createdObjectsCounter;
    private int tryNumber;


    public SolverHelperMastermind() {
        max = 10;
        min = 0;
        position = createdObjectsCounter;
        createdObjectsCounter++;

    }

    public int guessNumber() { // méthode qui permet de retourner une moyenne d'un max et min qui varient
        tryNumber = min;
        return tryNumber;
    }

    public void analyse(int[] tabWritePlaced, int[] tabPresentNumbers, int roundCounter) {
       /* if (tabWritePlaced[roundCounter-1]<tabWritePlaced[roundCounter]){ // S'il y a un élément bien placé en plus par rapport au tour précédent
        }*/

        if (((tabWritePlaced[roundCounter - 1] + tabPresentNumbers[roundCounter - 1]) == (tabWritePlaced[roundCounter] + tabPresentNumbers[roundCounter]))) {
            tryNumber=min++ ;
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTryNumber() {
        return tryNumber;
    }

    public void setTryNumber(int tryNumber) {
        this.tryNumber = tryNumber;
    }
}



