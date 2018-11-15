package font;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import server.*;

import java.util.ArrayList;

public class DrawingArea extends Pane {
    private MyCanvas myCanvas;
    private ArrayList<MyCanvas> myCanvasList;//保存创建的canvas
    private ArrayList<MyCanvas> RedoCanvasList;//保存重做的canvas
    private ArrayList<Drawings> DrawingItems;//Drawings的内容
    public DrawingArea(){
        super();
        myCanvasList=new ArrayList<>();
        RedoCanvasList=new ArrayList<>();
        DrawingItems=new ArrayList<>();
        this.setMinHeight(660);
        this.setMaxHeight(660);
        this.setMinWidth(880);
        this.setMaxWidth(880);
        this.setStyle("-fx-border-color: black;");
        this.addLayer();
    }

    //添加图层
    public void addLayer(){
        myCanvas=new MyCanvas(880,660);
        this.getChildren().add(myCanvas);
        myCanvas.toFront();
    }

    //清空算法
    public void clear(){
        //从DrawingArea清除canvas
        for (int i=0;i<myCanvasList.size();i++){
            MyCanvas temp=myCanvasList.get(i);
            temp.Reset();
            this.getChildren().remove(temp);
        }
        myCanvasList.clear();
        RedoCanvasList.clear();
        DrawingItems.clear();
    }

    //重做算法
    public String Redo(){
        String tips="";
        if (RedoCanvasList.size()==0){
            tips="当前没有可重做项";
        }else{
            MyCanvas temp=RedoCanvasList.get(RedoCanvasList.size()-1);
            this.getChildren().add(temp);
            RedoCanvasList.remove(RedoCanvasList.size()-1);
            myCanvasList.add(temp);
            tips="已经完成重做";
        }
        addLayer();
        return tips;
    }

    //撤销算法
    public String Revoke(){
        String tips="";
        if (myCanvasList.size()==0){
            tips="当前无可撤销项";
        }else{
            MyCanvas temp=myCanvasList.get(myCanvasList.size()-1);
            this.getChildren().remove(temp);
            RedoCanvasList.add(temp);
            myCanvasList.remove(myCanvasList.size()-1);
            tips="已经完成撤销";
        }
        addLayer();
        return tips;
    }

    //确认辨认方法
    public String Recognize(){
        myCanvasList.add(myCanvas);
        DrawingItems.add(myCanvas.getItem());
        return myCanvas.Recognize();
    }

    //从读取的文件内容将其组成圆
    public void makeCircle(String []comment){
        Drawings Item=new Circle();
        Item.setxlist(makexlist(comment));
        Item.setylist(makeylist(comment));
        Item.draw(myCanvas.getGc());
        DrawingItems.add(Item);
    }

    //组成三角形
    public void makeTriangle(String []comment){
        Drawings Item=new Triangle();
        Item.setxlist(makexlist(comment));
        Item.setylist(makeylist(comment));
        Item.draw(myCanvas.getGc());
        DrawingItems.add(Item);
    }

    //组成矩形
    public void makeRectangle(String []comment){
        Drawings Item=new Rectangle();
        Item.setxlist(makexlist(comment));
        Item.setylist(makeylist(comment));
        Item.draw(myCanvas.getGc());
        DrawingItems.add(Item);
    }

    //组成正方形
    public void makeSquare(String []comment){
        Drawings Item=new Square();
        Item.setxlist(makexlist(comment));
        Item.setylist(makeylist(comment));
        Item.draw(myCanvas.getGc());
        DrawingItems.add(Item);
    }

    //组成多边形
    public void makePolygon(String []comment){
        Drawings Item=new Polygon();
        Item.setxlist(makexlist(comment));
        Item.setylist(makeylist(comment));
        Item.draw(myCanvas.getGc());
        DrawingItems.add(Item);
    }

    public ArrayList<Double> makexlist(String []comment){
        ArrayList<Double> result=new ArrayList<>();
        System.out.println(comment.length/2);
        for (int i=1;i<=(comment.length/2);i++){
            result.add(Double.parseDouble(comment[i]));
        }
        return result;
    }

    public ArrayList<Double> makeylist(String[]comment) {
        ArrayList<Double> result=new ArrayList<>();
        for (int i=(comment.length/2+1);i<comment.length;i++){
            result.add(Double.parseDouble(comment[i]));
        }
        return result;
    }

    public ArrayList<Drawings> getDrawingItems() {
        return DrawingItems;
    }
}
