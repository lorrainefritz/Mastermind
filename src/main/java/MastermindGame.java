package main.java;

import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;

public class MastermindGame extends GameMode {
    private int computerTabLength; // var qui contient la taille de combinaison
    private int roundCounter; // compte les tours de jeu dans le mode défenseur
    private int maxNumberOfNumbers; // gère le max des chiffres utilisables (de 0 à 4/10)
    private List<int[]> list = new ArrayList<>();//utilisé pour le mode défenseur  une liste qui va nous permettre de gérer les solutions
    private List<List<Integer>> listOfList = new ArrayList<>();//utilisé pour le mode défenseur  une liste de liste qui va nous permettre de gérer les solutions
    private int numberOfWritePlaced; // compteur de chiffres bien placés
    private int numberOfPresentNumbers; // compteur des chiffres présent dans la combinaison mais mal placés
    private int sumWellPlacedAndMissPlaced;// Somme des chiffres présent et mal placés
    private int[] saveProp; // un tableau qui permet le maintien des propositions

    private final static Logger logger = Logger.getLogger(MastermindGame.class.getName());


    public MastermindGame() {
        super();
        GameGetPropertyValues gpv = new GameGetPropertyValues();
        try {
            gpv.getPropValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.combinationLength = gpv.getGameLength();
        this.difficulty = gpv.getDifficulty();
        this.devMode = gpv.getDevMode().equals("1");
    }

    @Override
    public int randomCombination() {// génère un random entre 0 et 9
        Random random = new Random();
        return random.nextInt(difficulty); // retourne un random dont le max est 10
    }

    @Override
    public GameMode challenger() { // gère le mode challenger
        //INTRO//
        combinationLengthGestion();
        numberOfTriesGestion();
        randomGestion();
        devMode();
        int numbOfTries = getNumberOfTries();
        int j = 0;
        //DEROULEMENT DU JEU
        while (j < numbOfTries) {
            combinationAndTipsGestion();
            if (isComparaison()) break;
            j++;
        }
        return null;
    }

    private void combinationAndTipsGestion() {
        userCombination(); // gère la combinaison utilisateur
        tipsGestion(); // gère les indices
        comparaison(); // comparaison tab utilisateur versus tab combinaison secrète
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
                    int number = tabUser[k];
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
        logger.info(numberOfWritePlaced + " chiffres bien placés ");
        logger.info(numberOfPresentNumbers + " chiffres présents ");
    }

    private void combinationNumberOfNumbers() { // méthode qui gère le nombre de chiffres utilisables pour le Mastermind
        try {
            int response = combinationLength;
            if (response >= 4 && response <= 10) {
                maxNumberOfNumbers = response;
            } else {
                throw new InputMismatchException();
            }
        } catch (InputMismatchException | NullPointerException e) {
            logger.warn("Merci de rentrer un chiffre entre 4 et 10 stp ");
            combinationLengthGestion();
        } catch (NegativeArraySizeException e) {
            logger.warn("Merci de rentrer un chiffre entre 4 et 10 stp ");
        }
    }

    //----------------------------------------------------------------------------------------------------


    @Override
    public GameMode defender() { // gère le mode défenseur
        //INTRO//
        combinationLengthGestion();
        numberOfTriesGestion();
        combinationNumberOfNumbers();
        secretGestion();
        roundCounter = 1; // commence à 1 pour les besoin de la méthode analyse dans SolverHelperMastermind
        computerTabLength = getTabLength();
        // Partie qui permet de générer toutes les listes combinaisons possibles
        int[] tab = initTab();
        list.add(copySol(tab));
        for (; isFinished(tab); ) {
            tab[computerTabLength - 1]++;
            if (tab[computerTabLength - 1] > maxNumberOfNumbers) {
                increment(tab);
            }
            list.add(copySol(tab));

        }
        solve(getSolution());

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

    private void roundGestion() { //gestion  des rounds de jeu avec le compteur
        logger.info("tour : " + roundCounter);
        insideOfRound();
        roundCounter++;
    }

// pour comptage auto des BP /MP  de-commenter lignes 189 192 193 196 197 + les méthodes ligne 555 (private int countWellPlaced(int[] t, int[] save)) et 569(private int countMissPlaced(int[] t, int[] save))
    private void insideOfRound() {  // intérieur du round en lui même. Cette option a été adoptée pour pouvoir être appelée seule dans le mode duel
        logger.info("Voilà ma proposition : ");
        printFirst(list.get(0));
        /*  int[] save = copySol(getSolution());*///<===================================================== à sweetcher pour le comptage automatique BP MP
        saveProp = copySol(list.get(0));
        userFeedBack();//<======================================================= à sweetcher pour le retour utilisateur
/*      numberOfWritePlaced = countWellPlaced(list.get(0), save); //<===================================== à sweetcher pour le comptage automatique BP MP
        numberOfPresentNumbers = countMissPlaced(list.get(0), save);*///<================================= à sweetcher pour le comptage automatique BP MP
        sumWellPlacedAndMissPlaced = numberOfPresentNumbers + numberOfWritePlaced;
        //utilisé pour le mode défenseur pour recevoir le nombre de liste de solutions
        list.size();
        /*logger.info("Bp : " + numberOfWritePlaced);//<================================================== à sweetcher pour le comptage automatique BP MP
        logger.info("MP : " + numberOfPresentNumbers);*///<=============================================== à sweetcher pour le comptage automatique BP MP
    }

    private void listGestion() { // méthode qui permet de gérer toutes les listes de solutions
        if (sumWellPlacedAndMissPlaced == 0) {
            filterList(saveProp);
            // supprime toutes les listes qui ont un point commun avec la solution proposée précédemment
        } else if (sumWellPlacedAndMissPlaced == 1) {
            extractWhenSimilarPoint(saveProp);
            // supprime toutes les listes qui ont un duos de chiffre en commun
        } else if (sumWellPlacedAndMissPlaced == 2) {
            extractPair(extractIngerList(saveProp));
            filterList(2);
            // supprime toutes les listes qui ont un ensemble de 3 chiffres en commun
        } else if (sumWellPlacedAndMissPlaced == 3) {
            extractTrio(extractIngerList(saveProp));
            filterList(3);
            // supprime toutes les listes qui ont un ensemble de 4 chiffres en commun
        } else if (sumWellPlacedAndMissPlaced == 4) {
            extractQuatuor(extractIngerList(saveProp));
            filterList(4);
            // supprime toutes les listes qui ont un ensemble de 5 chiffres en commun
        } else if (sumWellPlacedAndMissPlaced == 5) {
            extractQuinte(extractIngerList(saveProp));
            filterList(5);
            // supprime toutes les listes qui ont un ensemble de 6 chiffres en commun
        } else if (sumWellPlacedAndMissPlaced == 6) {
            extractSixte(extractIngerList(saveProp));
            filterList(6);
            // supprime toutes les listes qui ont un ensemble de 7 chiffres en commun
        } else if (sumWellPlacedAndMissPlaced == 7) {
            extractSeptuor(extractIngerList(saveProp));
            filterList(7);
            // supprime toutes les listes qui ont un ensemble de 8 chiffres en commun
        } else if (sumWellPlacedAndMissPlaced == 8) {
            extractOctuor(extractIngerList(saveProp));
            filterList(8);
            // supprime toutes les listes qui ont un ensemble de 9 chiffres en commun
        } else if (sumWellPlacedAndMissPlaced == 9) {
            extractNonet(extractIngerList(saveProp));
            filterList(9);
        }
    }

    private void solve(int[] tabSol) { // méthode qui va générer la solution
        boolean flag = false;

        while (roundCounting() == true && flag == false) {
            for (; list.size() != 1; ) {
                roundGestion();

                if (defenderModeComparaisonManagerMastermind() == true) {
                    flag = true;
                    setComputerSucess(true);
                    break;
                }
                listGestion();
                if (roundCounting() == false) break;
            }

        }

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

    private void userFeedBack() {  // permet de récupérer le feed back utilisateur sur les chiffres bien placés et mal placés
        Scanner scanner = new Scanner(System.in);
        logger.info("Bien placés :");
        numberOfWritePlaced = scanner.nextInt();
        logger.info("Mal placés :");
        numberOfPresentNumbers = scanner.nextInt();
    }

    // les listes qui ont un point commun avec la solution proposée précédemment
    private void extractWhenSimilarPoint(int[] tab) {
        List<int[]> temp = new ArrayList<>();
        list.remove(0);
        for (int[] ints : list) {
            boolean flag = false;
            for (int j = 0; j < ints.length; j++) {
                for (int anInt : ints) {
                    if (tab[j] == anInt) {
                        flag = true;
                    }
                }
            }
            if (flag == true) {
                temp.add(ints);
            }
        }
        list = temp;
    }

    // les listes qui ont 2 points communs avec la solution proposée précédemment
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

    // les listes qui ont 3 points communs avec la solution proposée précédemment
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

    // les listes qui ont 4 points communs avec la solution proposée précédemment
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

    // les listes qui ont 5 points communs avec la solution proposée précédemment
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

    // les listes qui ont 6 points communs avec la solution proposée précédemment
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

    // les listes qui ont 7 points communs avec la solution proposée précédemment
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

    // les listes qui ont 8 points communs avec la solution proposée précédemment
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

    // les listes qui ont 9 points communs avec la solution proposée précédemment
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


    // Permet de chercher les éléments communs au sein des listes et de supprimer les listes devenues inutiles
    private void filterList(int[] tab) {
        List<int[]> temp = new ArrayList<>();
        list.remove(0);
        for (int[] ints : list) {
            boolean flag = true;
            for (int j = 0; j < ints.length; j++) {
                for (int k = 0; k < ints.length; k++) {
                    if (tab[j] == ints[k]) {
                        flag = false;
                    }
                }
            }
            if (flag == true) {
                temp.add(ints);
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

    private void printFirst(int[] ints) {
        for (int i = 0; i < computerTabLength; i++) {
            System.out.print(ints[i] + "|");
        }
        System.out.print("\n");
    }

    private int countWellPlaced(int[] t, int[] save) { // méthode qui compte automatiquement les chiffres bien placés (donc sans retour utilisateur)
        int ret = 0;
        int[] tab = copySol(t);
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

    private int countMissPlaced(int[] t, int[] save) { // méthode qui compte automatiquement les chiffres mal placés (donc sans retour utilisateur)
        int ret = 0;
        int[] tab = copySol(t);
        for (int i = 0; i < computerTabLength; i++) {
            for (int j = 0; j < computerTabLength; j++) {
                if (tab[i] != -1 && tab[i] == save[j]) {
                    tab[i] = -1;
                    ret++;
                    save[j] = -1;
                }
            }
        }
        numberOfPresentNumbers = ret;
        return ret;
    }

    private boolean defenderModeComparaisonManagerMastermind() { // gère la fin de jeu pour le mode défenseur
        for (int i = 0; i < computerTabLength; i++) {
            if (numberOfWritePlaced != computerTabLength) {
                boolean comparaison = false;
                setComparaison(false);
                return comparaison;
            }
        }
        setComparaison(true);
        return true;
    }

    private boolean roundCounting() {
        //utilisé pour le mode défenseur sert à gérer le nombre de tours en fonction  de la demande de l'utilisateur
        boolean roundCountingBoolean;
        if (roundCounter != getNumberOfTries() + 1) {
            roundCountingBoolean = true;
        } else {
            roundCountingBoolean = false;
        }
        return roundCountingBoolean;
    }

    //---------------------------------------------------------------------------------------------------------------------
    @Override
    public GameMode duel() {
        //INTRO
        combinationLengthGestion();
        numberOfTriesGestion();
        randomGestion();
        int numbOfTries = getNumberOfTries();
        combinationNumberOfNumbers();
        secretGestion();
        computerTabLength = getTabLength();
        int[] tab = initTab();
        list.add(copySol(tab));
        for (; isFinished(tab); ) {
            tab[computerTabLength - 1]++;
            if (tab[computerTabLength - 1] > maxNumberOfNumbers) {
                increment(tab);
            }
            list.add(copySol(tab));
        }
        //DEROULEMENT DU JEU
        int j = 0;
        while (j < numbOfTries) {
            logger.info("\ntour n°" + (j + 1));
            //--------------------------------------------------------------------------------------------
            logger.info("\ntour utilisateur");
            for (; list.size() != 1; ) {
                insideOfRound();
                if (defenderModeComparaisonManagerMastermind() == true) {
                    setComputerSucess(true);
                    return null;
                }
                listGestion();
                if (roundCounting() == true) break;
            }
            //---------------------------------------------------------------------------------------------
            logger.info("\ntour ordi");
            devMode();
            combinationAndTipsGestion();
            if (isComparaison() == true) {
                setUserSucces(true);
                break;
            }
            j++;
        }

        return null;
    }


}