package bi.view.actions.dependency;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.utils.CMJEditorPaneFocusChangeable;
import bi.view.utils.CMTwinBox;

public class CMPanelDependency extends JPanel {

	private static ImageIcon elementIcon = CMIcon.ELEMENT.getImageIcon();
	private JPanel jPanelDependency = null;
	private EtchedBorder border31 = null;
	private JPanel jPanelGeneratedDescription = null;
	private JScrollPane jScrollPaneGeneratedDescription = null;
	private CMJEditorPaneFocusChangeable jTextAreaGeneratedDescription = null;
	private JPanel jPanelDescription = null;
	private JScrollPane jScrollPaneDescription = null;
	private CMJEditorPaneFocusChangeable jTextAreaDescription = null;
	private CMTwinBox shuttleElements = null;
	private DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer()

	{

		/* (non-Javadoc)
		 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
		 */
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		       setComponentOrientation(list.getComponentOrientation());

		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			setIcon(elementIcon );


		   	return this;
		       }


	};

	public CMPanelDependency() {
		super();
		initialize();
	}


	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setMinimumSize(new Dimension(670, 296));
		this.setPreferredSize(new Dimension(670, 526));
        this.setSize(new Dimension(670, 526));
        this.add(getJPanelDependency(), null);
	}
	public Collection<? extends Component> getTabOrder() {
		ArrayList<Component> list = new ArrayList<Component>();
			list.add(getJTextAreaDescription());
			list.addAll(getShuttleElements().getTabOrder());
			return list;
	}





	/**
	 * This method initializes jPanelDependency
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDependency() {
		if (jPanelDependency == null) {
			TitledBorder titledBorder4 = new TitledBorder(getBorder31(), CMMessages.getString("LABEL_DEPENDENCY"));
			jPanelDependency = new JPanel();
			jPanelDependency.setLayout(new BoxLayout(getJPanelDependency(), BoxLayout.Y_AXIS));
			jPanelDependency.setBorder(titledBorder4);
			jPanelDependency.add(getJPanelGeneratedDescription(), null);
			jPanelDependency.add(getJPanelDescription(), null);
			jPanelDependency.add(getShuttleElements(), null);
		}
		return jPanelDependency;
	}


	/**
	 * This method initializes border31
	 *
	 * @return javax.swing.border.EtchedBorder
	 */
	private EtchedBorder getBorder31() {
		if (border31 == null) {
			border31 = (EtchedBorder) BorderFactory.createEtchedBorder(Color.white,
					new Color(165, 163, 151));
		}
		return border31;
	}


	/**
	 * This method initializes jPanelGeneratedDescription
	 *
	 * @return javax.swing.JPanel
	 */
	public JPanel getJPanelGeneratedDescription() {
		if (jPanelGeneratedDescription == null) {
			jPanelGeneratedDescription = new JPanel();
			jPanelGeneratedDescription.setLayout(new BorderLayout());
			jPanelGeneratedDescription.setPreferredSize(new Dimension(0, 10));
			jPanelGeneratedDescription.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_DESCRIPTION1"), TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
			jPanelGeneratedDescription.add(getJScrollPaneGeneratedDescription(), java.awt.BorderLayout.CENTER);
		}
		return jPanelGeneratedDescription;
	}


	/**
	 * This method initializes jScrollPaneGeneratedDescription
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneGeneratedDescription() {
		if (jScrollPaneGeneratedDescription == null) {
			jScrollPaneGeneratedDescription = new JScrollPane();
			jScrollPaneGeneratedDescription.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			jScrollPaneGeneratedDescription.setViewportView(getJTextAreaGeneratedDescription());
		}
		return jScrollPaneGeneratedDescription;
	}


	/**
	 * This method initializes jTextAreaGeneratedDescription
	 *
	 * @return bi.view.utils.CMJEditorPaneFocusChangeable
	 */
	public CMJEditorPaneFocusChangeable getJTextAreaGeneratedDescription() {
		if (jTextAreaGeneratedDescription == null) {
			jTextAreaGeneratedDescription = new CMJEditorPaneFocusChangeable();
			jTextAreaGeneratedDescription.setBackground(SystemColor.inactiveCaptionBorder);
			jTextAreaGeneratedDescription.setText("");
			jTextAreaGeneratedDescription.setEditable(false);
			jTextAreaGeneratedDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		}
		return jTextAreaGeneratedDescription;
	}


	/**
	 * This method initializes jPanelDescription
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelDescription() {
		if (jPanelDescription == null) {
			jPanelDescription = new JPanel();
			jPanelDescription.setLayout(new BorderLayout());
			jPanelDescription.setPreferredSize(new Dimension(0, 10));
			jPanelDescription.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), CMMessages.getString("LABEL_DESCRIPTION"), TitledBorder.LEADING, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 11), SystemColor.activeCaption));
			jPanelDescription.add(getJScrollPaneDescription(), java.awt.BorderLayout.CENTER);
		}
		return jPanelDescription;
	}


	/**
	 * This method initializes jScrollPaneDescription
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneDescription() {
		if (jScrollPaneDescription == null) {
			jScrollPaneDescription = new JScrollPane();
			jScrollPaneDescription.setViewportView(getJTextAreaDescription());
		}
		return jScrollPaneDescription;
	}


	/**
	 * This method initializes jTextAreaDescription
	 *
	 * @return bi.view.utils.CMJEditorPaneFocusChangeable
	 */
	public CMJEditorPaneFocusChangeable getJTextAreaDescription() {
		if (jTextAreaDescription == null) {
			jTextAreaDescription = new CMJEditorPaneFocusChangeable();
			jTextAreaDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			jTextAreaDescription.setText("");
		}
		return jTextAreaDescription;
	}


	/**
	 * This method initializes shuttleElements
	 *
	 * @return bi.view.utils.CMTwinBox
	 */
	public CMTwinBox getShuttleElements() {
		if (shuttleElements == null) {
			shuttleElements = new CMTwinBox();
			shuttleElements.getJListAssigned().setCellRenderer(listCellRenderer);
			shuttleElements.getJListAvailable().setCellRenderer(listCellRenderer);
		}
		return shuttleElements;
	}
}
