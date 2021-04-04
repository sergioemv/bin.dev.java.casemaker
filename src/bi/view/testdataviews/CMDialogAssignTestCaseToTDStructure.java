package bi.view.testdataviews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import model.Structure;
import model.TestCase;
import bi.controller.TestCaseManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;
import bi.view.utils.JCustomDialog;
 
public class CMDialogAssignTestCaseToTDStructure extends JCustomDialog {
//hcanedo_21102004_begin
    JPanel panelDialog = new JPanel();
    JButton jButtonCancel = new JButton();
    Border border1;
    TitledBorder titledBorder2;
    TitledBorder titledBorder3;
    ////////////////////////////////////////
    boolean eventJButtonCancelClicked = false;
    boolean eventJButtonOKClicked = false;
    Structure selectedStructure = null;
    TestCaseManager testCaseManager = null;
    private JList jListLeft = new JList();
    private JList jListRight = new JList();
    private JScrollPane jScrollListLeft;
    private JScrollPane jScrollListRight;
    private JButton jButtonDelete = new JButton();
    private JButton jButtonAdd = new JButton();
    private JPanel jPanelAviables = new JPanel();
    private JPanel jPanelUses = new JPanel();
    private JPanel jPanelTitle = new JPanel();
    Vector leftElements = null;
    Vector rightElements = null;
    Vector leftViews = null;
    Vector rightViews = null;
    Vector m_generateTestCase;
    private boolean ispanelTestData;
    private JPanel jPanelButtons = new JPanel();
    JButton jButtonOK = new JButton();
//hcanedo_21102004_end
    public CMDialogAssignTestCaseToTDStructure(CMFrameView frame, Structure structure, Vector testCaseInTDStructure) {
        super(frame);
        this.selectedStructure = structure;
        m_generateTestCase = testCaseInTDStructure;
        leftElements = new Vector(0);
        rightElements = new Vector(0);
        leftViews = new Vector(0);
        rightViews = new Vector(0);
        ispanelTestData = false;
        testCaseManager = frame.getCmApplication().getSessionManager().getTestCaseManager();
        try {
            initGUI();
            pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//hcanedo_21102004_begin
    void initGUI() throws Exception {
        //     jScrollListLeft.setPreferredSize(new Dimension(250, 80));
        jButtonOK.setText(CMMessages.getString("BUTTON_OK"));
        jButtonOK.setBounds(new java.awt.Rectangle(135, 263, 77, 27));
        jButtonOK.setMaximumSize(new java.awt.Dimension(90, 25));
        jButtonOK.setMinimumSize(new java.awt.Dimension(35, 25));
        jButtonOK.setPreferredSize(new java.awt.Dimension(79, 25));
        jButtonOK.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    eventJButtonOKActionPerformed(e);
                }
            });
        jButtonCancel.setText(CMMessages.getString("BUTTON_CANCEL"));
        jButtonCancel.setBounds(new java.awt.Rectangle(235, 263, 78, 27));
        jButtonCancel.setPreferredSize(new java.awt.Dimension(79, 25));
        jButtonCancel.setMaximumSize(new java.awt.Dimension(90, 25));
        jButtonCancel.setMinimumSize(new java.awt.Dimension(35, 25));
        jButtonCancel.addActionListener(
            new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    eventJButtonCancelActionPerformed(e);
                }
            });
        jPanelButtons.setBounds(new java.awt.Rectangle(30, 253, 98, 33));
        jPanelButtons.add(jButtonOK);
        jPanelButtons.add(jButtonCancel);
        jPanelTitle.setBounds(new java.awt.Rectangle(15, 12, 359, 205));
        jPanelTitle.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
            CMMessages.getString("TESTDATA_CHOICE_TESTCASE_FOR_TESTDATA"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
            new java.awt.Font("SansSerif", 0, 11), //$NON-NLS-1$ //$NON-NLS-2$
            new java.awt.Color(60, 60, 60)));
        jButtonDelete.setText(">>");
        jButtonDelete.setBounds(new java.awt.Rectangle(158, 87, 73, 27));
        jButtonDelete.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonDeleteActionPerformed(e); }
            });
        jScrollListRight = new JScrollPane(jListRight);
        jListRight.setBounds(new java.awt.Rectangle(282, 88, 156, 134));
        jListRight.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollListLeft = new JScrollPane(jListLeft);
        jListLeft.setBounds(new java.awt.Rectangle(34, 88, 140, 134));
        jListLeft.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        //    jScrollListRight.setPreferredSize(new Dimension(250, 80));
        border1 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
        titledBorder2 = new TitledBorder(""); //$NON-NLS-1$
        titledBorder3 = new TitledBorder(""); //$NON-NLS-1$
        panelDialog.setLayout(null);
        //$NON-NLS-1$
        //$NON-NLS-1$
        setResizable(false);
        this.setTitle(CMMessages.getString("TESTDATA_TITLE_DIALOG_ASSIGN_TESTCASE_TO_TDSTRUCTURE")); //$NON-NLS-1$
        ///////////////////////////////////////////////////////////////////////////
        //    setBounds(new java.awt.Rectangle(0, 0, 479, 346));
        setModal(true);
        ///////////////////////////////////////////////////////////////////////////
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanelButtons, java.awt.BorderLayout.SOUTH);
        getContentPane().add(panelDialog, java.awt.BorderLayout.CENTER);
        panelDialog.setPreferredSize(new java.awt.Dimension(389, 229));
        panelDialog.setMinimumSize(new java.awt.Dimension(200, 200));
        panelDialog.setSize(new java.awt.Dimension(389, 229));
        panelDialog.add(jButtonAdd);
        panelDialog.add(jPanelAviables);
        panelDialog.add(jPanelUses);
        panelDialog.add(jButtonDelete);
        panelDialog.add(jPanelTitle);
        //$NON-NLS-1$
        jButtonAdd.setText("<<"); //$NON-NLS-1$
        jButtonAdd.setBounds(new java.awt.Rectangle(158, 133, 73, 27));
        //$NON-NLS-1$
        //$NON-NLS-1$
        jPanelAviables.setBounds(new java.awt.Rectangle(23, 37, 124, 172));
        jPanelAviables.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
            CMMessages.getString("TESTDATA_AVIABLES_TESTCASE"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP, //$NON-NLS-1$
            new java.awt.Font("SansSerif", 0, 11), new java.awt.Color(60, 60, 60))); //$NON-NLS-1$
        jPanelAviables.setLayout(new java.awt.BorderLayout());
        jPanelAviables.add(jScrollListLeft, java.awt.BorderLayout.CENTER);
        jPanelUses.setBounds(new java.awt.Rectangle(242, 37, 124, 172));
        jPanelUses.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
            CMMessages.getString("TESTDATA_ASSIGNED_TESTCASE"), javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP,
            new java.awt.Font("SansSerif", 0, 11), //$NON-NLS-1$ //$NON-NLS-2$
            new java.awt.Color(60, 60, 60)));
        jPanelUses.setLayout(new java.awt.BorderLayout());
        jPanelUses.add(jScrollListRight, java.awt.BorderLayout.CENTER);
        jButtonAdd.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) { jButtonAddActionPerformed(e); }
            });
        setBounds(new java.awt.Rectangle(0, 0, 395, 287));
        setLeftListView();
        setRightListView();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = this.getPreferredSize();
        Point loc = getLocation();
        this.setLocation((screenSize.width - dlgSize.width) / 2, (screenSize.height - dlgSize.height) / 2);
        setSize(new java.awt.Dimension(393,287));
//hcanedo_06102005_begin        
        getRootPane().setDefaultButton(jButtonOK);
        jScrollListLeft.setFocusable(false);
        jScrollListRight.setFocusable(false);
//hcanedo_06102005_end        
    }
//hcanedo_21102004_end
    /** Overridden so we can exit when window is closed */
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            eventJButtonCancelClicked = true;
            cancel();
        }
        super.processWindowEvent(e);
    }

    /** Close the dialog */
    void cancel() {
        dispose();
    }

    public boolean isEventJButtonCancelClicked() {
        return eventJButtonCancelClicked;
    }

    public boolean isEventJButtonOKClicked() {
        return eventJButtonOKClicked;
    }

    void eventJButtonOKActionPerformed(ActionEvent e) {
        int cantElements = rightElements.size();
        m_generateTestCase.removeAllElements();
        for (int i = 0; i < cantElements; i++)
            m_generateTestCase.addElement(rightElements.elementAt(i));
        eventJButtonOKClicked = true;
        dispose();
    }

    void eventJButtonCancelActionPerformed(ActionEvent e) {
        eventJButtonCancelClicked = true;
        cancel();
    }

    private Vector createViews(Vector cloneElements) {
        Vector views = new Vector(0);
        int numElements = cloneElements.size();
        for (int i = 0; i < numElements; i++) {
            TestCase element = (TestCase)cloneElements.elementAt(i);
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(element.getName());
            sBuffer.append(element.getStateName());
            views.addElement(sBuffer.toString());
        }
        return views;
    }

    private void setLeftListView() {
        int numOfTestCase = selectedStructure.getLnkTestCases().size();
        TestCase testCase = null;
        Vector avaliablesTestCase = new Vector(0);
        for (int i = 0; i < numOfTestCase; i++) {
            testCase = (TestCase)selectedStructure.getLnkTestCases().elementAt(i);
            if (!m_generateTestCase.contains(testCase))
                avaliablesTestCase.addElement(testCase);
        }
        Vector clones = (Vector)avaliablesTestCase.clone();
        Vector views = createViews(clones);
        this.setLeftList(clones, views);
    }

    private void setRightListView() {
        Vector clones = (Vector)m_generateTestCase.clone();
        Vector views = createViews(clones);
        this.setRightList(clones, views);
    }

    public void jButtonAddActionPerformed(ActionEvent e) {
        if (ispanelTestData) {
            int listSize = jListLeft.getModel().getSize();
            int selectedIndex = jListRight.getSelectedIndex();
            if (selectedIndex >= 0) {
                Object selectedElement = rightElements.elementAt(selectedIndex);
                Object selectedElementView = rightViews.elementAt(selectedIndex);
                leftElements.addElement(selectedElement);
                leftViews.addElement(selectedElementView);
                int newSelectedIndex = leftElements.indexOf(selectedElement);
                rightElements.removeElementAt(selectedIndex);
                rightViews.removeElementAt(selectedIndex);
                jListRight.setListData(rightViews);
                jListLeft.setListData(leftViews);
                jListLeft.setSelectedIndex(newSelectedIndex);
            }
            jButtonDelete.setEnabled(true);
        }
        else {
            int[] indices = jListRight.getSelectedIndices();
            int selectedIndex = 0;
            int newSelectedIndex = 0;
            Vector elementsToBeDeleted = new Vector(0);
            Vector viewsToBeDeleted = new Vector(0);
            Object selectedElement = null;
            Object selectedElementView = null;
            Object elementToBeDeleted = null;
            Object viewToBeDeleted = null;
            for (int i = 0; i < indices.length; i++) {
                selectedIndex = indices[i];
                if (selectedIndex >= 0) {
                    selectedElement = rightElements.elementAt(selectedIndex);
                    selectedElementView = rightViews.elementAt(selectedIndex);
                    leftElements.addElement(selectedElement);
                    leftViews.addElement(selectedElementView);
                    elementsToBeDeleted.addElement(selectedElement);
                    viewsToBeDeleted.addElement(selectedElementView);
                }
            }
            int numOfDeletingObjects = elementsToBeDeleted.size();
            for (int i = 0; i < numOfDeletingObjects; i++) {
                elementToBeDeleted = elementsToBeDeleted.elementAt(i);
                viewToBeDeleted = viewsToBeDeleted.elementAt(i);
                rightElements.removeElement(elementToBeDeleted);
                rightViews.removeElement(viewToBeDeleted);
            }
            if (indices.length > 0) {
                jListRight.setListData(rightViews);
                jListLeft.setListData(leftViews);
            }
        }
    }

    public void jButtonDeleteActionPerformed(ActionEvent e) {
        if (ispanelTestData) {
            int selectedIndex = jListLeft.getSelectedIndex();
            if (selectedIndex >= 0) {
                Object selectedElement = leftElements.elementAt(selectedIndex);
                Object selectedElementView = leftViews.elementAt(selectedIndex);
                rightElements.addElement(selectedElement);
                rightViews.addElement(selectedElementView);
                int newSelectedIndex = rightElements.indexOf(selectedElement);
                leftElements.removeElementAt(selectedIndex);
                leftViews.removeElementAt(selectedIndex);
                jListRight.setListData(rightViews);
                jListLeft.setListData(leftViews);
                jListRight.setSelectedIndex(newSelectedIndex);
            }
            jButtonDelete.setEnabled(false);
        }
        else {
            int[] indices = jListLeft.getSelectedIndices();
            int selectedIndex = 0;
            int newSelectedIndex = 0;
            Vector elementsToBeDeleted = new Vector(0);
            Vector viewsToBeDeleted = new Vector(0);
            Object selectedElement = null;
            Object selectedElementView = null;
            Object elementToBeDeleted = null;
            Object viewToBeDeleted = null;
            for (int i = 0; i < indices.length; i++) {
                selectedIndex = indices[i];
                if (selectedIndex >= 0) {
                    selectedElement = leftElements.elementAt(selectedIndex);
                    selectedElementView = leftViews.elementAt(selectedIndex);
                    rightElements.addElement(selectedElement);
                    rightViews.addElement(selectedElementView);
                    elementsToBeDeleted.addElement(selectedElement);
                    viewsToBeDeleted.addElement(selectedElementView);
                }
            }
            int numOfDeletingObjects = elementsToBeDeleted.size();
            for (int i = 0; i < numOfDeletingObjects; i++) {
                elementToBeDeleted = elementsToBeDeleted.elementAt(i);
                viewToBeDeleted = viewsToBeDeleted.elementAt(i);
                leftElements.removeElement(elementToBeDeleted);
                leftViews.removeElement(viewToBeDeleted);
            }
            if (indices.length > 0) {
                jListRight.setListData(rightViews);
                jListLeft.setListData(leftViews);
            }
        }
    }

    void setLeftList(Vector v, Vector views) {
        leftElements = v;
        leftViews = views;
        jListLeft.setListData(leftViews);
    }

    void setRightList(Vector v, Vector views) {
        rightElements = v;
        rightViews = views;
        jListRight.setListData(rightViews);
    }

    public boolean isIspanelTestData() { return ispanelTestData; }

    public void setIspanelTestData(boolean ispanelTestData) { this.ispanelTestData = ispanelTestData; }

    public void disableButtonDelete() {
        jButtonDelete.setEnabled(false);
    }
	protected void PressJButtonCancel(Object object) {
		eventJButtonCancelActionPerformed(null);
	}
	protected void PressJButtonOk(Object object) {
		eventJButtonOKActionPerformed(null);
	}
	protected List getOrder() {
		List focusOrder= new ArrayList();
		focusOrder.add(jListLeft);
		focusOrder.add(jButtonDelete);
		focusOrder.add(jListRight);
		focusOrder.add(jButtonAdd);
		focusOrder.add(jButtonOK);
		focusOrder.add(jButtonCancel);
		return focusOrder;
	}
}
