
import java.awt.Color;
import java.awt.Graphics;

/**
 * A sensor is fixed to a vehicle and can detect the presence of another vehicle
 * (or the world boundary) at its current location.
 */
public class Sensor {

    // Position relatively to the vehicle.
    private double x, y;
    private double size;

    // Position in the global world.
    private double xWorld, yWorld;

    private Color color;

    private int value;

    // Each sensor is linked to one neuron and transmits its value to it.
    private Neuron neuron;

    public Sensor(double xParam, double yParam) {
        this.x = xParam;
        this.y = yParam;
        this.size = 3;
        this.color = Color.GRAY;
        this.setValue(0);
    }

    public void setNeuron(Neuron n) {
        this.neuron = n;
        n.setValue(value);
    }

    @Override
    public String toString() {
        return this.value + "";
    }

    // If the vehicle is at (x0, y0) and oriented with angle,
    // what are the global coordinates of the sensor ?
    public void computeWorldCoordinates(double xVehicle, double yVehicle, double angle) {

        this.xWorld = xVehicle + this.x * Math.cos(angle) - this.y * Math.sin(angle);
        this.yWorld = yVehicle + this.y * Math.cos(angle) + this.x * Math.sin(angle);
    }

    public void paint(Graphics g, double panelHeight, double x0, double y0, double zoom) {

        int xApp = (int) (x0 + this.xWorld * zoom);
        int yApp = (int) (panelHeight - (y0 + this.yWorld * zoom));

        g.setColor(this.color);
        g.fillRect((int) (xApp - size / 2), (int) (yApp - size / 2), (int) size, (int) size);
    }

    public boolean collideWithBorders(double xMin, double xMax, double yMin, double yMax) {
        boolean res = false;
        this.setValue(0);
        if (this.xWorld > xMax || this.xWorld < xMin) {
            res = true;
            this.setValue(1);
        }
        if (this.yWorld > yMax || this.yWorld < yMin) {
            res = true;
            this.setValue(1);
        }

        // Transmit to the associated neuron.
        this.neuron.setValue(this.value);

        return res;
    }

    // Returns 1 if the sensor is inside the parameter vehicle, 0 otherwise.
    public boolean collideWithVehicle(Vehicle v) {

        if (v.containsPoint(this.xWorld, this.yWorld)) {
            this.value = 1;
            return true;
        } else {
            this.value = 0;
            return false;
        }
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void setSize(int param) {
        this.size = Math.max(3, param);
    }

    public double getValue() {
        return value;
    }

    // This method must be private. Only from within the sensor can someone set its value.
    private void setValue(int value) {
        this.value = value;
    }
}
