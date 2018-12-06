package main.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameGetPropertyValues {
    String result = "";
    InputStream inputStream;

    public GameProperties getPropValues() throws IOException {
        GameProperties gameProperties = new GameProperties();
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file" + propFileName + "Not found");
            }
//c'est un objet ^^
        } catch (Exception e) {
            System.out.println("Exception" + e);

        } finally {
            inputStream.close();
        }
        return gameProperties;
    }

}
