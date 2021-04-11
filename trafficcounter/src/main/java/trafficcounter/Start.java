package trafficcounter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Created by tobias on 25.08.17.
 */
public class Start {

    /* change this path to an image on your disk which you want to work with */
    public static final String IMAGE_PATH = "src/main/java/trafficcounter/Car2.jpg";

    /* window size */
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_WIDTH = 1600;

    public static void main(String[] args) {

    	
        // load the native library 
    	nu.pattern.OpenCV.loadShared();

        ImageHelper helper = new ImageHelper();
      //  Mat image = Mat.zeros( WINDOW_HEIGHT, WINDOW_WIDTH,  CV_8UC3 );

        // read an image to work with from disk 
        Mat input = Imgcodecs.imread(IMAGE_PATH);

        // perform image processing on this image 
        Mat processedImage = processImage(input);

        // add the original and the processed image to the panel and show the window 
        helper.addImage(input);
        helper.addImage(processedImage);
        
        
        

    }


    /**
     * takes a mat and performs some image processing on it
     * @param input the image to process
     * @return the processed image
     */
    public static Mat processImage(Mat input) {

        Mat processed = new Mat();

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *  This is your place to start.
         *  Do whatever you want with OpenCV here!
         *  For example: Convert colors to gray
         * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        Imgproc.cvtColor(input, processed, Imgproc.MORPH_GRADIENT);


        return processed;
    }
}