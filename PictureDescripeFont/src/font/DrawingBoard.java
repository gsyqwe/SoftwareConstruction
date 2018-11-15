package font;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DrawingBoard extends Application {
    //需要一个跟Group
    private Group root;
    //定义窗口主体
    private MyPane mypane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mypane=new MyPane(primaryStage);
        primaryStage.setTitle("画图板");
        root=new Group();
        Scene scene=new Scene(root);
        root.getChildren().add(mypane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
