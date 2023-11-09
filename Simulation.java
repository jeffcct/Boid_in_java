import javax.swing.*;

import Object.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/*
 * =============================================================================================================================
 * Simulation.java : The component of the program that holds the simulation of my boid program.
 * By Jeff Smith.
 * =============================================================================================================================
 */

public class Simulation extends JComponent implements Runnable {
    private int num_recursions;
    private Thread simulationThread;
    private DataStructure ds;

    public Simulation() {
        ds = new Naive();
        start();
    }

    public void start() {
        simulationThread = new Thread(this);
        num_recursions = 0;
        simulationThread.start();
        ds.begin();
    }
    
    public final void paintComponent(Graphics g) {
        super.paintComponent(g);
        ds.update(g);
    }

    @Override
    public void run() {
        int tickLength = 14000;
		Thread myThread = Thread.currentThread();
        long last = System.currentTimeMillis();
		while(simulationThread==myThread) {
			repaint();
            last = System.currentTimeMillis();
            
            try {
                Thread.sleep((long)(last + tickLength - System.currentTimeMillis()));
            } catch(InterruptedException ie) {} 
            catch(IllegalArgumentException ie) {System.out.printf("negative value");}

		}
	}

    abstract class DataStructure {
        protected ArrayList<Boid> boids;
        protected void begin() {
            this.boids = new ArrayList<Boid>();
            ObjectFactory.instantiateBoids(boids, 300);
            changeTo();
        }
        protected abstract void update(Graphics g);
        abstract protected void changeTo();
    }
    
    class Naive extends DataStructure {
        protected Naive() { }

        protected void update(Graphics g) {
            for (Boid boid : boids) {
                ArrayList<Boid> nearby_boids = new ArrayList<Boid>();
                for (Boid other : boids) {
                    if (boid == other) {
                        continue;
                    }
                    float distance = boid.getPosition().dist(other.getPosition());
                    if (distance < boid.getRanges()[1]) {
                        if (distance < boid.getRanges()[0]) {
                            boid.applyForce(boid.seperate(other).in_place_mult(0.5f));
                        }
                        nearby_boids.add(other);
                    }
                }
                if (nearby_boids.size() != 0) {
                    boid.applyForce(boid.align(nearby_boids).in_place_mult(0.9f));
                    boid.applyForce(boid.cohere(nearby_boids).in_place_mult(0.25f));
                }
                boid.update();
                boid.show(g);
            }
        }

        protected void changeTo() { }
    }
}
