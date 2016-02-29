package View;

import Model.Model;
import Model.IView;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by cassiehanyu on 2016-02-29.
 */
public class ColorChoose extends JPanel{
    private JLabel curColor;
    double width = 580/21;

    private Model model;

    private Border raisedetched;
    private Border raisedBevel;
    private Border empty;
    private Border compound;

    public ColorChoose(Model model) {
        this.model = model;
        this.setPreferredSize(new Dimension((int) width, (int) width/2));
//        this.setPreferredSize(new Dimension(0,0));
        this.setLayout(new GridLayout(1,1));

//        this.setLayout(new BorderLayo);

        raisedBevel = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

//        empty = BorderFactory.createEmptyBorder(getHeight()/10,getHeight()/5,getHeight()/10,getHeight()/5);
//        compound = BorderFactory.createCompoundBorder(raisedetched,empty);
//
//        this.setBorder(compound);
//        this.setOpaque(true);
//        this.setBackground(Color.pink);
//        this.setPreferredSize(new Dimension((int)width,(int)width));

        curColor = new JLabel();
        curColor.setBorder(raisedBevel);
        curColor.setOpaque(true);
        curColor.setBackground(Color.pink);
        curColor.setPreferredSize(new Dimension((int) (width*1.5), (int) width));
        this.add(curColor);


        registerListeners();

    }


    private void registerListeners(){
        curColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color c = JColorChooser.showDialog(null, "Choose a Color", curColor.getBackground());
                if(c!=null) {
                    model.setColorSelected(c.getRGB());
                }
//                curColor.setBackground(c);
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("cww: " + getWidth() + "chh: " + getParent().getHeight());
                width = getWidth();
                empty = BorderFactory.createEmptyBorder(getHeight()/10,getHeight()/5,getHeight()/10,getHeight()/5);
                raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
                compound = BorderFactory.createCompoundBorder(raisedetched,empty);

                curColor.setPreferredSize(new Dimension(getParent().getHeight()/14,getParent().getHeight()/21));
                curColor.setBorder(raisedBevel);
                curColor.repaint();

                ColorChoose.this.setBorder(compound);

                repaint();

            }
        });

        model.addView(new IView() {
            @Override
            public void updateView() {
                curColor.setBackground(new Color(model.getColorSelected()));
            }
        });
    }
}
