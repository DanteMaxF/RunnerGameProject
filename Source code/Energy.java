import java.awt.Color;
import java.awt.Graphics;

/*
 * 
 * Energy Class
 * 
 * It is in charge of having a record of the actual energy of the
 * player. It is managed by many threads as a shared resource
 */

public class Energy extends GraphicEntity{
	
	private int energy;
	private int full_energy;
	private Color energy_color;
	
	// Constructos
	public Energy(int xP, int yP, int e) {
		super(xP, yP, e, 20);
		full_energy = e;
		energy = e;
		color = new Color(255,0,0);
		energy_color = new Color(85,134,0);
		
	}
	
	public synchronized int getEnergy() {
		return this.energy;
	}
	
	// Rendering of the Energy bar by render thread
	public synchronized void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x1, y1, full_energy, 20);
		g.setColor(energy_color);
		g.fillRect(x1, y1, energy, 20);
	}
	
	// Function to substract energy by different threads
	public synchronized void drainEnergy(int e) {
		this.energy = this.energy - e;
	}

	// Function to add energy by different threads
	public synchronized void addEnergy(int e) {
		this.energy = this.energy + e;
		if (this.energy > this.full_energy) {
			this.energy = this.full_energy;
		}
	}
}
