package com.Mastermind;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManager {
    private GameMode game;
    private int chooseGame;
    int chooseMode;


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

    private void displayAvailableGames() {
        System.out.println("Veuillez choisir votre jeu");
        System.out.println("1 - Plus ou Moins");
        System.out.println("2 - Mastermind");
    }

    /**
     * Display a selected Game
     */

    private GameMode chooseGame(int nbOfGame) {
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

    private void displayAvailableModes() {
        System.out.println("Veuillez choisir votre mode de jeu");
        System.out.println("1 - Challenger");
        System.out.println("2 - Défenseur");
        System.out.println("3 - Duel");
    }

    /**
     * Display selected Game Mode
     */

    private GameMode chooseMode(int nbOfMode) {
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

    public void runMode() {
        Scanner scanner = new Scanner(System.in);
        chooseMode = scanner.nextInt();
        try {
            do {
                if (chooseMode == 1 || chooseMode == 2 || chooseMode == 3) {
                    System.out.println("Ok2");
                    chooseMode(chooseMode);
                }
            } while (chooseMode != 1 && chooseMode != 2 && chooseMode != 3);
        } catch (NullPointerException exception) {
            System.out.println("Merci de choisir 1 2 ou 3");
            runMode();
        } catch (InputMismatchException exception) {
            System.out.println("Merci de choisir 1 2 ou 3");
            runMode();
        }
    }


    public void runGame() {
        Scanner scanner = new Scanner(System.in);

        //INTRO
        displayIntroduction();

        try {
            do {
                displayAvailableGames();
                chooseGame = scanner.nextInt();

                if (chooseGame == 1 || chooseGame == 2) {
                    System.out.println("Ok1");
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


    }

}
