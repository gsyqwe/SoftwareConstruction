package server;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import server.Drawings;

import java.util.ArrayList;

//多边形类
//设为绿色
public class Polygon extends Drawings {
    public Polygon(){
        name="Polygon";
        xlist=new ArrayList<>();
        ylist=new ArrayList<>();
    }
    public void draw(GraphicsContext gc){
        gc.save();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        double []x=new double[xlist.size()];
        double []y=new double[ylist.size()];
        for(int i=0;i<xlist.size();i++){
            x[i]=xlist.get(i);
            y[i]=ylist.get(i);
        }
        gc.strokePolyline(x,y,x.length);
    }
}
