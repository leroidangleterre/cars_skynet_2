import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/** This button will always be large enough for all the texts
 * previously or currently displayed.
 * If such a button switches between two or more strings,
 * it will stabilize to the size needed for the longest string.
 */


public class EvolvedButton extends JButton{
	
	private static final long serialVersionUID = -3160310885267302672L;

	public EvolvedButton(String text){
		super(text);
		this.addActionListener(new ResizeListener());
	}
	
	private class ResizeListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0){
			
//			if(getPreferredSize().getWidth() > getSize().getWidth()){
				setPreferredSize(getPreferredSize());
//			}
		}
	}
}
