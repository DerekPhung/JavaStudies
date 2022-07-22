package PC_Controls;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.opencv.opencv_core.IplImage;

import javax.swing.*;

import java.awt.*;

import static org.bytedeco.opencv.global.opencv_core.cvFlip;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvSaveImage;

public class WebcamControl implements Runnable {
    //final int INTERVAL=1000;///you may use interval
    IplImage image;
    CanvasFrame canvas = new CanvasFrame("Web Cam");
    Image icon = Toolkit.getDefaultToolkit().getImage("src/main/java/resources/pup.jpg");

    public WebcamControl() {
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        canvas.setIconImage(icon);
    }

    @Override
    public void run() {
        FrameGrabber grabber = new VideoInputFrameGrabber(1);

        int i=0;
        try {
            grabber.start();
            IplImage img;
            var convert = new OpenCVFrameConverter.ToIplImage();

            while (true) {
                img = convert.convert(grabber.grab());
                if (img != null) {
                    cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise

                    //cvSaveImage((i++)+"-capture.jpg", img);

                    // show image on window
                    canvas.showImage(convert.convert(img));
                }
                //Thread.sleep(INTERVAL);
            }
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        new WebcamControl().run();
    }
}