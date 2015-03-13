package gui;

import data.FileLocator;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/*
 * Screen1Panel.java
 *
 * Created on 5 Jun, 2010, 8:20:19 PM
 */
/**
 *
 * Responsible for presenting the user with components to locate various library files needed for processing of CEL files
 * @author Anurag Sharma
 */
public class LibrarySelectionPanel extends javax.swing.JPanel {

    private static final int COUNT = 5;
    private File selectedFile[] = new File[COUNT];
    private ArrayList<File>[] fileArray = new ArrayList[COUNT];
    private JComboBox[] comboList = new JComboBox[COUNT];
    private File defaultDirectory = new File(".");
    private ArrayList<Boolean> manuallyFilled = new ArrayList<Boolean>();
    private File selectedCDFFile;
    private File localDatabaseDir = new File(FileLocator.localDBDirLocation);

    /** Creates new form Screen1Panel */
    public LibrarySelectionPanel() {
        initComponents();
        customInit();
    }

    /**
     *
     * @return true if CDF option is selected
     */
    public boolean isCDFSelected() {
        return cdfChecker.isSelected();
    }

    /**
     *
     * @return true if PGF option is selected
     */
    public boolean isPgfSelected() {
        return pgfChecker.isSelected();
    }

    /**
     *
     * @return true if CLF option is selected
     */
    public boolean isClfSelected() {
        return clfChecker.isSelected();
    }

    /**
     *
     * @return true if BGP option is selected
     */
    public boolean isBgpSelected() {
        return bgpChecker.isSelected();
    }

    /**
     *
     * @return true if QCC option is selected
     */
    public boolean isQccSelected() {
        return qccChecker.isSelected();
    }

    /**
     *
     * @return true if PS option is selected
     */
    public boolean isPsSelected() {
        return psChecker.isSelected();
    }

    /**
     *
     * @return the selected CDF library file
     */
    public File getCdfLibraryFile() {
        return selectedCDFFile;
    }

    /**
     *
     * @return the selected PGF file
     */
    public File getPgfLibraryFile() {
        return selectedFile[0];
    }

    /**
     *
     * @return the selected CLF file
     */
    public File getClfLibraryFile() {
        return selectedFile[1];
    }

    /**
     *
     * @return the selected BGP file
     */
    public File getBgpLibraryFile() {
        return selectedFile[2];
    }

    /**
     *
     * @return the selected QCC file
     */
    public File getQccLibraryFile() {
        return selectedFile[3];
    }

    /**
     *
     * @return the PS library file
     */
    public File getPsLibraryFile() {
        return selectedFile[4];
    }

    /**
     * disables all library options other than CDF
     * @param cdfFileName the list having a single entry of the CDF file name
     */
    public void selectCDFOption(ArrayList<String> cdfFileName) {
        //enable CDF
        cdfLabel.setEnabled(true);
        cdfChecker.setEnabled(false);
        cdfChecker.setSelected(true);
        cdfTextField.setEnabled(true);
        cdfSelectorButton.setEnabled(true);
        jCheckBox2.setSelected(false);
        jCheckBox2.setEnabled(false);
        jCheckBox1.setSelected(true);
        jCheckBox1.setEnabled(false);

        String cdf = cdfFileName.get(0);
        System.out.println("Searching for " + cdf);
        File[] files = localDatabaseDir.listFiles();
        System.out.print("localDB=");
        for (File f : files) {
            System.out.print("[" + f.getName() + "]");
            if (f.getName().compareToIgnoreCase(cdf) == 0) {
                System.out.println("matched*");
                cdfTextField.setText(f.getName());
                selectedCDFFile = f;
            }
        }
        System.out.println();


        //disable all other library fields checkboxesof this page.
        pgfChecker.setEnabled(false);
        clfChecker.setEnabled(false);
        bgpChecker.setEnabled(false);
        qccChecker.setEnabled(false);
        psChecker.setEnabled(false);


        pgfChecker.setSelected(false);
        clfChecker.setSelected(false);
        bgpChecker.setSelected(false);
        qccChecker.setSelected(false);
        psChecker.setSelected(false);

        pgfCheckerActionPerformed(null);
        clfCheckerActionPerformed(null);
        bgpCheckerActionPerformed(null);
        qccCheckerActionPerformed(null);
        psCheckerActionPerformed(null);

    }

    /**
     * enables all library options and disables only CDF library option
     * @param lib the list of names of the library files (pgf, clf, bgp ...)
     */
    public void selectPGFOption(ArrayList<String> lib) {
        File[] files = new File(localDatabaseDir.getAbsolutePath()).listFiles();
        System.out.println("searching files:" + lib);
        boolean pgfFound = false, clfFound = false, bgpFound = false;

        for (File f : files) {
            for (String name : lib) {
                if (f.getName().compareToIgnoreCase(name) == 0) {
                    System.out.println("found " + f.getName());
                    if (name.endsWith(".pgf")) {
                        fillCombo2(0, f);
                        pgfFound=true;
                    } else if (name.endsWith(".clf")) {
                        fillCombo2(1, f);
                        clfFound=true;
                    } else if (name.endsWith(".bgp")) {
                        fillCombo2(2, f);
                        bgpFound=true;
                    } 
                }
            }
        }

        if(!pgfFound)
            fillCombo2(0, null);
        if(!clfFound)
            fillCombo2(1, null);
        if(!bgpFound)
            fillCombo2(2, null);

        //disable CDF
        cdfLabel.setEnabled(false);
        cdfChecker.setEnabled(false);
        cdfChecker.setSelected(false);
        cdfTextField.setEnabled(false);
        cdfTextField.setText("");
        cdfSelectorButton.setEnabled(false);

        //enable all other library fields checkboxesof this page.
        pgfChecker.setEnabled(true);
        clfChecker.setEnabled(true);
        bgpChecker.setEnabled(true);
        qccChecker.setEnabled(true);
        psChecker.setEnabled(true);

        jCheckBox1.setSelected(true);
        jCheckBox1.setEnabled(true);
        jCheckBox2.setSelected(true);
        jCheckBox2.setEnabled(true);

        //select first 3
        pgfChecker.setSelected(true);
        clfChecker.setSelected(true);
        bgpChecker.setSelected(true);
        qccChecker.setSelected(false);
        psChecker.setSelected(false);


        pgfCheckerActionPerformed(null);
        clfCheckerActionPerformed(null);
        bgpCheckerActionPerformed(null);

    }
//    public ArrayList<File> getLibraryFiles() {
//        ArrayList<File> list = new ArrayList<File>();
//
//        if (pgfChecker.isSelected()) {
//            list.add(selectedFile[0]);
//        }
//        if (clfChecker.isSelected()) {
//            list.add(selectedFile[1]);
//        }
//        if (bgpChecker.isSelected()) {
//            list.add(selectedFile[2]);
//        }
//        if (qccChecker.isSelected()) {
//            list.add(selectedFile[3]);
//        }
//        if (psChecker.isSelected()) {
//            list.add(selectedFile[4]);
//        }
//
////        for (int i = 0; i < COUNT; i++) {
////            list.add(selectedFile[i]);
////        }
//        return list;
//    }

    /**
     *
     * @return the list of the algorithms selected (rma-sketch and dabg)
     */
    public ArrayList<String> getAlgorithms() {
        ArrayList<String> list = new ArrayList<String>();
        if (jCheckBox1.isSelected()) {
            list.add("rma-sketch");
        }
        if (jCheckBox2.isSelected()) {
            list.add("dabg");
        }

        return list;
    }

    /**
     * performs initialization process
     */
    void customInit() {
        comboList[0] = jComboBox1;
        comboList[1] = jComboBox2;
        comboList[2] = jComboBox3;
        comboList[3] = jComboBox4;
        comboList[4] = jComboBox5;

        ArrayList<File> pgfFiles = new ArrayList<File>();
        ArrayList<File> clfFiles = new ArrayList<File>();
        ArrayList<File> bgpFiles = new ArrayList<File>();
        ArrayList<File> qccFiles = new ArrayList<File>();
        ArrayList<File> psFiles = new ArrayList<File>();

        fileArray[0] = pgfFiles;
        fileArray[1] = clfFiles;
        fileArray[2] = bgpFiles;
        fileArray[3] = qccFiles;
        fileArray[4] = psFiles;

        for (int i = 0; i < COUNT; i++) {
            manuallyFilled.add(false);
        }
    }

    /**
     * Creates a File chooser with specific filters and file description and returns
     * @param suffix the suffix of the files to filter
     * @param description the file description
     * @return the File chooser with filter for suffix and description set
     */
    JFileChooser getChooser(final String suffix, final String description) {

        JFileChooser chooser = new JFileChooser(defaultDirectory);
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(suffix);
            }

            @Override
            public String getDescription() {
                return description;
            }
        });
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        return chooser;
    }

    private void comboAction(JFileChooser chooser, int ind) {
        File file = chooser.getSelectedFile();
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            defaultDirectory = parentFile;
        }

        fillCombo(ind, file);
    }

    private void fillCombo2(int ind, File file) {
        manuallyFilled.set(ind, Boolean.FALSE);
//        setDefaultValues(defaultDirectory, ind);
//        System.out.println("selected file:" + file.getAbsolutePath());
        comboList[ind].removeAllItems();
        fileArray[ind].clear();
        if (file != null) {
            comboList[ind].addItem(file.getName());
            comboList[ind].setSelectedIndex(0);
            fileArray[ind].add(file);
            selectedFile[ind] = file;
        }
    }

    private void fillCombo(int ind, File file) {
        manuallyFilled.set(ind, Boolean.TRUE);
        setDefaultValues(defaultDirectory, ind);
        System.out.println("selected file:" + file.getAbsolutePath());
        comboList[ind].removeAllItems();
        comboList[ind].addItem(file.getName());
        comboList[ind].setSelectedIndex(0);
        fileArray[ind].clear();
        fileArray[ind].add(file);
        selectedFile[ind] = file;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        psSelectorButton = new javax.swing.JButton();
        qccSelectorButton = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        clfSelectorButton = new javax.swing.JButton();
        bgpSelectorButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        pgfSelectorButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jComboBox5 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        pgfChecker = new javax.swing.JCheckBox();
        clfChecker = new javax.swing.JCheckBox();
        bgpChecker = new javax.swing.JCheckBox();
        qccChecker = new javax.swing.JCheckBox();
        psChecker = new javax.swing.JCheckBox();
        cdfChecker = new javax.swing.JCheckBox();
        cdfLabel = new javax.swing.JLabel();
        cdfSelectorButton = new javax.swing.JButton();
        cdfTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        defaultDirSelectorButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(200, 190, 250));

        jPanel1.setBackground(new java.awt.Color(200, 190, 250));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Library Files Manually"));

        psSelectorButton.setText("...");
        psSelectorButton.setEnabled(false);
        psSelectorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                psSelectorButtonActionPerformed(evt);
            }
        });

        qccSelectorButton.setText("...");
        qccSelectorButton.setEnabled(false);
        qccSelectorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qccSelectorButtonActionPerformed(evt);
            }
        });

        jComboBox4.setEnabled(false);
        jComboBox4.setMaximumSize(new java.awt.Dimension(30, 26));
        jComboBox4.setMinimumSize(new java.awt.Dimension(30, 26));
        jComboBox4.setPreferredSize(new java.awt.Dimension(30, 26));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jComboBox3.setMaximumSize(new java.awt.Dimension(30, 26));
        jComboBox3.setMinimumSize(new java.awt.Dimension(30, 26));
        jComboBox3.setPreferredSize(new java.awt.Dimension(30, 26));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jComboBox2.setMaximumSize(new java.awt.Dimension(30, 26));
        jComboBox2.setMinimumSize(new java.awt.Dimension(30, 26));
        jComboBox2.setPreferredSize(new java.awt.Dimension(30, 26));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jComboBox1.setMaximumSize(new java.awt.Dimension(30, 26));
        jComboBox1.setMinimumSize(new java.awt.Dimension(30, 26));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel3.setText("BGP file");

        clfSelectorButton.setText("...");
        clfSelectorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clfSelectorButtonActionPerformed(evt);
            }
        });

        bgpSelectorButton.setText("...");
        bgpSelectorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bgpSelectorButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("PS file");
        jLabel5.setEnabled(false);

        jLabel1.setText("PGF file");

        jCheckBox1.setBackground(new java.awt.Color(200, 190, 250));
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("RMA-SKETCH");

        pgfSelectorButton.setText("...");
        pgfSelectorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pgfSelectorButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("CLF file");

        jCheckBox2.setBackground(new java.awt.Color(200, 190, 250));
        jCheckBox2.setSelected(true);
        jCheckBox2.setText("DABG");

        jComboBox5.setEnabled(false);
        jComboBox5.setMaximumSize(new java.awt.Dimension(30, 26));
        jComboBox5.setMinimumSize(new java.awt.Dimension(30, 26));
        jComboBox5.setPreferredSize(new java.awt.Dimension(30, 26));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });

        jLabel4.setText("QCC file");
        jLabel4.setEnabled(false);

        pgfChecker.setBackground(new java.awt.Color(200, 190, 250));
        pgfChecker.setSelected(true);
        pgfChecker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pgfCheckerActionPerformed(evt);
            }
        });

        clfChecker.setBackground(new java.awt.Color(200, 190, 250));
        clfChecker.setSelected(true);
        clfChecker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clfCheckerActionPerformed(evt);
            }
        });

        bgpChecker.setBackground(new java.awt.Color(200, 190, 250));
        bgpChecker.setSelected(true);
        bgpChecker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bgpCheckerActionPerformed(evt);
            }
        });

        qccChecker.setBackground(new java.awt.Color(200, 190, 250));
        qccChecker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qccCheckerActionPerformed(evt);
            }
        });

        psChecker.setBackground(new java.awt.Color(200, 190, 250));
        psChecker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                psCheckerActionPerformed(evt);
            }
        });

        cdfChecker.setBackground(new java.awt.Color(200, 190, 250));
        cdfChecker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdfCheckerActionPerformed(evt);
            }
        });

        cdfLabel.setText("CDF file");

        cdfSelectorButton.setText("...");
        cdfSelectorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdfSelectorButtonActionPerformed(evt);
            }
        });

        cdfTextField.setEditable(false);
        cdfTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdfTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cdfChecker)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(cdfLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pgfChecker)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(clfChecker)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bgpChecker)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(qccChecker)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(psChecker)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, 186, Short.MAX_VALUE)
                            .addComponent(cdfTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(psSelectorButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(qccSelectorButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(bgpSelectorButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(clfSelectorButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(pgfSelectorButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(cdfSelectorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cdfChecker, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cdfLabel)
                        .addComponent(cdfSelectorButton)
                        .addComponent(cdfTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pgfSelectorButton)
                    .addComponent(pgfChecker))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(clfSelectorButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(bgpSelectorButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(qccSelectorButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBox1)
                                    .addComponent(jCheckBox2)))
                            .addComponent(psSelectorButton)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(clfChecker)
                        .addGap(18, 18, 18)
                        .addComponent(bgpChecker, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(qccChecker, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(psChecker, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(72, 72, 72))
        );

        jPanel2.setBackground(new java.awt.Color(200, 190, 250));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Library File Folder"));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        defaultDirSelectorButton.setText("...");
        defaultDirSelectorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultDirSelectorButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(defaultDirSelectorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(defaultDirSelectorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * presents user for selecting the Directory
     */
    public void defaultDirAction() {
        //select folder
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return true;
            }

            @Override
            public String getDescription() {
                return "folder containg the library files";
            }
        });

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Select the folder having library files");

        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File dir = chooser.getSelectedFile();
            if (dir.isDirectory()) {
                defaultDirectory = dir;
                jTextField1.setText(dir.getAbsolutePath());
                System.out.println("selected directory " + dir.getName());
                setDefaultValues(dir, -1);
            }
        }
    }

    private void defaultDirSelectorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultDirSelectorButtonActionPerformed

        //select folder
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return true;
            }

            @Override
            public String getDescription() {
                return "folder containg the library files";
            }
        });

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Select the folder having library files");

        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File dir = chooser.getSelectedFile();
            if (dir.isDirectory()) {
                defaultDirectory = dir;
                jTextField1.setText(dir.getAbsolutePath());
                System.out.println("selected directory " + dir.getName());
                setDefaultValues(dir, -1);
            }
        }
    }//GEN-LAST:event_defaultDirSelectorButtonActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

        String path = jTextField1.getText();
        File dir = new File(path);
        if (!dir.exists()) {
            JOptionPane.showMessageDialog(this, "Directory does not exists", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            jTextField1.requestFocus();
            return;
        }

        if (!dir.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Selected file is not a directory", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            jTextField1.requestFocus();
            return;
        }

        setDefaultValues(dir, -1);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void pgfSelectorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pgfSelectorButtonActionPerformed

        JFileChooser chooser = getChooser(".pgf", "select PGF library file");
        int res = chooser.showOpenDialog(this);
        int ind = 0;
        if (res == JFileChooser.APPROVE_OPTION) {
            comboAction(chooser, ind);
        }

    }//GEN-LAST:event_pgfSelectorButtonActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
//        if(comboList[0].getItemCount()==0)
//            return ;
//        int ind = comboList[0].getSelectedIndex();
//        if (ind != -1) {
//            selectedFile[0] = fileArray[0].get(ind);
//        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void clfSelectorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clfSelectorButtonActionPerformed
        JFileChooser chooser = getChooser(".clf", "select CLF library file");
        int res = chooser.showOpenDialog(this);
        int ind = 1;
        if (res == JFileChooser.APPROVE_OPTION) {
            comboAction(chooser, ind);
        }
    }//GEN-LAST:event_clfSelectorButtonActionPerformed

    private void bgpSelectorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bgpSelectorButtonActionPerformed
        JFileChooser chooser = getChooser(".bgp", "select BGP library file");
        int res = chooser.showOpenDialog(this);
        int ind = 2;
        if (res == JFileChooser.APPROVE_OPTION) {
            comboAction(chooser, ind);
        }
    }//GEN-LAST:event_bgpSelectorButtonActionPerformed

    private void qccSelectorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qccSelectorButtonActionPerformed
        JFileChooser chooser = getChooser(".qcc", "select QCC library file");
        int res = chooser.showOpenDialog(this);
        int ind = 3;
        if (res == JFileChooser.APPROVE_OPTION) {
            comboAction(chooser, ind);
        }
    }//GEN-LAST:event_qccSelectorButtonActionPerformed

    private void psSelectorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_psSelectorButtonActionPerformed
        JFileChooser chooser = getChooser(".ps", "select PS library file");
        int res = chooser.showOpenDialog(this);
        int ind = 4;
        if (res == JFileChooser.APPROVE_OPTION) {
            comboAction(chooser, ind);
        }
    }//GEN-LAST:event_psSelectorButtonActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
//        int ind = comboList[1].getSelectedIndex();
//        if (ind != -1) {
//            selectedFile[1] = fileArray[1].get(ind);
//        }
//        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
//        int ind = comboList[2].getSelectedIndex();
//        if (ind != -1) {
//            selectedFile[2] = fileArray[2].get(ind);
//        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
//        int ind = comboList[3].getSelectedIndex();
//        if (ind != -1) {
//            selectedFile[3] = fileArray[3].get(ind);
//        }
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
//        int ind = comboList[4].getSelectedIndex();
//        if (ind != -1) {
//            selectedFile[4] = fileArray[4].get(ind);
//        }
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void setCheckerAction(JCheckBox checker, JComboBox combobox, JButton button, JLabel label) {
        if (checker.isSelected()) {
            combobox.setEnabled(true);
            button.setEnabled(true);
            label.setEnabled(true);
        } else {
            combobox.setEnabled(false);
            button.setEnabled(false);
            label.setEnabled(false);
        }
    }

    private void pgfCheckerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pgfCheckerActionPerformed
        setCheckerAction(pgfChecker, comboList[0], pgfSelectorButton, jLabel1);
    }//GEN-LAST:event_pgfCheckerActionPerformed

    private void clfCheckerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clfCheckerActionPerformed
        setCheckerAction(clfChecker, comboList[1], clfSelectorButton, jLabel2);
    }//GEN-LAST:event_clfCheckerActionPerformed

    private void bgpCheckerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bgpCheckerActionPerformed
        setCheckerAction(bgpChecker, comboList[2], bgpSelectorButton, jLabel3);
    }//GEN-LAST:event_bgpCheckerActionPerformed

    private void qccCheckerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qccCheckerActionPerformed
        setCheckerAction(qccChecker, comboList[3], qccSelectorButton, jLabel4);
    }//GEN-LAST:event_qccCheckerActionPerformed

    private void psCheckerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_psCheckerActionPerformed
        setCheckerAction(psChecker, comboList[4], psSelectorButton, jLabel5);        // TODO add your handling code here:
    }//GEN-LAST:event_psCheckerActionPerformed

    private void cdfSelectorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdfSelectorButtonActionPerformed
        JFileChooser chooser = getChooser(".cdf", "select CDF library file");
        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file.getName().toLowerCase().endsWith(".cdf")) {
                cdfTextField.setText(file.getName());
                selectedCDFFile = file;
            }
        }

    }//GEN-LAST:event_cdfSelectorButtonActionPerformed

    private void cdfTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdfTextFieldActionPerformed

        cdfSelectorButtonActionPerformed(evt);

    }//GEN-LAST:event_cdfTextFieldActionPerformed

    private void cdfCheckerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdfCheckerActionPerformed
        //no need
//        if(cdfChecker.isSelected())
//        {
//            pgfChecker.setSelected(false);
//            clfChecker.setSelected(false);
//            bgpChecker.setSelected(false);
//            qccChecker.setSelected(false);
//            psChecker.setSelected(false);
//        }
    }//GEN-LAST:event_cdfCheckerActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox bgpChecker;
    private javax.swing.JButton bgpSelectorButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cdfChecker;
    private javax.swing.JLabel cdfLabel;
    private javax.swing.JButton cdfSelectorButton;
    private javax.swing.JTextField cdfTextField;
    private javax.swing.JCheckBox clfChecker;
    private javax.swing.JButton clfSelectorButton;
    private javax.swing.JButton defaultDirSelectorButton;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JCheckBox pgfChecker;
    private javax.swing.JButton pgfSelectorButton;
    private javax.swing.JCheckBox psChecker;
    private javax.swing.JButton psSelectorButton;
    private javax.swing.JCheckBox qccChecker;
    private javax.swing.JButton qccSelectorButton;
    // End of variables declaration//GEN-END:variables

    /**
     * scans the local database directory or the one selected by the user and automatically locates the library files
     * @param dir the selected dir.
     * @param skipIndex (unused for now)
     */
    public void setDefaultValues(File dir, int skipIndex) {
        System.out.println("scanning array");
        //scan the contents of directory and appropriately fill the other fields
        File[] files = dir.listFiles();

        for (int i = 0; i < fileArray.length; i++) {

            if (manuallyFilled.get(i)) {
                continue;
            }
            fileArray[i].clear();
            comboList[i].removeAllItems();
        }

//        pgfFiles.clear();
//        clfFiles.clear();
//        bgpFiles.clear();
//        qccFiles.clear();
//        psFiles.clear();

        for (File file : files) {
            String n = file.getName().toLowerCase();
            if (n.endsWith(".pgf") && !manuallyFilled.get(0)) {
                fileArray[0].add(file);
            } else if (n.endsWith(".clf") && !manuallyFilled.get(1)) {
                fileArray[1].add(file);
            } else if (n.endsWith(".bgp") && !manuallyFilled.get(2)) {
                fileArray[2].add(file);
            } else if (n.endsWith(".qcc") && !manuallyFilled.get(3)) {
                fileArray[3].add(file);
            } else if (n.endsWith(".ps") && !manuallyFilled.get(4)) {
                fileArray[4].add(file);
            }
        }
        for (int i = 0; i < fileArray.length; i++) {
            if (manuallyFilled.get(i)) {
                continue;
            }
            if (fileArray[i].size() > 0) {
                for (File file : fileArray[i]) {
                    comboList[i].addItem(file.getName());
                }
                comboList[i].setSelectedIndex(0);
                selectedFile[i] = fileArray[i].get(0);
            }
        }
    }

    /**
     * independently tests the class
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new LibrarySelectionPanel());
        f.setVisible(true);
    }
}
