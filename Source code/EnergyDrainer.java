import java.awt.event.KeyEvent;
/*
 * 
 * EnergyDrainer class
 * (Thread)
 * It is in charge of substracting energy while the game
 * is running, it takes the Energy object as a shared resource
 * 
 */
public class EnergyDrainer implements Runnable{
	
	private Energy shared_resource;
	
	//Constructor. It takes the Energy object from GamePanel as a shared resource
	public EnergyDrainer(Energy sr) {
		shared_resource = sr;
		
	}
	
	//Drains the energy in an infinite loop (at leas until the thread is interrupted)
	@Override
	public void run() {
		System.out.println("Draining energy...");
		while(true) {	
			try {
				
				shared_resource.drainEnergy(2);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Energy Thread Interrupted");
				return;
			}
		}
		//System.out.println("YOU RAN OUT OF ENERGY\nEnergy Thread ended");
	}

}
