package com.Mastermind;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameGetPropertyValues {
    String result = "";
    InputStream inputStream;

    public String getPropValues() throws IOException{
        try{
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null){
                prop.load(inputStream);
            }else {
                throw new FileNotFoundException("property file" + propFileName+ "Not found");
            }
            //recupération de la valeur de propriété et  la print
                String game1 = prop.getProperty("game1");
                String game2 = prop.getProperty("game2");

            String gameMode1= prop.getProperty("game1");
            String gameMode2= prop.getProperty("game2");
            String gameMode3= prop.getProperty("game3");
            // manque le result = + le Sysout
        } catch (Exception e){
            System.out.println("Exception" +e);

        } finally {
            inputStream.close();
        }
        return result;
    }

}
