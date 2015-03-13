package downloader;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

/**
 * this is the class to download a series of links provided
 * @author Anurag Sharma
 */
public class SequenceDownloader {

    private ArrayList<ActionListener> listenerList = new ArrayList<ActionListener>();

    /**
     * downloads the series of links provided to it as a list
     * @param urlList the list of URLs to download
     * @param pbar the progress bar where the download progress is intended to be reflected
     * @return the list of files actually downloaded
     */
    public ArrayList<File> downloadList(ArrayList<String> urlList, final JProgressBar pbar) {
        ArrayList<File> downloadedFiles = new ArrayList<File>();

        pbar.setVisible(true);

        for (String url : urlList) {
            final String url2 = url;
            final Downloader d = new Downloader();

//            Runnable runnable = new Runnable() {
//
//                public void run() {
            try {
                System.out.println("download()");
                d.download(url2);
                System.out.println("showProgressIN()");
                d.showProgressIn(pbar);
                System.out.println("waitFor()");
                d.waitFor();
                System.out.println("return waitFor()");
                File outputFile = d.getOutputFile();
                if (outputFile == null) {
                    System.out.println("downloaded output file found NULL. returning null..");
                    return null; 
                } else {
                    downloadedFiles.add(outputFile);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(SequenceDownloader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SequenceDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
//                }
//            };
//
//            new Thread(runnable).start();


        }

        notifyListeners();

        return downloadedFiles;
    }

    //calls listeners only when downloads successfully.
    private void notifyListeners() {
        for (ActionListener actionListener : listenerList) {
            actionListener.actionPerformed(null);
        }
    }

    /**
     * Adds the listner which is called when the download is finished
     * @param lis the listener
     */
    public void addActionListener(ActionListener lis) {
        listenerList.add(lis);
    }
}
