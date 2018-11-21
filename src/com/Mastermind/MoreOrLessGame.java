package com.Mastermind;

public class MoreOrLessGame extends GameMode {
    boolean compa;
    private int compteur =0;

    public MoreOrLessGame() {
        super(0, 0);
    }

    @Override
    public boolean comparaison() {
        super.comparaison();

        return false;
    }

    @Override
    public void tipsGestion() {
        for (int i = 0; i < getTabLength(); i++) {
            if (getElementFromTabUserAt(i) != getElementFromTabAt(i)) {
                if (getElementFromTabUserAt(i) < getElementFromTabAt(i)) {
                    System.out.print("+");
                    compa=false;

                } else {
                    System.out.print("-");
                    compa=false;

                }
            } else {
                System.out.print("=");
                compa=true;
            }
        }

    }

    @Override
    public GameMode challenger() {
        numberOfTriesGestion();
        randomGestion();
        for (int j=0; j < getNumberOfTries(); j++ ) {
            while (isComparaison() == false) {
                userCombination();
                tipsGestion();
                if (compa==true){
                    comparaison();
                }
                if (j==getNumberOfTries()) break;
                j++;
            }

        }

        return null;
    }

    @Override
    public GameMode defender() {
        System.out.println("défenseur +-");
        return null;
    }

    @Override
    public GameMode duel() {
        System.out.println("+-");
        return null;
    }
}
