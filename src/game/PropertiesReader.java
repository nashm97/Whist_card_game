package game;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    public PropertiesReader() {

    }

    /**
     * reads a properties file and sends to Whist
     * @param propertiesPath
     * @return
     * @throws IOException
     */
    static public Properties setUpProperties(String propertiesPath) throws IOException {

        Properties gameProperties = new Properties();

        FileReader inStream = null;
        try {
            inStream = new FileReader(propertiesPath);
            gameProperties.load(inStream);
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }
        return gameProperties;
    }

}
