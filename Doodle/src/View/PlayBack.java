package View;

import Model.Model;
import Model.IView;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

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
    Border raised = BorderFactory.createBevelBorder(BevelBorder.RAISED);

    ImageIcon startImage;
    ImageIcon pauseImage;

    private Timer timer;
    private enum State{
        READY,
        RUNNIN,
        PAUSED,
        FINISHED;
    }
    private State state;
    private int total;

    public PlayBack(Model model){
        this.model = model;
        state = State.READY;

        this.setLayout(new GridLayout(1,1));
        hbox = Box.createHorizontalBox();

        this.add(hbox);

        startImage = new ImageIcon("pic/start.png");
        startImage = new ImageIcon(startImage.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        pauseImage = new ImageIcon("pic/pause.png");
        pauseImage = new ImageIcon(pauseImage.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        play = new JToggleButton();
        play.setIcon(startImage);
        play.setSelectedIcon(pauseImage);


        ImageIcon imageIcon = new ImageIcon("pic/skip-to-start.png");
        imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        start = new JButton(imageIcon);

        imageIcon = new ImageIcon("pic/skip-to-end.png");
        imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
        end = new JButton(imageIcon);



        timeline = new JSlider();
        timeline.setValue(0);
        timeline.setPaintTicks(false);
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
                timeline.setPaintTicks(true);
                if(model.getLineNum()!=0)
                    timeline.setMajorTickSpacing(100/(model.getLineNum()));
            }
        });

        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JToggleButton button = (JToggleButton) e.getSource();
                total = model.getTotalLineSeg();
                if(state == State.FINISHED){
                    state = State.READY;
                }
                switch (state){
                    case READY:
                        timer = new Timer(50 ,new SliderActionListener());
                        timer.start();
//                        play.setPressedIcon(pauseImage);
                        state = State.RUNNIN;
                        break;
                    case RUNNIN:
                        timer.stop();
//                        button.setIcon(startImage);
                        state = State.PAUSED;
                        break;
                    case PAUSED:
                        timer.restart();
//                        button.setIcon(pauseImage);
                        state = State.RUNNIN;
                        break;
                    default:
                        break;
                }
            }
        });

    }



    private class SliderActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e)
        {
            int strokeNum = model.playback();

            if(model.isPlaybackFinished()) {
                timeline.setValue(timeline.getMaximum());
                timer.stop();
                play.setSelected(false);
                state = State.FINISHED;
                timeline.setValue(timeline.getMinimum());
            }
            else{

                int line_index = model.getLine_index();
                int seg_index = model.getSeg_index();
                int lineNum = model.getLineNum();
                int totalSeg = model.getLineSegNum(line_index);

                System.out.println(strokeNum);


                double test2 = (double) timeline.getMaximum()/ (double) lineNum*(line_index-1);
                double test = ((double)(seg_index/totalSeg))* (timeline.getMaximum()/lineNum) + test2;

                double test3 = ((double)seg_index)/((double)totalSeg) * ((double)timeline.getMaximum()) / ((double)lineNum) + test2;
                System.out.println(test2);
                timeline.setValue((int)test3);
            }
        }
    }

}
