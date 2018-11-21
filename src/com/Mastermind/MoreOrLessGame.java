package com.Mastermind;

public class MoreOrLessGame extends GameMode {

    public MoreOrLessGame() {
        super(0, 0);
    }

/*    @Override
    public void comparaison() {
        super.comparaison();

    }*/

    @Override
    public void tipsGestion() {
        for (int i =0; i<getTabLength();i++) {
            if (getElementFromTabUserAt(i) != getElementFromTab(i)){
                if (getElementFromTabUserAt(i)<getElementFromTab(i)){
                    System.out.print("+");
                } else {
                    System.out.print("-");
                }
            }
            else {
                System.out.print("=");
            }
        }
        super.tipsGestion();
    }

    @Override
    public GameMode challenger() {
        numberOfTriesGestion();
        randomGestion();
       for (int i = 0; i< getNumberOfTries(); i++ ){
           userCombination();
               tipsGestion();
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
