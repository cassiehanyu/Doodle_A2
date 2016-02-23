package Model;


/**
 * Created by cassiehanyu on 2016-02-19.
 */
public class LineSeg {
//    private double curPos_x_frac;
//    private double curPos_y_frac;
//    private double prePos_x_frac;
//    private double prePos_y_frac;
    private int oldX;
    private int oldY;
    private int currentX;
    private int currentY;
    private int color;

    public LineSeg(int x, int y, int ox, int oy){
        this.currentX = x;
        this.currentY = y;
        this.oldX = ox;
        this.oldY = oy;
    }

    public int getCurrentX(){
        return currentX;
    }

    public int getCurrentY(){
        return currentY;
    }

    public int getOldX(){
        return oldX;
    }

    public int getOldY(){
        return oldY;
    }


}
