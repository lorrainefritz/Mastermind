package main.java;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MoreOrLessGame extends GameMode {
    private boolean compa;
    private int compteur = 0;
    private SolverHelper computerTab[]; //tab utilisé pour le mode défenseur pour générer la réponse de l'ordinateur
    private String userResponse[];//tab utilisé pour le mode défenseur pour la réponse de l'utilisateur
    private ArrayList<Integer> indexOfWriteP; // tab pour garder l'indice des éléments bien placés dans le mode défenseur
    private int writePlaced[]; // tab pour garder les éléments bien placés dans le mode défenseur
    private int computerTabLength;

    public MoreOrLessGame() {
        super(0, 0);
    }


    @Override
    public void tipsGestion() { // gestion des indices pour le mode challenger
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


    }

    @Override
    public GameMode challenger() { // mode de jeu challenger
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


    private int mean() {// méthode qui calcule une moyenne des valeurs
        int number = 2;
        int middle = getRandomMax() / number;// recupère la valeur max du random et divise par 2 => générant la moyenne
        return middle;
    }


    /*private void firstTurnComputerResponseGeneration() { // gère la première réponse de l'ordinateur pour le mode défenseur
        //tour 1
        for (int i = 0; i < computerTabLength; i++) {
            computerTab[i] = mean();

        }
    }

    private void computerResponseGeneration() { // gère les réponses de l'ordinateur pour le mode défenseur

        // tour2
        indexOfWriteP = new ArrayList<>();
        for (int i = 0; i < computerTabLength; i++) {
            if (userResponse[i].equals("=")) { // si la réponse de l'utili comporte un =
                writePlaced[i] = computerTab[i]; // on stocke dans un tableau de réponse
                indexOfWriteP.add(i); // on ajoute l'index à un tableau des index

            }
        }
        int number = 1;
        for (int i = 0; i < computerTabLength; i++) {
            if (!indexOfWriteP.contains(i)) {// on rentre seulement pour les
                //indices qui ne sont pas dans l'index des bien placés
                if (userResponse[i].equals("+") && computerTab[i] < (getRandomMax() - 1)) { // si la réponse de l'utili comporte un + ET est < au max 9
                    computerTab[i] = computerTab[i] + number;
                } else if (userResponse[i].equals("-") && computerTab[i] > getRandomMin()) { // si la réponse de l'utili comporte un - ET est > au min 0
                    computerTab[i] = computerTab[i] - number;
                }

            }
        }
    }
*/
/*    public boolean compa() {
        for (int i = 0; i < computerTabLength; i++) {
            if (writePlaced[i] != computerTab[i]) {
                compa = false;
                setComparaison(false);
                return compa;
            }
        }
        setComparaison(true);
        return true;
    }*/

    public void userTips() { // recupération gestion des tips de l'utilisateur
        Scanner sc = new Scanner(System.in);
        userResponse = new String[getTabLength()];
        try {
            String response = sc.nextLine();
            if (response.length() != userResponse.length) {
                throw new InputMismatchException();
            }
            for (int i = 0; i < userResponse.length; i++) {
                userResponse[i] = new String(String.valueOf(response.charAt(i)));
                if (!response.contains("=") && !response.contains("+") && !response.contains("-")) {// filtrage des tips utilisateur
                    throw new InputMismatchException();
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("Merci de rentrer = + ou - ");
            userTips();
        } catch (NullPointerException e) {
            System.out.println("Merci de rentrer = + ou - ");
            userTips();
        } catch (NumberFormatException e) {
            System.out.println("Merci de rentrer = + ou -");
            userTips();
        }

    }

    @Override
    public GameMode defender() {
        /*System.out.println("défenseur +-");*/
        //INTRO
        combinationLengthGestion();
        numberOfTriesGestion();
        computerTabLength = getTabLength();
        computerTab = new SolverHelper[computerTabLength];
        writePlaced = new int[computerTabLength];

        for (int i = 0; i < writePlaced.length; i++) { // solut° pour la comparaison dans compa, qui sinon va avoir tendance à ne pas marcher pour un tableau
            // où computerTab ne serait remplit que de 0...
            int num = -9;
            writePlaced[i] = num;
        }

       /* firstTurnComputerResponseGeneration();*/
       /* compa();*/
        int j = 0;
        while (j < getNumberOfTries()) {
            System.out.println("Voilà ma proposition");
            for (int i = 0; i < computerTabLength; i++) {
                System.out.print(computerTab[i]);
            }
            System.out.println("\nVos indices svp (+ - ou =)");
            userTips();
            /*computerResponseGeneration();*/
           /* if (compa() == true) break;*/
            j++;
        }


        return null;
    }

    @Override
    public GameMode duel() {
        System.out.println("+-");
        return null;
    }
}
