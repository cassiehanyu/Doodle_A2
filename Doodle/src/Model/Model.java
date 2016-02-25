package Model;

import DataHelper.GameState;
import DataHelper.Thickness;
import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cassiehanyu on 2016-02-17.
 */
public class Model extends Object {

    private ArrayList<IView> views = new ArrayList<IView>();

    private int windowWidth;
    private int windowHeight;

    private LineSeg curLineSeg;
    private ArrayList<ArrayList<LineSeg> > lines;

    private Image curImage;
    private ArrayList<ArrayList<Image> > images;

    private int colorSelected;
    private ArrayList<Integer> linesColors;

    private Thickness selectedThickness;
    private ArrayList<Thickness> linesThickness;

    int lineNum;
    private GameState gameState;

    int line_index = 0, seg_index=0, seg_num;


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
        lines.add(new ArrayList<>());

        linesColors = new ArrayList<>();
        linesColors.add(Color.black.getRGB());

        linesThickness = new ArrayList<>();
        linesThickness.add(Thickness.ONE);

        lineNum = 0;

//        images = new ArrayList<>();
//        images.add(new ArrayList<>());
    }

    public LineSeg getCurLineSeg(){
        return curLineSeg;
    }

    public Image getCurImage(){
        return curImage;
    }

    public GameState getGameState() {
        return gameState;
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

    public int getColorSelected(){
        return colorSelected;
    }

    public int getTotalLineSeg(){
        int count = 0;
        for(int i = 0; i <= lineNum; i++){
            count += lines.get(i).size();
        }
        return count;
    }

    public boolean isPlaybackFinished(){
        boolean finished = line_index > lineNum || lineNum == 0;
        if(finished) {
            line_index = 0;
            seg_index = 0;
            seg_num = 0;
        }
        return finished;
    }


    public void saveLineSeg(int x, int y, int ox, int oy){
        curLineSeg = new LineSeg(x,y,ox,oy);
        lines.get(lines.size()-1).add(curLineSeg);
//        gameState = GameState.DRAWING;
        updateAllViews();
    }

    /* too slow */
//    public void saveImage(BufferedImage image){
//        curImage = image;
//        ColorModel cm = image.getColorModel();
//        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
//        WritableRaster raster = image.copyData(null);
//        curImage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
//        images.get(images.size()-1).add(curImage);
//    }

    public Thickness getSelectedThickness(){
        return selectedThickness;
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

    public int playback(){
//        curLineSeg = null;
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                System.out.println(Model.this.i);
//                System.out.println(curLineSeg == null);
//                updateAllViews();
//            }
//        });
//        new Thread(){
//            public void run(){
//                try {
//                    for (; Model.this.i < lines.size(); Model.this.i++) {
//                        System.out.println("i: " + Model.this.i);
//                        for (LineSeg lineSeg : lines.get(i)) {
//                            curLineSeg = lineSeg;
//                            Thread.sleep(1000);
//                        }
//                        SwingUtilities.invokeLater(new Runnable() {
//                            public void run() {
//                                System.out.println(Model.this.i);
//                                System.out.println(curLineSeg == null);
//                                updateAllViews();
//                            }
//                        });
//                    }
//                }catch (Exception e){
//
//                }
//            }
//        }.start();
//        for(int i = 0; i < lines.size(); i++){
//            for(LineSeg lineSeg : lines.get(i)){
//                curLineSeg = lineSeg;
//            }
//            SwingUtilities.invokeLater(new Runnable() {
//                public void run() {
//                    updateAllViews();
//                }
//            });
//        }



//        curLineSeg = null;
        if(this.line_index <= lineNum){
            colorSelected = linesColors.get(line_index);
            selectedThickness = linesThickness.get(line_index);
            seg_index++;
//            if(images.get(i).size() <= 0){
            if(lines.get(line_index).size() <= 0){
                curLineSeg = null;
                seg_index = 0;
                line_index++;
            }else{
//                curImage = images.get(i).get(j-1);
                curLineSeg = lines.get(line_index).get(seg_index-1);
                seg_num++;
                if(seg_index >= lines.get(line_index).size()) {
                    line_index++;
                    seg_index = 0;
                }
//                if(j < images.get(i).size()){
//                }else{
//                    i++;
//                    j = 0;
//                }
            }
        }
        updateAllViews();
        return seg_num;
    }


    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

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



}
