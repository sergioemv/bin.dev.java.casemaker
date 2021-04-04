package bi.view.testdataviews;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Keymap;

import org.apache.log4j.Logger;

import bi.view.lang.CMMessages;
import bi.view.utils.JCustomDialog;



public class CMDialogNewColumn extends JCustomDialog {
    public CMDialogNewColumn(Frame frame) {
        super(frame);
        try {
            initGUI();
            pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void initGUI() throws Exception {
        this.setModal(true);
        this.setResizable(false);
        
        getContentPane().setLayout(new java.awt.BorderLayout());
        this.setTitle(CMMessages.getString("LABEL_TESTDATA_TITLE_NEW_COLUMN")); 
        setSize(new java.awt.Dimension(320, 90));      

        jPanelLabelFields.setLayout(new GridLayout(1, 1));

        jLabelName.setText("   " + CMMessages.getString("LABEL_TESTDATA_NAME_NEWCOLUMN"));
        jLabelName.setPreferredSize(new java.awt.Dimension(95, 20));
        jLabelName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelName.setMinimumSize(new java.awt.Dimension(50, 20));
        jLabelName.setSize(new java.awt.Dimension(150, 0));
        jLabelName.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabelName.setAlignmentX(0.5f);
        jLabelName.setAlignmentY(0.5f);
        jPanelLabelName.setAlignmentY(0.5f);
        jPanelLabelName.setLayout(new javax.swing.BoxLayout(jPanelLabelName, javax.swing.BoxLayout.X_AXIS));
        jPanelLabelName.setPreferredSize(new java.awt.Dimension(95, 30));
        jPanelLabelName.add(jLabelName);
    

		jPanelLabelFields.setMaximumSize(new java.awt.Dimension(200, 32767));
        jPanelLabelFields.setPreferredSize(new java.awt.Dimension(110, 100));
        jPanelLabelFields.setSize(new java.awt.Dimension(50, 256));
        jPanelLabelFields.setMinimumSize(new java.awt.Dimension(50, 30));
        jPanelLabelFields.add(jPanelLabelName);     
        
		jPanelTextFields.setLayout(new GridLayout(1, 1));

        jTextFieldName.setPreferredSize(new java.awt.Dimension(180, 20));
        jTextFieldName.setMaximumSize(new java.awt.Dimension(180, 20));
        jTextFieldName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldName.setAlignmentX(0.5f);
        
        //
        
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        Keymap map = jTextFieldName.getKeymap();
        map.removeKeyStrokeBinding(enter);
        
        //
        jPanelTextName.setLayout(new javax.swing.BoxLayout(jPanelTextName, javax.swing.BoxLayout.X_AXIS));
        jPanelTextName.setPreferredSize(new java.awt.Dimension(180, 30));
        jPanelTextName.add(jTextFieldName);    

        jPanelTextFields.setPreferredSize(new java.awt.Dimension(250, 90));
        jPanelTextFields.add(jPanelTextName);     
		
        jPanelCenter.setLayout(new BoxLayout(jPanelCenter, BoxLayout.X_AXIS));
        jPanelCenter.setPreferredSize(new java.awt.Dimension(320, 90));
        jPanelCenter.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelCenter.add(jPanelLabelFields);
        jPanelCenter.add(jPanelTextFields);
       
        getContentPane().add(jPanelCenter, BorderLayout.CENTER);
        
        jPanelOKCancelButtons.setLayout(new FlowLayout());
        jButtonOK.setText(CMMessages.getString("BUTTON_OK"));
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
        jPanelOKCancelButtons.add(jButtonOK);
        jPanelOKCancelButtons.add(jButtonCancel);
        getContentPane().add(jPanelOKCancelButtons, BorderLayout.SOUTH);
        jButtonOK.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonOK_actionPerformed(e);
                }
            });
        jButtonCancel.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButtonCancel_actionPerformed(e);
                }
            });
        
       // final KeyEvent key = new KeyEvent(this,0,0,0,0);
        jTextFieldName.addKeyListener(        		
        		new java.awt.event.KeyListener()
        		{                   

					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						if (e.getKeyCode()==KeyEvent.VK_ENTER)
							jButtonOK_actionPerformed(null);
						
						if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
							jButtonCancel_actionPerformed(null);
							
						
					}

					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}
                });
    
        jButtonCancelClicked = false;
        jButtonOKClicked = false;
        OnEnterPressed = false;
        OnESCPressed = false;
//hcanedo_07102005_begin
        getRootPane().setDefaultButton(jButtonOK);
//hcanedo_07102005_end        
    }

    
   public void jTextField_KeyPressed_actionPerformed(KeyEvent ke) {
    if (ke.getID() == KeyEvent.KEY_PRESSED && ke.getKeyCode() == KeyEvent.VK_ENTER) {
    	Logger.getLogger(this.getClass()).info("BTF: " + ke);
    }
  }
    
    /** Overridden so we can exit when window is closed */
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            jButtonCancelClicked = true;
            cancel();
        }
        super.processWindowEvent(e);
    }

    /** Close the dialog */
    void cancel() {
        dispose();
    }

    public boolean jButtonCancelClicked() {
        return jButtonCancelClicked;
    }

    public boolean jButtonOKClicked() {
        return jButtonOKClicked;
    }

    void jButtonOK_actionPerformed(ActionEvent e) {
    	    
             this.m_NewColumn =jTextFieldName.getText();
             jButtonOKClicked = true;       
             dispose();
         
    }
    
    public String getNewColumnName(){
    	return m_NewColumn;
    }

    void jButtonCancel_actionPerformed(ActionEvent e) {
        jButtonCancelClicked = true;
        cancel();
    }

    

    

    JPanel jPanelOKCancelButtons = new JPanel();
    JPanel jPanelLabelFields = new JPanel();
    JPanel jPanelTextFields = new JPanel();
    
    JPanel jPanelCenter = new JPanel();
    JPanel jPanelLabelName = new JPanel();
    JPanel jPanelLabelDescription = new JPanel();
    JPanel jPanelTextName = new JPanel();
  
    JButton jButtonOK = new JButton();
    JButton jButtonCancel = new JButton();
    boolean jButtonCancelClicked = false;
    boolean jButtonOKClicked = false;
    JLabel jLabelName = new JLabel();
  
     
    JTextField jTextFieldName = new JTextField();
  
    private String  m_NewColumn;
    private boolean OnEnterPressed = false;
    private boolean OnESCPressed = false;
//hcanedo_07102005_Begin    
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(jTextFieldName);
		focusOrder.add(jButtonOK);
		focusOrder.add(jButtonCancel);
		return focusOrder;
	}

	protected void PressJButtonCancel(Object object) {
		jButtonCancel_actionPerformed(null);
		
	}

	protected void PressJButtonOk(Object object) {
		jButtonOK_actionPerformed(null);
		
	}
//hcanedo_07102005_end    
   

}

