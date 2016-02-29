package Model;


import java.io.Serializable;

/**
 * Created by cassiehanyu on 2016-02-19.
 */
public class LineSeg implements Serializable{
//    private double curPos_x_frac;
//    private double curPos_y_frac;
//    private double prePos_x_frac;
//    private double prePos_y_frac;
    private int oldX;
    private int oldY;
    private int currentX;
    private int currentY;
    private int color;
    private int time;

    public LineSeg(int x, int y, int ox, int oy, int time){
        this.currentX = x;
        this.currentY = y;
        this.oldX = ox;
        this.oldY = oy;
        this.time = time;
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

    public int getOldY()
    {
        return oldY;
    }

    public int getTime(){
        return time;
    }


}
