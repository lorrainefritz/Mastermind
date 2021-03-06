package main.java;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public abstract class GameMode {
    private int numberOfTries; // contient le nombre d'essais
    int combinationLength; // contient la taille de combinaison
    int[] tab;  // tableau contenaire CAD qui permet la recupération de certaines données
    int[] tabUser;// tableau de combinaison utilisateur
    private int tabLength;  // taille de combinaison
    private boolean comparaison; // utilisé pour l'affichage de la phrase de fin
    int difficulty; // contient la difficulté/ le max de la combinaison
    private int[] secretCombinationOfRandom;
    private boolean userSucces;
    private boolean computerSucess;
    boolean devMode;
    private int[] solution;//tab utilisé pour le mode défenseur pour recevoir la solution
    private boolean isCheating;// en cas de tricherie
    private final static Logger logger = Logger.getLogger(GameMode.class.getName());


    public GameMode(GameProperties gameProperties) {
        GameGetPropertyValues gpv = new GameGetPropertyValues();
        try {
            gpv.getPropValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.numberOfTries = gpv.getNumberOfTries();
        this.combinationLength = gpv.getGameLength();
        this.difficulty = gpv.getDifficulty();
        this.devMode = gpv.getDevMode().equals("1");
    }

    public GameMode(int i, int i1) {
    }

    public GameMode() {

    } // constructeur vide pour le MastermindDefender.....


    public void challenger() {

    }

    public void duel() {

    }

    public void defender() {

    }

    public void combinationAndTipsGestion() {// méthode qui permet de condenser un groupe de méthode pour dupliquer un peu moins de code dans le mode duel
        userCombination(); // gère la combinaison utilisateur
        tipsGestion(); // gère les indices
        comparaison(); // comparaison tab utilisateur versus tab combinaison secrète
    }

    void combinationLengthGestion() { // méthode qui gère la taille de la combinaison
        GameProperties gp = new GameProperties();
        try {

            tabLength = gp.getGameLength();
            if (tabLength >= 4 && tabLength <= 10) {
                tab = new int[tabLength];
            } else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException | NullPointerException e) {
            logger.warn("Merci de rentrer un chiffre entre 4 et 10 svp ");
            combinationLengthGestion();
        } catch (NegativeArraySizeException e) {
            logger.warn("Merci de rentrer un chiffre entre 4 et 10 svp ");

        }
    }


    void numberOfTriesGestion() { // méthode qui gère le nombre d'essais
        GameProperties gp = new GameProperties();
        try {
            numberOfTries = gp.getNumbersOfTries();
            if (numberOfTries < 1 || numberOfTries > 10000) {
                throw new InputMismatchException();
            }


        } catch (InputMismatchException e) {
            logger.warn("Merci de rentrer un chiffre entre 1 et 10000 ");
            numberOfTriesGestion();
        } catch (NullPointerException e) {
            logger.warn("Merci de rentrer un chiffre ");
            numberOfTriesGestion();
        }
    }


    void devMode() { // méthode qui gère le mode Dev => CAD Qui affiche la combinaison générée par l'ordinateur
        GameProperties gp = new GameProperties();
        try {
            devMode = gp.isDevMode();
            if (devMode == true) {
                logger.info("La combinaison secrète est : ");
                for (int i = 0; i < getTabLength(); i++) {
                    System.out.print(secretCombinationOfRandom[i] + ", ");
                }

            }

        } catch (InputMismatchException e) {
            logger.warn("Merci de rentrer un chiffre valide");
        } catch (NullPointerException e) {
            logger.warn("Merci de rentrer un chiffre");
        }
    }

    public void tipsGestion() {

    }

    public abstract int randomCombination();

    private void userCombination() { // méthode qui permet dans le mode challenger de recupérer la proposition de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        try {
            logger.info("\nFais ta proposition");
            tabUser = new int[tabLength];
            String response = scanner.nextLine();
            if (response.length() != tabLength) {
                throw new InputMismatchException();
            }
            for (int i = 0; i < response.length(); i++) {
                tabUser[i] = Integer.parseInt(String.valueOf(response.charAt(i)));
            }

        } catch (InputMismatchException | NullPointerException e) {
            logger.warn("Merci de rentrer un chiffre entre 0 et 9 svp ");
            userCombination();
        } catch (NumberFormatException e) {
            logger.warn("Merci de rentrer un nombre");
            userCombination();
        }
    }

    void secretGestion() { // méthode qui permet de demander à l'utilisateur sa combinaison secrète
        logger.info("Le secret");
        Scanner scan = new Scanner(System.in);
        try {
            String sol = scan.nextLine();
            if (sol.length() != getTabLength()) {
                throw new InputMismatchException();
            }
            solution = new int[getTabLength()];
            for (int i = 0; i < sol.length(); i++) {
                solution[i] = Integer.parseInt(String.valueOf(sol.charAt(i)));
            }

        } catch (InputMismatchException e) {
            logger.warn("Merci de rentrer une solution de taille équivalente à la longueur de combinaison rentrée précédement : "
                    + getTabLength());
            secretGestion();
        }
    }

    private boolean comparaison() { // compare pour le mode challenger le tableau réponse de l'utilisateur avec le tableau de la combinaison secrète

        for (int i = 0; i < tabUser.length; i++) {
            if (tab[i] != tabUser[i]) {
                comparaison = false;
                return comparaison;

            }

        }
        return true;
    }

    void randomGestion() { // génère un tableau de random avec la taille de combinaison souhaitée par l'utili
        secretCombinationOfRandom = new int[tabLength];
        for (int i = 0; i < getTabLength(); i++) {
            tab[i] = randomCombination();
            secretCombinationOfRandom[i] = tab[i];// création d'une var tierce pour stocker la combinaison et s'en resservir au besoin
        }
    }

    int getTabLength() {
        return tabLength;
    }


    boolean isComparaison() {
        return comparaison;
    }

    void setComparaison(boolean comparaison) {
        this.comparaison = comparaison;
    }

    int getNumberOfTries() {
        return numberOfTries;
    }


    boolean isUserSucces() {
        return userSucces;
    }

    void setUserSucces(boolean userSucces) {
        this.userSucces = userSucces;
    }

    boolean isComputerSucess() {
        return computerSucess;
    }

    void setComputerSucess(boolean computerSucess) {
        this.computerSucess = computerSucess;
    }

    int[] getSolution() {
        return solution;
    }

    boolean isCheating() {
        return isCheating;
    }

    void setCheating(boolean cheating) {
        isCheating = cheating;
    }
}



