package data;

import java.io.File;

/**
 * Stores the location of various configuration files needed by the program
 * @author Anurag Sharma
 */
public class FileLocator {

    public static final String user_home_dir = System.getProperty("user.home");
    public static final String home_dir = user_home_dir + "/APT-Plugin";
    public static final String aptPropertiesFileLocation = home_dir + "/conf/apt.properties";
    public static final String confPropertiesFileLocation = home_dir + "/conf/configuration.properties";
    public static final String urlPropertiesFileLocation = home_dir + "/conf/url.properties";
    public static final String confDirLocation = home_dir + "/conf";
    public static final String resDirLocation = home_dir + "/res";
    public static final String downloadDirLocation = home_dir + "/download";
    public static final String localDBDirLocation = home_dir + "/LocalDatabase";

    public static final String aptForWindowsURL = "http://localhost/apt-windows.zip";
    public static final String aptForLinux32BitURL = "http://localhost/apt-linux32bit.zip";
    public static final String aptForLinux64BitURL = "http://localhost/apt-linux64bit.zip";
    public static final String aptForMacURL = "http://localhost/apt-mac.zip";

    static {    //creates ~/APT-Plugin directory if it doesnt exist
        File file = new File(home_dir);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    //for testing only
    public static void main(String[] args) {
        System.out.println("" + FileLocator.user_home_dir);
    }
}
