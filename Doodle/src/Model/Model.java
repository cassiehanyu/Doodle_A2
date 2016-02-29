package Model;

import DataHelper.SizeState;
import DataHelper.Thickness;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cassiehanyu on 2016-02-17.
 */
public class Model extends Object {

    private ArrayList<IView> views = new ArrayList<IView>();

    private int width = 617;
    private int height = 568;

    private LineSeg curLineSeg;
    private ArrayList<ArrayList<LineSeg> > lines;

    private int originalColor;
    private int colorSelected;
    private ArrayList<Integer> linesColors;

    private Thickness originalThickness;
    private Thickness selectedThickness;
    private ArrayList<Thickness> linesThickness;

    private SizeState sizeState;
//    int lineNum;

    private int line_index, seg_index;
    private int lineNum;
    private double timeSlice;
//    int segIndex = -1;

    private BufferedImage image;
    private Graphics2D g2;

    private Timer timer;


//    public static final ArrayList<Integer> defalueColorRGB = new ArrayList<>(
//            Arrays.asList(3158323,5790043,8422020,11210005,15885897,16160377,16225053,
//                    16427861,16631177,16773632,16774240,16775055,897355,7061617,
//                    10343067,29116,3836360,8234968,47601,4507636,9361656,
//                    9453464,10775469,12358852,15622301,15962555,16302296,16504806));


    public static final ArrayList<Integer> defalueColorRGB = new ArrayList<>(
            Arrays.asList(3158323,5790043,8422020,11210005,15885897,16160377,16225053,
                    16427861,16631177,16773632,16774240,16775055,897355,7061617,
                    10343067,29116,3836360,8234968,4507636,9361656,
                    9453464,10775469,12358852,15622301,15962555,16302296));

    public Model(){
        curLineSeg = null;
        colorSelected = 3158323;
        selectedThickness = Thickness.ONE;
        lines = new ArrayList<>();
//        lines.add(new ArrayList<>());


        linesColors = new ArrayList<>();
//        linesColors.add(Color.black.getRGB());

        linesThickness = new ArrayList<>();
        sizeState = SizeState.FITSIZE;
        line_index = -1;
        seg_index = -1;
        timeSlice = 0;
        lineNum = 0;
        image = null;
    }

    public void init(){
        curLineSeg = null;
        colorSelected = 3158323;
        selectedThickness = Thickness.ONE;
        lines = new ArrayList<>();
//        lines.add(new ArrayList<>());


        linesColors = new ArrayList<>();
//        linesColors.add(Color.black.getRGB());

        linesThickness = new ArrayList<>();
        sizeState = SizeState.FITSIZE;
        line_index = -1;
        seg_index = -1;
        timeSlice = 0;
        image = null;
        lineNum = 0;
        updateAllViews();

    }

    //region save and load
    public void loadFileFromBinary(ArrayList<Integer> colors, ArrayList<Thickness> thicknesses, ArrayList<ArrayList<LineSeg> > lines){
        init();
        linesColors = colors;
        linesThickness = thicknesses;
        this.lines = lines;
        lineNum = lines.size();
        line_index = 0;
        seg_index = 0;
        updateAllViews();

    }

    public void saveFileToText(BufferedWriter bufferedWriter){
        try {
//            bufferedWriter.write("f");
            for(int i = 0; i < lineNum; i++){
                bufferedWriter.write(String.valueOf(linesColors.get(i)));
                bufferedWriter.newLine();
                bufferedWriter.write(linesThickness.get(i).toString());
                bufferedWriter.newLine();
                bufferedWriter.write(String.valueOf(lines.get(i).size()));
                bufferedWriter.newLine();
                for(int j = 0; j < lines.get(i).size(); j++){
                    bufferedWriter.write(String.valueOf(lines.get(i).get(j).getOldX())+" ");
                    bufferedWriter.write(String.valueOf(lines.get(i).get(j).getOldY())+" ");
                    bufferedWriter.write(String.valueOf(lines.get(i).get(j).getCurrentX())+" ");
                    bufferedWriter.write(String.valueOf(lines.get(i).get(j).getCurrentY())+" ");
                    bufferedWriter.write(String.valueOf(lines.get(i).get(j).getTime()));
                    bufferedWriter.newLine();
                }

            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void readFromText(){
        init();
    }

    public void readColorsFromText(int color){
        linesColors.add(new Integer(color));
    }

    public void readThicknessFromText(String thickness){
        linesThickness.add(Thickness.parseThickness(thickness));
    }



    public void readLineSegFromText(String[] lineSeg, int index){
        if(index == 0){
            lines.add(new ArrayList<LineSeg>());
        }
        lines.get(lines.size()-1).add(new LineSeg(Integer.parseInt(lineSeg[2]),Integer.parseInt(lineSeg[3]),
                Integer.parseInt(lineSeg[0]), Integer.parseInt(lineSeg[1]), Integer.parseInt(lineSeg[4])));
    }

    public void stopReadingFromText(){
        lineNum = lines.size();
        line_index = 0;
        seg_index = 0;
        updateAllViews();
    }
    //endregion


    //region getter setter
    public LineSeg getCurLineSeg(){
        return curLineSeg;
    }

    public void setColorSelected(int color){
        colorSelected = color;
        updateAllViews();
    }

    public int getColorSelected(){
        return colorSelected;
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

    public ArrayList<Integer> getLinesColors(){
        return linesColors;
    }

    public ArrayList<Thickness> getLinesThickness(){
        return linesThickness;
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



    public SizeState getSizeState(){
        return sizeState;
    }

    public void setSizeState(SizeState sizeState){
        this.sizeState = sizeState;
        updateAllViews();
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



    //region draw and save
    public void saveLineSeg(int x, int y, int ox, int oy, int time){
        if(!(line_index==-1) && !(seg_index==-1)){
            int segSize = lines.get(line_index).size();
            lines.get(line_index).subList(seg_index + 1, segSize).clear();
            if(seg_index == 0) {
                lines.subList(line_index, lines.size() - 1).clear();
                linesColors.subList(line_index,linesColors.size()-1).clear();
                linesThickness.subList(line_index, linesThickness.size()-1).clear();
            }else{
                lines.subList(line_index + 1, lines.size() - 1).clear();
                linesColors.subList(line_index+1,linesColors.size()-1).clear();
                linesThickness.subList(line_index+1, linesThickness.size()-1).clear();
            }
            timeSlice = 100;
            lineNum=lines.size()-1;
            line_index = -1;
            seg_index = -1;
        }
        curLineSeg = new LineSeg(x,y,ox,oy,time);
        lines.get(lines.size()-1).add(curLineSeg);
//        gameState = SizeState.DRAWING;
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
    //endregion


    //region playback
    public void playbackStart(){
        line_index = 0;
        seg_index = -1;
        curLineSeg = null;
        initImage();
    }

    public int play(){
        if(this.line_index < lines.size()){
            if(seg_index < lines.get(line_index).size()-1){
                seg_index++;
            }else{
                line_index++;
                seg_index = 0;
            }
        }
        if(line_index < lines.size()){
            System.out.println(line_index);
            double tickspacing = (double) 100/ (double) lines.size()*(line_index);
            timeSlice = ((double)seg_index)/((double)lines.get(line_index).size()) * ((double)100) / ((double)lines.size()) + tickspacing;

//            colorSelected = linesColors.get(line_index);
//            selectedThickness = linesThickness.get(line_index);
            curLineSeg = lines.get(line_index).get(seg_index);
        }

//        if (this.line_index < lines.size()) {
//            System.out.println(line_index);
//            double tickspacing = (double) 100/ (double) lines.size()*(line_index);
//            timeSlice = ((double)seg_index)/((double)lines.get(line_index).size()) * ((double)100) / ((double)lines.size()) + tickspacing;
//
//            colorSelected = linesColors.get(line_index);
//            selectedThickness = linesThickness.get(line_index);
//            seg_index++;
////            if(images.get(i).size() <= 0){
//            if (lines.get(line_index).size() <= 0) {
//                curLineSeg = null;
//                seg_index = 0;
//                line_index++;
//            } else {
//
//                if(seg_index != 0 || (seg_index == 0 && lines.get(line_index).size() == 1)){
//                    curLineSeg = lines.get(line_index).get(seg_index - 1);
//                }
//                if (seg_index >= lines.get(line_index).size()) {
//                    line_index++;
//                    seg_index = 0;
//                }
//            }
//        }
//        if (curLineSeg == null) {
//            initImage();
//        } else {
//            drawLine(curLineSeg.getOldX(), curLineSeg.getOldY(),
//                    curLineSeg.getCurrentX(), curLineSeg.getCurrentY());
//        }
//        updateAllViews();
        return 0;
    }

    public void playback(){
        playbackStart();
        timer = new Timer(6, new SliderActionListener());
        timer.start();
    }

    public boolean isPlaybackFinished(){
        boolean finished = line_index >= lines.size() || lines.size() == 0;

        if(finished) {
            line_index = -1;
            seg_index = -1;
//            segIndex = -1;
        }
        return finished;
    }
    //endregion

    //region move slider
    public void startMove(){
        originalColor = colorSelected;
        originalThickness = selectedThickness;
        this.line_index = 0;
        this.seg_index = 0;
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
    }
    //endregion


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

    private class SliderActionListener implements ActionListener{
        private int i = 6;
        private LineSeg prev;
        private int prevL;

        SliderActionListener(){
            prev = null;
            prevL = 0;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (prev == null) {
                initImage();
            } else {
                colorSelected = linesColors.get(prevL);
                selectedThickness = linesThickness.get(prevL);
                drawLine(prev.getOldX(), prev.getOldY(),
                        prev.getCurrentX(), prev.getCurrentY());
            }
            updateAllViews();
            if(isPlaybackFinished()) {
                timeSlice = 100;
                updateAllViews();
//                PlayBack.this.repaint();
                drawLine(prev.getOldX(), prev.getOldY(),
                        prev.getCurrentX(), prev.getCurrentY());
                updateAllViews();
                timer.stop();
            }else {
                prev = curLineSeg;
                prevL = line_index;
                play();
                int cur = 1;
                if(prev != null && prevL == line_index) {
                    cur = curLineSeg.getTime() - prev.getTime();
                }
                timer.setDelay(cur);

                if(i > 1000) {
                    i = i / 1000;
                }else {
                    i = i * 10;
                }
            }
        }

    }


}
