
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * This class describes a component that regroups buttons.
 */
public class Toolbar extends JPanel {

    private static final long serialVersionUID = 3431709111395343204L;

    private EvolvedButton buttonStart;
    private final JButton buttonEvolve;
    private final JButton buttonRotate;
    private final JButton buttonMutateNetwork;
    private final JButton buttonClone;
    private final JButton buttonKill;
    private final JButton buttonMutateNodes;
    private final JButton buttonOneStepAstar;

    private WorldGraphicPanel panel;

    public Toolbar(WorldGraphicPanel pan) {

        this.panel = pan;

        this.buttonStart = new EvolvedButton("Play");
        this.buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (buttonStart.getText().compareTo("Play") == 0) {
                    panel.startEquilibrating();
                    buttonStart.setText("Pause");
                } else {
                    panel.stopEquilibrating();
                    buttonStart.setText("Play");
                }
            }
        });
        this.add(this.buttonStart);

        this.buttonEvolve = new EvolvedButton("evolve");
        this.buttonEvolve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.evolve();
            }
        });
        this.add(this.buttonEvolve);

        this.buttonRotate = new EvolvedButton("rotate");
        this.buttonRotate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.rotateVehicles();
            }
        });
        this.add(this.buttonRotate);

        this.buttonMutateNetwork = new EvolvedButton("mutate networks");
        this.buttonMutateNetwork.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.mutateNeuralNets();
            }
        });
        this.add(this.buttonMutateNetwork);

        this.buttonClone = new EvolvedButton("clone");
        this.buttonClone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.cloneVehicles();
            }
        });
        this.add(this.buttonClone);

        this.buttonKill = new EvolvedButton("kill");
        this.buttonKill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.killWeakest();
            }
        });
        this.add(this.buttonKill);

        this.buttonMutateNodes = new EvolvedButton("mutate network");
        this.buttonMutateNodes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.mutateNeuralNets();
                panel.repaint();
            }
        });
        this.add(this.buttonMutateNodes);

        this.buttonOneStepAstar = new EvolvedButton("One step A*");
        this.buttonOneStepAstar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.oneStepAstar();
                panel.repaint();
            }
        });
        this.add(this.buttonOneStepAstar);

    }

    public void setKeyListener(KeyboardListener k) {
        this.addKeyListener(k);
        this.buttonStart.addKeyListener(k);
        this.buttonEvolve.addKeyListener(k);
        this.buttonClone.addKeyListener(k);
        this.buttonKill.addKeyListener(k);
        this.buttonMutateNodes.addKeyListener(k);
        this.buttonMutateNetwork.addKeyListener(k);
        this.buttonRotate.addKeyListener(k);
        this.buttonOneStepAstar.addKeyListener(k);
    }
}
