package main.java;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
        int numberOfPresentNumbers = 0;
        for (ListOfSolutionsMastermind listOfSolutionsMastermind = this; listOfSolutionsMastermind != null; listOfSolutionsMastermind = listOfSolutionsMastermind.previousValue) {
            for (ListOfSolutionsMastermind listOfSol = guessListOfSolutions; listOfSol != null; listOfSol = listOfSol.previousValue) {
                if (listOfSolutionsMastermind.equalsFirst(listOfSol)) {
                    numberOfPresentNumbers++;
                }
            }
        }
        return numberOfPresentNumbers;
    }

    int writePlacedList(ListOfSolutionsMastermind guessListOfSolutions) {//Liste d'éléments bien placés
        int numberOfWritePLaced = 0;
        for (ListOfSolutionsMastermind listOfSolutionsMastermind = this, guessListOfSol = guessListOfSolutions; listOfSolutionsMastermind != null && guessListOfSol != null;
             listOfSolutionsMastermind = listOfSolutionsMastermind.previousValue, guessListOfSol = guessListOfSol.previousValue) {
            if (listOfSolutionsMastermind.equalsFirst(guessListOfSol)) {
                numberOfWritePLaced++;
            }
        }
        return numberOfWritePLaced;
    }

    ResultMastermindDefender evaluate(ListOfSolutionsMastermind guessListOfSolutions) { // méthode qui retourne le nb de bien placés/ présents en fin de process
        int writePlacedNumbers = writePlacedList(guessListOfSolutions);
        int presentNumbers = presentNumbersList(guessListOfSolutions) - writePlacedNumbers;
        return new ResultMastermindDefender(writePlacedNumbers, presentNumbers);
    }


    //!!!!!!!!!!!!!! à remanier VALEURS EN DUR
    ResultMastermindDefender userTipsMastermindDefender(ListOfSolutionsMastermind guessListOfSolutions) {
         System.out.println("Voilà ma proposition : "+guessListOfSolutions);
        Scanner scanner = new Scanner(System.in);
        int writePlacedNumbers = 0;
        int presentNumbers = 0;

        try {
            // CHIFFRES BIEN PLACES
            System.out.println("Bien placés : ");
             writePlacedNumbers = scanner.nextInt();
            if (writePlacedNumbers < 0 || writePlacedNumbers > 10) { //valeur en dur!!! => min et max
                throw new InputMismatchException();
            }

            // CHIFFRES PRESENTS
            System.out.println("Présents : ");
             presentNumbers = scanner.nextInt();
            if (presentNumbers < 0 || presentNumbers > 10) { //valeur en dur!!! => min et max
                throw new InputMismatchException();
            }


        } catch (InputMismatchException e) {
            System.out.println(" Merci de rentrer un chiffre entre 0 et 10");
            userTipsMastermindDefender(guessListOfSolutions);
        } catch (NullPointerException e) {
            System.out.println(" Merci de rentrer un chiffre entre 0 et 10");
            userTipsMastermindDefender(guessListOfSolutions);
        }
        return new ResultMastermindDefender(writePlacedNumbers, presentNumbers);

    }



}
