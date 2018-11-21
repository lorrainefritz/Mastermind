package com.Mastermind;

public class Main {
    public static void main(String[] args) {
        GameManager gamu = new GameManager();
        /*MoreOrLessGame moreOrLessGame = new MoreOrLessGame();
        moreOrLessGame.setElementFromTabUserAt(1,66); // ne fonctionne que si tableau est initializé !
        System.out.println(moreOrLessGame.getElementFromTabUserAt(1));*/

        gamu.runGame();

    }
}
