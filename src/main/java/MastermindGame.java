package main.java;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MastermindGame extends GameMode {
    private int number;
    private boolean comparaison;
    /* private int computerTab[]; //tab utilisé pour le mode défenseur pour générer la réponse de l'ordinateur*/
    /*private SolverHelperMastermind tabSolverHelperMastermind[]; // tableau d'objets du type SolverHelperMastermind*/
    /* private int writePlacedTab[];
     private int presentNumbersTab[];//tab utilisé pour le mode défenseur pour la réponse de l'utilisateur
     private String goodResponseComparaisonTab[]; // tab de comparaison contenant autant de = que la taille de la combinaison  dans le mode défenseur*/
    private int computerTabLength; // var qui contient la taille de combinaison
    private int numberOfTries;// var qui contient le nombre d'essais.
    private int roundCounter; // compte les tours de jeu dans le mode défenseur
    private int maxNumberOfNumbers; // gère le max des chiffres utilisables (de 0 à 4/10)
    private List<int[]> list = new ArrayList<>();//utilisé pour le mode défenseur  une liste qui va nous permettre de gérer les solutions
    private List<List<Integer>> listOfList = new ArrayList<>();//utilisé pour le mode défenseur  une liste de liste qui va nous permettre de gérer les solutions
    private int[] solution;//tab utilisé pour le mode défenseur pour recevoir la solution
    private int listSize;//utilisé pour le mode défenseur pour recevoir le nombre de liste de solutions
    private int numberOfWritePlaced; // compteur de chiffres bien placés
    private int numberOfPresentNumbers; // compteur des chiffres présent dans la combinaison mais mal placés
    private String endingSolution;// utilisé pour le mode défenseur pour afficher quand l'ordinateur a trouvé la bonne combi


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
    public void tipsGestion() { // gestion des indices
        numberOfWritePlaced = 0;
        numberOfPresentNumbers = 0;
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

    public void combinationNumberOfNumbers() { // méthode qui gère le nombre de chiffres utilisables pour le Mastermind
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Entrez le maximum :  4 min à 10 max");
            int response = scan.nextInt();
            if (response >= 4 && response <= 10) {
                maxNumberOfNumbers = response;
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

    //----------------------------------------------------------------------------------------------------
  /*  public ListOfSolutionsMastermind secret;
    int length;
    private int usedGuesses;*/

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


  /*  public void userCombinationMasterMindDefender() { //<====================== ATTENTION DUPLICATION DE LA METHODE userCombination() dans GameMode a voir si on ne peut pas overide
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\nLe secret"); // <================= le changement par rapport à userCombination()
            tabUser = new int[computerTabLength];
            String response = scanner.nextLine();
            if (response.length() != computerTabLength) {
                throw new InputMismatchException();
            }
            //pour découper la réponse

            for (int i = 0; i < response.length(); i++) {

                tabUser[i] = Integer.parseInt(String.valueOf(response.charAt(i)));
            }
            //pour retourner la réponse
            for (int i = 0; i < response.length() / 2; i++) {
                int temp = tabUser[i];
                tabUser[i] = tabUser[tabUser.length - i - 1];
                tabUser[tabUser.length - i - 1] = temp;
            }

        } catch (InputMismatchException e) {
            System.out.println("Merci de rentrer un chiffre entre 0 et 9 svp ");
            userCombinationMasterMindDefender();
        } catch (NullPointerException e) {
            System.out.println("Merci de rentrer un chiffre entre 0 et 9 svp ");
            userCombinationMasterMindDefender();
        } catch (NumberFormatException e) {
            System.out.println("Merci de rentrer un nombre");
            userCombinationMasterMindDefender();
        }

    }

    public MastermindGame(int combinationLength) {
        computerTabLength = combinationLength;
        usedGuesses = 0;
        userCombinationMasterMindDefender();
        secret = null;
        for (int k = 0; k < combinationLength; k++) {
            secret = new ListOfSolutionsMastermind(tabUser[k], secret);
        }
    }

    public MastermindGame(int[] t) { // <======= normalement n'est pas utilisé
        length = t.length;
        usedGuesses = 0;
        secret = null;
        for (int k = length - 1; k >= 0; k--)
            secret = new ListOfSolutionsMastermind(t[k], secret);
    }


    private void launcher(MastermindGame mastermindGame) {
        System.out.println("Le secret est :" + mastermindGame);
        ListOfSolutionsMastermind solution = SolverHelperMastermind.find(mastermindGame);
        if (winningResult(processGuess(solution, false))) {
            System.out.println("Finder a perdu (la tête): " + solution);
        } else {
            System.out.println("Finder a trouvé en " + (getUsedGuesses() - 1) + " coups");
        }
    }*/


    /*  public boolean winningResult(ResultMastermindDefender r) {
          return r.writePlacedNumbers == length;
      }

      public ResultMastermindDefender processGuess(ListOfSolutionsMastermind guess, boolean verbose) { // ^._.^ à modif si c'est l'utili qui fait son retour
          usedGuesses++;
          ResultMastermindDefender r = secret.evaluate(guess);
          *//*ResultMastermindDefender r = secret.userTipsMastermindDefender(guess);*//*
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
    }*/
    private void secretGestion() {
        System.out.println("Le secret");
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
            System.out.println("Merci de rentrer une solution de taille équivalente à la longueur de combinaison rentrée précédement : " + getTabLength());
            secretGestion();
        }
    }

    @Override
    public GameMode defender() {
        //INTRO

        combinationLengthGestion();
        numberOfTriesGestion();
        combinationNumberOfNumbers();
        secretGestion();


        roundCounter = 1; // commence à 1 pour les besoin de la méthode analyse dans SolverHelperMastermind
        computerTabLength = getTabLength();
        numberOfTries = getNumberOfTries();
        int[] tab = initTab();
        list.add(copySol(tab));
        for (; isFinished(tab); ) {
            tab[computerTabLength - 1]++;
            if (tab[computerTabLength - 1] > maxNumberOfNumbers) {
                increment(tab);
            }
            list.add(copySol(tab));

        }
        solve(solution);



        /*MastermindGame mastermindGame = new MastermindGame(*//*numberOfTries,*//*computerTabLength);
        launcher(mastermindGame);*/


        //-----------------------------------------------------------------------------


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

    private int[] initTab() {
        int[] ret = new int[computerTabLength];
        for (int i = 0; i < computerTabLength; i++) {
            ret[i] = 0;
        }
        return ret;
    }

    private boolean isFinished(int[] tab) {
        for (int i = 0; i < computerTabLength; i++) {
            if (tab[i] != maxNumberOfNumbers) {
                return true;
            }
        }
        return false;
    }

    private void increment(int[] tab) {
        for (int i = computerTabLength - 1; i > 0; i--) {
            if (tab[i] > maxNumberOfNumbers) {
                tab[i] = 0;
                tab[i - 1]++;
            } else {
                break;
            }
        }
    }

    private void solve(int[] tabSol) {
       /* int j = 0;
        while (j < getNumberOfTries()) {*/
        for (; list.size() != 1; ) {
            System.out.print("Solution proposée : ");
            printFirst(list.get(0));
            int[] save = copySol(solution);
            int[] saveProp = copySol(list.get(0));

            /*userFeedBack();*///<========================================*/
            numberOfWritePlaced = countWellPlaced(list.get(0), save);
            numberOfPresentNumbers = countMissPlaced(list.get(0), save);
            int sumWellPlacedAndMissPlaced = numberOfPresentNumbers + numberOfWritePlaced; //<=================================
            listSize = list.size();
            System.out.println("Bp : " + numberOfWritePlaced);
            System.out.println("MP : " + numberOfPresentNumbers);
           /* if (numberOfWritePlaced == computerTabLength) {
                endingSolution = String.valueOf(saveProp);
            }*/
            System.out.println("taille de liste : " + listSize);

            System.out.println("tour : " + roundCounter);
            roundCounter++;

            list.remove(0);

            if (defenderModeComparaisonManagerMastermind() == true) {
                System.out.print("FIN la solution était : " /*+ endingSolution*/);
                break;
            } else if (sumWellPlacedAndMissPlaced == 0) {
                filterList(saveProp);
                // supr toutes les listes qui ont un pt commun
            } else if (sumWellPlacedAndMissPlaced == 1) {
                //prevoir méthode qui extrait de la liste toutes les combinaisons qui ont au - un nb en commun
                extractWhenSimilarPoint(saveProp);
            } else if (sumWellPlacedAndMissPlaced == 2) {
                // extraire tous les duos de nb contenu dedans
                extractPair(extractIngerList(saveProp));
                filterList(2);
            } else if (sumWellPlacedAndMissPlaced == 3) {
                // extraire tous les trio de nb contenu dedans
                extractTrio(extractIngerList(saveProp));
                filterList(3);
            } else if (sumWellPlacedAndMissPlaced == 4) {

                extractQuatuor(extractIngerList(saveProp));
                filterList(4);
            } else if (sumWellPlacedAndMissPlaced == 5) {

                extractQuinte(extractIngerList(saveProp));
                filterList(5);
            } else if (sumWellPlacedAndMissPlaced == 6) {

                extractSixte(extractIngerList(saveProp));
                filterList(6);
            } else if (sumWellPlacedAndMissPlaced == 7) {

                extractSeptuor(extractIngerList(saveProp));
                filterList(7);
            } else if (sumWellPlacedAndMissPlaced == 8) {

                extractOctuor(extractIngerList(saveProp));
                filterList(8);
            } else if (sumWellPlacedAndMissPlaced == 9) {

                extractNonet(extractIngerList(saveProp));
                filterList(9);
            }
        }
       /* }
        j++;*/

    }


    private void filterList(int i) {
        List<int[]> ret = new ArrayList<>();
        int[] smp = copySol(list.get(0));
        list.remove(0);
        for (int j = 0; j < list.size(); j++) {
            int count = 0;
            int[] cmp = copySol(smp);
            for (List<Integer> lst : listOfList) {
                for (Integer k : lst) {
                    for (int a = 0; a < cmp.length; a++) {
                        if (k == cmp[a]) {
                            count++;
                            cmp[a] = -1;
                        }

                    }
                }
            }
            if (count >= i && !ret.contains(list.get(j))) {
                ret.add(copySol(list.get(j)));

            }

        }
        list = ret;
    }


    private List<Integer> extractIngerList(int[] saveProp) {
        List<Integer> ret = new ArrayList<>();
        for (int i : saveProp) {
            ret.add(i);
        }
        return ret;
    }

    private void userFeedBack() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nombre de bien placés :");
        numberOfWritePlaced = scanner.nextInt();
        System.out.println("Nombre de mal placés :");
        numberOfPresentNumbers = scanner.nextInt();
    }

    // bis
    private void extractWhenSimilarPoint(int[] tab) {
        List<int[]> temp = new ArrayList<>();
        list.remove(0);
        for (int i = 0; i < list.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < maxNumberOfNumbers; j++) {// anterieurement max
                for (int k = 0; k < maxNumberOfNumbers; k++) {// anterieurement max
                    if (tab[j] == list.get(i)[k]) {
                        flag = true;
                    }
                }
            }
            if (flag == true) {
                temp.add(list.get(i));
            }
        }
        list = temp;
    }

    private void extractPair(List<Integer> list1) {
        List<List<Integer>> val = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for (int i2 = i1 + 1; i2 < list1.size(); i2++) {
                List<Integer> add = new ArrayList<>();
                add.add(list1.get(i1));
                add.add(list1.get(i2));
                val.add(add);
            }
        }
        listOfList = val;
    }

    private void extractTrio(List<Integer> list1) {
        List<List<Integer>> val = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for (int i2 = i1 + 1; i2 < list1.size(); i2++) {
                for (int i3 = i2 + 1; i3 < list1.size(); i3++) {
                    List<Integer> add = new ArrayList<>();
                    add.add(list1.get(i1));
                    add.add(list1.get(i2));
                    add.add(list1.get(i3));
                    val.add(add);
                }
            }
        }
        listOfList = val;
    }


    private void extractQuatuor(List<Integer> list1) {
        List<List<Integer>> val = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for (int i2 = i1 + 1; i2 < list1.size(); i2++) {
                for (int i3 = i2 + 1; i3 < list1.size(); i3++) {
                    for (int i4 = i3 + 1; i4 < list1.size(); i4++) {
                        List<Integer> add = new ArrayList<>();
                        add.add(list1.get(i1));
                        add.add(list1.get(i2));
                        add.add(list1.get(i3));
                        add.add(list1.get(i4));
                        val.add(add);
                    }
                }
            }
        }
        listOfList = val;
    }

    private void extractQuinte(List<Integer> list1) {
        List<List<Integer>> val = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for (int i2 = i1 + 1; i2 < list1.size(); i2++) {
                for (int i3 = i2 + 1; i3 < list1.size(); i3++) {
                    for (int i4 = i3 + 1; i4 < list1.size(); i4++) {
                        for (int i5 = i4 + 1; i5 < list1.size(); i5++) {
                            List<Integer> add = new ArrayList<>();
                            add.add(list1.get(i1));
                            add.add(list1.get(i2));
                            add.add(list1.get(i3));
                            add.add(list1.get(i4));
                            add.add(list1.get(i5));
                            val.add(add);
                        }
                    }
                }
            }
        }
        listOfList = val;
    }


    private void extractSixte(List<Integer> list1) {
        List<List<Integer>> val = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for (int i2 = i1 + 1; i2 < list1.size(); i2++) {
                for (int i3 = i2 + 1; i3 < list1.size(); i3++) {
                    for (int i4 = i3 + 1; i4 < list1.size(); i4++) {
                        for (int i5 = i4 + 1; i5 < list1.size(); i5++) {
                            for (int i6 = i5 + 1; i6 < list1.size(); i6++) {
                                List<Integer> add = new ArrayList<>();
                                add.add(list1.get(i1));
                                add.add(list1.get(i2));
                                add.add(list1.get(i3));
                                add.add(list1.get(i4));
                                add.add(list1.get(i5));
                                add.add(list1.get(i6));
                                val.add(add);
                            }
                        }
                    }
                }
            }
        }
        listOfList = val;
    }


    private void extractSeptuor(List<Integer> list1) {
        List<List<Integer>> val = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for (int i2 = i1 + 1; i2 < list1.size(); i2++) {
                for (int i3 = i2 + 1; i3 < list1.size(); i3++) {
                    for (int i4 = i3 + 1; i4 < list1.size(); i4++) {
                        for (int i5 = i4 + 1; i5 < list1.size(); i5++) {
                            for (int i6 = i5 + 1; i6 < list1.size(); i6++) {
                                for (int i7 = i6 + 1; i7 < list1.size(); i7++) {
                                    List<Integer> add = new ArrayList<>();
                                    add.add(list1.get(i1));
                                    add.add(list1.get(i2));
                                    add.add(list1.get(i3));
                                    add.add(list1.get(i4));
                                    add.add(list1.get(i5));
                                    add.add(list1.get(i6));
                                    add.add(list1.get(i7));
                                    val.add(add);
                                }
                            }
                        }
                    }
                }
            }
        }
        listOfList = val;
    }

    private void extractOctuor(List<Integer> list1) {
        List<List<Integer>> val = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for (int i2 = i1 + 1; i2 < list1.size(); i2++) {
                for (int i3 = i2 + 1; i3 < list1.size(); i3++) {
                    for (int i4 = i3 + 1; i4 < list1.size(); i4++) {
                        for (int i5 = i4 + 1; i5 < list1.size(); i5++) {
                            for (int i6 = i5 + 1; i6 < list1.size(); i6++) {
                                for (int i7 = i6 + 1; i7 < list1.size(); i7++) {
                                    for (int i8 = i7 + 1; i8 < list1.size(); i8++) {
                                        List<Integer> add = new ArrayList<>();
                                        add.add(list1.get(i1));
                                        add.add(list1.get(i2));
                                        add.add(list1.get(i3));
                                        add.add(list1.get(i4));
                                        add.add(list1.get(i5));
                                        add.add(list1.get(i6));
                                        add.add(list1.get(i7));
                                        add.add(list1.get(i8));
                                        val.add(add);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        listOfList = val;
    }

    private void extractNonet(List<Integer> list1) {
        List<List<Integer>> val = new ArrayList<>();
        for (int i1 = 0; i1 < list1.size(); i1++) {
            for (int i2 = i1 + 1; i2 < list1.size(); i2++) {
                for (int i3 = i2 + 1; i3 < list1.size(); i3++) {
                    for (int i4 = i3 + 1; i4 < list1.size(); i4++) {
                        for (int i5 = i4 + 1; i5 < list1.size(); i5++) {
                            for (int i6 = i5 + 1; i6 < list1.size(); i6++) {
                                for (int i7 = i6 + 1; i7 < list1.size(); i7++) {
                                    for (int i8 = i7 + 1; i8 < list1.size(); i8++) {
                                        for (int i9 = i8 + 1; i9 > list1.size(); i9++) {
                                            List<Integer> add = new ArrayList<>();
                                            add.add(list1.get(i1));
                                            add.add(list1.get(i2));
                                            add.add(list1.get(i3));
                                            add.add(list1.get(i4));
                                            add.add(list1.get(i5));
                                            add.add(list1.get(i6));
                                            add.add(list1.get(i7));
                                            add.add(list1.get(i8));
                                            add.add(list1.get(i9));
                                            val.add(add);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        listOfList = val;
    }


    // cherche point commun
    private void filterList(int[] tab) {
        List<int[]> temp = new ArrayList<>();
        list.remove(0);
        for (int i = 0; i < list.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < maxNumberOfNumbers; j++) { // anterieurement max
                for (int k = 0; k < maxNumberOfNumbers; k++) {// anterieurement max
                    if (tab[j] == list.get(i)[k]) {
                        flag = false;
                    }
                }
            }
            if (flag == true) {
                temp.add(list.get(i));
            }
        }
        list = temp;
    }

    private int[] copySol(int[] tab) {
        int[] ret = new int[computerTabLength];
        for (int i = 0; i < computerTabLength; i++) {
            ret[i] = tab[i];
        }
        return ret;
    }

  /*  private void ListOfListGenerator(int sumWellPlacedAndMissPlaced) {
        for (int i = 0; i < listSize; i++) {//<=============================== ATTENTION listSize?
            *//* listOfList.add(list);*//*
        }
    }

    // cherche point commun
    private void filterListOfList(int[] tab) {
        List<List<int[]>> temp = new ArrayList<List<int[]>>();
        for (int i = 0; i < list.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < maxNumberOfNumbers; j++) { // anterieurement max
                for (int k = 0; k < maxNumberOfNumbers; k++) {// anterieurement max
                    if (tab[j] == list.get(i)[k]) {
                        flag = false;
                    }
                }

            }
            if (flag == true) {
                *//*temp.add(listOfList.get(0));*//*//<=================================================================================
            }
        }
        *//*listOfList = temp;*//*
    }*/


    private void printFirst(int[] ints) {
        for (int i = 0; i < computerTabLength; i++) {
            System.out.print(ints[i] + "|");
        }
        System.out.print("\n");
    }

    private int countWellPlaced(int[] tab, int[] save) {
        int ret = 0;
        for (int i = 0; i < computerTabLength; i++) {
            if (tab[i] == save[i]) {
                ret++;
                tab[i] = -1;
                save[i] = -1;
            }
        }
        numberOfWritePlaced = ret;
        return ret;
    }

    private int countMissPlaced(int[] tab, int[] save) {
        int ret = 0;
        for (int i = 0; i < computerTabLength; i++) {
            for (int j = 0; j < computerTabLength; j++) {
                if (tab[i] != -1 && tab[i] == save[j]) {
                    tab[i] = -1;//<=================================================================================*/
                    ret++;
                    save[j] = -1;
                }
            }
        }
        numberOfPresentNumbers = ret;
        return ret;
    }

    public boolean defenderModeComparaisonManagerMastermind() {
        for (int i = 0; i < computerTabLength; i++) {
            if (numberOfWritePlaced != computerTabLength) {
                comparaison = false;
                setComparaison(false);
                return comparaison;
            }
        }
        setComparaison(true);
        return true;
    }

    @Override
    public GameMode duel() {
        System.out.println("duel Mastermind");
        return null;
    }
    public void patate(){}

}
