package bi.view.preferences;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.BannerPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class CMPreferencesDefaultPage extends AbstractDialogPage {

	/**
	 * This method initializes 
	 * 
	 */
	public CMPreferencesDefaultPage() {
		super();
		initialize();
	}

	

	public CMPreferencesDefaultPage(String string, ImageIcon imageIcon) {
		super(string,imageIcon);

	}



	@Override
	public void lazyInitialize() {
		// TODO Auto-generated method stub 
		
		initComponents();
	}



	protected void initComponents() {
		// TODO Auto-generated method stub
	            BannerPanel headerPanel = new BannerPanel(getTitle(), null);
	            headerPanel.setForeground(Color.WHITE);
	          //  headerPanel.setBackground(new Color(10, 36, 106));
	            headerPanel.setGradientPaint( new Color(0, 0, 128),Color.WHITE, false);
	            headerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.darkGray, Color.darkGray, Color.gray));
	            setLayout(new BorderLayout());
	            add(headerPanel, BorderLayout.BEFORE_FIRST_LINE);
	            add(new JPanel(){@Override
	            public Dimension getPreferredSize() {
	            	
	            	return new Dimension(20,20);
	            }},BorderLayout.NORTH);
	  	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
