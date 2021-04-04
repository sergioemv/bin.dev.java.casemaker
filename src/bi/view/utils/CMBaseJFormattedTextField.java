//hmendez_22112005_begin
package bi.view.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;

import org.apache.log4j.Logger;

public class CMBaseJFormattedTextField extends JFormattedTextField {
	private CMBaseJDialog m_parentContainer;
	public CMBaseJFormattedTextField(SimpleDateFormat p_simpleFormat,CMBaseJDialog p_parentContainer)
	{
	   super(p_simpleFormat);	
	   m_parentContainer = p_parentContainer;
	   this.addKeyListener(defaultKeyListener);
	   
	}
	private KeyListener defaultKeyListener = new KeyAdapter()
	{                   
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER)
			{
				if (e.getSource() instanceof JFormattedTextField)
				{ 
					if (getText().length()>0)
					{
					  try 
					  {
  					    commitEdit();
					  }
					  catch (ParseException pe)
					  {
						  Logger.getLogger(this.getClass()).error("Parse Error, restoring old value");
					  }
					}
					m_parentContainer.requestFocus();
					m_parentContainer.getDefaultButton().doClick();
					requestFocus();						
				}
			}
		}
  };
}
//hmendez_22112005_end
