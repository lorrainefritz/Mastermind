package main.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.*;

public class GameGetPropertyValues {
    String result = "";
    InputStream inputStream;
    private final static Logger logger = Logger.getLogger(GameGetPropertyValues.class.getName());
   private Integer difficulty;
    private String devMode;
    private int numberOfTries;
    private int gameLength;




    public GameProperties getPropValues() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "main/java/resources/config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file" + propFileName + "Not found");
            }
            if (prop.getProperty("isDevMode").equals("1") || prop.getProperty("isDevMode").equals("2")) {
                devMode = (prop.getProperty("isDevMode"));
            } else {
                logger.warning(" le chiffre entré pour activer/désactiver le mode dev est invalide : valeur attribyée par défaut = 1 /activé ");
                devMode ="1";
            }
            Integer tmp = Integer.parseInt(prop.getProperty("numberOfTries"));
            Integer tmp2 = Integer.parseInt(prop.getProperty("gameLength"));
            Integer tmp3 = Integer.parseInt(prop.getProperty("difficulty"));
            if (tmp<1||tmp>10000){
                logger.warning(" le nombre d'essais entré dans config.properties est invalide : valeur attribuée par défaut = 10 ");
               numberOfTries =10;
            } else {
                numberOfTries =tmp;

            }
            if (tmp2<4||tmp2>10){
                logger.warning(" la taille de combinaison entrée dans config.properties est invalide : valeur attribuée par défaut = 5");
               gameLength=5;
            } else {
                gameLength=tmp2;

            }
            if (tmp3<4||tmp3>10){
                logger.warning(" la difficulté entrée dans config.properties est invalide : valeur attribuée par défaut = 5");
               difficulty=5;
            } else {
                difficulty=tmp3;

            }

        } catch (NumberFormatException e){
            logger.warning(" la taille d'une variable dans le config.properties est trop importante, toutes les variables sont en valeur par défaut");
            devMode ="1";
            numberOfTries =10;
            gameLength=5;
            difficulty=5;

        } catch (Exception e) {
            logger.warning(" Exception " +e);

        } finally {
            inputStream.close();
        }
        return null;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public String getDevMode() {
        return devMode;
    }


    public int getNumberOfTries() {
        return numberOfTries;
    }

    public int getGameLength() {
        return gameLength;
    }
}
