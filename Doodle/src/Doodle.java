import View.DoodleMenuBar;
import View.*;

import javax.swing.*;
import java.awt.*;
import Model.Model;
import Model.IView;
import sun.security.krb5.SCDynamicStoreConfig;

/**
 * Created by cassiehanyu on 2016-02-17.
 */
public class Doodle{
    private Model model;
    private JFrame mainFrame;
    private DoodleMenuBar doodleMenuBar;
//    private View view;
    private DrawArea drawArea;
    private PlayBack playBack;
    private ColorPalette colorPalette;
    private MySrollPane scrollPane;
    private ThicknessChooser thicknessChooser;
    private ColorChoose colorChoose;
    private JButton button1;

    public Doodle(){
        model = new Model();

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                mainFrame = new JFrame("Doodle");
                mainFrame.setSize(new Dimension(700,700));
//                mainFrame.setTitle("Doodle");

                Container container = mainFrame.getContentPane();
                container.setLayout(new BorderLayout());

                doodleMenuBar = new DoodleMenuBar(model);
                mainFrame.setJMenuBar(doodleMenuBar);

                colorChoose = new ColorChoose(model);
                Box vBox = Box.createVerticalBox();
                vBox.add(colorChoose);

                colorPalette = new ColorPalette(model);
//                vBox.add(Box.createVerticalStrut(1));
                vBox.add(colorPalette);

                thicknessChooser = new ThicknessChooser(model);
                vBox.add(thicknessChooser);
//                vBox.add(Box.createVerticalGlue());
                mainFrame.add(vBox, BorderLayout.WEST);


                playBack = new PlayBack(model);
                mainFrame.add(playBack, BorderLayout.SOUTH);



                drawArea = new DrawArea(model);
//                scrollPane = new JScrollPane(drawArea);
                scrollPane = new MySrollPane(model,drawArea);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                container.add(scrollPane,BorderLayout.CENTER);

                mainFrame.setVisible(true);
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//                mainFrame.add(view);
            }
        });
    }

    private void registerListener(){

    }

    public static void main(String[] args){
//        System.setProperty( " apple.laf.useScreenMenuBar " , " true " );
        Doodle doodle = new Doodle();
    }

}
