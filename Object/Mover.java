package Object;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Mover {

    protected Vector position;
    protected Vector velocity;
    protected Vector acceleration;
    protected int panelHeight, panelWidth, mass, size, height;
    protected float maxSpeed, maxForce;

    Mover(int size) {
        this.size = size;
        this.position = new Vector(2);
        this.velocity = new Vector(2);
        //this.velocity.set(0, (float)ThreadLocalRandom.current().nextDouble(-0.2, 0.2));
        //this.velocity.set(1, (float)ThreadLocalRandom.current().nextDouble(-0.2, 0.2));
        this.acceleration = new Vector(2);
        this.maxSpeed = 1;
        this.maxForce = 0.01f;
        this.mass = 1;
        this.size = size;
    }

    void setPosition(Vector position) {
        this.position = position;
    }

    protected void update() {
        // will update the boid according to it's movement method.
        velocity.add(acceleration);
        acceleration.setAll(0);
        double vel_mag = velocity.getMag();
        if (vel_mag > this.maxSpeed) {
            velocity.mult(maxSpeed / (float)vel_mag);
        }
        position.add(velocity);
    }

    public void applyForce(Vector force) {
        if (force.getMag() > this.maxForce) {
            force.normalize();
            force.mult(this.maxForce);
        }
        force.mult(1 / this.mass);
        this.acceleration.add(force);
    }
}
