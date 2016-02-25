package View;

import Model.Model;
import Model.LineSeg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by cassiehanyu on 2016-02-18.
 */
public class DoodleMenuBar extends JMenuBar{
    private Model model;
    private JMenu menu;
    private JMenuItem menuItem;
    private JMenuItem saveMenuItem, openMenuItem, newMenuItem, exitMenuItem;
    private JRadioButtonMenuItem radioButtonMenuItem;

    ArrayList<ArrayList<Integer> > test;

    public DoodleMenuBar(Model model){
        this.model = model;

        test = new ArrayList<>();
        test.add(new ArrayList<>());
        test.get(0).add(new Integer(4));
        test.get(0).add(new Integer(5));
        test.get(0).add(new Integer(7));
        test.get(0).add(new Integer(8));

        menu = new JMenu("File");
        this.add(menu);

        newMenuItem = new JMenuItem("New Doodle");
        menu.add(newMenuItem);

        saveMenuItem = new JMenuItem("Save File...");
        menu.add(saveMenuItem);

        openMenuItem = new JMenuItem("Open File...");
        menu.add(openMenuItem);

        menu.add(new JPopupMenu.Separator());

        exitMenuItem = new JMenuItem("Exit");
        menu.add(exitMenuItem);

        menu = new JMenu("View");
        this.add(menu);

        ButtonGroup buttonGroup = new ButtonGroup();
        radioButtonMenuItem = new JRadioButtonMenuItem("Fit Window Size");
        radioButtonMenuItem.setSelected(true);

//        radioButtonMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("1111111111");
//            }
//        });
        buttonGroup.add(radioButtonMenuItem);

        menu.add(radioButtonMenuItem);

        radioButtonMenuItem = new JRadioButtonMenuItem("Full Size");
//        radioButtonMenuItem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("2222222222");
//            }
//        });
        buttonGroup.add(radioButtonMenuItem);
        menu.add(radioButtonMenuItem);

        registerController();

    }

    public void registerController(){
        radioButtonMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("1111111111");
            }
        });

        radioButtonMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("2222222222");
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sb = "TEST CONTENT";
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("./doc"));
                int retrival = chooser.showSaveDialog(null);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile() + ".ser");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        ArrayList<ArrayList<LineSeg> > result = model.getLines();
                        oos.writeObject(result);
                        oos.close();

//                        FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
//                        ObjectInputStream ois = new ObjectInputStream(fis);
//                        ArrayList<Integer> result = (ArrayList<Integer>) ois.readObject();
//                        ois.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sb = "TEST CONTENT";
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("./doc"));
                int retrival = chooser.showSaveDialog(null);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
//                        FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile() + ".ser");
//                        ObjectOutputStream oos = new ObjectOutputStream(fos);
//                        oos.writeObject(test);
//                        oos.close();

                        FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        ArrayList<ArrayList<Integer> > result = (ArrayList<ArrayList<Integer> >) ois.readObject();
//                        ArrayList<ArrayList<LineSeg> > result = (ArrayList<ArrayList<LineSeg> >) ois.readObject();
                        ois.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


    }
}
