package data;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * The data structure to store the Array data as parsed from the file ArrayFileInfo.txt
 * @author Anurag Sharma
 */
public class ArrayData implements Comparable {

    private static String baseURL = "http://localhost/";
//    private static final String baseURL = "http://altanalyze.org/archiveDBs/LibraryFiles/";
    public String arrayName, libraryFile, annotationFile, species, arrayType;

    /**
     * instantiates the new object
     */
    public ArrayData() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(FileLocator.urlPropertiesFileLocation));
            String bUrl = p.getProperty("baseURL");
            baseURL = bUrl;
        } catch (Exception e) {
        }
    }

    /**
     *
     * @return the list of names of the library files needed for the current array to process
     */
    public ArrayList<String> getLibraryFileNames() {
        ArrayList<String> list = new ArrayList<String>();

        if (arrayType.equals("3'array")) {
            list.add(libraryFile); //return the only cdf file
        } else {
            // add pgf
            String pgf = libraryFile;

            //add clf
            String clf = pgf.replace(".pgf", ".clf");

            list.add(pgf);
            list.add(clf);
            if (arrayType.equals("gene")) {
                //add .bgp
                String bgp = pgf.replace(".pgf", ".bgp");
                list.add(bgp);
            } else if (arrayType.equals("exon")) {
                //add .antigenomic.bgp
                String bgp = pgf.replace(".pgf", ".antigenomic.bgp");
                list.add(bgp);
            }
        }

        return list;
    }

    /**
     *
     * @return the list of URLs of the libraries which are needed by the current Array
     */
    public ArrayList<String> getLibraryFileURLs() {
        ArrayList<String> list = getLibraryFileNames();
        ArrayList<String> urls = new ArrayList<String>();
        for (String name : list) {
            urls.add(getURLOf(name));
        }
        return urls;
    }

    /**
     * Takes a library file name and returns its corresponding URL
     * @param libraryFile the library file whose url is to be found out
     * @return the URL of the libraryFile supplied
     */
    public String getURLOf(String libraryFile) {
        if (libraryFile.toLowerCase().endsWith(".cdf")) {
            String url = baseURL + libraryFile.replace(".cdf", ".zip");
            return url;
        }

        return baseURL + libraryFile + ".gz";
    }

    /**
     *
     * @return true if the current array is 3' array
     */
    public boolean isCDF() {
        return arrayType.equals("3'array");
    }

    @Override
    public String toString() {
        return "[A:" + arrayName + "," + libraryFile + "," +
                annotationFile + "," +
                species + "," +
                arrayType + "]";
    }

    public int compareTo(Object o) {
        ArrayData d = (ArrayData) o;
        return this.arrayName.compareTo(d.arrayName);
    }
}
