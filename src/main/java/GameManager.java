package main.java;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManager {
    private GameMode game;
    private int chooseGame;
    int chooseMode;
    boolean continueOrQuit;


    public GameManager() {

    }

    /**
     * Display Intro
     */

    private void displayIntroduction() {
        System.out.println("********************************************************************************************");
        System.out.println("                               Bienvenue sur le Mastermind ");
        System.out.println("********************************************************************************************");
    }

    /**
     * Display all available Games
     */

    private void displayAvailableGames() {// méthode qui affiche les différents types de jeux disponibles
        System.out.println("Veuillez choisir votre jeu");
        System.out.println("1 - Plus ou Moins");
        System.out.println("2 - Mastermind");
    }

    /**
     * Display a selected Game
     */

    private GameMode chooseGame(int nbOfGame) { // retour sur le type de jeu choisit
        switch (nbOfGame) {
            case 1:
                System.out.println("Vous avez choisi le jeu du Plus ou moins");
                game = new MoreOrLessGame();
                return game;
            case 2:
                System.out.println("Vous avez choisi le jeu du Mastermind");
                game = new MastermindGame();
                return game;
            default:
                System.out.println("hey! Mais ce jeu n'a pas encore été programmé ;) merci de saisir 1 ou 2... ");
                return null;
        }
    }

    /**
     * Display all available Modes
     */

    private void displayAvailableModes() { // affiche les différents modes de jeux disponibles
        System.out.println("Veuillez choisir votre mode de jeu");
        System.out.println("1 - Challenger");
        System.out.println("2 - Défenseur");
        System.out.println("3 - Duel");
    }

    /**
     * Display selected Game Mode
     */

    private GameMode chooseMode(int nbOfMode) { // retour sur le mode de jeu choisit
        switch (nbOfMode) {
            case 1:
                System.out.println("Vous avez choisi le mode Challenger");
                return game.challenger();

            case 2:
                System.out.println("Vous avez choisi le mode Défenseur");
                return game.defender();
            case 3:
                System.out.println("Vous avez choisi le mode Duel");
                return game.duel();
            default:
                System.out.println("hey! Mais ce mode de jeu n'a pas encore été programmé ;) merci de saisir 1  2 ou 3... ");
                return null;
        }
    }

    public void runMode() {// méthode qui permet de choisir le mode de jeu
        Scanner scanner = new Scanner(System.in);
        try {
            chooseMode = scanner.nextInt();
            if (chooseMode == 1 || chooseMode == 2 || chooseMode == 3) {
                chooseMode(chooseMode);
            } else if (chooseMode != 1 && chooseMode != 2 && chooseMode != 3) {
                throw new InputMismatchException();
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException exception) {
            System.out.println("Merci de choisir 1 2 ou 3");
            runMode();
        } catch (InputMismatchException exception) {
            System.out.println("Merci de choisir 1 2 ou 3");
            runMode();
        }
    }

    private void displayEnding() { // méthode qui gère la phrase de fin en fonction du type de jeu et en fonction de la réussite ou non
        if (chooseMode==1) {
            if (game.isComparaison() == true) {
                System.out.println("\nBravo tu as gagné! ☺ ♫");
            } else {
                System.out.println("\nPas de chance");
            }
        } else if (chooseMode==2){
            if (game.isComparaison()== true){
                System.out.println("\n\\o/ youpi j'ai trouvé ☺ ♫");
            } else {
                System.out.println("\nPas de chance je ferais mieux la prochaine fois!");
            }
        }
    }

    private void continueOrQuit() { // méthode qui gère le continue ou quitter de fin
        System.out.println("Voulez vous continuer? 1 : continuer 2 : quitter ");
        Scanner scanner = new Scanner(System.in);
        try {

            int cQ = scanner.nextInt();
            if (cQ == 1) {
                continueOrQuit = false;
            } else if (cQ == 2) {
                continueOrQuit = true;
            } else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException e) {
            System.out.println("Merci de rentrer 1 ou 2");
            continueOrQuit();
        } catch (NullPointerException e) {
            System.out.println("Merci de rentrer 1 ou 2");
            continueOrQuit();

        }
    }


    public void runGame() { // méthode qui va gérer le déroulement du programme final
        Scanner scanner = new Scanner(System.in);
        do {

            //INTRO
            displayIntroduction();

            try {
                do {
                    displayAvailableGames();
                    chooseGame = scanner.nextInt();

                    if (chooseGame == 1 || chooseGame == 2) {
                        chooseGame(chooseGame);
                        displayAvailableModes();
                        runMode();
                    }

                } while (chooseGame != 1 && chooseGame != 2);
            } catch (NullPointerException e) {
                System.out.println("Merci de choisir 1 ou 2");
                runGame();
            } catch (InputMismatchException e) {
                System.out.println("Merci de choisir 1 ou 2");
                runGame();
            }
            //END
            displayEnding();
            continueOrQuit();
        } while (continueOrQuit == false);

    }

}
