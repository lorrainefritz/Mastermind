package main.java;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MoreOrLessGame extends GameMode {
    private boolean comparaison;
    private int computerTab[]; //tab utilisé pour le mode défenseur pour générer la réponse de l'ordinateur
    private SolverHelper tabSolverHelper[]; // tableau d'objets du type SolverHelper => ces objets ont un min un max et un lastTry qui var en fonction du retour utilisateur
    private String userResponse[];//tab utilisé pour le mode défenseur pour la réponse de l'utilisateur
    private String goodResponseComparaisonTab[]; // tab de comparaison contenant autant de = que la taille de la combinaison  dans le mode défenseur
    private int computerTabLength; // var qui contient la taille du tableau computerTab

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


    public boolean defenderModeComparaisonManager() {
        for (int i = 0; i < computerTabLength; i++) { // comparaison pour le mode défenseur
            if (!goodResponseComparaisonTab[i].equals(userResponse[i])) {
                comparaison = false;
                setComparaison(false);
                return comparaison;
            }
        }
        setComparaison(true);
        return true;
    }

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
        //INTRO
        combinationLengthGestion();
        numberOfTriesGestion();
        computerTabLength = getTabLength();
        computerTab = new int[computerTabLength];
        goodResponseComparaisonTab = new String[computerTabLength];
        tabSolverHelper = new SolverHelper[computerTabLength];

        for (int i = 0; i < computerTabLength; i++) {
            tabSolverHelper[i] = new SolverHelper();
            goodResponseComparaisonTab[i] = "=";// on crée un tableau qui ne contiendra que des = et qui aura la même taille que le tabSolverHelper[], cela pour pouvoir le comparer dans la méthode defenderModeComparaisonManager()
        }
        int j = 0;
        while (j < getNumberOfTries()) {
            for (int i = 0; i < computerTabLength; i++) {
                computerTab[i] = tabSolverHelper[i].guessNumber();
            }
            System.out.println("Voilà ma proposition");
            for (int i = 0; i < computerTabLength; i++) {
                System.out.print(computerTab[i]);
            }
            System.out.println("\nVos indices svp (+ - ou =)");
            userTips();
            for (int i = 0; i < computerTabLength; i++) {
                tabSolverHelper[i].analyse(userResponse[i]);
            }
            if (defenderModeComparaisonManager() == true) break;
            j++;
        }


        return null;
    }

    @Override
    public GameMode duel() {
        combinationLengthGestion();
        numberOfTriesGestion();

        randomGestion();
        computerTabLength = getTabLength();
        computerTab = new int[computerTabLength];
        //----------------------------------------


        System.out.println("+-");
        return null;
    }
}