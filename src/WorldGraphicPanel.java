
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class WorldGraphicPanel extends GraphicPanel {

    private static final long serialVersionUID = 775535355282792517L;

    /*
	 * The origin of the represented environment will be visible
	 * at the x0-th pixel column and at the y0-th pixel line,
	 * starting from the lower-left corner.
	 * The zoom value is the amount of pixels between that origin
	 * and the point of coordinates (1, 0).
     */
    private World world;
    private Timer timer;
    private boolean isRunning;

    private int date;

    /*
	 * Either the machines are superposed (each one is displayed in the same referential),
	 * or they all have their own referential.
     */
    private boolean superposed = true;

    public WorldGraphicPanel(Window w) {
        this();
    }

    public WorldGraphicPanel() {
        super();
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                date++;
                world.evolve();
                repaint();
            }
        };
        int period = 50;
        this.timer = new Timer(period, listener);
        this.isRunning = false;
        this.date = 0;
    }

    public WorldGraphicPanel(World m) {
        this();
        this.world = m;
    }

    public void eraseAll(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, (int) (this.getSize().getWidth()), (int) (this.getSize().getHeight()));
    }

    @Override
    public void paintComponent(Graphics g) {

        this.eraseAll(g);

        int panelHeight = (int) this.getSize().getHeight();

        this.world.paint(g, panelHeight, this.x0, this.y0, this.zoom);

        this.drawAxis(g, panelHeight);
    }

    public void evolve() {
        this.world.evolveOneStep();
        this.repaint();
    }

    public void switchSuperposition() {
        this.superposed = !this.superposed;
        this.repaint();
    }

    public void startEquilibrating() {
        this.timer.start();
        if (this.timer == null) {
            // System.out.println("timer null");
        }
        this.world.startSimulation();
    }

    public void stopEquilibrating() {
        this.timer.stop();
        this.world.stopSimulation();
    }

    public void togglePlayPause() {
        if (this.isRunning) {
            this.isRunning = false;
            this.stopEquilibrating();
        } else {
            this.isRunning = true;
            this.startEquilibrating();
        }
    }

    public void rotateVehicles(double angle) {
        world.rotateVehicles(angle);
        this.repaint();
    }

    public void rotateVehicles() {
        double angle = 0.5;
        this.rotateVehicles(angle);
    }

    public void accelerateVehicles(double dV) {
        world.accelerateVehicles(dV);
    }

    public void cloneVehicles() {
        world.cloneVehicles();
    }

    public void killWeakest() {
        world.killWeakest();
    }

    public void toggleDisplayNets() {
        world.toggleDisplayNets();
        this.repaint();
    }

    // Randomly mutate the neural net of some vehicles.
    public void mutateNeuralNets() {
        world.mutateNeuralNets();
    }

    public void oneStepAstar() {
        world.oneStepAstar();
    }
}
