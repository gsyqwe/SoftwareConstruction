package server;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import server.Drawings;

import java.util.ArrayList;

//三角形类
//设为蓝色
public class Triangle extends Drawings {
    public Triangle(){
        name="Triangle";
        xlist=new ArrayList<>();
        ylist=new ArrayList<>();
    }
    public void draw(GraphicsContext gc){
        gc.save();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        double x1=xlist.get(0);
        double y1=ylist.get(0);
        double x2=xlist.get(1);
        double y2=ylist.get(1);
        double x3=xlist.get(2);
        double y3=ylist.get(2);
        gc.strokeLine(x1,y1,x2,y2);
        gc.strokeLine(x2,y2,x3,y3);
        gc.strokeLine(x3,y3,x1,y1);
    }
}
