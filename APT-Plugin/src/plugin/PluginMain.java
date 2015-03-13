/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin;

import cytoscape.Cytoscape;
import cytoscape.plugin.CytoscapePlugin;
import cytoscape.util.CytoscapeAction;
import gui.BaseFrame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Anurag Sharma, the user
 */
public class PluginMain extends CytoscapePlugin {

    private ArrayList<APTEventListener> listenerList;
    private boolean outputFileReady = false;

    public PluginMain() {
        BaseFrame.setMainPluginClass(this);
        listenerList = new ArrayList<APTEventListener>();
        PluginMenuAction menuAction = new PluginMenuAction(this);
        // (2) Add the action to Cytoscape menu
        Cytoscape.getDesktop().getCyMenus().addCytoscapeAction((CytoscapeAction) menuAction);
    }

    public static void main(String[] args) {
        BaseFrame.main(null);
    }
// now you need to have methods for adding to and deleting frm the list of listeners

    public void addAPTEventListener(APTEventListener listener) {
        listenerList.add(listener);
    }

    public void removeAPTEventListener(APTEventListener listener) {
        listenerList.remove(listener);
    }

    /**
     * passes the output file ready event to all its listeners
     * @param event
     */
    public void fireEventListener(APTEvent event) {
        outputFileReady = true;
        for (APTEventListener listener : listenerList) {
            listener.outputFileReady(event);
        }
    }

    public boolean isOutputFileReady() {
        return outputFileReady;
    }

    public class PluginMenuAction extends CytoscapeAction {

        public PluginMenuAction(PluginMain plugin) {
            super("APT-Plugin");
            setPreferredMenu("Plugins");
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            JOptionPane.showMessageDialog(Cytoscape.getDesktop(), "Launching APT-plugin");

            BaseFrame.main(null);
        }
    }
}
