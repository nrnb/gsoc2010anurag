/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import data.FileLocator;
import downloader.Downloader;
import extractor.ZipExtractor;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 *
 * @author Anurag Sharma, the user
 */
public class TempClass {

    public static void main(String[] args) throws MalformedURLException, IOException {
        Downloader d = new Downloader();
        d.download("http://localhost/test.zip");
        d.waitFor();
        File file = d.getOutputFile();
        if (file.exists()) {
            System.out.println("File Downloaded ...");
            ZipExtractor e = new ZipExtractor();
            e.extract(d.getOutputFile(), FileLocator.home_dir);
            System.out.println("DONE--");
        }
    }
}
