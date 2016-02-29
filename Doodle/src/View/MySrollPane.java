package View;

import DataHelper.SizeState;
import Model.Model;
import Model.IView;
import javax.swing.*;
import java.awt.*;

/**
 * Created by cassiehanyu on 2016-02-28.
 */
public class MySrollPane extends JScrollPane{
    private Model model;

    public MySrollPane(Model model, Component component)
    {
        super(component);
        this.model = model;


        model.addView(new IView() {
            @Override
            public void updateView() {
                if(model.getSizeState() == SizeState.FITSIZE){
                    setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                }else{
                    setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                }
                repaint();

            }
        });


    }
}
