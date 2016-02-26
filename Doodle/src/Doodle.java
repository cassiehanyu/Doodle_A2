import View.DoodleMenuBar;
import View.*;

import javax.swing.*;
import java.awt.*;
import Model.Model;
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
    private ThicknessChooser thicknessChooser;
    private JButton button1;

    public Doodle(){
        model = new Model();

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                mainFrame = new JFrame("Doodle");
                mainFrame.setSize(new Dimension(800,800));
//                mainFrame.setTitle("Doodle");

                Container container = mainFrame.getContentPane();
                container.setLayout(new BorderLayout());

                doodleMenuBar = new DoodleMenuBar(model);
                mainFrame.setJMenuBar(doodleMenuBar);

                colorPalette = new ColorPalette(model);
                Box vBox = Box.createVerticalBox();
                vBox.add(colorPalette);

                thicknessChooser = new ThicknessChooser(model);
                vBox.add(thicknessChooser);
                vBox.add(Box.createVerticalGlue());
                mainFrame.add(vBox, BorderLayout.WEST);


                playBack = new PlayBack(model);
                mainFrame.add(playBack, BorderLayout.SOUTH);



                drawArea = new DrawArea(model);
                container.add(drawArea,BorderLayout.CENTER);

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
