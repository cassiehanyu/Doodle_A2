package View;

import DataHelper.Thickness;
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
import java.util.ArrayList;

/**
 * Created by cassiehanyu on 2016-02-19.
 */
public class ThicknessChooser extends JPanel{
    private ArrayList<JLabel> thickLabels;
    private Border raised,lowered, raisedEtched;
    private double width = 800/12;
    private Model model;

    public ThicknessChooser(Model model) {
        this.model = model;
        this.thickLabels = new ArrayList<>();
        raised = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        lowered = BorderFactory.createLoweredBevelBorder();

        this.setLayout(new GridLayout(0,1,0,3));
        initThicknessOptions();

        raisedEtched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        this.setBorder(raisedEtched);

        model.addView(new IView() {
            @Override
            public void updateView() {
                repaint();
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for(JLabel label : thickLabels){
                    label.setSize(new Dimension(getWidth()/2,getHeight()/5));
                }
            }

        });
    }

    private void initThicknessOptions(){
        for (Thickness t : Thickness.values()) {
//            button = new JButton();
            ImageIcon imageIcon = new ImageIcon("pic/line_width-line_pts.gif");
            imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance((int)width/2,20, Image.SCALE_SMOOTH));
            thickLabels.add(new JLabel(imageIcon));
            thickLabels.get(thickLabels.size()-1).setBorder(raised);
            thickLabels.get(thickLabels.size()-1).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel temp = (JLabel)e.getSource();
                    for(JLabel label : thickLabels){
                        label.setBorder(raised);
                    }
//                    if(temp.getBorder() == raised){
                    temp.setBorder(lowered);
//                    }else{
//                        temp.setBorder(raised);
//                    }
                    model.setSelectedThickness(t);
                }
            });
            this.add(thickLabels.get(thickLabels.size()-1));
        }
    }

//    private float scaleThickness(float value){
//        int area = model.getImage().getWidth() * model.getImage().getHeight();
//        double fraction = (double)area/(double)(getSize().width * getSize().height);
//        double result = (double)value * fraction;
//        return (float) result;
//    }
}
