package trafficcounter;

import java.io.File;



import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by tobias on 25.08.17.
 */
public class YOLOStart {

    /* change this path to an image on your disk which you want to work with */
    //public static final String IMAGE_PATH = "src/main/java/trafficcounter/Traffic1.jpg";

    /* window size */
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = 1600;

    public static void main(String[] args) {
    	nu.pattern.OpenCV.loadShared();
        ImageHelper helper = new ImageHelper();
      
        VideoCapture capture = new VideoCapture();
        capture.open(0);

        if (!capture.isOpened()) {
            System.err.println("Unable to open vid");
            System.exit(0);
        } 
        
       

    
    }
   }