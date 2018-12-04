package com.Mastermind;

import java.util.ArrayList;
import java.util.Scanner;

public class MastermindGame extends GameMode {
    private int number;


    public MastermindGame() {
        super(0, 0);
    }

    @Override
    public GameMode challenger() {
        System.out.println("challenger Mastermind");
        numberOfTriesGestion();
        randomGestion();
        int numbOfTries = getNumberOfTries();
        int j = 0;
        while (j < numbOfTries) {
            userCombination();
            tipsGestion();
            comparaison();
            if (isComparaison() == true) break;
            j++;
        }
        return null;
    }


    @Override
    public void tipsGestion() {
        int numberOfWritePlaced = 0;
        int numberOfPresentNumbers = 0;
        ArrayList<Integer> numbersAlreadyCount = new ArrayList<>();
        ArrayList<Integer> indexOfWritePlaced = new ArrayList<>();
        for (int i = 0; i < getTabLength(); i++) {

            if (tabUser[i] == tab[i]) {
                numberOfWritePlaced++;
                indexOfWritePlaced.add(i);

            }
        }
        for (int j = 0; j < getTabLength(); j++) {

            for (int k = 0; k < getTabLength(); k++) {

                if (!indexOfWritePlaced.contains(j) && !indexOfWritePlaced.contains(k) && tab[j] == tabUser[k]) {
                    number = tabUser[k];
                    while (!numbersAlreadyCount.contains(number)) {
                        numbersAlreadyCount.add(number);
                        numberOfPresentNumbers++;
                    }

                    break;
                }
            }
        }

        if (numberOfWritePlaced == getTabLength()) {
            setComparaison(true);
        } else {
            setComparaison(false);
        }
        System.out.println(numberOfWritePlaced + " nombre bien placés ");
        System.out.println(numberOfPresentNumbers + " nombres présents ");
    }

    @Override
    public GameMode defender() {
        System.out.println("défenseur Mastermind");
        combinationLengthGestion();
        userCombination();
        String ret = "";

        for (int i = 0; i < getTabLength(); i++) {
            while (tab[i] != tabUser[i]) {

                tab[i] += 1;
            }
            ret += tab[i];
        }


        System.out.println("La combinaison est : " + ret);
        return null;
    }

    @Override
    public GameMode duel() {
        System.out.println("duel Mastermind");
        return null;
    }
}
