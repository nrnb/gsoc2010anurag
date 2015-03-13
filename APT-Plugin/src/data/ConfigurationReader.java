package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to read the configurations stored in files and load in the program
 * @author Anurag Sharma
 */
public class ConfigurationReader {

//    private static final String confFileName = FileLocator.confPropertiesFileLocation;
    private static final String confDirName = FileLocator.confDirLocation;
    private Properties configurationProperties;

    /**
     * instantiates new object and loads the configuration files
     * @throws IOException
     */
    public ConfigurationReader() throws IOException {
        File confDir = new File(confDirName);
        if (!confDir.exists()) {
            confDir.mkdir();
        }

        File confFile = new File(FileLocator.confPropertiesFileLocation);//confDirName + "/" + confFileName);
        if (!confFile.exists()) {
            confFile.createNewFile();
        }
        configurationProperties = new Properties();
        configurationProperties.load(new FileInputStream(confFile));
    }

    /**
     * finds a particular property
     * @param key the key to find
     * @return the value of the keys
     */
    public String getValueOf(String key) {
        return configurationProperties.getProperty(key);
    }

    /**
     * sets the property
     * @param key the property to set
     * @param value the value of the key
     */
    public void setValueOf(String key, String value) {
        configurationProperties.setProperty(key, value);
        try {
            configurationProperties.store(new FileWriter(new File(FileLocator.confPropertiesFileLocation)), "hehehahhaha");
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * sets the specified directory to appropriate field permanently
     * @param dir the CEL directory
     */
    public void setCELDirectory(String dir) {
        setValueOf("cel_dir", dir);
    }

    /**
     *
     * @return the last accessed CEL directory and if its not present, the current directory
     */
    public String getCELDirectory() {
        String dir = getValueOf("cel_dir");
        if (dir == null) {
            return null;
        }
        if (new File(dir).exists()) //return it if it exists
        {
            return dir;
        }
        return ".";     //return the current directory if the directory doesnot exist
    }
}
