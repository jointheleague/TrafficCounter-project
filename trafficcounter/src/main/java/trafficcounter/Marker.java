package trafficcounter;

import java.awt.Color;

import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class Marker {
	int x;
	int y;
	int timer;
	Scalar color;
	Marker buddy;
	
	public Marker(int xLoc, int yLoc) {
		x = xLoc;
		y = yLoc;
		timer = 0;
		color = new Scalar(0,255,0);
		buddy = null;
	}
	
	public Point getPoint() {
		Point p = new Point(x,y);
		return p;
	}
}
