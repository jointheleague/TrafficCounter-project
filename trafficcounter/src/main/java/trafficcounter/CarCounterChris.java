package trafficcounter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

public class CarCounterChris {
	VideoCapture capture;
	Mat fgMask = new Mat();
    Scalar lowerb  = new Scalar(0);
    Scalar upperb = new Scalar (150);
    ImageDetection id = new ImageDetection();
    Scalar topb = new Scalar(255);
    ImageHelper helper = new ImageHelper();
    BackgroundSubtractor backSub = Video.createBackgroundSubtractorMOG2();
    
    ArrayList<Marker> markers = new ArrayList<Marker>();
    ArrayList<Marker> oldMarkers = new ArrayList<Marker>();
    ArrayList<LineInfo> lineInfos = new ArrayList<LineInfo>();
    
    int carCount;
    
public static void main(String[] args) {
	nu.pattern.OpenCV.loadShared();
	new CarCounterChris().run();
}

public void run() {
    capture = new VideoCapture();
    capture.open(0);
    if (!capture.isOpened()) {
        System.err.println("Unable to open vid");
        System.exit(0);
    }
    
    while(true) {
    	Mat frame = new Mat();
    	capture.read(frame);
    	Mat resultMat = processImage(frame);
    	processMarkers();
    	for(Marker mark: markers) {
    		Imgproc.circle(frame, mark.getPoint(), 8, mark.color, 8);
    	}
    	for(Marker oldMark: oldMarkers) {
    		Imgproc.circle(frame, oldMark.getPoint(), 4, new Scalar(255,0,0), 4);
    	}
    	for(LineInfo lineInfo: lineInfos) {
    		Imgproc.line(frame, lineInfo.point1, lineInfo.point2, lineInfo.color, lineInfo.thickness);
    	}
    	helper.addImage(frame);
    }
}
public Mat processImage(Mat input){
	backSub.apply(input, fgMask);
    Imgproc.GaussianBlur(fgMask, fgMask, new Size(9, 9), 0);
    Imgproc.blur(fgMask, fgMask, new Size(40,40));
    Core.inRange(fgMask, lowerb, upperb, fgMask);
    Imgproc.blur(fgMask, fgMask, new Size(40,40));
    Core.inRange(fgMask, lowerb, upperb, fgMask);
    
    List<MatOfPoint> contours = new ArrayList<>();
    Mat hierarchy = new Mat();
    
    Imgproc.findContours(fgMask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
    for(MatOfPoint mop: contours) {
    	Rect r = Imgproc.boundingRect(mop);
    	if(r.width*r.height>150) {
    		Imgproc.rectangle(input, r.tl(), r.br(), new Scalar(0,0,255));
    		markers.add( new Marker( r.x+(r.width/2), r.y+(r.height/2) ));
    	}
    }
    return fgMask;
}

public void processMarkers() {
	Collections.sort(markers, Comparator.comparing(m -> m.y));
	Collections.reverse(markers);
	for(Marker mark: markers) {mark.timer+=1;}
	for(int i = 0; i<markers.size(); i++) {
		INNER_LOOP: for(int j = i+1; j<markers.size(); j++) {
			if(Math.abs(markers.get(i).x-markers.get(j).x)<80) {
				if(markers.get(i).timer+1 == markers.get(j).timer) {
					if(Math.sqrt( Math.pow(markers.get(i).x-markers.get(j).x, 2) + Math.pow(markers.get(i).y-markers.get(j).y, 2)) <60) {
						oldMarkers.add(markers.get(j));
						lineInfos.add(new LineInfo(markers.get(i).getPoint(), markers.get(j).getPoint()));
						markers.get(i).buddy = markers.get(j);
						markers.remove(j);
						break INNER_LOOP;
					}
				}
			}
		}
	}
	ArrayList<Marker> marksToRemove = new ArrayList<Marker>();
	for(int i = 0; i<markers.size(); i++) {
		if(markers.get(i).timer>4) {
			if(markers.get(i).buddy != null) {
				if(recursiveLineFollow(0, markers.get(i), marksToRemove) >12) {
					carCount+=1;
					System.out.println("possible car found. total cars is "+carCount);
					oldMarkers.add(markers.get(i));
				}
			} else {
				marksToRemove.add(markers.get(i));
				}
		}
	}
	markers.removeAll(marksToRemove);
	oldMarkers.removeAll(marksToRemove);
}

	public int recursiveLineFollow(int n, Marker marker, ArrayList<Marker> marksToRemove) {
		n+=1;
		marksToRemove.add(marker);
		
		if(marker.buddy != null) {
			return recursiveLineFollow(n, marker.buddy, marksToRemove);
		} else {
			return n;
		}
	}

}
