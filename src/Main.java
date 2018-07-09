
import java.awt.Dimension;

public class Main {

    public static void main(String args[]) {

        int nbVehicles = 10;
        double width = 50.0;
        double height = 20.0;
        World world = new World(nbVehicles, width, height);
        GraphicPanel carsPanel = new WorldGraphicPanel(world);
        Window carsWindow = new Window(carsPanel);
        carsPanel.repaint();
        carsWindow.pack();
        carsWindow.setSize(new Dimension(800, 800));

        Vehicle v = world.getFirstVehicle();

        NeuralNetGraphicPanel neuralPanel = new NeuralNetGraphicPanel(v.getNeuralNet());
        Window neuralWindow = new Window(neuralPanel);
        v.getNeuralNet().addObserver(neuralPanel);
        neuralPanel.repaint();
        neuralWindow.pack();
        neuralWindow.setSize(800, 800);

    }
}
