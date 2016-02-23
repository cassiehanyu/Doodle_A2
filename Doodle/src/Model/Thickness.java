package Model;

/**
 * Created by cassiehanyu on 2016-02-21.
 */
public enum Thickness {
    ONE(1.0),
    TWO(2.0),
    THREE(5.0),
    FOUR(10.0);

    Thickness(double thick){
        this.thick = thick;
    }

    private double thick;

    public float getThickness(){
        return (float) thick;
    }

}
