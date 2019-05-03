import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


/*
 * 	GamePanel Class (Main)
 * 	It is in charge of managing the whole game and its threads
 */

public class GamePanel extends JPanel implements Runnable{
	
	private int score;
	private boolean physics_running = false;
	
	private Player dude;
	
	private Energy ene;
	
	private GravityPhysics engine;
	private Thread physics_engine;
	
	private EnergyDrainer ed;
	private Thread energy_drainer;
	
	private EnergySource es;
	private Thread energy_source;
	
	private Obstacle enemy[];
	private CollisionChecker cc;
	private Thread collision_checker;
	
	private Thread update_thread;
	
	// Constructor functions
	public GamePanel() {
		System.out.println("Rendering...");
		setBackground(new Color(188, 196, 255));
		dude = new Player(30,80,50,50);
		ene = new Energy(10, 10, 1000);
		keyboard_setup();
		
		// Setup for the physics engine in a thread
		engine = new GravityPhysics(dude);
		physics_engine = new Thread(engine);
		//physics_engine.start();
		
		// Setup for the energy drainer in a thread
		ed = new EnergyDrainer(ene);
		energy_drainer = new Thread(ed);
		//energy_drainer.start();
		
		// Setup for the energy manager in a thread
		es = new EnergySource(10,40, ene);
		energy_source = new Thread(es);
		//energy_source.start();
		
		// Setup for the obstacle objects
		enemy = new Obstacle[3];
		for (int i=0; i<enemy.length; i++) {
			enemy[i] = new Obstacle(10);
		}
		
		// Setup for the collision manager in a thread
		cc = new CollisionChecker(ene, dude, enemy);
		collision_checker = new Thread(cc);
		//collision_checker.start();
		
		score = 0;
		
		/*
		 * Setup for the update thread, in order to
		 * separate it from the main thread which is
		 * in charge of managing the rendering
		 */
		update_thread = new Thread(this);
		update_thread.start();
	}
	
	// Function in charge of rendering the objects
	// (this works in the main thread)
	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		if (!game_started) {
			g.drawString("Press ENTER to start", 640, 250);
		}
		for (int i=0; i<enemy.length; i++) {
			enemy[i].paint(g);
		}
		
		dude.paint(g);
		ene.paint(g);
		es.paint(g);
		g.setColor(new Color(0,0,0));
		g.drawString("Score: "+score, 1200, 20);
		
	}
	
	// Function in charge of updating all the ingame objects
	// (this works in a thread started by the main)
	// It also ends the game and stop the rest of the threads, except the main
	public void update() throws InterruptedException {
		if (ene.getEnergy() > 0) {
			if (game_started) {
				dude.update();
				for (int i=0; i<enemy.length; i++) {
					enemy[i].move();
				}
				score++;
			}
		}
		else {
			energy_drainer.interrupt();
			energy_drainer.join();
			
			physics_engine.interrupt();
			physics_engine.join();
			
			energy_source.interrupt();
			energy_source.join();
			
			collision_checker.interrupt();
			collision_checker.join();
			
			dude.kill();
			update_thread.interrupt();
			update_thread.join();
			
		}
	}
	
	boolean game_started = false;
	
	// Function in charge of managing inputs in the program
	// (Runs in the main thread)
	private void keyboard_setup() {
		KeyListener listener = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (game_started) {	
					dude.keyPressed(e);
					es.keyPressed(e);
				}else {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						game_started = true;
						energy_drainer.start();
						physics_engine.start();
						energy_source.start();
						collision_checker.start();
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				dude.keyReleased(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
			
		};
		addKeyListener(listener);
		setFocusable(true);
	}
	
	// Function that calls the update update function in the update_thread
	@Override
	public void run() {
		System.out.println("Updating ingame objects...");
		while (true) {
			try {
				this.update();
				Thread.sleep(9);
			} catch (InterruptedException e) {
				System.out.println("Update thread interrupted");
				return;
			}
		}
	}
	
	// Main function that initializes the game
	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Dante Flores");
		GamePanel game = new GamePanel();
		frame.getContentPane().add(game, BorderLayout.CENTER);
		frame.setSize(1280,500);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		while (true) {
			//game.update();
			game.repaint();
			Thread.sleep(10);
		}
	}

	
}
