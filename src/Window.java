
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

public class Window extends JFrame{

	private static final long serialVersionUID = -6697999408390194121L;

	private GraphicPanel graphicPanel;
	private Toolbar toolbar;
	private int xMouse, yMouse;
	private boolean centerClickActive;
	private boolean rightClickActive;

	private KeyboardListener keyboardListener;

	public Window(){
		super();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.addMouseListener(new MyMouseListener());
		this.addMouseMotionListener(new MyMouseMotionListener());
		this.addMouseWheelListener(new MyMouseWheelListener());

		this.xMouse = 0;
		this.yMouse = 0;
		this.centerClickActive = false;
		this.rightClickActive = false;

		this.setLayout(new BorderLayout());
	}

	public Window(GraphicPanel pan){
		this();
		this.graphicPanel = pan;
		this.getContentPane().add(this.graphicPanel, BorderLayout.CENTER);

		this.graphicPanel.addMouseListener(new MyMouseListener());
		this.graphicPanel.addMouseMotionListener(new MyMouseMotionListener());
		this.graphicPanel.addMouseWheelListener(new MyMouseWheelListener());

		// Only world panels have a toolbar.
		if (pan instanceof WorldGraphicPanel){
			WorldGraphicPanel wgp = (WorldGraphicPanel)pan;
			this.toolbar = new Toolbar(wgp);
			this.getContentPane().add(this.toolbar, BorderLayout.SOUTH);

			this.keyboardListener = new KeyboardListener(wgp);
			this.addKeyListener(this.keyboardListener);
			this.graphicPanel.addKeyListener(this.keyboardListener);
			this.toolbar.setKeyListener(this.keyboardListener);
		}
	}

	public void setPanel(NeuralNetGraphicPanel panel){

		this.graphicPanel = panel;
		this.getContentPane().add(this.graphicPanel, BorderLayout.CENTER);

		this.graphicPanel.addMouseListener(new MyMouseListener());
		this.graphicPanel.addMouseMotionListener(new MyMouseMotionListener());
		this.graphicPanel.addMouseWheelListener(new MyMouseWheelListener());
	}

	/**
	 * Change the zoom factor and keep unchanged only the point hovered by the mouse.
	 */
	public void zoomOnMouse(double fact){
		this.graphicPanel.zoomOnMouse(fact, xMouse, yMouse);
	}

	private class MyMouseMotionListener implements MouseMotionListener{

		public MyMouseMotionListener(){
			super();
		}

		public void mouseMoved(MouseEvent e){
			xMouse = e.getX();
			yMouse = e.getY();
		}

		public void mouseDragged(MouseEvent e){

			// TODO: the left and right clicks may have other properties.
			if (centerClickActive || rightClickActive){
				/*
				 * The Y coordinate axis is oriented downwards in the window,
				 * upwards in the GraphicPanel.
				 */
				graphicPanel.translate(e.getX() - xMouse, -(e.getY() - yMouse));
			}
			xMouse = e.getX();
			yMouse = e.getY();
		}
	}

	private class MyMouseWheelListener implements MouseWheelListener{

		public MyMouseWheelListener(){
			super();
		}

		public void mouseWheelMoved(MouseWheelEvent e){
			// System.out.print("wheel turn ");
			double fact = 1.05;
			if (e.getPreciseWheelRotation() < 0){
				// System.out.println("down");
				zoomOnMouse(fact);
			}else{
				// System.out.println("up");
				zoomOnMouse(1 / fact);
			}
		}
	}

	private class MyMouseListener implements MouseListener{

		public MyMouseListener(){
			super();
		}

		public void mouseClicked(MouseEvent e){
		}

		public void mouseEntered(MouseEvent e){
			// System.out.println("Mouse entered");
		}

		public void mouseExited(MouseEvent e){
			// System.out.println("Mouse exited");
		}

		public void mousePressed(MouseEvent e){
			// if(e.getButton() == MouseEvent.BUTTON1){
			// leftClickActive = true;
			// }
			if (e.getButton() == MouseEvent.BUTTON2){
				centerClickActive = true;
			}
			if (e.getButton() == MouseEvent.BUTTON3){
				rightClickActive = true;
			}
		}

		public void mouseReleased(MouseEvent e){
			// if(e.getButton() == MouseEvent.BUTTON1){
			// leftClickActive = false;
			// }
			if (e.getButton() == MouseEvent.BUTTON2){
				centerClickActive = false;
			}
			if (e.getButton() == MouseEvent.BUTTON3){
				rightClickActive = false;
			}
		}

	}

}
