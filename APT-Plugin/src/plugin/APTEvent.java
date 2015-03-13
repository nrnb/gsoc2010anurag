package plugin;

// first, in the application, you need to have an event class, let's call it APTEvent
public class APTEvent {
// the event object can be used to communicate in this case we just want to know
//    when output file is ready

    private String outputPath; //location of the output directory
    private int exitStatus = FAILURE;
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;

    public APTEvent(String outputPath, int exitStatus) {
        this.exitStatus = exitStatus;
        this.outputPath = outputPath;
    }

    /**
     * Returns the output directory's absolute path
     * @return the output directory's absolute path
     */
    public String getOutputPath() {
        return outputPath;
    }

    public int getExitStatus() {
        return exitStatus;
    }

    public boolean isOutputReady() {
        return (exitStatus == SUCCESS);
    }
}
// that's it for the APTEvent class

