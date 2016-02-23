package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by cassiehanyu on 2016-02-18.
 */
public class PlayBack extends JPanel{

//    private JLabel play;
    private JButton play,start,end;
    private JSlider timeline;
    private Box hbox;
    private Border empty;

    public PlayBack(){
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

        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

    }
}
