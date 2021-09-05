import java.awt.Color;
import java.awt.Graphics2D;

public class Car {
	int width = 40;
	int height = 40;
	double x;
	double y;
	String direction;
	double angle;
	int positionNum;
	public Car(int x, int y, String direction, int pos) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.positionNum = pos;
		switch(direction) {
			case "up":
				angle = Math.PI*1.5;
				break;
			case "down":
				angle = Math.PI/2;
				break;
			case "right":
				angle = 0;
				break;
			case "left":
				angle = Math.PI;
				break;
			default:
				break;
		}
	}
	
	public void move() {
			x+=Math.cos(angle);
			y+=Math.sin(angle);
		}
	public void turnLeft() {
		angle-=0.005;
		switch( direction ){
			case "up":
				x+=Math.cos(angle);
				y+=Math.sin(angle);
				y-=.3;
				break;
			case "down":
				x+=Math.cos(angle);
				y+=Math.sin(angle);
				y+=.25;
				break;
			case "right":
				x+=Math.cos(angle);
				y+=Math.sin(angle);
				x+=.2;
				break;
			case "left":
				x+=Math.cos(angle);
				y+=Math.sin(angle);
				x-=.3;
				break;
			default:
				break;
		}
	}
	public void turnRight() {
		angle+=0.018;
		switch( direction ){
			case "up":
				x+=Math.cos(angle);
				y+=Math.sin(angle);
				break;
			case "down":
				x+=Math.cos(angle);
				y+=Math.sin(angle);
				angle+=.03;
				break;
			case "right":
				x+=Math.cos(angle);
				y+=Math.sin(angle);
				x-=.4;
				y+=.1;
				break;
			case "left":
				x+=Math.cos(angle);
				y+=Math.sin(angle);
				angle+=.025;
				break;
			default:
				break;
		}
	}
	
	public void draw(Graphics2D g) {
		//g.setColor(Color.RED);
		g.drawRect((int)x, (int)y, width, height);
	}
}
