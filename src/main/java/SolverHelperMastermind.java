package main.java;

import javax.xml.transform.Result;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SolverHelperMastermind {
    private int max;
    private int min;
    private int position;
    private static int createdObjectsCounter;
    private int tryNumber;
    private static int combinationLength; //<= taille rentrée par l'utili


 /*   public SolverHelperMastermind() {
        max = 10;
        min = 0;
        position = createdObjectsCounter;
        createdObjectsCounter++;

    }*/

    public int guessNumber() { // méthode qui permet de retourner une moyenne d'un max et min qui varient
        tryNumber = min;
        return tryNumber;
    }

    public void analyse(int[] tabWritePlaced, int[] tabPresentNumbers, int roundCounter) {
       /* if (tabWritePlaced[roundCounter-1]<tabWritePlaced[roundCounter]){ // S'il y a un élément bien placé en plus par rapport au tour précédent
        }*/

        if (((tabWritePlaced[roundCounter - 1] + tabPresentNumbers[roundCounter - 1]) == (tabWritePlaced[roundCounter] + tabPresentNumbers[roundCounter]))) {
            tryNumber = min++;
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

    //_____________________________________________________________________________________________________________________


   /* public SolverHelperMastermind(int length) {
        this.length = length;
    }*/


   // Tous les sous-ensembles (listes ordonnées décroissantes) de 1..2n de taille n
    static ListOfListOfSolutionsMastermind subsets(int n) {

        return subsets(0, 9, n, null, null);
    }


      /*Renvoie la concatenation de :
      1. La liste de toutes les listes
        (liste de taille card dont les éléments sont pris dans min..max)
        concaténée avec suffix.
      2.  et de k*/



    static ListOfListOfSolutionsMastermind
    subsets(int min, int max, int card, ListOfSolutionsMastermind listOfSol, ListOfListOfSolutionsMastermind listOfListOfSol) {
        if (max - min + 1 < card) {
            return listOfListOfSol;
        } else if (card == 0) {
            return new ListOfListOfSolutionsMastermind(listOfSol, listOfListOfSol);
        } else {
            listOfListOfSol = subsets(min + 1, max, card, listOfSol, listOfListOfSol);
            return subsets(min, max, card - 1, new ListOfSolutionsMastermind(min, listOfSol), listOfListOfSol);
        }
    }

    // méthode importante qui permet de filtrer les Liste etc en fonction du nombre de bien placés + présents
    static ListOfListOfSolutionsMastermind filterSubsets(ResultMastermindDefender result, ListOfSolutionsMastermind guess,
                                                         ListOfListOfSolutionsMastermind listOfListOfSol) {
        ListOfListOfSolutionsMastermind listOfListOfSolutionsMastermind = null;
        int numberOfCorrect = result.presentNumbers + result.writePlacedNumbers;
        for (; listOfListOfSol != null; listOfListOfSol = listOfListOfSol.previousValue) {
            if (numberOfCorrect == listOfListOfSol.listValue.presentNumbersList(guess)) {
                listOfListOfSolutionsMastermind = new ListOfListOfSolutionsMastermind(listOfListOfSol.listValue, listOfListOfSolutionsMastermind);//<============== ICI
            }
        }
        return listOfListOfSolutionsMastermind;
    }

    // échange les éléments une fois que l'on a trouvé la bonne combinaison
    static ListOfListOfSolutionsMastermind permut(int n, ListOfSolutionsMastermind differentNumbers, TryMastermindDefender tries) {
        int[] tab = new int[n];
        int k = 0;
        for (ListOfSolutionsMastermind listOfSolutionsMastermind = differentNumbers; listOfSolutionsMastermind != null;
             listOfSolutionsMastermind = listOfSolutionsMastermind.previousValue) {
            tab[k++] = listOfSolutionsMastermind.listValue;
        }
        return permut(tries, 0, n, tab, null, null);
    }

    private static boolean compatible(ListOfSolutionsMastermind listOfSolutionsMastermind, TryMastermindDefender tries) {
        for (; tries != null; tries = tries.previous) {
            if (listOfSolutionsMastermind.evaluate(tries.guess).equals(tries.result))
                return false;
        }
        return true;
    }

    // échange les éléments une fois que l'on a trouvé la bonne combinaison
    private static ListOfListOfSolutionsMastermind permut(TryMastermindDefender tries, int i, int n, int[] tab, ListOfSolutionsMastermind listOfSolutionsMastermind, ListOfListOfSolutionsMastermind listOfListOfSolutionsMastermind) {
        if (i >= n) {
            if (compatible(listOfSolutionsMastermind, tries)) {
                return new ListOfListOfSolutionsMastermind(listOfSolutionsMastermind, listOfListOfSolutionsMastermind);
            } else {
                return listOfListOfSolutionsMastermind;
            }
        } else {
            int c = tab[i];
            for (int j = i; j < n; j++) {
                int x = tab[j];
                tab[j] = c;
                listOfListOfSolutionsMastermind = permut(tries, i + 1, n, tab, new ListOfSolutionsMastermind(x, listOfSolutionsMastermind),
                        listOfListOfSolutionsMastermind);
                tab[j] = x;
            }
            return listOfListOfSolutionsMastermind;
        }
    }


    // filtre la réponse
    static ListOfListOfSolutionsMastermind filter(ResultMastermindDefender res, ListOfSolutionsMastermind guess, ListOfListOfSolutionsMastermind listOfListOfSolutionsMastermind) {
        ListOfListOfSolutionsMastermind r = null;
        for (; listOfListOfSolutionsMastermind != null; listOfListOfSolutionsMastermind = listOfListOfSolutionsMastermind.previousValue) {
            if (res.equals(listOfListOfSolutionsMastermind.listValue.evaluate(guess))) {
                r = new ListOfListOfSolutionsMastermind(listOfListOfSolutionsMastermind.listValue, r);
            }
        }
        return r;
    }







    // méthode pour trouver la réponse
    static ListOfSolutionsMastermind find(MastermindGame mastermindGame) {
        combinationLength = mastermindGame.getComputerTabLength();//<++++++ rajout
        TryMastermindDefender tries = null;
        ListOfListOfSolutionsMastermind listOfListOfSol = subsets(combinationLength);
       listOfListOfSol.printPossibilities(listOfListOfSol);// <================================================ pour écrire les possibilités


        while (listOfListOfSol.previousValue != null) {
            ListOfSolutionsMastermind guess = listOfListOfSol.listValue;
            ResultMastermindDefender result = mastermindGame.processGuess(guess);
            tries = new TryMastermindDefender(guess, result, tries);
            listOfListOfSol = filterSubsets(result, guess, listOfListOfSol);

        }

        ListOfSolutionsMastermind differentNumbers = listOfListOfSol.listValue;
        ListOfListOfSolutionsMastermind listOfListOfSolutionsMastermind = permut(combinationLength, differentNumbers, tries);//<============== pb

        while (listOfListOfSolutionsMastermind.previousValue != null) {
            ListOfSolutionsMastermind guess = listOfListOfSolutionsMastermind.listValue;
            ResultMastermindDefender resultMastermindDefender = mastermindGame.processGuess(guess);
            listOfListOfSolutionsMastermind = filter(resultMastermindDefender, guess, listOfListOfSolutionsMastermind);
        }

        return listOfListOfSol.listValue;
    }

    public static int getCombinationLength() {
        return combinationLength;
    }

    public static void setCombinationLength(int combinationLength) {
        SolverHelperMastermind.combinationLength = combinationLength;
    }




}



