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
public class Start {

    /* change this path to an image on your disk which you want to work with */
    //public static final String IMAGE_PATH = "src/main/java/trafficcounter/Traffic1.jpg";

    /* window size */
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = 1600;

    public static void main(String[] args) {
    	nu.pattern.OpenCV.loadShared();
        ImageHelper helper = new ImageHelper();
        BackgroundSubtractor backSub = Video.createBackgroundSubtractorMOG2();
      
        VideoCapture capture = new VideoCapture();
        capture.open(0);

        if (!capture.isOpened()) {
            System.err.println("Unable to open vid");
            System.exit(0);
        } 
        
        Mat frame = new Mat(), fgMask = new Mat();
        Scalar lowerb  = new Scalar(0);
        Scalar upperb = new Scalar (150);
        
        while (true) {
        capture.read(frame);
        
        backSub.apply(frame, fgMask);
        
        //Imgproc.rectangle(frame, new Point(10, 2), new Point(100, 20), new Scalar(255, 255, 255), -1);
        //String frameNumberString = String.format("%d", (int)capture.get(Videoio.CAP_PROP_POS_FRAMES));
        //Imgproc.putText(frame, frameNumberString, new Point(15, 15), Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0));
        
        
        Imgproc.GaussianBlur(fgMask, fgMask, new Size(7, 7), 0);
        Imgproc.blur(fgMask, fgMask, new Size(25,25));
        Core.inRange(fgMask, lowerb, upperb, fgMask);
        Imgproc.blur(fgMask, fgMask, new Size(25,25));
        Core.inRange(fgMask, lowerb, upperb, fgMask);
        
        //draws rectangles around blurs
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        
        Imgproc.findContours(fgMask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        for(MatOfPoint mop: contours) {
        	Rect r = Imgproc.boundingRect(mop);
        	if(r.width*r.height>200) {
        	Imgproc.rectangle(frame, r.tl(), r.br(), new Scalar(0,0,255));
        	}
        }
        //frame is the output, fgMask is the processed image
        //helper.addImage(frame);
        helper.addImage(fgMask);
        
        }
    }


    /**
     * takes a mat and performs some image processing on it
     * @param input the image to process
     * @return the processed image
     */
    public static Mat processImage(Mat input) {

        Mat processed = new Mat();
        Mat mask = new Mat();
        Mat morphOutput = new Mat();
        
        /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *  This is your place to start.
         *  Do whatever you want with OpenCV here!
         *  For example: Convert colors to gray
         * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        Imgproc.blur(input, processed, new Size(7, 7));
        
       // Imgproc.cvtColor(processed, processed, Imgproc.COLOR_BGR2HSV);
         Imgproc.cvtColor(processed, processed, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(processed, processed);

        /*
        Scalar minValues = new Scalar(20, 20, 20);
        Scalar maxValues = new Scalar(120,120,120);
        		
        
        Core.inRange(processed, minValues, maxValues, mask);
        
        
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));

        Imgproc.erode(mask, morphOutput, erodeElement);
        Imgproc.erode(mask, morphOutput, erodeElement);

        Imgproc.dilate(mask, morphOutput, dilateElement);
        Imgproc.dilate(mask, morphOutput, dilateElement);

        
        processed = findContours(processed,morphOutput);*/
        return processed;
    }
    
    public static Mat findContours(Mat frame , Mat maskedImage) {
    	// init
    	List<MatOfPoint> contours = new ArrayList<>();
    	Mat hierarchy = new Mat();
 
    	//Imgproc.cvtColor(maskedImage, blackAndWhite, Imgproc.COLOR_BGR2GRAY);
    	// find contours
    	
    	Imgproc.findContours(maskedImage, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

    	// if any contour exist...
    	if (hierarchy.size().height > 0 && hierarchy.size().width > 0)
    	{
    	        // for each contour, display it in blue
    			
    	        for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0])
    	        {
    	                Imgproc.drawContours(frame, contours, idx, new Scalar(0, 255, 255));
    	        }
    	}
    	
    	return frame;
    }
    
}