
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class GraphicPanel extends JPanel{

	private static final long serialVersionUID = 775535355282792517L;

	/*
	 * The origin of the represented environment will be visible
	 * at the x0-th pixel column and at the y0-th pixel line,
	 * starting from the lower-left corner.
	 * The zoom value is the amount of pixels between that origin
	 * and the point of coordinates (1, 0).
	 */
	protected double x0, y0, zoom;

	private Timer timer;
	private boolean isRunning;

	private int date;

	/*
	 * Either the machines are superposed (each one is displayed in the same referential),
	 * or they all have their own referential.
	 */
	private boolean superposed = true;

	public GraphicPanel(Window w){
		this();
	}

	public GraphicPanel(){
		super();
		this.x0 = 149;
		this.y0 = 127;
		this.zoom = 46;
		int period = 50;
		this.isRunning = false;
		this.date = 0;
	}

	public void eraseAll(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int)(this.getSize().getWidth()), (int)(this.getSize().getHeight()));
	}

	@Override
	public void paintComponent(Graphics g){

	}

	public void drawAxis(Graphics g, double panelHeight){

		double axisLength = 1; // Length in physical world.

		g.setColor(Color.BLACK);
		g.drawLine((int)(this.x0), (int)(panelHeight - this.y0), (int)(this.x0 + axisLength * this.zoom), (int)(panelHeight - this.y0));
		g.drawLine((int)(this.x0), (int)(panelHeight - this.y0), (int)(this.x0), (int)(panelHeight - (this.y0 + axisLength * this.zoom)));
	}

	public double getX0(){
		return this.x0;
	}

	public void setX0(double newX0){
		this.x0 = newX0;
		repaint();
	}

	public double getY0(){
		return this.x0;
	}

	public void setY0(double newY0){
		this.y0 = newY0;
		repaint();
	}

	public void translate(double dx, double dy){
		this.x0 += dx;
		this.y0 += dy;
		repaint();
	}

	public double getZoom(){
		return this.zoom;
	}

	public void setZoom(double newZoom){
		this.zoom = newZoom;
		repaint();
	}

	public void multiplyZoom(double fact){
		this.zoom *= fact;
		repaint();
	}

	public void zoomOnMouse(double fact, int xMouse, int yMouse){

		double panelHeight = this.getSize().getHeight();

		x0 = fact * (x0 - xMouse) + xMouse;
		y0 = (panelHeight - yMouse) + fact * (y0 - (panelHeight - yMouse));

		this.zoom *= fact;
		repaint();
	}

	public void resetView(){

		this.x0 = 0;
		this.y0 = 0;
		this.zoom = 1;

		repaint();
	}

	public void swipe(int dx, int dy){
		this.x0 += dx;
		this.y0 += dy;
		repaint();
	}

	public void zoomIn(){
		this.zoom *= 1.1;
		repaint();
	}

	public void zoomOut(){
		this.zoom /= 1.1;
		repaint();
	}

	// public void startEquilibrating(){
	// this.timer.start();
	// }
	//
	// public void stopEquilibrating(){
	// this.timer.stop();
	// }

	// public void togglePlayPause(){
	// if (this.isRunning){
	// this.isRunning = false;
	// this.stopEquilibrating();
	// }else{
	// this.isRunning = true;
	// this.startEquilibrating();
	// }
	// }

}
