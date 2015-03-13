/*
 * BaseFrame.java
 * Created on 4 Jun, 2010, 5:55:55 PM
 */
package gui;

import apt.StreamReaderThread;
import data.ArrayData;
import data.FileLocator;
import downloader.SequenceDownloader;
import extractor.GZIPExtractor;
import extractor.ZipExtractor;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;
import plugin.APTEvent;
import plugin.PluginMain;

/**
 *
 * @author Anurag Sharma
 * This is the main GUI class which contains the base GUI to host various
 * other components. This represents the main window of the program.
 */
public class BaseFrame extends javax.swing.JFrame {

    private int currentScreen = 1;
    private LeftPanel leftPanel;
    private IntroductionPanel introPanel;
    private DatasetParametersPanel datasetPanel;
    private CELSelectionPanel celPanel;
    private LibrarySelectionPanel librarySelectionPanel;
    private InformationPanel informationPanel;
    private CommandOutputPanel commandOutputPanel;
    private Process aptProcess = null;
    private boolean aptProcessRunning = false;
    private File outputDirectory = null;
    private SplashScreenFrame splashFrame;
    private boolean commandSuccessfullyCompleted = false;
    private static PluginMain mainPluginClass = null;

    /** Creates new form BaseFrame provided with proper objects of LibrarySelectionPanel and DatasetParametersPanel class*/
    public BaseFrame(LibrarySelectionPanel lsp, DatasetParametersPanel dpp) {
        super();

        //try following commented code to change the Look and Feel as per the underlying operating system
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(BaseFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            Logger.getLogger(BaseFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(BaseFrame.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(BaseFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }

        initComponents();

        customInit(lsp, dpp);

    }

    /**
     * This functions does the initialization for various components to be used
     * @param libraryPanel object of LibrarySelectionPanel class
     * @param datasetPanel object of DatasetParametersPanel class
     */
    private void customInit(LibrarySelectionPanel libraryPanel, DatasetParametersPanel datasetPanel) {
        jProgressBar1.setVisible(false);

        //adding contents of the left panel
        ArrayList<String> list = new ArrayList<String>();
        list.add("      Introduction");
        list.add("      Dataset Parameters");
        list.add("      CEL files");
        list.add("      Library files");
        list.add("      Information");
        list.add("      Command Output");

        LeftPanel panel = new LeftPanel(list);
        panel.setForegroundColor(Color.white);
        panel.setBackgroundColor(new Color(200, 190, 250));
        panel.setBackground(Color.white);
        panel.addLabels();
        panel.highlight(0);
        leftPanel = panel;
        leftPanelParent.add(panel, BorderLayout.CENTER);


        this.introPanel = new IntroductionPanel();
        this.celPanel = new CELSelectionPanel();
        this.librarySelectionPanel = libraryPanel;
        this.informationPanel = new InformationPanel(celPanel, libraryPanel);
        this.commandOutputPanel = new CommandOutputPanel();
        this.datasetPanel = datasetPanel;

        ///////////////////main Panel//////////////
        RoundPanel rp1 = new RoundPanel();
        rp1.setInsidePanel(introPanel);

        RoundPanel rp2 = new RoundPanel();
        rp2.add(datasetPanel);

        RoundPanel rp3 = new RoundPanel();
        rp3.add(celPanel);

        RoundPanel rp4 = new RoundPanel();
        rp4.add(libraryPanel);

        RoundPanel rp5 = new RoundPanel();
        rp5.add(informationPanel);

        RoundPanel rp6 = new RoundPanel();
        rp6.add(commandOutputPanel);

        mainPanelParent.add(rp1, "card1");
        mainPanelParent.add(rp2, "card2");
        mainPanelParent.add(rp3, "card3");
        mainPanelParent.add(rp4, "card4");
        mainPanelParent.add(rp5, "card5");
        mainPanelParent.add(rp6, "card6");

        CardLayout layout = (CardLayout) mainPanelParent.getLayout();
        layout.show(mainPanelParent, "card" + currentScreen);
        System.out.println("showing " + "p" + currentScreen);
    }

    /**
     * Adds the flags to the apt-probeset-summarize command.
     * TODO: move this function to other APT related utility class in package apt
     * @param commandList the list of commands to be added to the apt-probeset-summarize command
     */
    private void addLibraryFlags(ArrayList<String> commandList) {
        if (librarySelectionPanel.isCDFSelected()) {
            commandList.add("-d");
            commandList.add(librarySelectionPanel.getCdfLibraryFile().getAbsolutePath());
            return;
        }

        if (librarySelectionPanel.isPgfSelected()) {
            commandList.add("-p");
            commandList.add(librarySelectionPanel.getPgfLibraryFile().getAbsolutePath());
        }
        if (librarySelectionPanel.isClfSelected()) {
            commandList.add("-c");
            commandList.add(librarySelectionPanel.getClfLibraryFile().getAbsolutePath());
        }
        if (librarySelectionPanel.isBgpSelected()) {
            commandList.add("-b");
            commandList.add(librarySelectionPanel.getBgpLibraryFile().getAbsolutePath());
        }
        if (librarySelectionPanel.isQccSelected()) {
            commandList.add("--qc-probesets");
            commandList.add(librarySelectionPanel.getQccLibraryFile().getAbsolutePath());
        }
        if (librarySelectionPanel.isPsSelected()) {
            commandList.add("-s");
            commandList.add(librarySelectionPanel.getPsLibraryFile().getAbsolutePath());
        }
    }

    /**
     * adds to the existing command for apt-probeset-summarize, the -o, --cel-files and -a switches
     * @param commandList the existing parameter list of the command apt-probeset-summarize
     * @param algos the algorithm to be used
     * @return returns whether this function was able to execute successfully without errors or not
     */
    private boolean addOtherFlags(ArrayList<String> commandList, ArrayList<String> algos) {
        commandList.add("-o");
//        commandList.add("output-gene");
        if (datasetPanel.isDatasetNameGiven()) {
            File selectedDirectory = datasetPanel.getOutputDirectoryFile();
            File tempOutputDir = new File(selectedDirectory.getAbsolutePath() + "/ExpressionInput/APT-output");
            if (!tempOutputDir.exists()) {
                boolean status = tempOutputDir.mkdirs();
                if (!status) {
                    return false;
                }
                //if everythign went perfect add this new directory as the APT output directory
            }
            commandList.add(tempOutputDir.getAbsolutePath());
            outputDirectory = tempOutputDir;

        } else {
            commandList.add(datasetPanel.getOutputDirectoryFile().getAbsolutePath());
            outputDirectory = datasetPanel.getOutputDirectoryFile();
        }

        commandList.add("--cel-files");
        commandList.add(FileLocator.resDirLocation + "/celfiles.txt");
        if (algos.size() > 0) {
            commandList.add("-a");
            commandList.add(algos.get(0));
        }
        if (algos.size() > 1) {
            commandList.add("-a");
            commandList.add(algos.get(1));
        }
        return true;
    }

    /**
     * Performs some post analysis steps like:
     * moving rma-sketch.summary.txt to its respective location by proper name
     * moving dabg.summary.txt to its respective location by proper name
     */
    private void postAnalysisSteps() {
        if (!datasetPanel.isDatasetNameGiven()) {
            return;
        }
        String datasetName = datasetPanel.getDatasetName();
        //copy rma-sketch.report.txt to ../exp.datasetName.txt

        try {
            File rmaFile = new File(outputDirectory.getAbsolutePath() + "/rma-sketch.summary.txt");
            File expInputDir = outputDirectory.getParentFile();
            File targetFile = new File(expInputDir.getAbsolutePath() + "/exp." + datasetName + ".txt");
            rmaFile.renameTo(targetFile);

            File dabgFile = new File(outputDirectory.getAbsolutePath() + "/dabg.summary.txt");
            targetFile = new File(expInputDir.getAbsolutePath() + "/stats." + datasetName + ".txt");
            dabgFile.renameTo(targetFile);

        } catch (Exception e) {
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        leftPanelParent = new javax.swing.JPanel();
        mainPanelParent = new javax.swing.JPanel();
        headingPanel = new javax.swing.JPanel();
        nextButton = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        backButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("APT");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logo.gif"))); // NOI18N

        javax.swing.GroupLayout logoPanelLayout = new javax.swing.GroupLayout(logoPanel);
        logoPanel.setLayout(logoPanelLayout);
        logoPanelLayout.setHorizontalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        logoPanelLayout.setVerticalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        getContentPane().add(logoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        leftPanelParent.setLayout(new java.awt.BorderLayout());
        getContentPane().add(leftPanelParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 210, 180));

        mainPanelParent.setLayout(new java.awt.CardLayout());
        getContentPane().add(mainPanelParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, 410, 360));

        headingPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout headingPanelLayout = new javax.swing.GroupLayout(headingPanel);
        headingPanel.setLayout(headingPanelLayout);
        headingPanelLayout.setHorizontalGroup(
            headingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
        );
        headingPanelLayout.setVerticalGroup(
            headingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );

        getContentPane().add(headingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 410, 50));

        nextButton.setMnemonic('N');
        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        getContentPane().add(nextButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 430, -1, -1));

        jProgressBar1.setStringPainted(true);
        getContentPane().add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 250, 20));

        backButton.setMnemonic('B');
        backButton.setText("Back");
        backButton.setEnabled(false);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        getContentPane().add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 430, 80, -1));

        quitButton.setMnemonic('Q');
        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });
        getContentPane().add(quitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 430, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Calls various functions on the press of 'next' button in different screens
     * @param evt the ActionEvent object which is automatically passed by system
     */
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if (currentScreen == 2) {   //dataset Panel screen

            if (!datasetPanel.validateForm()) {
                return;
            }
            final ActionListener changeScreenAction = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
//                    if (e == null)
                    {
                        nextButton.setEnabled(true);
                        jProgressBar1.setVisible(false);
                    }
                    backButton.setEnabled(true);
                    CardLayout layout = (CardLayout) mainPanelParent.getLayout();
                    layout.next(mainPanelParent);
                    leftPanel.highlightNext();
                    currentScreen++;
                }
            };

            final ArrayData selectedArrayData = datasetPanel.getSelectedArrayData();
            final ArrayList<String> libraryFiles = datasetPanel.getNeededLibraryFiles();

            /*download the library files if necessary (DONE)
             *extract in a Local Database (DONE)
             *set that directory as the default directory in librarySelectionPanel
             *proceed to next screen
             */
            if (datasetPanel.isDownloadSelected()) {
                final ArrayList<String> libraryURLs = datasetPanel.getDownloadList();
                final SequenceDownloader sd = new SequenceDownloader();
//                sd.addActionListener(changeScreenAction);
                if (libraryURLs.size() > 0) {
                    nextButton.setEnabled(false);
                    new Thread() {

                        @Override
                        public void run() {
                            jProgressBar1.setString(null);
                            ArrayList<File> compressedLibraryFiles = sd.downloadList(libraryURLs, jProgressBar1);
                            String localDatabaseDir = DatasetParametersPanel.localDatabaseDirectory;

                            if (compressedLibraryFiles == null) {
                                JOptionPane.showMessageDialog(BaseFrame.this, "Error downloading library files from internet. Please make sure you are connected to internet.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                jProgressBar1.setVisible(false);
                                nextButton.setEnabled(true);
                                return;
                            }

//                        jProgressBar1.setString("Uncompressing...");

//                            ArrayList<String> libraryFiles = new ArrayList<String>();
//                            for (File f : compressedLibraryFiles) {
//                                String name = f.getName();
//                                String actual = "";
//                                if (name.toLowerCase().endsWith(".zip")) {  //in case of CDF
//                                    isCDF = true;
//                                    actual = name.toLowerCase().replace(".zip", ".cdf");
//                                } else {
//                                    actual = name.toLowerCase().replace(".gz", "");
//                                }
//                                libraryFiles.add(actual);
//                            }

                            File f = compressedLibraryFiles.get(0);
                            for (File file : compressedLibraryFiles) {
                                if (file.getName().toLowerCase().endsWith(".gz")) {
                                    GZIPExtractor.extract(file, localDatabaseDir);
                                } else if (file.getName().toLowerCase().endsWith(".zip")) {
                                    ZipExtractor.extract(file, localDatabaseDir);
                                }
                                System.out.println("delete status of " + file.getName() + " is " + file.delete());
                            }

                            datasetPanel.arrayComboAction();
                            changeScreenAction.actionPerformed(null);
                            if (selectedArrayData.isCDF()) {
                                librarySelectionPanel.selectCDFOption(libraryFiles);
                            } else {
                                librarySelectionPanel.selectPGFOption(libraryFiles);
                            }

                        }
                    }.start();
                } else {
                    changeScreenAction.actionPerformed(null);
                }

//                librarySelectionPanel.setDefaultValues(new File(DatasetParametersPanel.localDatabaseDirectory), -1);
            } else {
                changeScreenAction.actionPerformed(null);
            }
            if (selectedArrayData.isCDF()) {
                librarySelectionPanel.selectCDFOption(libraryFiles);
            } else {
                librarySelectionPanel.selectPGFOption(libraryFiles);
            }
        } else if (1 <= currentScreen && currentScreen < 4) {

//            jProgressBar1.setVisible(false);
            backButton.setEnabled(true);
            CardLayout layout = (CardLayout) mainPanelParent.getLayout();
            layout.next(mainPanelParent);
            leftPanel.highlightNext();
            currentScreen++;
            System.out.println("showing " + "p" + currentScreen);
        } else if (currentScreen == 4) {
            if (informationPanel.fillInformation() == 0) {
                nextButton.setText("Finish");
                nextButton.setMnemonic('F');

                CardLayout layout = (CardLayout) mainPanelParent.getLayout();
                layout.next(mainPanelParent);
                leftPanel.highlightNext();
                currentScreen++;
                System.out.println("showing " + "p" + currentScreen);
            }
        } else if (nextButton.getText().equals("Finish")) {   //Finish Clicked

            jProgressBar1.setString(null);
            jProgressBar1.setMinimum(0);
            jProgressBar1.setMaximum(100);
            jProgressBar1.setValue(0);
            jProgressBar1.setVisible(true); // display the progress Bar
            jProgressBar1.repaint();
            this.validate();
            this.repaint();
            nextButton.setEnabled(false);
            backButton.setEnabled(false);
            this.setCursor(Cursor.WAIT_CURSOR);

            currentScreen++;
            CardLayout layout = (CardLayout) mainPanelParent.getLayout();
            layout.next(mainPanelParent);
            leftPanel.highlightNext();


            try {

//                //apt-probeset-summarize -p HuEx-1_0-st-v2.r2.pgf -c HuEx-1_0-st-v2.r2.clf -b HuEx-1_0-st-v2.r2.antigenomic.bgp --qc-probesets HuEx-1_0-st-v2.r2.qcc -s HuEx-1_0-st-v2.r2.all.ps -a rma-sketch -a dabg -o output-gene --cel-files celfiles.txt
                System.out.println("processing with APT");
//                String aptPath = "./";// "../genmapp/apt-1.12.0-20091012-i386-intel-linux/bin";

                ArrayList<File> celFiles = celPanel.getCELFileList();
//                ArrayList<File> libFiles = screen2Panel.getLibraryFiles();
                ArrayList<String> algos = librarySelectionPanel.getAlgorithms();

                //making celfiles.txt
                PrintWriter out = new PrintWriter(FileLocator.resDirLocation + "/celfiles.txt");
                out.println("cel_files");
                for (File file : celFiles) {
                    out.println(file.getAbsolutePath());
                }
                out.close();

                Properties prop = new Properties();
                prop.load(new FileInputStream(FileLocator.aptPropertiesFileLocation));
                String aptCommand = FileLocator.home_dir + "/" + prop.getProperty("apt");

                ArrayList<String> commandList = new ArrayList<String>();
                commandList.add(aptCommand);
                addLibraryFlags(commandList);
                boolean status;
                status = addOtherFlags(commandList, algos);
                if (!status) {
                    JOptionPane.showMessageDialog(this, "Error creating subdirectories in the output directory specified. Please check if it has write permissions",
                            "Error Message", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                final String[] command = new String[commandList.size()];
                commandList.toArray(command);

                String fullCommandString = "";
                for (String ts : command) {
                    fullCommandString += ts + " ";
                }
                System.out.println("Command =\n" + fullCommandString);
                //apt-probeset-summarize.exe -p D:\gsoc\GraphicalAPT-1\G    eneArrayFiles\HuGene-1_0-st-v1.r4.pgf -c D:\gsoc\GraphicalAPT-1\GeneArrayFiles\HuGene-1_0-st-v1.r4.clf -b D:\gsoc\GraphicalAPT-1\GeneArrayFiles\HuGene-1_0-st-v1.r4.bgp --qc-probesets D:\gsoc\GraphicalAPT-1\GeneArrayFiles\HuGene-1_0-st-v1.r4.qcc -s D:\gsoc\GraphicalAPT-1\GeneArrayFiles\HuGene-1_0-st-v1.r4.all.ps -a rma-sketch -o output-gene --cel-files celfiles.txt

                new Thread() {

                    @Override
                    public void run() {
                        try {
                            aptProcess = Runtime.getRuntime().exec(command);
                            aptProcessRunning = true;
                            System.out.println("RUNNING APT");
                            //starting standard input and error stream readers
//                            StreamReaderThread inputReader = new StreamReaderThread(process.getInputStream());
//                            inputReader.start();

                            StreamReaderThread errorReader = new StreamReaderThread(BaseFrame.this, commandOutputPanel.getOutputPane(), jProgressBar1, aptProcess.getErrorStream(), datasetPanel.getOutputDirectoryFile().getAbsolutePath());
                            errorReader.start();

                            aptProcess.waitFor();

                            int status = aptProcess.exitValue();
                            if (status == 0) {
                                JOptionPane.showMessageDialog(BaseFrame.this, "Successfully processed the data",
                                        "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                                notifyCommandCompletion(true);
                            } else {
                                JOptionPane.showMessageDialog(BaseFrame.this, "Failed to process the data",
                                        "Failure", JOptionPane.ERROR_MESSAGE);
                                backButton.setEnabled(true);
//                                nextButton.setEnabled(true);
                                BaseFrame.this.setCursor(Cursor.DEFAULT_CURSOR);
                                notifyCommandCompletion(false);
                            }
                            System.out.println("FINISHED :)");

                            postAnalysisSteps();

                            jProgressBar1.setVisible(false);
//                            nextButton.setEnabled(true);
                            BaseFrame.this.setCursor(Cursor.DEFAULT_CURSOR);
//                            System.exit(0);

                        } catch (Exception e) {
                            System.out.println("exception:" + e);
                            JOptionPane.showMessageDialog(BaseFrame.this, "An error encountered", "Exception", JOptionPane.ERROR_MESSAGE);
                        } finally {
                            aptProcessRunning = false;
                        }

                    }
                }.start();
            } catch (Exception e) {
                System.out.println("Exception::" + e);
                JOptionPane.showMessageDialog(this, "An error encountered:" + e);
                jProgressBar1.setVisible(false);
//                nextButton.setEnabled(true);
                backButton.setEnabled(true);
                BaseFrame.this.setCursor(Cursor.DEFAULT_CURSOR);

            }
        }

    }//GEN-LAST:event_nextButtonActionPerformed

    /***
     * Moves to previous screen visible.
     * @param evt passed by system automatically
     */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed

        if (currentScreen == 6) {
            CardLayout layout = (CardLayout) mainPanelParent.getLayout();
            layout.previous(mainPanelParent);
            layout.previous(mainPanelParent);
            leftPanel.highlightPrevious();
            leftPanel.highlightPrevious();

            currentScreen -= 2;

            nextButton.setText("Next");
            nextButton.setMnemonic('N');
            nextButton.setEnabled(true);
            return;
        }
        if (1 < currentScreen && currentScreen <= 5) {
            CardLayout layout = (CardLayout) mainPanelParent.getLayout();
            layout.previous(mainPanelParent);
            leftPanel.highlightPrevious();

            if (currentScreen == 5)// if clicked on the last screen. change the Finish button caption to Next
            {
                nextButton.setText("Next");
                nextButton.setMnemonic('N');
            }

            currentScreen--;
            System.out.println("showing " + "p" + currentScreen);
        }

        if (currentScreen == 1) {
            backButton.setEnabled(false);
        }

    }//GEN-LAST:event_backButtonActionPerformed

    /**
     * Exits the program after confirming from the user and aborts the running command if necessary
     * @param evt
     */
    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        boolean exit = false;
        if (aptProcess != null && aptProcessRunning) { //if process is running and user clicked 'quit'
            int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to abort the process?",
                    "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                aptProcess.destroy();
                System.out.println("Process forcefully terminated");
//                System.exit(0);
                exit = true;
            }
        } else if (aptProcess != null && !aptProcessRunning) { // if process is finished and user clicked 'quit' then do nothing
//            System.exit(0);
            exit = true;
        } else if (aptProcess == null) {        // if process is not yet started and user clicked 'quit'
            int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?",
                    "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
            if (res == JOptionPane.YES_OPTION) {
//                System.exit(0);
                exit = true;
            }
        }

        if (exit) {
//            System.exit(0);   // changed for plugin only
            this.setVisible(false);
            this.dispose();

            fireOutputFileReadyEvent();
        }

    }//GEN-LAST:event_quitButtonActionPerformed

    public void notifyCommandCompletion(boolean success) {
        this.commandSuccessfullyCompleted = success;
    }

    public static void setMainPluginClass(PluginMain mainPlugin) {
        BaseFrame.mainPluginClass = mainPlugin;
    }

    private void fireOutputFileReadyEvent() {
        int exitStatus = -1;
        if (commandSuccessfullyCompleted) {
            exitStatus = APTEvent.SUCCESS;
        } else {
            exitStatus = APTEvent.FAILURE;
        }

        APTEvent event = new APTEvent(datasetPanel.getOutputDirectoryFile().getAbsolutePath(), exitStatus);
        if (mainPluginClass != null) {
            mainPluginClass.fireEventListener(event);
        }
    }

    /**
     * The main starting point for this program. Passes the control to SplashScreenFrame.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
//                new BaseFrame().setVisible(true);
                SplashScreenFrame.main(null);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel headingPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JPanel leftPanelParent;
    private javax.swing.JPanel logoPanel;
    private javax.swing.JPanel mainPanelParent;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton quitButton;
    // End of variables declaration//GEN-END:variables
}
