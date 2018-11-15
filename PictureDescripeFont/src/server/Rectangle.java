package server;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import server.Drawings;

import java.util.ArrayList;

//长方形类
//设为黄色
public class Rectangle  extends Drawings {
    public Rectangle(){
        name="Rectangle";
        xlist=new ArrayList<>();
        ylist=new ArrayList<>();
    }
    public void draw(GraphicsContext gc){
        gc.save();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(1);
        double x1=xlist.get(0);//左上角和右下角
        double x2=xlist.get(1);
        double y1=ylist.get(0);
        double y2=ylist.get(1);
        gc.strokeRect(x1,y1,x2-x1,y2-y1);
    }
}
