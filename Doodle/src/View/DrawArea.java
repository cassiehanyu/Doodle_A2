package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.rmi.MarshalledObject;

import DataHelper.GameState;
import Model.Model;
import Model.IView;

/**
 * Created by cassiehanyu on 2016-02-19.
 */
public class DrawArea extends JComponent {
    private BufferedImage image;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;
    private Model model;


    public DrawArea(Model model){
        this.model = model;
        setDoubleBuffered(false);
//        this.setBackground(Color.white);
//        this.setPreferredSize(new Dimension(300,300));
        registerListeners();

        this.model.addView(new IView() {
            @Override
            public void updateView() {
                setFocusable(true);
                if(model.getCurLineSeg() != null){
                    drawLine();
//                    repaint();
                }
//                else{
//                    image = null;
//                }
                DrawArea.this.repaint();
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g){
//        super.paintComponent(g);
        if(image == null){
            image = (BufferedImage) createImage(getSize().width,getSize().height);
            g2 = (Graphics2D) image.getGraphics();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            clear();

        }
//        g.setColor(Color.black);
//        g.fillRect(0,0,getWidth(),getHeight());
//        g.drawImage(image,0,0,null);
          g.drawImage(model.getCurImage(), 0, 0, null);
    }

    public void clear(){
        g2.setPaint(Color.pink);
        g2.fillRect(0,0,getSize().width,getSize().height);
        model.saveImage(image);
//        g2.setPaint(Color.black);
        repaint();
    }

    private void registerListeners(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.drawStart();
                oldX = e.getX();
                oldY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                model.drawFinish();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

                if(g2 != null){
                    //here needs improve so that i can have lines with different thickness
//                    g2.drawLine(oldX,oldY,currentX,currentY);

                    /* saveLineSeg contains update view which will call repaint here */
                    model.saveLineSeg(currentX,currentY,oldX,oldY);
//                    model.saveImage(image);

//                    drawLine();
//                    repaint();
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    private void drawLine(){
        g2.setPaint(new Color(model.getColorSelected()));
        g2.setStroke(new BasicStroke(model.getSelectedThickness().getThickness()));

        int test = model.getCurLineSeg().getOldX();
        int test2 = model.getCurLineSeg().getOldY();
        int test3 = model.getCurLineSeg().getCurrentX();
        int test4 = model.getCurLineSeg().getCurrentY();


//        g2.drawLine(model.getCurLineSeg().getOldX(),model.getCurLineSeg().getOldY(),
//                model.getCurLineSeg().getCurrentX(),model.getCurLineSeg().getCurrentY());

        g2.drawLine(oldX,oldY,currentX,currentY);
        model.saveImage(image);
    }
}
