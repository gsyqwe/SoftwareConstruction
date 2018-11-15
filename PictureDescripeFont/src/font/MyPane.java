package font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.Drawings;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;


//重新定义JFrame类，添加读取，存储，新建功能
public class MyPane extends BorderPane {
    //需要一个菜单条实现文件读取，存储与新建
    private MenuBar bar;
    private Menu fileMenu;
    //需要一个画图区域
    private DrawingArea drawingArea;
    //定义按钮面板
    private ToolBar buttonPanel;
    private String tips;
    private Label statusBar;//提示条
    private Label ImageBar;//提示框
    private HBox hbox;
    private VBox vbox;

    public MyPane(final Stage stage){//构造函数
        super();
        hbox=new HBox();
        vbox=new VBox();
        //定义菜单条
        bar = new MenuBar();
        fileMenu = new Menu("文件");
        fileMenu.setVisible(true);
        //新建文件菜单条
        MenuItem newItem = new MenuItem("新建文件");
        //添加新建文件事件
        newItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //进行新建文件设置
                newFile(drawingArea);
            }
        });
        fileMenu.getItems().add(newItem);
        //保存文件菜单项
        MenuItem saveItem = new MenuItem("保存文件");
        //添加保存文件事件
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //进行保存文件设置
                saveFile(stage);
            }
        });
        fileMenu.getItems().add(saveItem);
        //打开文件菜单项
        MenuItem loadItem = new MenuItem("读取文件");
        //添加读取文件事件
        loadItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //进行读取文件设置
                loadFile(stage);
            }
        });
        fileMenu.getItems().add(loadItem);
        //退出菜单项
        MenuItem exitItem = new MenuItem("退出");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.getItems().add(exitItem);
        bar.getMenus().add(fileMenu);
        //定义bar大小
        bar.setMaxHeight(30);
        bar.setMinHeight(30);
        bar.setMinWidth(70);
        bar.setMaxWidth(70);
        hbox.setSpacing(430);
        hbox.getChildren().add(bar);
        //定义按钮条
        buttonPanel=new ToolBar();
        //定义确认按钮，重做按钮，撤销按钮
        Button confirmButton=new Button("确认");
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //定义确认事件
                confirm();
            }
        });
        buttonPanel.getItems().add(confirmButton);
        //定义重做按钮
        Button RedoButton=new Button("重做");
        RedoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //定义撤销事件
                Redo();
            }
        });
        buttonPanel.getItems().add(RedoButton);
        //定义撤销按钮
        Button RevokeButton=new Button("撤销");
        RevokeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //定义重做事件
                Revoke();
            }
        });
        buttonPanel.getItems().add(RevokeButton);
        buttonPanel.toFront();
        vbox.setSpacing(100);
        vbox.getChildren().add(buttonPanel);
        //定义画图区域
        drawingArea=new DrawingArea();
        this.setCenter(drawingArea);
        //定义提示条
        tips="请开始画图";
        statusBar=new Label();
        statusBar.setFont(new Font("Cambria",25));
        statusBar.setText(tips);
        hbox.getChildren().add(statusBar);
        this.setTop(hbox);
        //定义提示框
        ImageBar=new Label();
        Image image;
        image = new Image(getClass().getResourceAsStream("tips.png"),200,400,true,true);
        ImageBar.setGraphic(new ImageView(image));
        vbox.getChildren().add(ImageBar);
        this.setLeft(vbox);
        //定义区域大小
        this.setMinHeight(800);
        this.setMaxHeight(800);
        this.setMinWidth(1080);
        this.setMaxWidth(1080);
    }

    //新文件事件
    public void newFile(DrawingArea drawingArea){
        drawingArea.clear();
        drawingArea.addLayer();
    }

    //保存文件事件
    public void saveFile(Stage stage){
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);
        ArrayList<Drawings> DrawingItems=new ArrayList<>();
        DrawingItems=drawingArea.getDrawingItems();//将其中的xlist,ylist都保存下去
        String comment="";
        //取消最后一个分号
        //每一个Item以分号分割
        if (DrawingItems.size()>0) {
            if (DrawingItems.size() > 1) {
                for (int i = 0; i < (DrawingItems.size() - 1); i++) {
                    comment = comment + getComment(DrawingItems.get(i)) + System.lineSeparator();
                }
            }
            comment = comment + getComment(DrawingItems.get(DrawingItems.size() - 1));
        }
        if(file!=null){
            try {
                BufferedWriter bf=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                bf.write(comment);
                bf.flush();
                bf.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            statusBar.setText("存储了"+file.getName());
        }
    }

    public String getComment(Drawings Item){
        String result="";
        result=Item.getName();
        ArrayList<Double> list1=Item.getXlist();
        String temp="";
        for (int i=0;i<list1.size();i++){
            temp=temp+list1.get(i).toString()+",";
        }
        ArrayList<Double> list2=Item.getYlist();
        String temp1="";
        for (int i=0;i<(list2.size()-1);i++){
            temp1=temp1+list2.get(i).toString()+",";
        }
        temp1=temp1+list2.get(list2.size()-1);//不要最后一个逗号
        result=result+","+temp+temp1;
        return result;
    }

    //读取文件事件
    public void loadFile(Stage stage){
        FileChooser fileChooser=new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null){
            drawingArea.clear();
            drawingArea.addLayer();
            try {
                BufferedReader bf=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String temp=bf.readLine();
                while(temp!=null){
                    String []comment=temp.split(",");
                    if (comment[0].equals("Circle")){
                        drawingArea.makeCircle(comment);
                    }else if (comment[0].equals("Triangle")){
                        drawingArea.makeTriangle(comment);
                    }else if(comment[0].equals("Rectangle")){
                        drawingArea.makeRectangle(comment);
                    }else if (comment[0].equals("Square")){
                        drawingArea.makeSquare(comment);
                    }else if (comment[0].equals("Polygon")){
                        drawingArea.makePolygon(comment);
                    }
                    temp=bf.readLine();
                }
                bf.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            statusBar.setText("读取了"+file.getName());
        }
        drawingArea.addLayer();
    }


    //确认事件
    public void confirm(){
        tips=drawingArea.Recognize();
        statusBar.setText("刚刚画的图像为"+tips);
        if(!tips.equals("没有画图")){
            drawingArea.addLayer();
        }
    }

    //重做事件
    public void Redo(){
        statusBar.setText(drawingArea.Redo());
    }

    //撤销事件
    public void Revoke(){
        statusBar.setText(drawingArea.Revoke());
    }
}
