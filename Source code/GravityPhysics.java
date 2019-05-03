/*
 * 
 * GravityPhysics class
 * (thread)
 * It is in charge of managing the physics of the player in order to make
 * the jump more organic.
 * 
 */
public class GravityPhysics implements Runnable{
	private Player shared_resource;
	private int gravity = 2;
	
	// Constructor, it receives the Player object as a shared resource
	public GravityPhysics(Player fs) {
		shared_resource = fs;
	}
	
	// A function that increase the vertical velocity in a loop, depending on the position of the player.
	// (until the thread is interrupted)
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Applying physics...");
		while(true) {
			try {
				shared_resource.increaseFallingSpeed(gravity);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Physics Thread interrupted");
				return;
			}
		}
		
	}

}
