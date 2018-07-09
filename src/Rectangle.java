
/* This class describes a rectangular object that can be drawn.
 */

import java.awt.Graphics;
import java.awt.Color;

public class Rectangle{

	private double xCenter, yCenter;
	private double width, height;
	private double angle;
	private double[] xList = {0, 0, 0, 0};
	private double[] yList = {0, 0, 0, 0};

	public Rectangle(double x, double y, double l, double h){
		this.xCenter = x;
		this.yCenter = y;
		this.width = l;
		this.height = h;
	}
	public Rectangle(double x, double y, double l, double h, double angle){
		this(x, y, l, h);
		this.angle = angle;
	}
	
	// Compute the real coordinates of the four corners.
	private void computeRealCorners(){
		// Real coordinates of the corners.
		xList[0] = xCenter + Math.cos(angle)*width/2 - Math.sin(angle)*height/2;
		xList[1] = xCenter - Math.cos(angle)*width/2 - Math.sin(angle)*height/2;
		xList[2] = xCenter - Math.cos(angle)*width/2 + Math.sin(angle)*height/2;
		xList[3] = xCenter + Math.cos(angle)*width/2 + Math.sin(angle)*height/2;
		
		yList[0] = yCenter + Math.cos(angle)*height/2 + Math.sin(angle)*width/2;
		yList[1] = yCenter + Math.cos(angle)*height/2 - Math.sin(angle)*width/2;
		yList[2] = yCenter - Math.cos(angle)*height/2 - Math.sin(angle)*width/2;
		yList[3] = yCenter - Math.cos(angle)*height/2 + Math.sin(angle)*width/2;
	}
	
	
	public void display(Graphics g, double x0, double y0, double zoom, int panelHeight){

//		int xApp = (int)(this.xCenter * zoom + x0);
//		int yApp = (int)(panelHeight - (this.yCenter * zoom + y0));
//		int wApp = (int)(this.width * zoom);
//		int hApp = (int)(this.height * zoom);

		g.setColor(Color.gray);
		// g.fillRect(xApp - largeurApp / 2, yApp - hauteurApp / 2, largeurApp, hauteurApp);

		this.computeRealCorners();
		
		int[] xConv = {0, 0, 0, 0};
		int[] yConv = {0, 0, 0, 0};
//		System.out.println("Paint rectangle:");
		for(int i=0; i<4; i++){
			xConv[i] = (int)(x0 + xList[i] * zoom);
			yConv[i] = (int)(panelHeight - (y0 + yList[i] * zoom));
//			System.out.println("     x = " + xConv[i] + ", y = " + yConv[i]);
		}
		g.setColor(Color.blue);
		g.fillPolygon(xConv, yConv, 4);
		g.setColor(Color.black);
		g.drawPolygon(xConv, yConv, 4);
	}

	public boolean containsPoint(double x, double y){
		return (x > this.xCenter - this.width / 2 && x < this.xCenter + this.width / 2 && y > this.yCenter - this.height / 2
				&& y < this.yCenter + this.height / 2);
	}
}