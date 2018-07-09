
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NeuralNet extends Observable implements Observer {

    private int centralListSize = 5;

    // Input data. The net must have one first-layer node for each element in
    // that list.
    private ArrayList<Neuron> inputList;

    // The nodes that make the inner, invisible part the network.
    // One layer for now.
    private ArrayList<Neuron> centralList;

    // The output nodes.
    private ArrayList<Neuron> outputList;

    // All neurons (or sensors) that make this net.
    private ArrayList<Neuron> allNeurons;

    public NeuralNet(List<Sensor> sensorList, List<Neuron> outputList) {

        double posX, posY;
        int columnMaxSize = 10;
        int j;

        this.inputList = new ArrayList<Neuron>();
        posX = 0;

        // Make a link between each sensor and the associated neuron.
        j = 1;
        for (Sensor s : sensorList) {
            Neuron inputNeuron = new Neuron();
            s.setNeuron(inputNeuron);
            if (j > columnMaxSize) {
                // Switch to a new column.
                j = 1;
                posX++;
            }
            posY = j;
            inputNeuron.setPos(posX, posY);
            this.inputList.add(inputNeuron);
            j++;
        }

        this.centralList = new ArrayList<Neuron>();
        posX += 3;
        j = 1;
        for (int i = 0; i < centralListSize; i++) {
            Neuron centralNeuron = new Neuron();
            posY = j;
            centralNeuron.setPos(posX, posY);
            this.centralList.add(centralNeuron);
            // Each central neuron takes all the input neurons as inputs.
            for (Neuron input : this.inputList) {
                centralNeuron.takeNewInput(input);
            }
            j++;
        }

        this.outputList = (ArrayList<Neuron>) outputList;
        // Each central neuron takes all the input neurons as inputs.
        for (Neuron central : this.centralList) {
            for (Neuron output : this.outputList) {
                output.takeNewInput(central);
            }
        }

        // Set the coordinates of the output neurons.
        posX += 3;
        j = 1;
        for (int i = 0; i < outputList.size(); i++) {
            Neuron outputNeuron = outputList.get(i);
            posY = j;
            outputNeuron.setPos(posX, posY);
            j++;
        }

        this.allNeurons = new ArrayList<Neuron>();
        this.allNeurons.addAll(inputList);
        this.allNeurons.addAll(centralList);
        this.allNeurons.addAll(outputList);
    }

    /**
     * Change the value of a randomly selected coefficient from a randomly
     * selected neuron.
     */
    public void mutate() {
        int randomValue = (int) (Math.random() * this.allNeurons.size());
        Neuron mutant = this.allNeurons.get(randomValue);

        mutant.mutate();
    }

    public void addNewCentralNeuron() {
        Neuron theNewNeuron = new Neuron();
        this.centralList.add(theNewNeuron);
        this.allNeurons.add(theNewNeuron);
        linkCentralToInput();
        linkOutputToCentral();
    }

    /**
     * Randomly choose an output and a central neuron, and make a link between
     * them.
     */
    public void linkOutputToCentral() {
        Neuron selectedOutput, selectedCentral;
        int value = (int) (Math.random() * 0.01);

        if (this.outputList.size() > 0) {
            int outputIndex = (int) (Math.random() * this.outputList.size());
            selectedOutput = this.outputList.get(outputIndex);

            if (this.centralList.size() > 0) {
                int nodeIndex = (int) (Math.random() * this.centralList.size());
                selectedCentral = this.centralList.get(nodeIndex);
                selectedOutput.takeNewInput(selectedCentral, value);
            }
        }
    }

    /**
     * Randomly choose an input and a central neuron, and make a link between
     * them.
     */
    public void linkCentralToInput() {
        Neuron selectedCentral, selectedInput;
        int value = (int) (Math.random() * 0.01);

        if (this.centralList.size() > 0) {
            int nodeIndex = (int) (Math.random() * this.centralList.size());
            selectedCentral = this.centralList.get(nodeIndex);

            if (this.inputList.size() > 0) {
                int inputIndex = (int) (Math.random() * this.inputList.size());

                selectedInput = this.inputList.get(inputIndex);
                selectedCentral.takeNewInput(selectedInput, value);
            }
        }
    }

    /**
     * Paint the neural net in a subregion of the panel. Display the input nodes
     * on the left, the output nodes on the right, all other nodes in the
     * central area. Display the links between nodes.
     *
     * @param g The graphics on which the net is being drawn
     * @param panelHeight The height of the drawing area, in pixels
     * @param x0 the abscissa on the screen of the origin of the world
     * @param y0 the ordinate on the screen of the origin of the world
     * @param zoom the current zoom
     */
    public void paint(Graphics g, int panelHeight, double x0, double y0, double zoom) {

        /* The three lists (input, center, output) are displayed on three different columns. */
        for (Neuron n : allNeurons) {
            n.paint(g, panelHeight, x0, y0, zoom);
        }
    }

    // Use all the values of the input neurons to compute the new value of all neurons.
    public void compute() {

        for (Neuron n : this.inputList) {
            n.compute();
        }
        for (Neuron n : this.centralList) {
            n.compute();
        }
        for (Neuron n : this.outputList) {
            n.compute();
        }

    }

    /**
     * Actions to be taken out when the corresponding vehicle has
     * changed/evolved.
     */
    @Override
    public void update(Observable o, Object arg) {
        this.compute();
        this.setChanged();
        this.notifyObservers();
    }
}
