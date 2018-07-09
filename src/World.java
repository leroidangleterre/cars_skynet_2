
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class World {

    private int nbVehicles;
    private ArrayList<Vehicle> vehicleList;

    private double width, height;
    private double margin;

    private boolean isRunning;
    private double dt;

    private boolean displayNets;

    public World() {
        this(5);
    }

    public World(int nbVehicles, double width, double height) {
        this.isRunning = false;
        this.nbVehicles = nbVehicles;
        this.width = width;
        this.height = height;
        this.margin = 0.1 * this.width;

        this.vehicleList = new ArrayList<>();

        this.displayNets = true;

        double x, y;
        for (int i = 0; i <= this.nbVehicles / 2; i++) {
            // Vehicles on the left, facing right.
//            x = 0.2 * (1 + Math.random()) * this.width;
//            y = 0.2 * Math.random() * this.height;
            x = this.width * (0.5 + 0.5 * (0.5 - Math.random()));
            y = this.height * (0.5 + 0.5 * (0.5 - Math.random()));
            Vehicle v = new Vehicle(x, y);
            this.vehicleList.add(v);
            // // Vehicles on the right, facing left.
            // x = (0.8 + 0.2*Math.random()) * this.width;
            // y = Math.random() * this.height;
            // v = new Vehicle(x, y);
            // v.rotate(Math.PI);
            // this.vehicleList.add(v);
        }
        dt = 0.3;
    }

    public World(int nbPoints) {
        this(nbPoints, 1.0, 1.0);
    }

    /* Return the physical width of the mesh. */
    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    /* Move each point away from its closest neighbor. */
    public void evolve() {
        if (this.isRunning) {
            this.evolveOneStep();
        }
    }

    public void evolveOneStep() {
        for (Vehicle v : this.vehicleList) {
            if (this.dt == 0) {
                this.dt = 0.3;
            }
            // System.out.println("World.evolve: dt = " + this.dt);
            v.evolve(this.dt);
            v.rebound(0, this.width, 0, this.height, margin);
        }
        this.computeCollisions();

    }

    // Simple version.
    private void computeCollisions() {

        for (Vehicle v : this.vehicleList) {
            v.razCollisions();
            v.collideWithBorders(0, this.width, 0, this.height);
            for (Vehicle v2 : this.vehicleList) {
                if (v != v2) {
                    v.collideWith(v2);
                }
            }
        }
    }

    private void paintBorders(Graphics g, double panelHeight, double x0, double y0, double zoom) {
        g.setColor(Color.black);
        // Legal borders
        g.drawRect((int) x0, (int) (panelHeight - (y0 + height * zoom)), (int) (width * zoom), (int) (height * zoom));
        // Extended borders
        g.drawRect((int) (x0 - margin * zoom), (int) (panelHeight - (y0 + (height + margin) * zoom)), (int) ((width + 2 * margin) * zoom),
                (int) ((height + 2 * margin) * zoom));
    }

    /* Display the mesh on a graphic panel. */
    public void paint(Graphics g, int panelHeight, double x0, double y0, double zoom) {

        this.paintBorders(g, panelHeight, x0, y0, zoom);

        for (Vehicle v : this.vehicleList) {
            v.paint(g, panelHeight, x0, y0, zoom);
        }
        for (Vehicle v : this.vehicleList) {
            v.paintCollisions(g, panelHeight, x0, y0, zoom);
        }
        for (Vehicle v : this.vehicleList) {
            v.paintSensors(g, panelHeight, x0, y0, zoom);
        }
    }

    public void startSimulation() {
        this.isRunning = true;
    }

    public void stopSimulation() {
        this.isRunning = false;
    }

    public void toggleSimulation() {
        this.isRunning = !this.isRunning;
    }

    public void rotateVehicles() {
        double angle = 0.5;
        this.rotateVehicles(angle);
    }

    public void rotateVehicles(double angle) {
        for (Vehicle v : this.vehicleList) {
            v.rotate(angle);
        }
    }

    public void accelerateVehicles(double dV) {
        for (Vehicle v : this.vehicleList) {
            v.accelerate(dV);
        }
    }

    // Randomly mutate the neural net of some vehicles.
    public void mutateNeuralNets() {

        for (Vehicle v : this.vehicleList) {
            v.mutateNeuralNet();
        }
    }

    public void cloneVehicles() {
        // TODO Auto-generated method stub

    }

    public void killWeakest() {

    }

    public void toggleDisplayNets() {
        this.displayNets = !this.displayNets;
    }

    /**
     * Retrieve the first vehicle.
     *
     * @return the first vehicle of the world, if it exists; null otherwise.
     */
    public Vehicle getFirstVehicle() {
        if (this.vehicleList != null) {
            return this.vehicleList.get(0);
        } else {
            return null;
        }
    }

    /**
     * Reset the score of each vehicle.
     */
    public void resetAllScores() {
        // TODO
    }

    /**
     * Execute one step of the A-star algorithm.
     *
     * Step 1: reset the scores of all vehicles.
     *
     * Step 2: let them evolve for a given amount of timesteps
     *
     * Step 3: sort them according to their score
     *
     * Step 4: keep only the best half of them (possibly with a bit of
     * randomness)
     *
     * Step 5: re-populate by creating new vehicles as clones of the survivors,
     * with slight modifications in the neural net coefficients.
     */
    public void oneStepAstar() {

    }
}
