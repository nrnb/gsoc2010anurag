package test;

import data.FileLocator;
import downloader.Downloader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * ZIP compressed file extraction utility class
 * @author Anurag Sharma
 */
public class MyZipExtractor {

    /**
     * extracts the ZIP compressed file
     * @param gzippedFile the ZIP compressed file
     * @param outputDir the directory where the uncompressed file is to be placed
     * @return the uncompressed file
     */
    public static File extract(File gzippedFile, String outputDir) {
        File outputFile = null;
        try {
            ZipInputStream gis = new ZipInputStream(new FileInputStream(gzippedFile));
            BufferedInputStream in = new BufferedInputStream(gis);
            ZipEntry entry = gis.getNextEntry();
            while (entry != null) {
                String outputFileName = entry.getName();// gzippedFile.getName();
//            outputFileName = outputFileName.replace(".gz", "");
                outputFile = new File(outputDir + "/" + outputFileName);
                if (entry.isDirectory()) {
                    outputFile.mkdir();
                } else {
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));

                    byte[] buffer = new byte[1024];
                    int d = 0;

                    while ((d = in.read(buffer)) != -1) {
                        out.write(buffer, 0, d);
                    }
                    out.close();
                    outputFile.setExecutable(true);
                    gis.closeEntry();
                }
                entry = gis.getNextEntry();
            }
            in.close();         //close ZipStream

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return outputFile;
    }

    public static void main(String[] args) throws MalformedURLException, IOException {
//        //just for testing
//        Downloader d=new Downloader();
//        d.download("http://localhost/RAE230B.zip");
//        d.waitFor();
//        File zipFile=d.getOutputFile();
//        File output = extract(zipFile,FileLocator.home_dir);
//        System.out.println("file extracted to " + output.getAbsolutePath());

        java.util.Properties properties = System.getProperties();
        properties.list(System.out);
    }
}
