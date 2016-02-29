package View;

import DataHelper.SizeState;
import DataHelper.Thickness;
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
    private JMenu filemenu,viewmenu;
    private JMenuItem menuItem;
    private JMenuItem saveMenuItem, openMenuItem, newMenuItem, exitMenuItem;
    private JRadioButtonMenuItem radioButtonMenuItem_fit,radioButtonMenuItem_full;

    ArrayList<ArrayList<Integer> > test;

    public DoodleMenuBar(Model model){
        this.model = model;

        test = new ArrayList<>();
        test.add(new ArrayList<>());
        test.get(0).add(new Integer(4));
        test.get(0).add(new Integer(5));
        test.get(0).add(new Integer(7));
        test.get(0).add(new Integer(8));

        filemenu = new JMenu("File");
        this.add(filemenu);

        newMenuItem = new JMenuItem("New Doodle");
        filemenu.add(newMenuItem);

        saveMenuItem = new JMenuItem("Save File...");
        filemenu.add(saveMenuItem);

        openMenuItem = new JMenuItem("Open File...");
        filemenu.add(openMenuItem);

        filemenu.add(new JPopupMenu.Separator());

        exitMenuItem = new JMenuItem("Exit");
        filemenu.add(exitMenuItem);

        viewmenu= new JMenu("View");
        this.add(viewmenu);

        ButtonGroup buttonGroup = new ButtonGroup();
        radioButtonMenuItem_fit = new JRadioButtonMenuItem("Fit Window Size");
        radioButtonMenuItem_fit.setSelected(true);
        buttonGroup.add(radioButtonMenuItem_fit);
        viewmenu.add(radioButtonMenuItem_fit);

        radioButtonMenuItem_full = new JRadioButtonMenuItem("Full Size");
        buttonGroup.add(radioButtonMenuItem_full);
        viewmenu.add(radioButtonMenuItem_full);

        registerController();

    }

    public void registerController(){
        radioButtonMenuItem_fit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                model.setSizeState(SizeState.FITSIZE);
                viewmenu.setSelected(false);
                System.out.println("1111111111");
            }
        });

        radioButtonMenuItem_full.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                model.setSizeState(SizeState.FULLSIZE);
                viewmenu.setSelected(false);
                System.out.println("2222222222");

            }
        });

        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.init();
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
                int retrival2 = chooser.showSaveDialog(null);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile() + ".ser");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        ArrayList<Integer> color = model.getLinesColors();
                        ArrayList<Thickness> thicknesses = model.getLinesThickness();
                        ArrayList<ArrayList<LineSeg> > lines = model.getLines();
                        oos.writeObject(color);
                        oos.writeObject(thicknesses);
                        oos.writeObject(lines);
                        oos.close();

//                        FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
//                        ObjectInputStream ois = new ObjectInputStream(fis);
//                        ArrayList<Integer> result = (ArrayList<Integer>) ois.readObject();
//                        ois.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if(retrival2 == JFileChooser.APPROVE_OPTION){
                    try {
                        File file = new File(chooser.getSelectedFile()+".txt");
                        FileWriter fileWriter = new FileWriter(file);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        model.saveFileToText(bufferedWriter);
//                        bufferedWriter.write(String.valueOf(33333));
//                        bufferedWriter.write("a test");
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    }catch (Exception ex){
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
                int retrival = chooser.showOpenDialog(null);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
                        if(chooser.getSelectedFile().toString().contains("txt")) {
                            File file = new File(chooser.getSelectedFile().toString());
                            FileReader fileReader = new FileReader(file);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);
                            model.readFromText();
                            String line = bufferedReader.readLine();
                            while (line != null) {
                                System.out.println(line);
                                int c = Integer.parseInt(line);
                                model.readColorsFromText(c);

                                line = bufferedReader.readLine();
                                System.out.println(line);
                                String thickness = line;
                                model.readThicknessFromText(thickness);

                                line = bufferedReader.readLine();
                                System.out.println(line);
                                int size = Integer.parseInt(line);

                                for (int i = 0; i < size; i++) {
                                    line = bufferedReader.readLine();
                                    System.out.println(line);
                                    String[] lineSeg = line.split(" ");
                                    System.out.println(lineSeg);
                                    model.readLineSegFromText(lineSeg,i);
                                }
                                line = bufferedReader.readLine();

                            }
                            model.stopReadingFromText();
                            bufferedReader.close();
                        }else {

                            FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
                            ObjectInputStream ois = new ObjectInputStream(fis);
                            ArrayList<Integer> colors = (ArrayList<Integer>) ois.readObject();
                            ArrayList<Thickness> thicknesses = (ArrayList<Thickness>) ois.readObject();
                            ArrayList<ArrayList<LineSeg> > lines = (ArrayList<ArrayList<LineSeg> >) ois.readObject();
                            ois.close();
                            model.loadFileFromBinary(colors,thicknesses,lines);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


    }
}
