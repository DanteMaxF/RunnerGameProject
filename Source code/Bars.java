import java.awt.Graphics;
/*
 * Bars class
 * 
 * (Inherits variables and methods from GraphicEntity) 
 * 
 * It is used in the EnergySource class as
 * objects that manage the energy
 */
public class Bars extends GraphicEntity{
	public Bars(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.fillRect(x1, y1, width, height);
		
	}
}
