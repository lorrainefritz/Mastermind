package main.java;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public abstract class GameMode {
    private int numberOfTries;
    private int combinationLength;
    private Scanner scanner;
    protected int tab[];
    protected int tabUser[];
    private int tabLength;
    private boolean comparaison;
    private int RANDOM_MAX;
    private int RANDOM_MIN;

    public GameMode(int numberOfTries, int combinationLength) {
        this.numberOfTries = numberOfTries;
        this.combinationLength = combinationLength;
        RANDOM_MAX=10;
        RANDOM_MIN=0;
    }

    public GameMode challenger() {

        return null;
    }

    public GameMode duel() {
        return null;

    }

    public GameMode defender() {
        return null;

    }

    public void combinationLengthGestion() { // gère la taille de la combinaison
        try {
            scanner = new Scanner(System.in);
            System.out.println("Entrez la taille souhaitée de combinaison :  de 4 min à 10 max");
            tabLength = scanner.nextInt();
            if (tabLength >= 4 && tabLength <= 10) {
                tab = new int[tabLength];
            } else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Merci de rentrer un chiffre entre 4 et 10 svp ");
            combinationLengthGestion();
        } catch (NullPointerException e) {
            System.out.println("Merci de rentrer un chiffre entre 4 et 10 svp ");
            combinationLengthGestion();
        } catch (NegativeArraySizeException e) {
            System.out.println("Merci de rentrer un chiffre entre 4 et 10 svp ");

        }
    }


    public void numberOfTriesGestion() { // gère le nombre d'essais
        scanner = new Scanner(System.in);
        System.out.println("Merci de rentrer le nombre voulu d'essais ");
        try {
            numberOfTries = scanner.nextInt();
            if (numberOfTries < 1 || numberOfTries > 1000) {
                throw new InputMismatchException();
            }


        } catch (InputMismatchException e) {
            System.out.println("Merci de rentrer un chiffre entre 1 et 1000 ");
            numberOfTriesGestion();
        } catch (NullPointerException e) {
            System.out.println("Merci de rentrer un chiffre ");
            numberOfTriesGestion();
        }
    }



    public void tipsGestion() {

    }

    public int randomCombination() { // génère un random entre 0 et 9
        Random random = new Random();
        return random.nextInt(RANDOM_MAX); // retourne un random dont le max est 10

    }

    public void userCombination() {
        scanner = new Scanner(System.in);
        try {
            System.out.println("\nFaites votre propoposition");
            tabUser = new int[tabLength];
            String response = scanner.nextLine();
            if (response.length() != tabLength) {
                throw new InputMismatchException();
            }
            for (int i = 0; i < response.length(); i++) {
                tabUser[i] = Integer.parseInt(new String(String.valueOf(response.charAt(i))));
            }

        } catch (InputMismatchException e) {
            System.out.println("Merci de rentrer un chiffre entre 0 et 9 svp ");
            userCombination();
        } catch (NullPointerException e) {
            System.out.println("Merci de rentrer un chiffre entre 0 et 9 svp ");
            userCombination();
        } catch (NumberFormatException e) {
            System.out.println("Merci de rentrer un nombre");
            userCombination();
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
        combinationLengthGestion();
        for (int i = 0; i < tabLength; i++) {
            tab[i] = randomCombination();
            System.out.print(tab[i] + ", ");
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

    public int getRandomMax() {
        return RANDOM_MAX;
    }

    public void setRandomMax(int randomMax) {
        this.RANDOM_MAX = randomMax;
    }

    public int getRandomMin() {
        return RANDOM_MIN;
    }

    public void setRandomMin(int randomMin) {
        this.RANDOM_MIN = randomMin;
    }
}



