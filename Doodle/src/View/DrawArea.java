package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import Model.Model;
import Model.IView;

/**
 * Created by cassiehanyu on 2016-02-19.
 */
public class DrawArea extends JComponent {
    private Image image;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;
    private Model model;


    public DrawArea(Model model){
        this.model = model;
//        setDoubleBuffered(false);
//        this.setBackground(Color.white);
//        this.setPreferredSize(new Dimension(300,300));
        registerListeners();

        this.model.addView(new IView() {
            @Override
            public void updateView() {
                if(model.getCurLineSeg()!=null) {
                    drawLine();
                    repaint();
                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g){
        if(image == null){
            image = createImage(getSize().width,getSize().height);
            g2 = (Graphics2D) image.getGraphics();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            clear();

        }
//        g.setColor(Color.black);
//        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(image,0,0,null);
    }

    public void clear(){
        g2.setPaint(Color.pink);
        g2.fillRect(0,0,getSize().width,getSize().height);
        g2.setPaint(Color.black);
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
//
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
        g2.drawLine(oldX,oldY,currentX,currentY);
    }
}
