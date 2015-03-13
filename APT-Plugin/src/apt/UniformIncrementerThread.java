package apt;

import javax.swing.JProgressBar;

/**
 * Uniformly increments the Progress Bar. It is required in some steps.
 * @author Anurag Sharma
 */
public class UniformIncrementerThread implements Runnable {

    private JProgressBar pbar;
    private int interval;
    private int count;
    private boolean running=false;

    /**
     * creates new object
     * @param bar the Progress bar where the progress is to be displayed
     * @param interval the interval at which the bar is to be updated
     * @param count the number of times the progress is to be increased by 1
     */
    public UniformIncrementerThread(JProgressBar bar, int interval, int count) {
        pbar = bar;
        this.interval = interval;
        this.count = count;
    }

    public void set(int interval,int count)
    {
        this.interval=interval;
        this.count=count;
    }

    public void start() {
        if (running) {
            return;
        }

        Thread t = new Thread(this);
        running = true;
        t.start();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        for (int i = 0; running && i < count; i++) {
            pbar.setValue(pbar.getValue() + 1);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
            }
        }
    }
}
