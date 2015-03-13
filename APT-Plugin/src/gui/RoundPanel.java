package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Custom JPanel with rounded corners
 * @author Anurag Sharma, the user
 */
public class RoundPanel extends JPanel {

    private JPanel insidePanel;

    /**
     * creates new object
     */
    public RoundPanel() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     *
     * @param insidePanel sets insidePanel within the current rounded panel
     */
    public RoundPanel(JPanel insidePanel) {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setInsidePanel(insidePanel);
    }

    /**
     *
     * @param p the panel to put within this rounded panel
     */
    public void setInsidePanel(JPanel p) {
        insidePanel = p;
        this.add(p, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        g.setColor(new Color(200, 190, 250));
        g.fillRoundRect(0, 0, width, height, 40, 40);

    }

    /**
     * independently tests the class
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500, 500);
        RoundPanel panel = new RoundPanel();

        f.add(panel);
        f.setVisible(true);
    }
}
