package Model;

import DataHelper.Thickness;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cassiehanyu on 2016-02-17.
 */
public class Model extends Object {

    private ArrayList<IView> views = new ArrayList<IView>();

    private int width;
    private int height;

    private LineSeg curLineSeg;
    private ArrayList<ArrayList<LineSeg> > lines;

    private int originalColor;
    private int colorSelected;
    private ArrayList<Integer> linesColors;

    private Thickness originalThickness;
    private Thickness selectedThickness;
    private ArrayList<Thickness> linesThickness;

//    int lineNum;

    private int line_index = -1, seg_index = -1, seg_num;
    private int lineNum;
    private double timeSlice;
//    int segIndex = -1;

    private BufferedImage image;
    private Graphics2D g2;


//
//    public static final ArrayList<Integer> defalueColorRGB = new ArrayList<>(
//            Arrays.asList(3158323,5790043,8422020,10987948,11210005,15885897,16160377,16500916, 16225053,
//                    16427861,16631177,16769215,16773632,16774240,16775055,16776139,897355,7061617,
//                    10343067,13428426,29116,3836360,8234968,12242410,47601,4507636,9361656,13101819,
//                    9453464,10775469,12358852,14271968,15622301,15962555,16302296,16504806));

    public static final ArrayList<Integer> defalueColorRGB = new ArrayList<>(
            Arrays.asList(3158323,5790043,8422020,11210005,15885897,16160377,16225053,
                    16427861,16631177,16773632,16774240,16775055,897355,7061617,
                    10343067,29116,3836360,8234968,47601,4507636,9361656,
                    9453464,10775469,12358852,15622301,15962555,16302296,16504806));

    public Model(){
        curLineSeg = null;
        colorSelected = 3158323;
        selectedThickness = Thickness.ONE;
//        gameState = GameState.BEFORE_DRAWING;
        lines = new ArrayList<>();
//        lines.add(new ArrayList<>());


        linesColors = new ArrayList<>();
//        linesColors.add(Color.black.getRGB());

        linesThickness = new ArrayList<>();
//        linesThickness.add(Thickness.ONE);

//        lineNum = 0;

//        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
//        g2 = (Graphics2D) image.getGraphics();
//        g2.setPaint(Color.pink);
//        g2.fillRect(0,0,image.getWidth(),image.getHeight());

//        images = new ArrayList<>();
//        images.add(new ArrayList<>());
    }

    //region getter setter
    public LineSeg getCurLineSeg(){
        return curLineSeg;
    }

    public void setColorSelected(int color){
        colorSelected = color;
    }

    public int getLineNum(){
        return lineNum;
    }

    public int getLine_index(){
        return line_index;
    }

    public int getSeg_index(){
        return seg_index;
    }

    public double getTimeSlice(){
        return timeSlice;
    }

    public int getLineSegNum(int i){
        return lines.get(i).size();
    }

    public ArrayList<ArrayList<LineSeg> > getLines(){
        return lines;
    }

    public void setLines(ArrayList<ArrayList<LineSeg> > setLines){
        lines = setLines;
    }

    public void setSelectedThickness(Thickness thickness){
        selectedThickness = thickness;
    }

    public Thickness getSelectedThickness(){
        return selectedThickness;
    }

    public int getWindowHeight()
    {
        return height;
    }

    public int getWindowWidth(){
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }


    public int getTotalLineSeg(){
//        int count = 0;
//        for(int i = 0; i <= lineNum; i++){
//            count += lines.get(i).size();
//        }
//        return count;
        return seg_num;
    }

    public BufferedImage getImage(){
        if(image == null){
            image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
//            image = new BufferedImage(400,400,BufferedImage.TYPE_INT_RGB);

            initImage();
        }
        return image;
    }

    //endregion




    public void saveLineSeg(int x, int y, int ox, int oy){
        if(!(line_index==-1) && !(seg_index==-1)){
            int segSize = lines.get(line_index).size();
            lines.get(line_index).subList(seg_index+1, segSize).clear();
            lines.subList(line_index+1,lines.size()-1).clear();
            linesColors.subList(line_index+1,linesColors.size()-1).clear();
            linesThickness.subList(line_index+1, linesThickness.size()-1).clear();
            timeSlice = 100;
            lineNum=lines.size()-1;
            line_index = -1;
            seg_index = -1;
        }
        curLineSeg = new LineSeg(x,y,ox,oy);
        lines.get(lines.size()-1).add(curLineSeg);
//        gameState = GameState.DRAWING;
        seg_num++;
        drawLine(ox,oy,x,y);
        updateAllViews();
    }



    public void drawStart(){
        lines.add(new ArrayList<>());
//        images.add(new ArrayList<>());
        linesColors.add(new Integer(colorSelected));
        linesThickness.add(selectedThickness);
    }

    public void drawFinish(){
        lineNum++;
        updateAllViews();
    }

    public void playbackStart(){
        line_index = 0;
        seg_index = 0;
        initImage();
    }

    public int playback(){
        if (this.line_index < lines.size()) {
            double tickspacing = (double) 100/ (double) lines.size()*(line_index);
            timeSlice = ((double)seg_index)/((double)lines.get(line_index).size()) * ((double)100) / ((double)lines.size()) + tickspacing;

            colorSelected = linesColors.get(line_index);
            selectedThickness = linesThickness.get(line_index);
            seg_index++;
//            if(images.get(i).size() <= 0){
            if (lines.get(line_index).size() <= 0) {
                curLineSeg = null;
                seg_index = 0;
                line_index++;
            } else {
//               curImage = images.get(i).get(j-1);
                curLineSeg = lines.get(line_index).get(seg_index - 1);
                if (seg_index >= lines.get(line_index).size()) {
                    line_index++;
                    seg_index = 0;
                }
            }
        }
        if (curLineSeg == null) {
            initImage();
        } else {
            drawLine(curLineSeg.getOldX(), curLineSeg.getOldY(),
                    curLineSeg.getCurrentX(), curLineSeg.getCurrentY());
        }


        updateAllViews();
        return 0;
    }

    public boolean isPlaybackFinished(){
        boolean finished = line_index >= lines.size() || lines.size() == 0;

        if(finished) {
            line_index = -1;
            seg_index = -1;
            seg_num = -1;
//            segIndex = -1;
        }
        return finished;
    }

    public void startMove(){
        originalColor = colorSelected;
        originalThickness = selectedThickness;
        this.line_index = 0;
        this.line_index = 0;
    }

    public void movingSlider(int value, int max){
        double tickSpace = (double)max / (double)lines.size();
        timeSlice = value;

        double _line = Math.ceil((double)value / tickSpace);
        double portion = (value - tickSpace * (_line-1))/ tickSpace;
        int line = (int) _line;

        int count = 0;
        initImage();
        if(line > 0 && line <= lines.size()) {
            for (int i = 0; i < line - 1; i++) {
                count += lines.get(i).size();
            }
            count += (int) (portion * lines.get(line - 1).size());
            for (this.line_index = 0; this.line_index < line; this.line_index++) {
                if(count <= 0) break;
                ArrayList<LineSeg> curLine = lines.get(this.line_index);
                colorSelected = linesColors.get(this.line_index);
                selectedThickness = linesThickness.get(this.line_index);
                for (this.seg_index = 0; this.seg_index < curLine.size(); this.seg_index++) {
                    LineSeg seg = curLine.get(this.seg_index);
                    drawLine(seg.getOldX(), seg.getOldY(), seg.getCurrentX(), seg.getCurrentY());
                    count--;
                    if (count <= 0) break;
                }
                if(count <= 0) break;
            }
        }
        updateAllViews();
    }


    public void finishMove(){
        colorSelected = originalColor;
        selectedThickness = originalThickness;
        this.line_index = -1;
        this.seg_index = -1;

    }

    //region MVC
    /** Add a new view of this triangle. */
    public void addView(IView view) {
        this.views.add(view);
        view.updateView();
    }

    /** Remove a view from this triangle. */
    public void removeView(IView view) {
        this.views.remove(view);
    }


    /** Update all the views that are viewing this triangle. */
    private void updateAllViews() {
        for (IView view : this.views) {
            view.updateView();
        }
    }
    //endregion

    //region image helper function
    private void drawLine(int oldX, int oldY, int currentX, int currentY){
        g2.setPaint(new Color(colorSelected));
        g2.setStroke(new BasicStroke(selectedThickness.getThickness()));

        g2.drawLine(oldX,oldY,currentX,currentY);

    }

    private void initImage(){
        g2 = (Graphics2D) image.getGraphics();
        g2.setPaint(Color.pink);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRect(0,0,image.getWidth(),image.getHeight());
    }
    //endregion


}
