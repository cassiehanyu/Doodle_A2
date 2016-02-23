package Model;

import com.sun.javafx.image.impl.IntArgb;
import com.sun.javafx.sg.prism.NGShape;

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

    private int colorSelected;
    private ArrayList<Integer> linesColors;

    private Thickness selectedThickness;
    private ArrayList<Thickness> linesThickness;


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
        lines = new ArrayList<>();
        linesColors = new ArrayList<>();
        linesThickness = new ArrayList<>();
    }

    public LineSeg getCurLineSeg(){
        return curLineSeg;
    }

    public void initNewLineSeg(){

    }

    public void setColorSelected(int color){
        colorSelected = color;
    }

    public void setSelectedThickness(Thickness thickness){
        selectedThickness = thickness;
    }

    public int getColorSelected(){
        return colorSelected;
    }

    public void saveLineSeg(int x, int y, int ox, int oy){
        curLineSeg = new LineSeg(x,y,ox,oy);
        lines.get(lines.size()-1).add(curLineSeg);
        updateAllViews();
    }

    public Thickness getSelectedThickness(){
        return selectedThickness;
    }

    public void drawStart(){
        lines.add(new ArrayList<>());
        linesColors.add(new Integer(colorSelected));
        linesThickness.add(selectedThickness);
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
