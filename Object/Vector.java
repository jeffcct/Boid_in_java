package Object;
import java.lang.Math;

public class Vector {
    float[] vals;

    public Vector(int dim) {
        vals = new float[dim];
        setAll(0);
    }

    public float get(int dimNum) {
        if (dimNum < vals.length) {
            return vals[dimNum];
        }
        return 0;
    }

    public void set(int dimNum, float val) { 
        if (dimNum < vals.length) {
            vals[dimNum] = val;
        }
    }

    public void setAll(float val) {
        for (int i = 0; i < vals.length; i++) {
            this.vals[i] = val;
        }
    }

    public double getMag() {
        double total = 0;
        for (int i = 0; i < vals.length; i++) {
            total += Math.pow(vals[i], 2);
        }
        return Math.sqrt(total);
    }

    public boolean normalize() {
        double magnitude = getMag();
        if (magnitude == 0) {
            return false;
        }
        for (int i = 0; i < vals.length; i++) {
            vals[i] /= magnitude;
        }
        return true;
    }

    public void add(Vector other) {
        if (other.vals.length != this.vals.length) {
            return;
        }
        for (int i = 0; i < vals.length; i++) {
            this.vals[i] += other.vals[i];
        }
    }

    public void mult(float val) {
        for (int i = 0; i < vals.length; i++) {
            this.vals[i] *= val;
        }
    }

    public Vector copy() {
        Vector newVector = new Vector(vals.length);
        for (int i = 0; i < vals.length; i++) {
            newVector.set(i, vals[i]);
        }
        return newVector;
    }

    public Vector multiplyVectors(Vector vector) {
        if (vals.length != vector.vals.length) {
            return null;
        }

        Vector output = new Vector(this.vals.length);
            for (int i = 0; i < this.vals.length; i++) {
                output.set(i, this.get(i) * vector.get(i));
            }
            return output;
    }

    public Vector in_place_mult(float val) {
        Vector newVector = this.copy();
        newVector.mult(val);
        return newVector;
    }

    public Vector getPerpindicularClockWise2D() {
        Vector newVector = new Vector(2);
        newVector.set(0, this.get(1));
        newVector.set(1, -1 * this.get(0));
        return newVector;
    }

    public float dist(Vector other) {
        float total = 0;
        if (other.vals.length != this.vals.length) {
            return -1;
        }
        for (int i = 0; i < vals.length; i++) {
            total += Math.pow(other.vals[i] - this.vals[i], 2);
        }
        return (float)Math.sqrt(total);
    }
}
