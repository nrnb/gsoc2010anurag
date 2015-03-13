//package plugin;
//
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
///**
// *
// * @author Anurag Sharma, the user
// */
//class APTApp {
//
//    public APTApp() {
//    }
//    public static final int OUTPUT_FILE_READY = 1;
//    private String outputPath; //location of the output file
//    List<APTEventListener> listeners = new ArrayList<APTEventListener>();
//
//// now you need to have methods for adding to and deleting frm the list of listeners
//    public void addAPTEventListener(APTEventListener listener) {
//
//        listeners.add(listener);
//
//    }
//
//    public void removeAPTEventListener(APTEventListener listener) {
//
//        listeners.remove(listener);
//
//    }
//
//    public void myfunction() {
//// now you need code that build the APTEVent and calls the OutputFIleReady() method on each of the listeners registered with the application
//// in the body of the application code somewhere you will have the check of whether the output file is ready, let's say it's done by checking a boolean flag,outputFileReady.
//        if (OUTPUT_FILE_READY==1) {
//
//            APTEvent e = new APTEvent(outputPath, OUTPUT_FILE_READY);
//
//// now iterate through the list of Listeners invoking their callback methods
//
//            Iterator it = listeners.iterator();
//
//            while (it.hasNext()) {
//
//                APTEventListener listener = (APTEventListener)it.next();//listeners.next();
//
//                listener.outputFileReady(e);
//
//            }
//
//        }
//
//// That is it.
//
//
//
//    }
//}
