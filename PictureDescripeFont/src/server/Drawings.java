package server;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

//图形父类，用以继承
public class Drawings {
    ArrayList<Double> xlist;
    ArrayList<Double> ylist;
    String name="";

    public void draw(GraphicsContext gc){ }
    public void setxlist(ArrayList<Double> list){
        for (int i=0;i<list.size();i++){
            xlist.add(list.get(i));
        }
    }

    public void setylist(ArrayList<Double> list){
        for (int i=0;i<list.size();i++){
            ylist.add(list.get(i));
        }
    }

    public ArrayList<Double> getXlist() {
        return xlist;
    }

    public ArrayList<Double> getYlist() {
        return ylist;
    }

    public String getName() {
        return name;
    }
}
