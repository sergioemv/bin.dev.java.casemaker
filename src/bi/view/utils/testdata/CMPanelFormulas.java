/**
 * 09/10/2006
 * svonborries
 */
package bi.view.utils.testdata;

import java.awt.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import bi.view.lang.CMMessages;


/**
 * @author svonborries
 *
 */
public class CMPanelFormulas extends JComponent {

	/**
	 * 09/10/2006
	 * svonborries
	 */
	private static final long serialVersionUID = 1L;
    private JPanel jPanelFunction = new JPanel();
    private JPanel jPanelFormulas = new JPanel();
    private JScrollPane jScrollPaneFunction = new JScrollPane();
    private JScrollPane jScrollPaneFormulas = new JScrollPane();
    private JList jListFunction = new JList();
    private JList jListFormulas;
    private JPanel container = new JPanel();
    private JLabel jLabelDescription = new JLabel();
    private JTextArea jTextAreaDescription = new JTextArea();
    
    /**
    * @deprecated
    * @author svonborries
    *
    */
	public CMPanelFormulas(){
		add(container);
		container.setLayout(new java.awt.BorderLayout());
		container.setBounds(new Rectangle(0,0,432,432));
        container.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                CMMessages.getString("TESTDATA_CATEGORY_FUNCTION"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, //$NON-NLS-1$
                new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60)));
		
        container.add(jPanelFunction);
		container.add(jPanelFormulas);
        container.add(jLabelDescription);
        container.add(jTextAreaDescription);
		
        jPanelFunction.setBounds(new java.awt.Rectangle(0, 0, 191, 181));
        jPanelFunction.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
            CMMessages.getString("TESTDATA_CATEGORY_FUNCTION"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, //$NON-NLS-1$
            new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60)));
        jPanelFunction.setLayout(new java.awt.BorderLayout());
        jPanelFunction.add(jScrollPaneFunction, java.awt.BorderLayout.CENTER);
        
        jPanelFormulas.setBounds(new java.awt.Rectangle(248, 0, 198, 181));
        jPanelFormulas.setLayout(new java.awt.BorderLayout());
        jPanelFormulas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
            CMMessages.getString("TESTDATA_NAME_FORMULA"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP,
            new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60)));
        jPanelFormulas.add(jScrollPaneFormulas, java.awt.BorderLayout.CENTER);
        
        jScrollPaneFunction.getViewport().add(jListFunction);
        jListFunction.setBounds(new java.awt.Rectangle(74, 50, 32, 32));
        jListFunction.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        jScrollPaneFormulas.getViewport().add(getJListFormulas());
        getJListFormulas().setBounds(new java.awt.Rectangle(62, 55, 32, 32));
        getJListFormulas().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
        jLabelDescription.setBounds(new java.awt.Rectangle(28, 195, 414, 24));
        jLabelDescription.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        jLabelDescription.setToolTipText(CMMessages.getString("TESTDATA_DESCRPTION_FORMULA"));
        
        
        jTextAreaDescription.setBounds(new java.awt.Rectangle(28, 224, 418, 83));
        jTextAreaDescription.setCaretColor(new java.awt.Color(212, 208, 200));
        jTextAreaDescription.setBackground(this.getBackground());//new java.awt.Color(212, 208, 200));
        jTextAreaDescription.setForeground(this.getForeground());
        jTextAreaDescription.setDisabledTextColor(new java.awt.Color(212, 208, 200));
        jTextAreaDescription.setLineWrap(true);
        jTextAreaDescription.setWrapStyleWord(true);
        jTextAreaDescription.setEditable(false);
        jTextAreaDescription.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
	}
	
	public JList getJListFormulas() {
		if(jListFormulas == null){
			jListFormulas = new JList();
			jListFormulas.setModel(new DefaultListModel());
		}
		return jListFormulas;
	}

	public JList getJListFunction() {
		return jListFunction;
	}

	public JLabel getJLabelDescription() {
		return jLabelDescription;
	}

	public void setJLabelDescription(JLabel labelDescription) {
		jLabelDescription = labelDescription;
	}

	public JTextArea getJTextAreaDescription() {
		return jTextAreaDescription;
	}

	public void setJTextAreaDescription(JTextArea textAreaDescription) {
		jTextAreaDescription = textAreaDescription;
	}

}
