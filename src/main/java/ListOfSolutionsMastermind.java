package main.java;

import java.util.List;

public class ListOfSolutionsMastermind { // Classe qui permet de gérer les listes de solutions de l'algo du Mastermind défenseur
    int listValue;
    ListOfSolutionsMastermind previousValue;

     ListOfSolutionsMastermind(int listValue, ListOfSolutionsMastermind previousValue) {
        this.listValue = listValue;
        this.previousValue = previousValue;
    }
     public String toString() {
        if (previousValue != null)
            return listValue + " -> " + previousValue.toString() ;
        else
            return listValue + "" ;
    }

    boolean equalsFirst(ListOfSolutionsMastermind guess) {
        return listValue == guess.listValue;
    }

    int presentNumbersList(ListOfSolutionsMastermind guessListOfSolutions) { // Liste des éléments présents
        int i = 0;
        for (ListOfSolutionsMastermind listOfSolutionsMastermind = this; listOfSolutionsMastermind != null; listOfSolutionsMastermind = listOfSolutionsMastermind.previousValue) {
            for (ListOfSolutionsMastermind listOfSol = guessListOfSolutions; listOfSol != null; listOfSol = listOfSol.previousValue) {
                if (listOfSolutionsMastermind.equalsFirst(listOfSol)) {
                    i++;
                }
            }
        }
        return i;
    }

    int writePlacedList(ListOfSolutionsMastermind guessListOfSolutions) {//Liste d'éléments bien placés
        int i = 0;
        for (ListOfSolutionsMastermind listOfSolutionsMastermind = this, guessListOfSol = guessListOfSolutions; listOfSolutionsMastermind != null && guessListOfSol != null;
             listOfSolutionsMastermind = listOfSolutionsMastermind.previousValue, guessListOfSol = guessListOfSol.previousValue) {
            if (listOfSolutionsMastermind.equalsFirst(guessListOfSol)) {
                i++;
            }
        }
        return i;
    }

    ResultMastermindDefender evaluate(ListOfSolutionsMastermind guessListOfSolutions) { // méthode qui retourne le nb de bien placés/ présents en fin de process
        int writePlacedNumbers = writePlacedList(guessListOfSolutions);
        int presentNumbers = presentNumbersList(guessListOfSolutions) - writePlacedNumbers;
        return new ResultMastermindDefender(writePlacedNumbers, presentNumbers);
    }


}
