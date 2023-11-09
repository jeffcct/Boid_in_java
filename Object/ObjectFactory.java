package Object;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ObjectFactory {
    public static Boid createDefaultBoid(int size) {
        return new Boid(size);
    }
    public static Boid createColoredBoid(int size, Color color) {
        Boid newBoid = new Boid(size);
        newBoid.setColor(color);
        return newBoid;
    }

    public static void instantiateBoids(ArrayList<Boid> boids, int num) {
        // Takes in an ArrayList and adds Boids to the ArrayList.
        for (int i = 0; i < num; i++) {
            Boid boid = new Boid(10);
            int xPos = ThreadLocalRandom.current().nextInt(0, 601);
            int yPos = ThreadLocalRandom.current().nextInt(0, 801);
            Vector position = new Vector(2);
            position.set(0, xPos);
            position.set(1, yPos);
            boid.setPosition(position);
            boids.add(boid);
        }
    }
}
