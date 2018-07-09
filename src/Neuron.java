
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * One single element of a neural network. It know its inputs and requests their
 * current value when it is evaluated.
 */
public class Neuron {

    private static int nbNeurons = 0;
    protected int id;

    private double threshold;
    private double value;

    protected ArrayList<Neuron> inputs;
    private ArrayList<Double> coefficients;

    double x, y;
    double radius = 3;

    public Neuron() {
        this.inputs = new ArrayList<>();
        this.threshold = 1;
//        this.compute();

        this.coefficients = new ArrayList<>();
        this.x = 0;
        this.y = 0;

        this.id = Neuron.nbNeurons;
        Neuron.nbNeurons++;
    }

    /**
     * Artificially set the value of the neuron. To be used only for the first
     * layer of neuron, not for computation.
     *
     * @param val the value of the neuron.
     */
    public void setValue(double val) {
        this.value = val;
    }

    public double getValue() {
        return this.value;
    }

    public double getThreshold() {
        return this.threshold;
    }

    public void setThreshold(double val) {
        this.threshold = val;
    }

    /**
     * Change the value of one link.
     */
    public void mutate() {
        if (!this.coefficients.isEmpty()) {
            int index = (int) (Math.random() * (this.coefficients.size() - 1));
            double coef = this.coefficients.get(index);
            double random = Math.random() - 0.5;
            coef += random;

            if (coef < 0) {
                coef = 0;
            }

            this.coefficients.set(index, coef);
        }
    }

    public void compute() {
        double sum = 0;

        if (inputs != null && !inputs.isEmpty()) {
            for (int index = 0; index < inputs.size(); index++) {
                Neuron input = inputs.get(index);
                double coefficient = coefficients.get(index);
                sum += coefficient * input.getValue();
            }

            // TODO calculation to modify.
            if (sum >= this.threshold) {
                this.value = sum;
            } else {
                this.value = sum;
            }
        } else {
            // Special case for neurons that are directly fed by sensors:
        }
    }

    public boolean isActive() {
        return this.value >= this.threshold;
    }

    @Override
    public String toString() {
        return "Neuron " + this.id + ": score: " + this.getValue() + ", inputs: ";// + this.outputs.size() + ".";
    }

    /**
     * Add a new input with zero impact on the current neuron. The impact may be
     * changed later on.
     *
     * @param input The neuron added to the inputs of the current neuron
     */
    public void takeNewInput(Neuron input) {
        this.takeNewInput(input, 0.0);
    }

    /**
     * Add a new input with a specified value, wich is the amount of impact the
     * input will have on the current neuron.
     *
     * @param input The neuron added to the inputs of the current neuron
     * @param value The value associated with the new input
     */
    public void takeNewInput(Neuron input, double value) {
        if (input != this && !this.inputs.contains(input)) {
            this.inputs.add(input);
            this.coefficients.add(value);
//            System.out.println("Neuron " + this.id + " taking neuron " + input.id + " as a new input.");
        }
        // else{
        // System.out.println("Error: cannot assign neuron as own input");
        // }
    }

    // public void changeRandomCoefficient(){
    // if(coefficients.size() > 0){
    // int index = (int)(Math.random() * coefficients.size());
    // int val = (int)(Math.random() * 10);
    // this.coefficients.set(index, new Integer(val));
    // }
    // }
    // /** Get a graph node for this neuron (it will be created if it does not exist yet).
    // * Make sure it is positionned properly: if it has no links, it must still be in the specified box (xMin,...)
    // */
    // public GraphNode getGraphNode(int xMin, int xMax, int yMin, int yMax){
    // if(this.graphNode == null){
    // this.graphNode = new GraphNode(this, xMin, xMax, yMin, yMax);
    // }
    // return this.graphNode;
    // }
    // private GraphNode getGraphNode(){
    // return this.getGraphNode(0, 0, 0, 0);
    // }
    // public void setGraphNode(GraphNode node){
    // this.graphNode = node;
    // }
    public boolean hasDoubleInput() {
        boolean res = false;
        for (int i = 0; i < this.inputs.size(); i++) {
            for (int j = 0; j < this.inputs.size(); j++) {
                if (i != j) {
                    if (this.inputs.get(i).id == this.inputs.get(j).id) {
                        /*
						 * The current neuron has two different inputs
						 * that come from the same neuron. Not correct !
                         */
                        res = true;
                    }
                }
            }
        }
        return res;
    }

    public void setPos(double xParam, double yParam) {
        this.x = xParam;
        this.y = yParam;
    }

    public void paint(Graphics g, int panelHeight, double x0, double y0, double zoom) {

        double size = 2 * this.radius;

        if (this.isActive()) {
            g.setColor(Color.red);
            size = 2.5 * size;
        } else {
            g.setColor(Color.black);
        }

        int xApp = (int) (x0 + this.x * zoom);
        int yApp = (int) (panelHeight - (y0 + this.y * zoom));
        g.fillOval(xApp, yApp, (int) size, (int) size);
        this.paintLinksToInputs(g, panelHeight, x0, y0, zoom);
    }

    public void paintLinksToInputs(Graphics g, int panelHeight, double x0, double y0, double zoom) {
        if (this.inputs != null && !this.inputs.isEmpty()) {

            int xApp = (int) (x0 + this.x * zoom);
            int yApp = (int) (panelHeight - (y0 + this.y * zoom));

            for (Neuron n : inputs) {
                // Paint the link between the input and the current neuron.
                int xAppOther = (int) (x0 + n.x * zoom);
                int yAppOther = (int) (panelHeight - (y0 + n.y * zoom));
                if (n.isActive()) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.black);
                }
                g.drawLine(xApp, yApp, xAppOther, yAppOther);
            }
        }
    }

    /**
     * Util method.
     *
     * @return the amount of inputs that feed the current neuron
     */
    public int getNbInputs() {
        return this.inputs.size();
    }
}
