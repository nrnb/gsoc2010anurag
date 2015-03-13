package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Shows the list of titles of the screens which are available to be filled
 * @author Anurag Sharma
 */
public class LeftPanel extends JPanel {

    private Color backgroundColor;
    private Color foregroundColor;
    private JLabel[] labelList;
    private int highlightedIndex = 0;

    /**
     * Performs initialization
     * @param listOfElements the list of strings which it displays
     */
    public LeftPanel(ArrayList<String> listOfElements) {
        setBackground(backgroundColor);
        setForeground(foregroundColor);

        setLayout(new GridLayout(listOfElements.size(), 1));

        labelList = new JLabel[listOfElements.size()];

        for (int i = 0; i < listOfElements.size(); i++) {
            labelList[i] = new RoundLabel(listOfElements.get(i));
        }

        setBorder(BorderFactory.createLoweredBevelBorder());
    }

    /**
     * adds labels to the list
     */
    public void addLabels() {
        for (JLabel label : labelList) {
            label.setBackground(foregroundColor);
            label.setForeground(Color.BLACK);
            this.add(label);
        }
    }

    /**
     * highlights an element of the list
     * @param index the index of the element to hightlight
     */
    public void highlight(int index) {
        for (int i = 0; i < labelList.length; i++) {
            if (i == index) {
//                labelList[i].setForeground(foregroundColor);
                labelList[i].setBackground(backgroundColor);
            } else {
//                labelList[i].setForeground(backgroundColor);
                labelList[i].setBackground(foregroundColor);

            }
        }
        repaint();
    }

    /**
     * highlights the next element in the list with wrap around
     */
    public void highlightNext() {
        highlightedIndex = (highlightedIndex + 1) % labelList.length;
        highlight(highlightedIndex);
    }

    /**
     * highlights the previous element in the list with wrap around
     */
    public void highlightPrevious() {
        highlightedIndex = (highlightedIndex + labelList.length - 1) % labelList.length;
        highlight(highlightedIndex);
    }

    /**
     * independently tests this class
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(500, 500);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArrayList<String> list = new ArrayList<String>();
        list.add("Tippana");
        list.add("Sreevani");
        list.add("Anurag");
        list.add("Sharma");

        LeftPanel panel = new LeftPanel(list);
        panel.setForegroundColor(Color.red);
        panel.setBackgroundColor(Color.darkGray);
        panel.addLabels();

        panel.highlight(0);

//        panel.add(new RoundLabel("hehe !$# datsme"));

        f.add(panel);

        f.setVisible(true);

    }

    /**
     * @return the backgroundColor
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the foregroundColor
     */
    public Color getForegroundColor() {
        return foregroundColor;
    }

    /**
     * @param foregroundColor the foregroundColor to set
     */
    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
}

