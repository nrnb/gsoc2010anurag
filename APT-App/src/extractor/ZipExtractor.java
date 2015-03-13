package extractor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipInputStream;

/**
 * ZIP compressed file extraction utility class
 * @author Anurag Sharma
 */
public class ZipExtractor {

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

            String outputFileName = gis.getNextEntry().getName();// gzippedFile.getName();
//            outputFileName = outputFileName.replace(".gz", "");
            outputFile = new File(outputDir + "/" + outputFileName);

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));

            byte[] buffer = new byte[1024];
            int d = 0;

            while ((d = in.read(buffer)) != -1) {
                out.write(buffer, 0, d);
            }

            out.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return outputFile;
    }

    public static void main(String[] args) {
        //just for testing
        File output = extract(new File("RAE230B.zip"), ".");
        System.out.println("file extracted to " + output.getAbsolutePath());
    }
}
