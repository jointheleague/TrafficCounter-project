import java.awt.Graphics2D;
import java.util.ArrayList;

public class TrafficController {
	public ArrayList<TrafficLight> trafficLights = new ArrayList<TrafficLight>();
	public TrafficController() {
		trafficLights.add(new TrafficLight(420,350,"XXX"));
		trafficLights.add(new TrafficLight(650,420,"XXX"));
		trafficLights.add(new TrafficLight(800,800,"XXX"));
		trafficLights.add(new TrafficLight(100,800,"XXX"));
	}
	
	public void draw(Graphics2D g) {
		for(TrafficLight tf: trafficLights) {
			tf.draw(g);
		}
	}
}
