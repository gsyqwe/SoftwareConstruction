package font;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import server.*;
import server.Polygon;
import server.Rectangle;
import tool.RecognizeTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MyCanvas extends Canvas{
    private GraphicsContext gc;
    private double startx,starty,endx,endy=0;
    private ArrayList<Double> startxlist,startylist,endxlist,endylist,pausexlist,pauseylist;
    private int count;//画了几笔
    private Drawings Item;
    public MyCanvas(double width, double height){//构造函数
        super(width, height);
        count=0;
        startxlist=new ArrayList<>();
        startylist=new ArrayList<>();
        endxlist=new ArrayList<>();
        endylist=new ArrayList<>();
        pausexlist=new ArrayList<>();
        pauseylist=new ArrayList<>();
        gc=getGraphicsContext2D();
        draw(gc);
    }

    //实现自由画图功能
    public void draw(GraphicsContext gc){//自由画图
        gc.save();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        //改变鼠标样式
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.CROSSHAIR);
            }
        });
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                startx=event.getX();
                starty=event.getY();
                if(startxlist.size()!=0){
                    startxlist.remove(startxlist.size()-1);
                    startylist.remove(startylist.size()-1);
                }
                startxlist.add(startx);
                startylist.add(starty);
                pausexlist.add(startx);
                pauseylist.add(starty);
                count++;
            }
        });
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                endx=event.getX();
                endy=event.getY();
                endxlist.add(endx);
                endylist.add(endy);
                gc.strokeLine(startx,starty,endx,endy);
                startx=endx;
                starty=endy;
                startxlist.add(startx);
                startylist.add(starty);
            }
        });
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double pausex=event.getX();
                double pausey=event.getY();
                pausexlist.add(pausex);
                pauseylist.add(pausey);
            }
        });
    }

    //实现清空功能
    public void Reset(){
        gc.clearRect(0,0,880,660);
    }

    public String Recognize(){
        String shape="";
        if(count==0){
            //没有画图
            return("没有画图");
        }else {
            String filename=makeImage();
            RecognizeTool recognizeTool=new RecognizeTool();
            String temp=recognizeTool.Recognize(filename);
            if (0 < count && count <= 4) {//在圆，三角形，矩形，正方形之列
                if(count==1){
                    RecognizeCircle();
                    shape="圆";
                }else if (count==2){
                    RecognizeTriangle();
                    shape="三角形";
                }else if (count==3){
                    RecognizeRectangle();
                    shape="矩形";
                }else if (count==4){
                    RecognizeSquare();
                    shape="正方形";
                }
            } else {//是多边形
                RecognizePolygon();
                shape="多边形";
            }
            //对比temp与shape
            if (!temp.equals(shape)){
                return shape;
            }else{
                return temp;
            }
        }
    }

    //将当前canvas的内容转换为图片保存到cache去然后将其中图片交给tool中的识别工具来保存
    public String makeImage(){
        Image myImage = this.snapshot(null, null);
        String path="src/cache/temp1.png";
        path=path;
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(myImage, null), "png",
                    new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    //在画图的时候进行改变
    public void RecognizeCircle(){//认出为圆的话设为红色
        Reset();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        Item=new Circle();
        double x1=getMin(startxlist);
        double x2=getMax(startxlist);
        double y1=getMin(startylist);
        double y2=getMax(startylist);
        ArrayList<Double> list1=new ArrayList<>();
        list1.add(x1);
        list1.add(x2);
        Item.setxlist(list1);
        ArrayList<Double> list2=new ArrayList<>();
        list2.add(y1);
        list2.add(y2);
        Item.setylist(list2);
        Item.draw(gc);
    }

    public void RecognizeTriangle(){//认出为三角形的话设为蓝色
        Reset();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        Item=new Triangle();
        double x1,x2,x3=0;//3个点
        double y1,y2,y3=0;//3个店
        //x1,x2为起始点
        x1=startxlist.get(0);
        y1=startylist.get(0);
        //找到其余2个转折点
        x2=pausexlist.get(1);
        y2=pauseylist.get(1);
        //找到x3,y3
        x3=startxlist.get(2*startxlist.size()/3);
        y3=startylist.get(2*startylist.size()/3);
        ArrayList<Double>list1=new ArrayList<>();
        ArrayList<Double>list2=new ArrayList<>();
        list1.add(x1);
        list1.add(x2);
        list1.add(x3);
        list2.add(y1);
        list2.add(y2);
        list2.add(y3);
        Item.setxlist(list1);
        Item.setylist(list2);
        Item.draw(gc);
    }

    public void RecognizeRectangle(){//认出为矩形的话设为黄色
        Reset();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(1);
        Item=new Rectangle();
        //矩形左上角和右下角
        double x1=getMin(startxlist);
        double x2=getMax(startxlist);
        double y1=getMin(startylist);
        double y2=getMax(startylist);
        ArrayList<Double>list1=new ArrayList<>();
        list1.add(x1);
        list1.add(x2);
        Item.setxlist(list1);
        ArrayList<Double>list2=new ArrayList<>();
        list2.add(y1);
        list2.add(y2);
        Item.setylist(list2);
        Item.draw(gc);
    }

    public void RecognizeSquare(){//认出为正方形，正方形为紫色
        Reset();
        gc.setStroke(Color.PURPLE);
        gc.setLineWidth(1);
        Item=new Square();
        //正方形左上角和右下角
        double x1=getMin(startxlist);
        double x2=getMax(startxlist);
        double y1=getMin(startylist);
        double y2=getMax(startylist);
        ArrayList<Double>list1=new ArrayList<>();
        list1.add(x1);
        list1.add(x2);
        Item.setxlist(list1);
        ArrayList<Double>list2=new ArrayList<>();
        list2.add(y1);
        list2.add(y2);
        Item.setylist(list2);
        Item.draw(gc);
    }

    public void RecognizePolygon(){//认出为多边形，多边形设为绿色
        Reset();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        Item=new Polygon();
        //找出多边形中间停顿的点
        Item.setxlist(pausexlist);
        Item.setylist(pauseylist);
        Item.draw(gc);
    }


    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count=count;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public Drawings getItem(){
        return Item;
    }

    //从一堆点中找到最小值
    public double getMin(ArrayList<Double> list){
        double result=0;
        result=list.get(0);
        double temp=0;
        for (int i=0;i<list.size();i++){
            temp=list.get(i);
            if (result>temp){
                result=temp;
            }
        }
        return result;
    }

    //从一堆点中找到最大值
    public double getMax(ArrayList<Double> list){
        double result=0;
        result=list.get(0);
        double temp=0;
        for (int i=0;i<list.size();i++){
            temp=list.get(i);
            if (result<temp){
                result=temp;
            }
        }
        return result;
    }

}
