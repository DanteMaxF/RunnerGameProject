import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Player extends GraphicEntity{
	private static boolean hitbox=false; // True to display hitbox 
	private int dirY;
	private int dirX;
	private int speed;
	private int falling_speed;
	private boolean is_alive;


	
	public Player(int xP, int yP, int wP, int hP) {
		super(xP, yP, wP, hP);
		dirY = 0;
		dirX = 0;
		speed = 10;
		falling_speed = 0;
		is_alive = true;
	}


	@Override
	public synchronized void paint(Graphics g) {
		if (hitbox) {
			g.setColor(new Color(255,0,0));
			g.drawRect(x1, y1, width, height);
			g.fillOval(x1, y1, 2, 2);
			g.fillOval(x1, y2, 2, 2);
			g.fillOval(x2, y1, 2, 2);
			g.fillOval(x2, y2, 2, 2);
		}
		
		if (is_alive) {
			g.setColor(new Color(255, 101, 0));
			g.fillOval(x1, y1, width, height);
		}else {
			g.setColor(new Color(0,0,0));
			g.drawString("GAME OVER", x1, y1);
			
		}
		
		//g.fillRect(x1, y1, width, height);
		//g.fillOval(x1, y1, width, height);
		//System.out.println(this.toString());
		
	
	}
	
	public void kill() {
		this.is_alive = false;
		this.dirX = 0;
		this.dirY = 0;
		this.setX1(640);
		this.setY1(250);
	}
	
	public boolean isAlive() {
		return is_alive;
	}
	
	public  void moveDown() {
		this.setY1(y1 + speed);	
	}
	
	public  void moveUp() {
		this.setY1(y1 - speed);	
	}
	
	public  void moveRight() {
		if (this.getX2() < 1280) {
			this.setX1(x1 + speed);
		}else {
			this.setX1(1280-this.getWidth());
		}
	}
	
	public  void moveLeft() {
		if (this.getX1() > 0) {
			this.setX1(x1 - speed);
		} else {
			this.setX1(0);
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if (isAlive()) {
			if (e.getKeyCode() == KeyEvent.VK_UP && (this.y2 >= 470)) {
				//System.out.println("Up");
				dirY = -1;
			}
			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				//System.out.println("Down");
				dirY = 1;
			}
			else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				//System.out.println("Left");
				dirX = -1;
			}
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				//System.out.println("Right");
				dirX = 1;
			}
			
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP && (this.y2 <= 470)) {	
			dirY = 0;
			this.falling_speed = 0;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dirY = 0;
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {	
			dirX = 0;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dirX = 0;
		}
	}
	
	public synchronized void checkDirection() {
		switch (dirY) {
			case 1:
				moveDown();
				break;
			case -1:
				moveUp();
				break;
		}
		
		switch(dirX) {
			case 1:
				moveRight();
				break;
			case -1:
				moveLeft();
				break;
		}
	}
	
	public synchronized void checkFall() {
		
		if (this.y2 < 470) {
			setY1(getY1() + this.falling_speed);
		}else {
			this.falling_speed = 0;
			setY1(470-this.getHeight());
		}
	}


	public void increaseFallingSpeed(int gravity) {
		this.falling_speed = this.falling_speed + gravity;
		if (this.falling_speed > 60) {
			this.falling_speed = 60;
		}
	}
	
	
	public void update() {
		checkFall();
		checkDirection();
	}
}
