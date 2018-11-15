package server;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import server.Drawings;

import java.util.ArrayList;

//圆类
//圆为红色
public class Circle extends Drawings {
    public Circle(){
        name="Circle";
        xlist=new ArrayList<>();
        ylist=new ArrayList<>();
    }
    public void draw(GraphicsContext gc){
        gc.save();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        double x1=xlist.get(0);
        double y1=ylist.get(0);
        double x2=xlist.get(1);
        double y2=ylist.get(1);
        gc.strokeOval(Math.min(x1, x2), Math.min(y1, y2),
                Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
                Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
    }
}
