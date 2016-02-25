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
                doodleMenuBar = new DoodleMenuBar(model);
                drawArea = new DrawArea(model);
                playBack = new PlayBack(model);
                colorPalette = new ColorPalette(model);
                thicknessChooser = new ThicknessChooser(model);
//                view = new View();
                Container container = mainFrame.getContentPane();
                container.setLayout(new BorderLayout());
                mainFrame.setJMenuBar(doodleMenuBar);

                Box vBox = Box.createVerticalBox();
                vBox.add(colorPalette);
//              vBox.add(Box.createVerticalGlue());
                vBox.add(thicknessChooser);
                vBox.add(Box.createVerticalGlue());

                mainFrame.add(vBox, BorderLayout.WEST);
                mainFrame.add(playBack, BorderLayout.SOUTH);
                container.add(drawArea,BorderLayout.CENTER);

//
                mainFrame.setVisible(true);
                mainFrame.setSize(new Dimension(800,800));
//
                mainFrame.setTitle("Doodle");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//                mainFrame.add(view);
            }
        });
    }

    public static void main(String[] args){
//        System.setProperty( " apple.laf.useScreenMenuBar " , " true " );
        Doodle doodle = new Doodle();
    }

}
