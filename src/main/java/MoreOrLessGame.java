package main.java;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class MoreOrLessGame extends GameMode {
    private int[] computerTab; //tab utilisé pour le mode défenseur pour générer la réponse de l'ordinateur
    private SolverHelper[] tabSolverHelper; // tableau d'objets du type SolverHelper => ces objets ont un min un max et un lastTry qui var en fonction du retour utilisateur
    private String[] userResponse;//tab utilisé pour le mode défenseur pour la réponse de l'utilisateur
    private String[] goodResponseComparaisonTab; // tab de comparaison contenant autant de = que la taille de la combinaison dans le mode défenseur
    private int computerTabLength; // var qui contient la taille du tableau computerTab


    private final static Logger logger = Logger.getLogger(MoreOrLessGame.class.getName());

    public MoreOrLessGame() {
        super(0, 0);
    }


    @Override
    public int randomCombination() {// méthode génère un random entre 0 et 9
        {
            Random random = new Random();
            return random.nextInt(10); // retourne un random dont le max est 10
        }
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
        logger.info(ret);
    }

    private void combinationAndTipsGestion() {// méthode qui permet de condenser un groupe de méthode pour dupliquer un peu moins de code dans le mode duel
        userCombination();
        tipsGestion();
        comparaison();

    }

    @Override
    public GameMode challenger() { // mode de jeu challenger
        //INTRO
        combinationLengthGestion();
        numberOfTriesGestion();
        randomGestion();
        devMode();
        int numbOfTries = getNumberOfTries();
        int j = 0;
        //DEROULEMENT DU JEU
        while (j < numbOfTries) {
            combinationAndTipsGestion();
            if (isComparaison() == true) break;
            j++;
        }
        return null;
    }


    public boolean defenderModeComparaisonManager() {// comparaison entre la réponse et du retour utilisateur pour le mode défenseur
        for (int i = 0; i < computerTabLength; i++) {
            if (!goodResponseComparaisonTab[i].equals(userResponse[i])) {
                boolean comparaison = false;
                setComparaison(false);
                return comparaison;
            }
        }
        setComparaison(true);
        return true;
    }

    public void userTips() { // méthode qui gère les indices de l'utilisateur
        Scanner sc = new Scanner(System.in);
        userResponse = new String[getTabLength()];
        try {
            String response = sc.nextLine();
            if (response.length() != userResponse.length) {
                throw new InputMismatchException();
            }
            for (int i = 0; i < userResponse.length; i++) {
                userResponse[i] = String.valueOf(response.charAt(i));
                if (!response.contains("=") && !response.contains("+") && !response.contains("-")) {// filtrage des tips utilisateur
                    throw new InputMismatchException();
                }
            }
        } catch (InputMismatchException | NullPointerException | NumberFormatException e) {
            logger.warn("Merci de rentrer = + ou - ");
            userTips();
        }

    }

    @Override
    public GameMode defender() { // mode défenseur
        //INTRO
        combinationLengthGestion();
        numberOfTriesGestion();
        secretGestion();
        computerTabLength = getTabLength();
        computerTab = new int[computerTabLength];
        goodResponseComparaisonTab = new String[computerTabLength];
        tabSolverHelper = new SolverHelper[computerTabLength];

        for (int i = 0; i < computerTabLength; i++) {
            tabSolverHelper[i] = new SolverHelper();
            goodResponseComparaisonTab[i] = "=";// on crée un tableau qui ne contiendra que des = et qui aura la même taille que le tabSolverHelper[], cela pour pouvoir le comparer dans la méthode defenderModeComparaisonManager()
        }
        int j = 0;
        //DEROULEMENT DU JEU
        while (j < getNumberOfTries()) {
            roundDefenderGestion();
            propositionGestion();
            if (isCheating() == true) {
                logger.info("Cheater spotted!");
                break;
            } else if (defenderModeComparaisonManager() == true) break;
            j++;
        }
        return null;
    }

    private void roundDefenderGestion() {// méthode qui gère le contenu de chaque round du mode défenseur
        for (int i = 0; i < computerTabLength; i++) {
            computerTab[i] = tabSolverHelper[i].guessNumber();
        }
    }

    private void propositionGestion() { // méthode qui gère les propositions de l'ordinateur et les indices utilisateurs
        logger.info("Voilà ma proposition");
        for (int i = 0; i < computerTabLength; i++) {
            System.out.print(computerTab[i] + "|");
        }
        logger.info("\nTes indices stp (+ - ou =)");
        userTips();
        for (int i = 0; i < computerTabLength; i++) {
            tabSolverHelper[i].analyse(userResponse[i]);
            // var qui va intervenir dans la méthode pour gérer certaines tentatives de tricherie.
            int counterForCheating = tabSolverHelper[i].getCounter();
            int var = counterForCheating / getTabLength();
            if (var >= 2) {
                setCheating(true);
            }
        }
    }

    @Override
    public GameMode duel() {// mode duel
        //INTRO
        combinationLengthGestion();
        numberOfTriesGestion();
        secretGestion();
        randomGestion();
        computerTabLength = getTabLength();
        computerTab = new int[computerTabLength];
        goodResponseComparaisonTab = new String[computerTabLength];
        tabSolverHelper = new SolverHelper[computerTabLength];
        for (int i = 0; i < computerTabLength; i++) {
            tabSolverHelper[i] = new SolverHelper();
            goodResponseComparaisonTab[i] = "=";// on crée un tableau qui ne contiendra que des = et qui aura la même taille que le tabSolverHelper[],
            // cela pour pouvoir le comparer dans la méthode defenderModeComparaisonManager()
        }
        int j = 0;
        //DEROULEMENT DU JEU
        while (j < getNumberOfTries()) {
            logger.info("\ntour n°" + (j + 1));
//----------------------------------------------------------------------------------------------------------------------
            logger.info("\ntour utilisateur");
            roundDefenderGestion();
            propositionGestion();
            if (isCheating() == true) {
                logger.info("Cheater Spotted!");
                break;
            } else if (defenderModeComparaisonManager() == true) {
                setComputerSucess(true);
                break;
            }
//----------------------------------------------------------------------------------------------------------------------
            if (isCheating() == false) {
                logger.info("\ntour ordi");
                devMode();//pour l'affichage du secret
                combinationAndTipsGestion();
                if (isComparaison() == true) {
                    setUserSucces(true);
                    break;
                }
            }
            j++;
        }
        return null;
    }
}