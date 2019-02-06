package main.java;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MastermindGame extends GameMode {
    private int number;
    // ! attention ces var en dessous sont similaires à celles de MoreOrLess!
    private boolean comparaison;
    private int computerTab[]; //tab utilisé pour le mode défenseur pour générer la réponse de l'ordinateur
    private SolverHelperMastermind tabSolverHelperMastermind[]; // tableau d'objets du type SolverHelperMastermind
    private int writePlacedTab[];//tab utilisé pour le mode défenseur pour la réponse de l'utilisateur
    private int presentNumbersTab[];//tab utilisé pour le mode défenseur pour la réponse de l'utilisateur
    private String goodResponseComparaisonTab[]; // tab de comparaison contenant autant de = que la taille de la combinaison  dans le mode défenseur
    private int computerTabLength; // var qui contient la taille du tableau computerTab
    private int numberOfTries;// var qui contient le nombre d'essais.
    private int roundCounter; // compte les tours de jeu dans le mode défenseur


    public MastermindGame() {
        super(0, 0);
    }

    @Override
    public GameMode challenger() {
        System.out.println("challenger Mastermind");
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


    @Override
    public void tipsGestion() { // gestion des indices pour le mode défenseur
        int numberOfWritePlaced = 0;
        int numberOfPresentNumbers = 0;
        ArrayList<Integer> numbersAlreadyCount = new ArrayList<>();
        ArrayList<Integer> indexOfWritePlaced = new ArrayList<>();
        for (int i = 0; i < getTabLength(); i++) {

            if (tabUser[i] == tab[i]) {
                numberOfWritePlaced++;
                indexOfWritePlaced.add(i);

            }
        }
        for (int j = 0; j < getTabLength(); j++) {

            for (int k = 0; k < getTabLength(); k++) {

                if (!indexOfWritePlaced.contains(j) && !indexOfWritePlaced.contains(k) && tab[j] == tabUser[k]) {
                    number = tabUser[k];
                    while (!numbersAlreadyCount.contains(number)) {
                        numbersAlreadyCount.add(number);
                        numberOfPresentNumbers++;
                    }

                    break;
                }
            }
        }

        if (numberOfWritePlaced == getTabLength()) {
            setComparaison(true);
        } else {
            setComparaison(false);
        }
        System.out.println(numberOfWritePlaced + " nombre bien placés ");
        System.out.println(numberOfPresentNumbers + " nombres présents ");
    }

    //!!!!!!!!!!!!!! à remanier VALEURS EN DUR
    public void userTipsMastermind() {
        Scanner scanner = new Scanner(System.in);
        writePlacedTab = new int[getNumberOfTries()]; // crée un tableau des éléments bien placés, de la taille du nombre d'essais def par l'utilisateur
        presentNumbersTab = new int[getNumberOfTries()];// crée un tableau des éléments mal placés mais présent, de la taille du nombre d'essais def par l'utilisateur
        try {
            // CHIFFRES BIEN PLACES
            System.out.println("Bien placés : ");
            int writePlaced = scanner.nextInt();
            if (writePlaced < 0 || writePlaced > 9) { //valeur en dur!!! => min et max
                throw new InputMismatchException();
            }
            writePlacedTab[roundCounter] = writePlaced; // round counter = 1 est pour garder le premier élément = 0

            // CHIFFRES PRESENTS
            System.out.println("Présents : ");
            int presentNumbers = scanner.nextInt();
            if (presentNumbers < 0 || presentNumbers > 9) { //valeur en dur!!! => min et max
                throw new InputMismatchException();
            }
            presentNumbersTab[roundCounter] = presentNumbers; //  round counter = 1 est pour garder le premier élément = 0


        } catch (InputMismatchException e) {
            System.out.println(" Merci de rentrer un chiffre entre 0 et 9");
            userTipsMastermind();
        } catch (NullPointerException e) {
            System.out.println(" Merci de rentrer un chiffre entre 0 et 9");
            userTipsMastermind();
        }

    }


    //----------------------------------------------------------------------------------------------------
    public ListOfSolutionsMastermind secret;
    int length;
    private int usedGuesses;

/*    public MastermindGame(int numberOfTries, int combinationLength) {
        super(numberOfTries, combinationLength);
        this.number = number;
        length = combinationLength;
        usedGuesses = 0;
        int[] t = new int[combinationLength];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Le secret : ");
        String response = scanner.nextLine();
        for (int i = 0; i < response.length(); i++) {
            t[i] = Integer.parseInt(String.valueOf(response.charAt(i)));
            secret = null;
            for (int k = 0; k < combinationLength; k++) {
                secret = new ListOfSolutionsMastermind(t[k], secret);
            }
        }
    }*/

        public MastermindGame(int combinationLength) {
        computerTabLength = combinationLength;
        usedGuesses = 0;
        int[] tab = new int[combinationLength];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Le secret : ");
        String response = scanner.nextLine();
        for (int i = 0; i < response.length(); i++) {
            tab[i] = Integer.parseInt(String.valueOf(response.charAt(i)));
            secret = null;
            for (int k = 0; k < combinationLength; k++) {
                secret = new ListOfSolutionsMastermind(tab[k], secret);
            }
        }
    }
    public MastermindGame (int [] t) { // <======= normalement n'est pas utilisé
        length = t.length ; usedGuesses = 0 ;
        secret = null ;
        for (int k = length-1 ; k >= 0 ; k--)
            secret = new ListOfSolutionsMastermind (t[k], secret) ;
    }


    private void launcher(MastermindGame mastermindGame) {
        System.out.println("Le secret est :" + mastermindGame);
        ListOfSolutionsMastermind solution = SolverHelperMastermind.find(mastermindGame);
        if (winningResult(processGuess(solution, false))) {
            System.out.println("Finder a perdu (la tête): " + solution);
        } else {
            System.out.println("Finder a trouvé en " + (getUsedGuesses() - 1) + " coups");
        }
    }


    public boolean defenderModeComparaisonManagerMastermind() {
        for (int i = 0; i < computerTabLength; i++) {
            if (writePlacedTab[i] != computerTabLength) {
                comparaison = false;
                setComparaison(false);
                return comparaison;
            }
        }
        setComparaison(true);
        return true;
    }

    public boolean winningResult(ResultMastermindDefender r) {
        return r.writePlacedNumbers == length;
    }

    public ResultMastermindDefender processGuess(ListOfSolutionsMastermind guess, boolean verbose) { // ^._.^ à modif si c'est l'utili qui fait son retour
        usedGuesses++;
        ResultMastermindDefender r = secret.evaluate(guess);
        if (verbose) {
            System.err.println(guess + " = [" + r + "]");
        }
        return r;
    }

    public ResultMastermindDefender processGuess(ListOfSolutionsMastermind guess) {
        return processGuess(guess, true);
    }

    int getUsedGuesses() {
        return usedGuesses;
    }

    public String toString() {
        return secret.toString();
    }

    public ListOfSolutionsMastermind getSecret() {
        return secret;
    }

    public int getComputerTabLength() {
        return computerTabLength;
    }

    @Override
    public GameMode defender() {
        //INTRO

        combinationLengthGestion();
        numberOfTriesGestion();

        roundCounter = 1; // commence à 1 pour les besoin de la méthode analyse dans SolverHelperMastermind
        computerTabLength = getTabLength();
        numberOfTries = getNumberOfTries();
        MastermindGame mastermindGame = new MastermindGame(/*numberOfTries,*/computerTabLength);
        launcher(mastermindGame);
        //-----------------------------------------------------------------------------
      /* SolverHelperMastermind solvHelper = new SolverHelperMastermind();
       SolverHelperMastermind.setCombinationLength(computerTabLength);*/


        // <=========================================================================
     /*SolverHelperMastermind solverHelperMa = new SolverHelperMastermind();
        ListOfListOfSolutionsMastermind listOfListOfSol = solverHelperMa.subsets(computerTabLength);
        listOfListOfSol.printPossibilities(listOfListOfSol);*/

       /* computerTab = new int[computerTabLength];
        goodResponseComparaisonTab = new String[computerTabLength];
        tabSolverHelperMastermind = new SolverHelperMastermind[computerTabLength];


        for (int i = 0; i < computerTabLength; i++) {
            tabSolverHelperMastermind[i] = new SolverHelperMastermind();
        }
        int j = 0;
        while (j < getNumberOfTries()) {
            for (int i = 0; i < computerTabLength; i++) {

                computerTab[i] = tabSolverHelperMastermind[i].guessNumber();
            }
            System.out.println("Voilà ma proposition");
            for (int i = 0; i < computerTabLength; i++) {
                System.out.print(computerTab[i]);
            }
            System.out.println("\nVos indices svp ");
            userTipsMastermind();
                for (int i = 0; i < computerTabLength; i++) {
                    if (writePlacedTab[roundCounter - 1] < writePlacedTab[roundCounter]){
                        computerTab[roundCounter-1] = tabSolverHelperMastermind[roundCounter-1].getTryNumber(); // tentative d'assigner une valeur à une place ♠
                    }

                    tabSolverHelperMastermind[i].analyse(writePlacedTab, presentNumbersTab, roundCounter);
                }
            if (defenderModeComparaisonManagerMastermind() == true) break;
            roundCounter++;
            j++;

        }
*/

        return null;
    }

    @Override
    public GameMode duel() {
        System.out.println("duel Mastermind");
        return null;
    }

}
