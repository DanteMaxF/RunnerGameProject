/*
 * CollisionChecker class
 * (Thread)
 * It is in charge on checking the 
 * collisions between the obstacles and the player
 * 
 */

public class CollisionChecker implements Runnable{
	
	private Energy shared_energy;
	private Player shared_player;
	private Obstacle shared_enemies[];
	
	// Constructor, it recieves the player, the obstacles and the energy, which are shared resources
	public CollisionChecker(Energy sen, Player sp, Obstacle se[]) {
		this.shared_energy = sen;
		this.shared_player = sp;
		this.shared_enemies = se;
	}
	
	// Verifies collisions in an endless loop (at least until the thread is interrupted)
	@Override
	public void run() {
		System.out.println("Verifying collisions...");
		while(true) {
			try {
				for (int i=0; i<shared_enemies.length; i++) {
					if (shared_player.hasCollision(shared_enemies[i], shared_player.getX1(), shared_player.getY1(), shared_player.getX2(), shared_player.getY2())) {
						//System.out.println("COLLISION WITH: " + i);
						shared_energy.drainEnergy(10);
					}
				}
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Collision Thread Interrupted");
				return;
			}
		}
	}

}


