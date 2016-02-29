package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import DataHelper.SizeState;
import Model.Model;
import Model.IView;

/**
 * Created by cassiehanyu on 2016-02-19.
 */
public class DrawArea extends JComponent {
    private BufferedImage after;
//    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;
    private Model model;
    private int init, i;


    public DrawArea(Model model){
        super();
        this.model = model;
        setDoubleBuffered(false);
        i = 0;
//        this.setBackground(Color.white);
//        this.setPreferredSize(new Dimension(600,600));

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
                repaint();
                if(model.getSizeState() == SizeState.FULLSIZE){
                    //DrawArea.this.setPreferredSize(new Dimension(model.getImage().getWidth(), model.getImage().getHeight()));
                }else{
//                    if(getParent()!=null)
//                        DrawArea.this.setPreferredSize(new Dimension(getParent().getWidth(),getParent().getHeight()));
                }

//                if(after != null){
//                    DrawArea.this.setSize(new Dimension(after.getWidth(),after.getHeight()));
//                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g){
        int x = getX();
        int y = getY();

        Graphics2D g2d = (Graphics2D) g;

//        /* good method to translate image */
        BufferedImage before = model.getImage();
        if(model.getSizeState() == SizeState.FITSIZE) {
            int w = before.getWidth();
            int h = before.getHeight();
            after = new BufferedImage(getParent().getWidth(), getParent().getHeight(), BufferedImage.TYPE_INT_RGB);
            AffineTransform at = new AffineTransform();
            at.scale((double) getParent().getWidth()/(double)w, (double) getParent().getHeight()/(double)h);
            AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            after = scaleOp.filter(before, after);
            g.drawImage(after,0-getX(),0-getY(),null);
        }else {
            after = before;
            g.drawImage(after,0,0,null);
            DrawArea.this.setPreferredSize(new Dimension(model.getImage().getWidth(), model.getImage().getHeight()));
        }
//        g2d.setTransform(at);
//        g2d.drawImage(model.getImage(),at,this);
//        image = model.getImage().getScaledInstance(getSize().width, getSize().height,Image.SCALE_AREA_AVERAGING);
//        g.drawImage(model.getImage(),0,0,null);


//        g2d.drawImage(after,0-getX(),0-getY(),null);

//          g.drawImage(model.getImage(), 0, 0, null);
    }

    //region listeners
    private void registerListeners(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                init = (int) System.currentTimeMillis();
                oldX = e.getX();
                oldY = e.getY();
                currentX = e.getX();
                currentY = e.getY();
                model.drawStart();
                model.saveLineSeg(scaleX(currentX),scaleY(currentY),scaleX(oldX),scaleY(oldY),init);
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
                i = (int) System.currentTimeMillis();
                System.out.println(i - init);
                int temp = i - init;
                init = i;
                currentX = e.getX();
                currentY = e.getY();

//                if(g2 != null){
                    //here needs improve so that i can have lines with different thickness
//                    g2.drawLine(oldX,oldY,currentX,currentY);

                    /* saveLineSeg contains update view which will call repaint here */

//                    model.saveLineSeg(currentX,currentY,oldX,oldY);
                    model.saveLineSeg(scaleX(currentX),scaleY(currentY),scaleX(oldX),scaleY(oldY),i);

//                    drawLine();
//                    repaint();
                    oldX = currentX;
                    oldY = currentY;
//                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
//                System.out.println("ww: " + getWidth() + "hh: " + getHeight());
                int width = getWidth();
                repaint();
//                ColorPalette.this.setLayout(new GridLayout(0,2,((int)width/10),(int)(width/20)));
            }
        });
    }
    //endregion


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
