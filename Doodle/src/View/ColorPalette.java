package View;

import Model.Model;
import Model.IView;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by cassiehanyu on 2016-02-18.
 */
public class ColorPalette extends JPanel{

    private Model model;

    ArrayList<JLabel> colors = new ArrayList<>(Model.defalueColorRGB.size());

    private Border raisedetched;
    private Border empty;
    private Border compound;

    double height = 800/3.2;
    double width = 800/14;

    public ColorPalette(Model model){
        this.model = model;
//        setBackground(new Color(-));
        GridLayout gridLayout = new GridLayout(0,2,((int)width/10),(int)(width/20));
        this.setLayout(gridLayout);

        raisedetched = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

        initColorPalette();

        empty = BorderFactory.createEmptyBorder((int)width/20,(int)width/10,(int)width/20,(int)width/10);
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        compound = BorderFactory.createCompoundBorder(raisedetched,empty);
        this.setBorder(compound);

        model.addView(new IView() {
            @Override
            public void updateView() {
                repaint();
            }
        });

    }

    private void initColorPalette(){

        int totalDefaultColor = Model.defalueColorRGB.size();

        for(int i = 0; i < totalDefaultColor; i++){
            colors.add(i, new JLabel());
            colors.get(i).setBackground(new Color(Model.defalueColorRGB.get(i)));
            colors.get(i).setPreferredSize(new Dimension((int)(width/2), (int)(width/2.2)));
            colors.get(i).setOpaque(true);
            colors.get(i).setBorder(raisedetched);
            this.add(colors.get(i));
            colors.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel test = (JLabel)e.getSource();
                    Color test2 = test.getBackground();
                    int test3 = test2.getRGB();
                    model.setColorSelected(((JLabel)(e.getSource())).getBackground().getRGB());
                    System.out.println(((JLabel)(e.getSource())).getBackground().getRGB());
                }
            });

        }
    }


}
