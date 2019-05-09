package main.java;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class GameManager {
    private GameMode game;
    private int chooseMode; // contient le choix du mode
    private boolean continueOrQuit;
    private final static Logger logger = Logger.getLogger(GameManager.class.getName());

    public GameManager() {
    }

    private void displayIntroduction() {
        logger.info("********************************************************************************************");
        logger.info("                               Bienvenue sur le Mastermind ");
        logger.info("********************************************************************************************");
    }

    private void displayAvailableGames() {// méthode qui affiche les différents types de jeux disponibles
        logger.info("Choisis ton jeu");
        logger.info("1 - Plus ou Moins");
        logger.info("2 - Mastermind");

    }

    private void chooseGame(int nbOfGame) { // retour sur le type de jeu qui a été choisi
        switch (nbOfGame) {
            case 1:
                logger.info("Tu as choisi le jeu du Plus ou moins");
                game = new MoreOrLessGame();
                return;
            case 2:
                logger.info("Tu as choisi le jeu du Mastermind");
                game = new MastermindGame();
                return;
            default:
                logger.warn("hey! Mais ce jeu n'a pas encore été programmé ;) merci de saisir 1 ou 2... ");
        }
    }

    private void displayAvailableModes() { // affiche les différents modes de jeux disponibles
        logger.info("Choisis ton mode de jeu");
        logger.info("1 - Challenger");
        logger.info("2 - Défenseur");
        logger.info("3 - Duel");


    }

    private void chooseMode(int nbOfMode) { // retour sur le mode de jeu qui a été choisi
        switch (nbOfMode) {
            case 1:
                logger.info("Tu as choisi le mode Challenger");
                game.challenger();
                return;

            case 2:
                logger.info("Tu as choisi le mode Défenseur");
                game.defender();
                return;
            case 3:
                logger.info("Tu as choisi le mode Duel");
                game.duel();
                return;
            default:
                logger.warn("hey! Mais ce mode de jeu n'a pas encore été programmé ;) merci de saisir 1  2 ou 3... ");
        }
    }

    private void runMode() {// méthode qui permet de choisir le mode de jeu
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
        } catch (NullPointerException | InputMismatchException exception) {
            logger.warn("Merci de choisir 1 2 ou 3");
            runMode();
        }
    }

    private void displayEnding() { // méthode qui affiche une phrase de fin
        if (chooseMode == 1) {
            if (game.isComparaison() == true) {
                logger.info("\nBravo tu as gagné! ☺ ♫");
            } else {
                logger.info("\nPas de chance");
            }
        } else if (chooseMode == 2) {
            if (game.isComparaison() == true) {
                logger.info("\n\\o/ youpi j'ai trouvé ☺ ♫");
            } else if (game.isCheating() == true) {
                logger.info("\nTricher c'est mal ! ");
            } else {
                logger.info("\nPas de chance je ferais mieux la prochaine fois!");
            }
        } else if (chooseMode == 3) {
            if (game.isUserSucces() == true) {
                logger.info("\nBravo tu as gagné! ☺ ♫");
            } else if (game.isComputerSucess() == true) {
                logger.info("\n\\o/ youpi j'ai trouvé ☺ ♫");
            } else if (game.isCheating() == true) {
                logger.info("\nTricher c'est mal ! ");
            } else {
                logger.info("Match nul ☺ ");
            }
        }
    }

    private void continueOrQuit() { // méthode qui gère le continue ou quitter de fin
        logger.info("Veux-tu continuer? 1 : continuer 2 : quitter ");
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
        } catch (InputMismatchException | NullPointerException e) {
            logger.warn("Merci de rentrer 1 ou 2");
            continueOrQuit();
        }
    }


    void runGame() { // méthode qui va gérer le déroulement du programme final
        Scanner scanner = new Scanner(System.in);
        do {

            //INTRO
            displayIntroduction();

            try {
                // contient le choix du jeu
                int chooseGame;
                do {
                    displayAvailableGames();
                    chooseGame = scanner.nextInt();

                    if (chooseGame == 1 || chooseGame == 2) {
                        chooseGame(chooseGame);
                        displayAvailableModes();
                        runMode();
                    }

                } while (chooseGame != 1 && chooseGame != 2);
            } catch (NullPointerException | InputMismatchException e) {
                logger.warn("Merci de rentrer 1 ou 2");
                runGame();
            }
            //END
            displayEnding();
            continueOrQuit();
        } while (continueOrQuit == false);

    }

}
