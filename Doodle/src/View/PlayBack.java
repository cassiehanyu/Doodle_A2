package View;

import Model.Model;
import Model.IView;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by cassiehanyu on 2016-02-18.
 */
public class PlayBack extends JPanel{

    private Model model;
//    private JLabel play;
    private JToggleButton play;
    private JButton start,end;
    private JSlider timeline;
    private Box hbox;
    private Border empty;

    ImageIcon startImage;
    ImageIcon pauseImage;

    private int width = 700;

    public PlayBack(Model model){
        this.model = model;

        this.setLayout(new GridLayout(1,1));
        hbox = Box.createHorizontalBox();

        this.add(hbox);
        startImage = new ImageIcon("pic/start.png");
        startImage = new ImageIcon(startImage.getImage().getScaledInstance(width/24,width/24,Image.SCALE_SMOOTH));
        pauseImage = new ImageIcon("pic/pause.png");
        pauseImage = new ImageIcon(pauseImage.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        play = new JToggleButton();
        play.setIcon(startImage);
        play.setEnabled(false);
        play.setSelectedIcon(pauseImage);

        ImageIcon imageIcon = new ImageIcon("pic/skip-to-start.png");
        imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(width/24,width/24,Image.SCALE_SMOOTH));
        start = new JButton(imageIcon);
        start.setEnabled(false);

        imageIcon = new ImageIcon("pic/skip-to-end.png");
        imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(width/24,width/24,Image.SCALE_SMOOTH));
        end = new JButton(imageIcon);
        end.setEnabled(false);



        timeline = new JSlider();
        timeline.setValue(0);
        timeline.setPaintTicks(false);
        timeline.setEnabled(false);
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

        empty = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                                                    BorderFactory.createEmptyBorder(10,10,10,10));

//        empty = BorderFactory.createEmptyBorder(10,10,10,10);
        this.setBorder(empty);
//        this.setBackground(Color.BLACK);

        registerListener();

        model.addView(new IView() {
            @Override
            public void updateView() {
                timeline.setPaintTicks(true);
                if(model.getLineNum()!=0) {
                    timeline.setEnabled(true);
                    timeline.setMajorTickSpacing(100 / model.getLineNum());
                    play.setEnabled(true);
                    start.setEnabled(true);
                    end.setEnabled(true);
                }
                else {
                    timeline.setEnabled(false);
                    timeline.setPaintTicks(false);
                    play.setEnabled(false);
                    start.setEnabled(false);
                    end.setEnabled(false);
                }
                int sliderValue = (int) model.getTimeSlice();
                timeline.setValue(sliderValue);
                if(sliderValue == timeline.getMaximum() || sliderValue == timeline.getMinimum() ){
                    play.setSelected(false);
                }else{
                    play.setSelected(true);
                }
            }
        });



    }


    private void registerListener(){
        timeline.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(timeline.isEnabled()) {
                    model.startMove();
                    model.movingSlider(timeline.getValue(), timeline.getMaximum());
                }
            }
//

            @Override
            public void mouseReleased(MouseEvent e) {
                if(timeline.isEnabled()) {
                    model.finishMove();
                }
            }

        });

        timeline.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(timeline.isEnabled()) {
                    model.movingSlider(timeline.getValue(), timeline.getMaximum());
                }
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getLineNum() >= 1) {
                    play.setSelected(true);
                    model.playback();
                }
            }
        });

        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(model.getLineNum()>=1) {
                    timeline.setValue(timeline.getMinimum());
                    model.startMove();
                    model.movingSlider(timeline.getMinimum(), timeline.getMinimum());
                    model.finishMove();
                }
            }
        });

        end.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(model.getLineNum() >= 1) {
                    timeline.setValue(timeline.getMaximum());
                    model.startMove();
                    model.movingSlider(timeline.getMaximum(), timeline.getMaximum());
                    model.finishMove();
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("pww: " + getWidth() + "phh: " + getHeight());
                width = getWidth();
//                init();
                repaint();
//                ColorPalette.this.setLayout(new GridLayout(0,2,((int)width/10),(int)(width/20)));
            }
        });

    }


}
