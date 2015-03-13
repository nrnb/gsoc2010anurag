
package extractor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

/**
 * GZIP compressed file extraction utility class
 * @author Anurag Sharma
 */
public class GZIPExtractor {

    /**
     * extracts a compressed file with GZIP algorithm
     * @param gzippedFile the compressed file
     * @param outputDir the location of the directory where the uncompressed file is to be placed
     * @return the uncompressed file
     */
    public static File extract(File gzippedFile, String outputDir) {
        File outputFile = null;
        try {
            String outputFileName = gzippedFile.getName();
            outputFileName = outputFileName.replace(".gz", "");
            outputFile = new File(outputDir + "/" + outputFileName);

            GZIPInputStream gis = new GZIPInputStream(new FileInputStream(gzippedFile));
            BufferedInputStream in = new BufferedInputStream(gis);

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
        File output=extract(new File("testFile.clf.gz"), ".");
        System.out.println("file extracted to "+output.getAbsolutePath());
    }
}
