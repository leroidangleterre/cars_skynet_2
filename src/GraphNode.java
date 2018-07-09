import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.xml.soap.Node;

/** This element is a single piece of a drawn graph;
 * it may be linked to others,
 * and its apparent position is defined by the position of the other
 * elements it is linked to.
 */


public class GraphNode{
	
	public static int nbGraphNodes = 0;
	public int id;
	
	// Apparent position on the Graphics;
	private int x, y;
	private int radius;
	private boolean moving;
	private int xMin, xMax, yMin, yMax; // Limits of the allowed area.
	
	// The corresponding neuron
	private Neuron neuron;
	
	public Color color;
	
	// All the nodes located on the left
	private ArrayList<GraphNode>leftNodes;
	// All the nodes located on the right
	private ArrayList<GraphNode>rightNodes;
	// All the nodes from left and right lists grouped in the same list.
	private ArrayList<GraphNode>allNodes;
	
	private GraphNode(Neuron n){
		this.x = 0;
		this.y = 0;
		this.radius = 10;
		this.leftNodes = new ArrayList();
		this.rightNodes = new ArrayList();
		this.allNodes = new ArrayList();
		this.moving = false;
		this.neuron = n;
		this.color = Color.BLACK;
		this.id = GraphNode.nbGraphNodes;
		GraphNode.nbGraphNodes++;
	}
	
//	public GraphNode(Neuron n, int radius){
//		this(n);
//		this.radius = radius;
//	}
	
	public GraphNode(Neuron n, int xMin, int xMax, int yMin, int yMax){
		this(n);
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	public void setMoving(boolean param){
		this.moving = param;
	}
	
	/** No-doubles adding. */
	public void addNode(GraphNode node, boolean left){
		if(node == this){
//			System.out.println("Node adding itself...");
		}
		if(left){
			if(!this.leftNodes.contains(node)){
//				System.out.println("                         ADDING LEFT NODE.");
				this.leftNodes.add(node);
				this.allNodes.add(node);
			}
		}
		else{
			if(!this.rightNodes.contains(node)){
//				System.out.println("                         ADDING RIGHT NODE.");
				this.rightNodes.add(node);
				this.allNodes.add(node);
			}
		}
	}
	
	/** Place the node on the two axes:
	 * set abscissa to the mean value of the max abscissa in the left list and the min abscissa in the right list;
	 * set ordinate to the mean of all ordinates from both lists.
	 * @param graphNodes All the nodes, so that the current node gets pulled away from another node that may be too close.
	 */
	public void updatePos(ArrayList<GraphNode> graphNodes){
		if(this.moving){
			for(int i=0; i<5; i++){
				this.x = this.computeAbscissa();
				this.y = this.computeMeanNeighborOrdinate();
			}
			System.out.println("Node " + this.id + "; updated position: " + this.x + ", " + this.y
					+ "; links left: " + leftNodes.size() + "; links right: " + rightNodes.size());
		}
	}
	
	private int computeAbscissa(){
		// Compute the maximal abscissa of the left list.
		
		
		int xLeft = Integer.MIN_VALUE;
		int xRight= Integer.MIN_VALUE;
		
		if(this.leftNodes.size() > 0){
			for(GraphNode n: this.leftNodes){
				if(n.x > xLeft){
					xLeft = n.x;
				}
			}
		}
		if(this.rightNodes.size() > 0){
			for(GraphNode n: this.rightNodes){
				if(n.x < xRight){
					xRight = n.x;
				}
			}
		}
		System.out.println("xMin = " + xMin + "; xMax = " + xMax);
		if(xLeft == Integer.MIN_VALUE && xRight == Integer.MIN_VALUE){
			xLeft = xMin/2 + xMax/2;
			xRight= xMin/2 + xMax/2;
			this.color = Color.PINK;
		}
		if(xLeft == Integer.MIN_VALUE && xRight != Integer.MIN_VALUE){
			xLeft = xMin*9/10 + xMax/10;
			this.color = Color.YELLOW;
		}
		if(xLeft != Integer.MIN_VALUE && xRight == Integer.MIN_VALUE){
			xRight = xMin/10 + xMax*9/10;
			this.color = Color.ORANGE;
		}
		if(xLeft != Integer.MIN_VALUE && xRight != Integer.MIN_VALUE){
			this.color = Color.BLACK;
		}
		// Compute the minimal abscissa of the right list.
		// Use the mean of these two values.
		return (xLeft + xRight)/2;
	}
	
	private int computeMeanNeighborOrdinate(){
		double sum = 0;
		double count = 0;
		int res;
		for(GraphNode node: this.leftNodes){
			sum += node.y;
			count++;
		}
		for(GraphNode node: this.rightNodes){
			sum += node.y;
			count++;
		}
		if(count > 0){
			res = (int)(sum/count);
		}
		else{
			res = (this.yMin + this.yMax)/2;
		}
		return res;
	}

	public void setPos(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setColor(Color c){
		this.color = c;
	}
	
	public void paint(Graphics g, int panelHeight){

		// Paint the circle.
//		if(neuron.getValue() == 1){
//			g.setColor(Color.blue.brighter());
//		}
//		else{
//			g.setColor(Color.black);
//		}
		g.setColor(this.color);
		g.fillOval(x - radius, (int)(panelHeight - (y + radius)), 2 * radius, 2 * radius);
		
		// Write information.
		if(neuron.hasDoubleInput()){
			g.drawString("Double input !",
					x - radius / 2 - 150,
					(int)(panelHeight - (y - 7 * radius)));
		}

		g.drawString(neuron.toString(),
				x - radius / 2 - 150,
				(int)(panelHeight - (y - 5 * radius)));
		
		g.drawString(this.x + ", " + this.y + ", GN " + this.id,
				x - radius / 2 - 100,
				(int)(panelHeight - (y - 3 * radius)));
		
		// Draw lines coming from the left.
		for(GraphNode node: this.leftNodes){
			if(this.hasCoordinates() && node.hasCoordinates()){
//				System.out.println("Node coordinates: " + this.x + ", " + this.y + ", " + node.x + ", " + node.y
//						+ "; min: " + Integer.MIN_VALUE + ", max: " + Integer.MAX_VALUE);
				if(node.neuron.getValue() == 1){
					g.setColor(Color.BLUE.brighter());
				}
				else{
					g.setColor(Color.gray);
				}
				g.drawLine(this.x, panelHeight - this.y, node.x, panelHeight - node.y);
			}
		}
	}
	
	public String toString(){
		return this.x + ", "+ this.y + ", " + (this.moving ? "" : "fixed.");
	}
	
	/** Check that no parameter x or y has the value Integer.MAX_VALUE of Integer.MIN_VALUE. */
	private boolean hasCoordinates(){
		int max = 1000000;
		return this.x > -max && this.x < max && this.y > -max && this.y < max;
//		return (this.x != Integer.MAX_VALUE && this.x != Integer.MIN_VALUE && this.y != Integer.MIN_VALUE);
	}
	
}
