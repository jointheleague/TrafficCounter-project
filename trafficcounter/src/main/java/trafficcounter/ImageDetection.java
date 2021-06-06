package trafficcounter;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.*;

import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;


public class ImageDetection {
	static ImageHelper ih = new ImageHelper();
	public static void main(String[] args) {
        // Load the native OpenCV library
		nu.pattern.OpenCV.loadShared();
        new ImageDetection().run(args, null);
    }
	
	
	public void detectAndDisplay(Mat frame, CascadeClassifier faceCascade, CascadeClassifier eyesCascade/*, CascadeClassifier SidefaceCascade*/) {
        Mat frameGray = new Mat();
        
        //Image processing
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(frameGray, frameGray);
        // --
        
        //Detect Faces
        MatOfRect faces = new MatOfRect();
        //MatOfRect sideFaces = new MatOfRect();
        faceCascade.detectMultiScale(frameGray, faces);
        //SidefaceCascade.detectMultiScale(frameGray, sideFaces);
        List<Rect> listOfFaces = new ArrayList<Rect>(faces.toList());
        //listOfFaces.addAll(sideFaces.toList());
        for (Rect face : listOfFaces) {
           /* Point center = new Point(face.x + face.width / 2, face.y + face.height / 2);
            Imgproc.ellipse(frame, center, new Size(face.width / 2, face.height / 2), 0, 0, 360,
                    new Scalar(255, 0, 255));*/
        	Point upRight =  new Point(face.x + face.width, face.y + face.height);
        	Point downLeft =  new Point(face.x, face.y);
        	Imgproc.rectangle(frame, downLeft, upRight, new Scalar(255, 0, 255));
            
            Mat faceROI = frameGray.submat(face);
            // -- In each face, detect eyes
            MatOfRect eyes = new MatOfRect();
            eyesCascade.detectMultiScale(faceROI, eyes);
            List<Rect> listOfEyes = eyes.toList();
            for (Rect eye : listOfEyes) {
                Point eyeCenter = new Point(face.x + eye.x + eye.width / 2, face.y + eye.y + eye.height / 2);
                int radius = (int) Math.round((eye.width + eye.height) * 0.25);
                Imgproc.circle(frame, eyeCenter, radius, new Scalar(255, 0, 0), 4);
            }
        }
        // --

        //-- Show what you got
        ih.addImage(frame);
    }
    public void run(String[] args, Mat frameInput) {
        String filenameFaceCascade = args.length > 2 ? args[0] : "src/main/java/trafficcounter/resources/cars.xml";
      //  String filenameSideFaceCascade = "src/main/java/trafficcounter/resources/haarcascade_profileface.xml";
        String filenameEyesCascade = args.length > 2 ? args[1] : "src/main/java/trafficcounter/resources/haarcascade_eye_tree_eyeglasses.xml";
        int cameraDevice = args.length > 2 ? Integer.parseInt(args[2]) : 0;
        CascadeClassifier faceCascade = new CascadeClassifier();
        CascadeClassifier sideFaceCascade = new CascadeClassifier();
        CascadeClassifier eyesCascade = new CascadeClassifier();
        if (!faceCascade.load(filenameFaceCascade)) {
            System.err.println("--(!)Error loading face cascade: " + filenameFaceCascade);
            System.exit(0);
        }
       /* if (!sideFaceCascade.load(filenameSideFaceCascade)) {
            System.err.println("--(!)Error loading side face cascade: " + filenameSideFaceCascade);
            System.exit(0);
        }*/
        if (!eyesCascade.load(filenameEyesCascade)) {
            System.err.println("--(!)Error loading eyes cascade: " + filenameEyesCascade);
            System.exit(0);
        }
     /*   VideoCapture capture = new VideoCapture(-1);
        if (!capture.isOpened()) {
            System.err.println("--(!)Error opening video capture");
            System.exit(0);
        }
        Mat frame = new Mat();
        System.out.println("ran");
        while (capture.read(frame)) {
        	
            if (frame.empty()) {
                System.err.println("--(!) No captured frame -- Break!");
                break;
            }
            //-- 3. Apply the classifier to the frame
           
          
        }*/
        //Mat frame = Imgcodecs.imread("src/main/java/trafficcounter/Traffic1.jpg");
        detectAndDisplay(frameInput, faceCascade, eyesCascade/*, sideFaceCascade*/);
       // System.exit(0);
    }
}

