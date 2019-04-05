package main.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class GameGetPropertyValues {
    String result = "";
    InputStream inputStream;
    private final static Logger logger = Logger.getLogger(MastermindGame.class.getName());
   private Integer difficulty;
    private String devMode;
    private int numberOfTries;
    private int gameLength;




    public GameProperties getPropValues() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file" + propFileName + "Not found");
            }
            if (prop.getProperty("isDevMode").equals("1") || prop.getProperty("isDevMode").equals("2")) {
                devMode = (prop.getProperty("isDevMode"));
            } else {
                logger.warning(" la valeur de Game Dev Mode est invalide ");
                devMode ="2";
            }
            Integer tmp = Integer.parseInt(prop.getProperty("numberOfTries"));
            Integer tmp2 = Integer.parseInt(prop.getProperty("gameLength"));
            Integer tmp3 = Integer.parseInt(prop.getProperty("difficulty"));
            if (tmp<1||tmp>10000){
                logger.warning(" le nombre d'essais est moche !!! on a mis une valeur par défault ");
               numberOfTries =10;
            } else {
                numberOfTries =tmp;

            }
            if (tmp2<4||tmp2>10){
                logger.warning(" le nombre d'essais est moche !!! on a mis une valeur par défault ");
               gameLength=5;
            } else {
                gameLength=tmp2;

            }
            if (tmp3<4||tmp3>10){
                logger.warning(" le nombre d'essais est moche !!! on a mis une valeur par défault ");
               difficulty=5;
            } else {
                difficulty=tmp3;

            }



        } catch (Exception e) {
            System.out.println("Exception" + e);

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
