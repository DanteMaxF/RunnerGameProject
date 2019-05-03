import java.awt.Graphics;
import java.util.Random;
/*
 * 
 * Obstacle class
 * 
 * This entities are in charge of moving to the
 * left, and the player needs to dodge them in
 * order to avoid losing energy
 * 
 */
public class Obstacle extends GraphicEntity{

	private int speed;
	private int floor_height;
	
	// Constructor, it receives the speed value
	public Obstacle(int speed) {
		super();
		this.floor_height = 470;
		this.speed = speed;
		this.height = 10;
		setX1(1290);
		setY1(this.floor_height - height);
	}
	

	// Rendering function
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(color);
		g.fillRect(x1, y1, width, height);
	}
	
	// Function that makes the obstacle move to the left, depending on the speed
	public void move() {
		setX1(this.getX1() - speed);
		if (this.getX1() < -this.width) {
			change();
			//System.out.println(this.toString());
		}
	}
	
	// If the obstacle leaves the screen, it will change its size and will appear in a random timestamp
	public void change() {
		Random r = new Random();
		setX1(r.nextInt((4000 - 1280) + 1) + 1280);
		setHeight(r.nextInt(190));
		setY1(this.floor_height - height);
	}

}
