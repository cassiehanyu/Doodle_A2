package DataHelper;

import java.io.Serializable;

/**
 * Created by cassiehanyu on 2016-02-21.
 */
public enum Thickness implements Serializable{
    ONE(1.0),
    TWO(2.0),
    THREE(5.0),
    FOUR(10.0);

    Thickness(double thick){
        this.thick = thick;
    }

    private double thick;

    public float getThickness()
    {
        return (float) thick;
    }

    static public Thickness parseThickness(String thickness){
        Thickness t = null;
        switch (thickness){
            case "ONE":
                t = ONE;
                break;
            case "TWO":
                t = TWO;
                break;
            case "THREE":
                t = THREE;
                break;
            case "FOUR":
                t = FOUR;
                break;
        }
        return t;
    }

}
