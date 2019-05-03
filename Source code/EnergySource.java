import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;
/*
 * 
 * EnergySource class
 * (Thread)
 * It is in charge of managing the energy (the shared resource)
 * depending on the player actions during the game
 * 
 */

public class EnergySource extends GraphicEntity implements Runnable{
	
	
	private Energy shared_resource;
	private Bars threshold;
	private Bars pointer;
	private int pointer_dir_y;
	private boolean space_released;
	 
	// Constructor. It recieves the Energy object as a shared resource
	public EnergySource(int xE, int yE, Energy sr) {
		super(xE, yE, 20, 300);
		threshold = new Bars(xE, 10, 20, 30);
		pointer = new Bars(xE, yE, 20, 15);
		changeThreshold();
		pointer_dir_y = 1;
		space_released = false;
		shared_resource = sr;
	}

	// Rendering of the different bars that manage the energy
	@Override
	public synchronized void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(color);
		g.drawRect(x1, y1, width, height);
		g.setColor(new Color(255,0,0));
		threshold.paint(g);
		g.setColor(color);
		pointer.paint(g);
	}

	// Function that locates the threshold bar randomly
	public void changeThreshold() {
		Random r = new Random();
		threshold.setY1(this.y1 + r.nextInt(this.height - threshold.getHeight()));
		//System.out.println("Threshold at: "+threshold.getY1());
	}

	// Function that manages the input of the user
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			space_released = true;

		}
	}
	
	// Function that moves the bar that needs to collide with the threshold
	// and makes it bounce depending on boundaries
	public void movePointer() {
		if (pointer_dir_y == 1 && pointer.getY2() < this.y2) {
			movePointerDown();
		}else {
			pointer_dir_y = 0;
		}
		
		if (pointer_dir_y == 0 && pointer.getY1() > this.y1) {
			movePointerUp();
		} else {
			pointer_dir_y = 1;
		}
		
		//System.out.println(pointer_dir_y);
		
	}
	
	public  void movePointerDown() {
		pointer.setY1(pointer.getY1() + 5);	
	}
	
	public  void movePointerUp() {
		pointer.setY1(pointer.getY1() - 5);	
	}
	
	// Function that starts the thread and verifies if the user hits the
	// threshold with the pointer bar in an infinite loop (until the thread is interrupted)
	@Override
	public void run() {
		System.out.println("Managing energy...");
		while(true) {
			try {
				movePointer();
			
				
				if (space_released) {
					
					if (pointer.hasCollision(threshold, pointer.getX1(), pointer.getY1(), pointer.getX2(), pointer.getY2())) {
						System.out.println("YAY!!!!");
						shared_resource.addEnergy(250);
					}else {
						System.out.println("MISS!!!");
						shared_resource.drainEnergy(50);
					}
					space_released = false;
					changeThreshold();
				}
				
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Source of energy thread interrupted");
				return;
			}
		}
	}
	
}
