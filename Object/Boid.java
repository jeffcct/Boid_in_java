package Object;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.math.*;

public class Boid extends Mover {
    private Color color = Color.blue;
    private double wanderTheta;
    private Vector tempGoal;
    public int protected_range, visible_range;

    public int[] getRanges() {
        return new int[] {protected_range, visible_range};
    }
    
    //initializations
    public Boid(int size) {
        super(size);
        this.wanderTheta = 0;
        this.protected_range = 10;
        this.visible_range = 40;
    }

    public Vector getPosition() {
        // doesn't let other classes modify a boids position, only returns a copy of the position for them to look at / use.
        return this.position.copy();
    }

    public Vector getVelocity() {
        return this.velocity.copy();
    }

    void setColor(Color color) {
        this.color = color;
    }

    //movement functionalities
    @Override
    public void update() {
        // will update the boid according to it's movement method.
        //Vector goal = new Vector(2);
        //goal.setAll(100);
        //applyForce(seek(goal).in_place_mult(0.005f));
        //applyForce(randomWander());
        super.update();

        if (position.get(0) > 300) {
            position.set(0, 0);
        }
        if (position.get(0) < 0) {
            position.set(0, 300);
        }
        if(position.get(1) > 500) {
            position.set(1, 0);
        }
        if (position.get(1) < 0) {
            position.set(1, 500);
        }
    }

    public void show(Graphics g) {
        g.setColor(this.color);
        Vector direction = getVelocity();
        if (direction.getMag() == 0) {
            direction.set(0, 1);
        } else {
            direction.normalize();
        }

        direction.mult(size);
        Vector perpindicular = direction.getPerpindicularClockWise2D();
        perpindicular.mult(0.5f);

        int[] front = new int[] {(int)(this.position.get(0) + direction.get(0)), (int)(this.position.get(1) + direction.get(1))};
        int[] back = new int[] {(int)(this.position.get(0) - direction.get(0)), (int)(this.position.get(1) - direction.get(1))};
        int[] back_right = new int[] {(int)(back[0] + perpindicular.get(0)), (int)(back[1] + perpindicular.get(1))};
        int[] back_left = new int[] {(int)(back[0] - perpindicular.get(0)), (int)(back[1] - perpindicular.get(1))};

        int[] xPoints = {front[0], back_left[0], back_right[0]}; // X-coordinates of the triangle vertices
        int[] yPoints = {front[1], back_left[1], back_right[1]}; // Y-coordinates of the triangle vertices

        g.fillPolygon(new Polygon(xPoints, yPoints, 3));
        //g.fillOval((int)tempGoal.get(0), (int)tempGoal.get(1), 4, 4);
    }

    //Steering Forces
    public Vector seek(Vector position) {
        Vector goal = position;
        goal.add(this.position.in_place_mult(-1));
        return seekVel(goal);
    }

    public Vector seekVel(Vector velocity) {
        Vector goal = velocity;
        goal.normalize();
        goal.mult(this.maxSpeed);
        goal.add(this.velocity.in_place_mult(-1));
        if (goal.getMag() < this.maxForce) {
            goal.normalize();
            goal.mult(this.maxForce);
        }
        return goal;
    }

    public Vector flee(Vector position) {
        Vector goal = seek(position);
        goal.mult(-1);
        return goal;
    }

    public Vector persue(Vector position, Vector velocity) {
        velocity.mult(10);
        position.add(velocity);
        return seek(position);
    }

    public Vector evade(Vector position, Vector velocity) {
        Vector goal = persue(position, velocity);
        goal.mult(-1);
        return goal;
    }

        //Wandering Forces
    public Vector intentionalWander() {
        Vector goal = getVelocity();
        if (!goal.normalize()) {
            goal.setAll(0);
            return goal;
        }

        goal.mult(100);
        return wander(goal);
    }

    public Vector randomWander() {
        Vector goal = new Vector(2);
        return wander(goal);
    }

    public Vector wander(Vector goal) {

        float wanderRadius = 100;
        wanderTheta += ThreadLocalRandom.current().nextDouble(-0.2, 0.2);

        Vector wanderTarget = new Vector(2);
        wanderTarget.set(0, (float) Math.cos(wanderTheta));
        wanderTarget.set(1, (float) Math.sin(wanderTheta));
        wanderTarget.mult(wanderRadius);
        goal.add(wanderTarget);
        
        tempGoal = goal.copy();
        tempGoal.add(position);
        
        goal.normalize();
        goal.mult(this.maxForce);
        return goal;
    }

        //Necessary Boid Forces
    public Vector seperate(Boid boid) {
        Vector force = evade(boid.getPosition(), boid.getVelocity());
        force.mult(1 / (1 + position.dist(boid.position)));
        return force;
    }

    public Vector cohere(ArrayList<Boid> local_boids) {
        Vector average_position = new Vector(2);
        for (Boid boid : local_boids) {
            average_position.add(boid.position);
        }
        average_position.mult(1 / local_boids.size());
        return seek(average_position);
    }

    public Vector align(ArrayList<Boid> local_boids) {
        Vector goal = new Vector(2);
        for (Boid boid : local_boids) {
            goal.add(boid.velocity);
        }
        return seekVel(goal);
    }
}
