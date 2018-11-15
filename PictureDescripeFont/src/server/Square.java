package server;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import server.Drawings;

import java.util.ArrayList;

//正方形类
//设为紫色
public class Square extends Drawings {
    public Square(){
        name="Square";
        xlist=new ArrayList<>();
        ylist=new ArrayList<>();
    }
    public void draw(GraphicsContext gc){
        gc.save();
        gc.setStroke(Color.PURPLE);
        gc.setLineWidth(1);
        gc.setLineWidth(1);
        double x1=xlist.get(0);//左上角和右下角
        double x2=xlist.get(1);
        double y1=ylist.get(0);
        double y2=ylist.get(1);
        gc.strokeRect(x1,y1,x2-x1,y2-y1);
    }
}
