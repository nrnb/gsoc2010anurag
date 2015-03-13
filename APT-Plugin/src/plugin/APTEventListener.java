package plugin;

// next we need an APTEventListener Interface, also defined in the application

// it defines the callback methods on the object that is listening for this APTEvent.
// in this case we just want to listen for the output file being ready and do something when it is
// one method is defined outputFileReady() and each listener imiplements this method in its own way
public interface APTEventListener {
    void outputFileReady(APTEvent e);
}

