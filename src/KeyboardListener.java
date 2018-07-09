
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener{

	private WorldGraphicPanel panel;

	private double turnAngle = 0.3;
	private double speedIncrement = 0.1;

	public KeyboardListener(WorldGraphicPanel p){
		super();
		this.panel = p;
		// System.out.println("New KeyboardListener");
	}

	public void keyPressed(KeyEvent e){

		switch (e.getKeyCode()){

		case KeyEvent.VK_LEFT:
			panel.rotateVehicles(turnAngle);
			break;
		case KeyEvent.VK_RIGHT:
			panel.rotateVehicles(-turnAngle);
			break;
		case KeyEvent.VK_UP:
			panel.accelerateVehicles(speedIncrement);
			break;
		case KeyEvent.VK_DOWN:
			panel.accelerateVehicles(-speedIncrement);
			break;
		case KeyEvent.VK_0:
			panel.resetView();
			break;
		case KeyEvent.VK_4:
			panel.swipe(-1, 0);
			break;
		case KeyEvent.VK_6:
			panel.swipe(+1, 0);
			break;
		case KeyEvent.VK_8:
			panel.swipe(0, +1);
			break;
		case KeyEvent.VK_2:
			panel.swipe(0, -1);
			break;
		case KeyEvent.VK_PLUS:
			panel.zoomIn();
			break;
		case KeyEvent.VK_MINUS:
			panel.zoomOut();
			break;
		case KeyEvent.VK_SPACE:
			panel.evolve();
			break;
		case KeyEvent.VK_P:
			panel.togglePlayPause();
			break;
		default:
			break;
		}

		/*
		 * // switch (e.getKeyChar()){
		 * // case '0':
		 * // panel.resetView();
		 * // break;
		 * // case '4':
		 * // panel.swipe(-1, 0);
		 * // break;
		 * // case '6':
		 * // panel.swipe(+1, 0);
		 * // break;
		 * // case '8':
		 * // panel.swipe(0, +1);
		 * // break;
		 * // case '2':
		 * // panel.swipe(0, -1);
		 * // break;
		 * // case '+':
		 * // panel.zoomIn();
		 * // break;
		 * // case '-':
		 * // panel.zoomOut();
		 * // break;
		 * // case ' ':
		 * // panel.evolve();
		 * // break;
		 * // case 'p':
		 * // panel.togglePlayPause();
		 * // break;
		 * //
		 * // default:
		 * // break;
		 * // }
		 */
	}

	public void keyReleased(KeyEvent e){
	}

	public void keyTyped(KeyEvent e){
	}

}
