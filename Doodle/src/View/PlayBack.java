package View;

import Model.Model;
import Model.IView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by cassiehanyu on 2016-02-18.
 */
public class PlayBack extends JPanel{

    private Model model;
//    private JLabel play;
    private JButton play,start,end;
    private JSlider timeline;
    private Box hbox;
    private Border empty;

    public PlayBack(Model model){
        this.model = model;

        this.setLayout(new GridLayout(1,1));
        hbox = Box.createHorizontalBox();

        this.add(hbox);

        ImageIcon imageIcon = new ImageIcon("pic/start.png");
        imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        play = new JButton(imageIcon);

        imageIcon = new ImageIcon("pic/skip-to-start.png");
        imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        start = new JButton(imageIcon);

        imageIcon = new ImageIcon("pic/skip-to-end.png");
        imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        end = new JButton(imageIcon);



        timeline = new JSlider();
        timeline.setValue(0);
        timeline.setPaintTicks(true);
//        timeline.setMajorTickSpacing(65);
//        timeline.setMajorTickSpacing(10);

        hbox.add(Box.createHorizontalStrut(30));
        hbox.add(play);
        hbox.add(Box.createHorizontalStrut(30));
        hbox.add(timeline);
        hbox.add(Box.createHorizontalGlue());
        hbox.add(start);
        hbox.add(Box.createHorizontalStrut(30));
        hbox.add(end);
        hbox.add(Box.createHorizontalStrut(30));

//        this.setPreferredSize(new Dimension(30,30));
        empty = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                                                    BorderFactory.createEmptyBorder(10,10,10,10));

//        empty = BorderFactory.createEmptyBorder(10,10,10,10);
        this.setBorder(empty);
//        this.setBackground(Color.BLACK);

        model.addView(new IView() {
            @Override
            public void updateView() {
                timeline.setMajorTickSpacing(100/(model.getLineNum() + 1));
            }
        });

        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.playback();
            }
        });

    }



    private class SliderActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            model.playback();
        }
    }

}
