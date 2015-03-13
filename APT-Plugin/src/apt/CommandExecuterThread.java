///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package apt;
//
//import gui.BaseFrame;
//import javax.swing.JOptionPane;
//
///**
// *
// * @author user
// */
//public class CommandExecuterThread implements Runnable {
//
//    private String command;
//    private Thread thread;
//
//    public CommandExecuterThread(String com) {
//        command = com;
//        thread = new Thread(this);
//    }
//
//    public void start() {
//        thread.start();
//    }
//
//    public void run() {
//        try {
//            final Process process = Runtime.getRuntime().exec(command);
//            System.out.println("RUNNING APT");
//
//            //starting input stream reader
//            StreamReaderThread inputReader = new StreamReaderThread(process.getInputStream());
//            inputReader.start();
//
//            StreamReaderThread errorReader = new StreamReaderThread(process.getErrorStream());
//            errorReader.start();
//
//
//            process.waitFor();
//
//            int status = process.exitValue();
//            if (status == 0) {
//                JOptionPane.showMessageDialog(BaseFrame.this, "Successfully processed the data",
//                        "Congratulations", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(BaseFrame.this, "Failed to processe the data",
//                        "Failure", JOptionPane.ERROR_MESSAGE);
//            }
//            System.out.println("FINISHED :)");
//            System.exit(0);
//        } catch (Exception e) {
//            System.out.println("exception:" + e);
//            JOptionPane.showMessageDialog(BaseFrame.this, "An error encountered", "Exception",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//        jProgressBar1.setVisible(false);
//        nextButton.setEnabled(true);
//    }
//}
//
//}
