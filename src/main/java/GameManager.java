package main.java;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class GameManager {
    private GameMode game;
    private int chooseGame;
    int chooseMode;
    boolean continueOrQuit;
    private final static Logger logger = Logger.getLogger(GameManager.class.getName());


    public GameManager() {

    }

    /**
     * Display Intro
     */

    private void displayIntroduction() {
        logger.info("********************************************************************************************");
        logger.info("                               Bienvenue sur le Mastermind ");
        logger.info("********************************************************************************************");

       /* System.out.println("********************************************************************************************");
        System.out.println("                               Bienvenue sur le Mastermind ");
        System.out.println("********************************************************************************************");*/

    }

    /**
     * Display all available Games
     */

    private void displayAvailableGames() {// méthode qui affiche les différents types de jeux disponibles
        logger.info("Veuillez choisir votre jeu");
        logger.info("1 - Plus ou Moins");
        logger.info("2 - Mastermind");
/*
        System.out.println("Veuillez choisir votre jeu");
        System.out.println("1 - Plus ou Moins");
        System.out.println("2 - Mastermind");*/
    }

    /**
     * Display a selected Game
     */

    private GameMode chooseGame(int nbOfGame) { // retour sur le type de jeu choisit
        switch (nbOfGame) {
            case 1:
                logger.info("Vous avez choisi le jeu du Plus ou moins");
                /*System.out.println("Vous avez choisi le jeu du Plus ou moins");*/
                game = new MoreOrLessGame();
                return game;
            case 2:
                logger.info("Vous avez choisi le jeu du Mastermind");
                /*System.out.println("Vous avez choisi le jeu du Mastermind");*/
                game = new MastermindGame();
                return game;
            default:
                logger.warn("hey! Mais ce jeu n'a pas encore été programmé ;) merci de saisir 1 ou 2... ");
                return null;
        }
    }

    /**
     * Display all available Modes
     */

    private void displayAvailableModes() { // affiche les différents modes de jeux disponibles
        logger.info("Veuillez choisir votre mode de jeu");
        logger.info("1 - Challenger");
        logger.info("2 - Défenseur");
        logger.info("3 - Duel");

        /*System.out.println("Veuillez choisir votre mode de jeu");
        System.out.println("1 - Challenger");
        System.out.println("2 - Défenseur");
        System.out.println("3 - Duel");
*/
    }

    /**
     * Display selected Game Mode
     */

    private GameMode chooseMode(int nbOfMode) { // retour sur le mode de jeu choisit
        switch (nbOfMode) {
            case 1:
                logger.info("Vous avez choisi le mode Challenger");
                /*System.out.println("Vous avez choisi le mode Challenger");*/
                return game.challenger();

            case 2:
                logger.info("Vous avez choisi le mode Défenseur");
                /*System.out.println("Vous avez choisi le mode Défenseur");*/
                return game.defender();
            case 3:
                logger.info("Vous avez choisi le mode Duel");
                /*System.out.println("Vous avez choisi le mode Duel");*/
                return game.duel();
            default:
                logger.warn("hey! Mais ce mode de jeu n'a pas encore été programmé ;) merci de saisir 1  2 ou 3... ");
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
            logger.warn("Merci de choisir 1 2 ou 3");
            runMode();
        } catch (InputMismatchException exception) {
            logger.warn("Merci de choisir 1 2 ou 3");
            runMode();
        }
    }

    private void displayEnding() { // méthode qui gère la phrase de fin en fonction du type de jeu et en fonction de la réussite ou non
        if (chooseMode==1) {
            if (game.isComparaison() == true) {
                logger.info("\nBravo tu as gagné! ☺ ♫");
                /*System.out.println("\nBravo tu as gagné! ☺ ♫");*/
            } else {
                logger.info("\nPas de chance");
                /*System.out.println("\nPas de chance");*/
            }
        } else if (chooseMode==2){
            if (game.isComparaison()== true){
                logger.info("\n\\o/ youpi j'ai trouvé ☺ ♫");
                /*System.out.println("\n\\o/ youpi j'ai trouvé ☺ ♫");*/
            } else {
                logger.info("\nPas de chance je ferais mieux la prochaine fois!");
                /*System.out.println("\nPas de chance je ferais mieux la prochaine fois!");*/
            }
        } else if (chooseMode==3){
            if (game.isUserSucces()==true){
                logger.info("\nBravo tu as gagné! ☺ ♫");
               /* System.out.println("\nBravo tu as gagné! ☺ ♫");*/
            } else if (game.isComputerSucess() == true){
                logger.info("\n\\o/ youpi j'ai trouvé ☺ ♫");
                /*System.out.println("\n\\o/ youpi j'ai trouvé ☺ ♫");*/
            } else {
                logger.info("Match nul ☺ ");
                /*System.out.println("Match nul ☺ ");*/
            }
        }
    }

    private void continueOrQuit() { // méthode qui gère le continue ou quitter de fin
        logger.info("Voulez vous continuer? 1 : continuer 2 : quitter ");
        /*System.out.println("Voulez vous continuer? 1 : continuer 2 : quitter ");*/
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
            logger.warn("Merci de rentrer 1 ou 2");
            continueOrQuit();
        } catch (NullPointerException e) {
            logger.warn("Merci de rentrer 1 ou 2");
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
                logger.warn("Merci de rentrer 1 ou 2");
                runGame();
            } catch (InputMismatchException e) {
                logger.warn("Merci de rentrer 1 ou 2");
                runGame();
            }
            //END
            displayEnding();
            continueOrQuit();
        } while (continueOrQuit == false);

    }

}
