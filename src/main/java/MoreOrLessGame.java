package main.java;

public class MoreOrLessGame extends GameMode {
    boolean compa;
    private int compteur = 0;

    public MoreOrLessGame() {
        super(0, 0);
    }


    @Override
    public void tipsGestion() {
        String ret = "";
        for (int i = 0; i < getTabLength(); i++) {
            if (tab[i] == tabUser[i]) {
                ret += "=";
                setComparaison(true);
            } else if (tabUser[i] > tab[i]) {
                ret += "-";
                setComparaison(false);
            } else {
                ret += "+";
                setComparaison(false);
            }
        }
        System.out.print(ret);
        /*if (ret.contains("-") && ret.contains("+")){
            compa=false;
        } else {
            compa= true;
        }*/

    }

    @Override
    public GameMode challenger() {
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
