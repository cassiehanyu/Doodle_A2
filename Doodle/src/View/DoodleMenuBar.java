package View;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by cassiehanyu on 2016-02-18.
 */
public class DoodleMenuBar extends JMenuBar{
    private JMenu menu;
    private JMenuItem menuItem;
    private JRadioButtonMenuItem radioButtonMenuItem;


    public DoodleMenuBar(){
        menu = new JMenu("File");
        this.add(menu);

        menuItem = new JMenuItem("New Doodle");
        menu.add(menuItem);

        menuItem = new JMenuItem("Save File...");
        menu.add(menuItem);

        menuItem = new JMenuItem("Open File...");
        menu.add(menuItem);

//        Action toggleAction = new AbstractAction("Full Size") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //AbstractButton button = (AbstractButton)e.getSource();
//                JMenuItem button = (JMenuItem)e.getSource();
//                if (button.isSelected()) {
//                    button.setText("Fit");
//                    // Start the action here
//                } else {
//                    button.setText("Full Size");
//                    // Stop the action here
//                }
//            }
//        };
        menu = new JMenu("View");
        this.add(menu);

//        JToggleButton toggleButton = new JToggleButton();
//        toggleButton.setSize(new Dimension(50,50));

//        menuItem = new JMenuItem(toggleAction);
//        menuItem.setText("1212121");

        ButtonGroup buttonGroup = new ButtonGroup();
        radioButtonMenuItem = new JRadioButtonMenuItem("Fit Window Size");
        radioButtonMenuItem.setSelected(true);

        radioButtonMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("1111111111");
            }
        });
        buttonGroup.add(radioButtonMenuItem);

        menu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("Full Size");
        radioButtonMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("2222222222");
            }
        });
        buttonGroup.add(radioButtonMenuItem);
        menu.add(radioButtonMenuItem);


    }
}
