package tool;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.*;

public class RecognizeTool {
    private static Map<String, Integer> shapeResult = new HashMap<String, Integer>();

    public String Recognize(String filename) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Imgcodecs.imread(filename);
        //缩放之后的图片
        Mat imageResized = image.clone();
        float width=image.width();
        float height=image.height();
        //缩放图片
        Imgproc.resize(image, imageResized, new Size(width,height));
        float ratio = image.width() / imageResized.width();//计算比例
        //模糊图像
        Mat blurredImg = imageResized.clone();
        Imgproc.GaussianBlur(imageResized,blurredImg,new Size(5,5),0);
        Mat grayImg = blurredImg.clone();
        //转换为灰度图
        Imgproc.cvtColor(blurredImg, grayImg, Imgproc.COLOR_BGR2GRAY);

        //生成阈值图像
        Mat threshImg = grayImg.clone();
        Imgproc.threshold(grayImg, threshImg, 60, 255, Imgproc.THRESH_BINARY);
        //定义2个
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(threshImg,contours,hierarchy,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);
        //计算轮廓距
        List<Moments> momList = new ArrayList<>(contours.size());
        shapeResult.put("triangle",0);
        shapeResult.put("square",0);
        shapeResult.put("rectangle",0);
        shapeResult.put("pentagon",0);
        shapeResult.put("circle",0);
        String shape;
        MatOfPoint2f  newMatOfPoint2f = new MatOfPoint2f( contours.get(contours.size()-1).toArray() );
        ShapeDector  shapeDector = new ShapeDector();
        shape = shapeDector.detect(contours.get(contours.size()-1),newMatOfPoint2f);
        return shape;
    }

}

