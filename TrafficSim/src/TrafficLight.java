import java.awt.Graphics2D;

public class TrafficLight {
	public int x;
	public int y;
	public String state;
	public TrafficLight(int x, int y, String state) {
		this.state = state;
		this.x = x;
		this.y = y;
		this.state = state;
	}
	
	public void draw(Graphics2D g) {
		g.drawString(state.substring(0,1), x, y);
		g.drawString(state.substring(1,2), x, y+15);
		g.drawString(state.substring(2,3), x, y+30);
		g.drawRect(x-5, y-15, 20, 50);
	}
}
