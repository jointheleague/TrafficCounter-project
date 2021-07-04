package trafficcounter;

import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class LineInfo {
	Point point1;
	Point point2;
	Scalar color;
	int thickness;
	
	public LineInfo(Point p1, Point p2) {
		point1 = p1;
		point2 = p2;
		color = new Scalar(255,255,255);
		thickness = 1;
	}
}
