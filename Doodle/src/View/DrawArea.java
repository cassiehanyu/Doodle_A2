package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.rmi.MarshalledObject;

import DataHelper.GameState;
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
        setDoubleBuffered(false);
//        this.setBackground(Color.white);
//        this.setPreferredSize(new Dimension(300,300));

        registerListeners();

        this.model.addView(new IView() {
            @Override
            public void updateView() {
//                setFocusable(true);
//                if(model.getCurLineSeg() != null){
//                    drawLine();
//                    repaint();
//                }
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
//
        }
        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.black);
//        g.fillRect(0,0,getWidth(),getHeight());
        model.setWidth(getSize().width);
        model.setHeight(getSize().height);
        int f = getSize().height;

        /* good method to translate image */
        BufferedImage before = model.getImage();
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage after = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        AffineTransform at = new AffineTransform();
        double s = (double) getWidth()/(double)w;
        at.scale((double) getWidth()/(double)w, (double) getHeight()/(double)h);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(before, after);
//        g2d.setTransform(at);
//        g2d.drawImage(model.getImage(),at,this);
//        image = model.getImage().getScaledInstance(getSize().width, getSize().height,Image.SCALE_AREA_AVERAGING);
//        g.drawImage(model.getImage(),0,0,null);

        g.drawImage(after,0,0,null);
//        g2d.drawImage(after,0,0,null);

//          g.drawImage(model.getCurImage(), 0, 0, null);
    }

    public void clear(){
        g2.setPaint(Color.pink);
        g2.fillRect(0,0,getSize().width,getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    //region listeners
    private void registerListeners(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
                currentX = e.getX();
                currentY = e.getY();
                model.drawStart();
                model.saveLineSeg(scaleX(currentX),scaleY(currentY),scaleX(oldX),scaleY(oldY));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
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

//                    model.saveLineSeg(currentX,currentY,oldX,oldY);
                    model.saveLineSeg(scaleX(currentX),scaleY(currentY),scaleX(oldX),scaleY(oldY));

//                    drawLine();
//                    repaint();
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }
    //endregion

//    private void drawLine(){
//        g2.setPaint(new Color(model.getColorSelected()));
//        g2.setStroke(new BasicStroke(model.getSelectedThickness().getThickness()));
//
//        int test = model.getCurLineSeg().getOldX();
//        int test2 = model.getCurLineSeg().getOldY();
//        int test3 = model.getCurLineSeg().getCurrentX();
//        int test4 = model.getCurLineSeg().getCurrentY();
//
//
//        g2.drawLine(model.getCurLineSeg().getOldX(),model.getCurLineSeg().getOldY(),
//                model.getCurLineSeg().getCurrentX(),model.getCurLineSeg().getCurrentY());
//
////        g2.drawLine(oldX,oldY,currentX,currentY);
////        model.saveImage(image);
//    }

    private int scaleX(int value){
        int width = model.getImage().getWidth();
        double fraction = (double)width/(double)getSize().width;
        double result = (double)value * fraction;
        return (int)result;
    }

    private int scaleY(int value){
        int height = model.getImage().getHeight();
        double fraction = (double)height/(double)getSize().height;
        double result = (double)value * fraction;
        return (int)result;
    }

    private int scaleArea(int value){
        int area = model.getImage().getWidth() * model.getImage().getHeight();
        double fraction = (double)area/(double)(getSize().width * getSize().height);
        double result = (double)value * fraction;
        return (int)result;
    }
}
