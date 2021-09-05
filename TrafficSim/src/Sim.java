import java.awt.Component;

import javax.swing.JFrame;
public class Sim extends JFrame{
	static int width = 1000;
	static int height = 1000;
	
	public Sim() {
		super("Sim");
		setSize(width, height);
		Start start = new Start();
		((Component)start).setFocusable(true);
		getContentPane().add(start);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Sim run = new Sim();
	}
	
}
