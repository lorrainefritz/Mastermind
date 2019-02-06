package main.java;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class ListOfListOfSolutionsMastermind { // Classe qui permet de gérer les listes de listes solutions de l'algo du Mastermind défenseur

    ListOfSolutionsMastermind listValue;
    ListOfListOfSolutionsMastermind previousValue;// <=========== changement dans la dénomination pour quelque chose d'à priori plus logique


    // méthode pour créer un fichier txt en écriture qui contient une liste des solutions générées par l'algo
    static PrintWriter writer;
    static {
        try {
            writer = new PrintWriter("liste.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public ListOfListOfSolutionsMastermind(ListOfSolutionsMastermind listValue, ListOfListOfSolutionsMastermind previousValue) {
        this.listValue = listValue;
        this.previousValue = previousValue;


    }
    static int count = 0;

    static void printPossibilities(ListOfListOfSolutionsMastermind listOfListOfSolutionsMastermind) {
        count++;
        for (; listOfListOfSolutionsMastermind != null; listOfListOfSolutionsMastermind = listOfListOfSolutionsMastermind.previousValue) {
            writer.println(listOfListOfSolutionsMastermind.listValue);
        }
    }


}
