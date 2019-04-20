package main.java;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public abstract class GameMode {
    private int numberOfTries;
    protected int combinationLength;
    private Scanner scanner;
    protected int tab[];
    protected int tabUser[];
    private int tabLength;
    private boolean comparaison;
    protected int difficulty;
    /* private int RANDOM_MAX =10;//<=========================================================================*/
    private int RANDOM_MIN;
    protected int secretCombinationOfRandom[];
    private boolean userSucces;
    private boolean computerSucess;
    protected boolean devMode;
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
        RANDOM_MIN = 0;
    }

    public GameMode(int i, int i1) {
    }

    ;

    public GameMode() {

    } // constructeur vide pour le MastermindDefender.....


    public GameMode challenger() {

        return null;
    }

    public GameMode duel() {
        return null;

    }

    public GameMode defender() {
        return null;

    }

    public void combinationLengthGestion() { // méthode qui gère la taille de la combinaison
        GameProperties gp = new GameProperties();
        try {

            tabLength = gp.getGameLength();
            if (tabLength >= 4 && tabLength <= 10) {
                tab = new int[tabLength];
            } else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            logger.warn("Merci de rentrer un chiffre entre 4 et 10 svp ");
            combinationLengthGestion();
        } catch (NullPointerException e) {
            logger.warn("Merci de rentrer un chiffre entre 4 et 10 svp ");
            combinationLengthGestion();
        } catch (NegativeArraySizeException e) {
            logger.warn("Merci de rentrer un chiffre entre 4 et 10 svp ");

        }
    }


    public void numberOfTriesGestion() { // méthode qui gère le nombre d'essais
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


    public void difficultyGestion() { // méthode qui gère la difficulté : CAD le nombre max
        GameProperties gp = new GameProperties();
        try {
            difficulty = gp.getDifficulty();
            if (difficulty < 1 || difficulty < 10) {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            logger.warn("Merci de rentrer un chiffre entre 1 et 10 ");
            difficultyGestion();

        } catch (NullPointerException e) {
            logger.warn("Merci de rentrer un chiffre");
            difficultyGestion();

        }

    }

    public void devMode() {
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

    public void userCombination() { // méthode qui permet dans le mode challenger de recupérer la proposition de l'utilisateur
        scanner = new Scanner(System.in);
        try {
            logger.info("\nFaites votre propoposition");
            tabUser = new int[tabLength];
            String response = scanner.nextLine();
            if (response.length() != tabLength) {
                throw new InputMismatchException();
            }
            for (int i = 0; i < response.length(); i++) {
                tabUser[i] = Integer.parseInt(new String(String.valueOf(response.charAt(i))));
            }

        } catch (InputMismatchException e) {
            logger.warn("Merci de rentrer un chiffre entre 0 et 9 svp ");
            userCombination();
        } catch (NullPointerException e) {
            logger.warn("Merci de rentrer un chiffre entre 0 et 9 svp ");
            userCombination();
        } catch (NumberFormatException e) {
            logger.warn("Merci de rentrer un nombre");
            userCombination();
        }
    }

    public void secretGestion() { // méthode qui permet de demander à l'utilisateur sa combinaison secrète
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

    public boolean comparaison() { // compare pour le mode challenger le tableau réponse de l'utilisateur avec le tableau de la combinaison secrète

        for (int i = 0; i < tabUser.length; i++) {
            if (tab[i] != tabUser[i]) {
                comparaison = false;
                return comparaison;

            }

        }
        return true;
    }

    public void randomGestion() { // génère un tableau de random avec la taille de combinaison souhaitée par l'utili
        /* combinationLengthGestion();*/
        secretCombinationOfRandom = new int[tabLength];
        for (int i = 0; i < getTabLength(); i++) {
            tab[i] = randomCombination();
            secretCombinationOfRandom[i] = tab[i];// création d'une var tierce pour stocker la combinaison et s'en resservir au besoin
        }
    }


    public int[] getTab() {
        return tab;
    }

    public void setTab(int[] tab) {
        this.tab = tab;
    }

    //recup élément précis du tableau tableau de random
    public int getElementFromTabAt(int position) {
        return tab[position];
    }

    //sett un élément précis du tableau tab de random
    public void setElementFromTabAt(int position, int value) {
        tab[position] = value;
    }


    public int[] getTabUser() {
        return tabUser;
    }

    public void setTabUser(int[] tabUser) {
        this.tabUser = tabUser;
    }

    //recup élément précis du tableau User
    public int getElementFromTabUserAt(int position) {
        return tabUser[position];
    }

    //sett un élément précis du tableau User
    public void setElementFromTabUserAt(int position, int value) {
        tabUser[position] = value;
    }


    public int getTabLength() {
        return tabLength;
    }

    public void setTabLength(int tabLength) {
        this.tabLength = tabLength;
    }

    public boolean isComparaison() {
        return comparaison;
    }

    public void setComparaison(boolean comparaison) {
        this.comparaison = comparaison;
    }

    public int getNumberOfTries() {
        return numberOfTries;
    }

    public void setNumberOfTries(int numberOfTries) {
        this.numberOfTries = numberOfTries;
    }


    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getRandomMin() {
        return RANDOM_MIN;
    }

    public void setRandomMin(int randomMin) {
        this.RANDOM_MIN = randomMin;
    }

    public int[] getSecretCombinationOfRandom() {
        return secretCombinationOfRandom;
    }

    public void setSecretCombinationOfRandom(int[] secretCombinationOfRandom) {
        this.secretCombinationOfRandom = secretCombinationOfRandom;
    }

    public boolean isUserSucces() {
        return userSucces;
    }

    public void setUserSucces(boolean userSucces) {
        this.userSucces = userSucces;
    }

    public boolean isComputerSucess() {
        return computerSucess;
    }

    public void setComputerSucess(boolean computerSucess) {
        this.computerSucess = computerSucess;
    }

    public int[] getSolution() {
        return solution;
    }

    public void setSolution(int[] solution) {
        this.solution = solution;
    }

    public boolean isCheating() {
        return isCheating;
    }

    public void setCheating(boolean cheating) {
        isCheating = cheating;
    }
}



