package com.Mastermind;

import java.util.Scanner;

public class MastermindGame extends GameMode {


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
        for (int i = 0; i < getTabLength(); i++) {
            if (tabUser[i] == tab[i]) {
                numberOfWritePlaced++;
            }
        }

        for (int j = 0; j < getTabLength(); j++) {
            for (int k = 0; k < getTabLength(); k++)
                if (tab[j] == tabUser[k]) {
                    numberOfPresentNumbers++;
                    break;
                }
        }

        numberOfPresentNumbers -= numberOfWritePlaced;


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
            ret+=tab[i];
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
