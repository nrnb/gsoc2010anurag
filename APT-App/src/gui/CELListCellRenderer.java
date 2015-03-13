///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package gui;
//
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.FlowLayout;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import javax.swing.JCheckBox;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JPanel;
//import javax.swing.ListCellRenderer;
//
///**
// *
// * @author user
// */
//public class CELListCellRenderer extends JPanel implements ListCellRenderer {
//
//    private JCheckBox checkBox;
//    private String value;
//    private Color bColor,fColor=Color.black;
//    private final Color selectedBackground=new Color(200,180,255);
//
//    public CELListCellRenderer() {
//        checkBox = new JCheckBox();
//        setLayout(new FlowLayout());
//        add(checkBox);
//        add(new JLabel("hehe"));
//        setFocusable(true);
//        addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                checkBox.setSelected(!checkBox.isSelected());
//            }
//
//        });
//    }
//
//    private void addComponents(){
//
//    }
//    public boolean isSelected() {
//        return checkBox.isSelected();
//    }
//
//    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
////        checkBox.setSelected(isSelected);
////        bColor=isSelected?new Color(200,190,250):selectedBackground;
//        this.value=(String)value;
//
////        addComponents();
//        return this;
//    }
//
////    public void paintComponent(Graphics g) {
////        g.setColor(new Color(200, 190, 250));
////        g.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
////    }
//}
