/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */

package bi.view.dependencycombinationviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;

import model.Combination;
import model.Dependency;
import model.Structure;
import bi.controller.DependencyManager;
import bi.controller.ElementManager;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMStyleModelHeader;
import bi.view.utils.CMAdjustmentListener;
import bi.view.utils.ScrollBarSynchronizer;

import com.eliad.swing.JSmartGrid;
import com.eliad.swing.JSmartGridHeader;

/**
 * Title:        CaseMaker Description:  Test tool for the automatic generation of test cases.
 * Copyright:    Copyright (c) 2003 Company:      BUSINESS SOFTWARE INNOVATIONS
 * @author Gary Rueda Sandoval
 * @version 1.0
 */
public class CMPanelDependencies extends JPanel {
    CMHeaderGridModelDependencies headerGridModel = null;
    CMGridDependencies cmGridDependencies = null;


    CMFrameView cmFrame = null;
    DependencyManager dependencyManager = null;
    StructureManager structureManager = null;
    ElementManager elementManager = null;
    TestCaseManager m_TestCaseManager = null;
    Structure structure = null;
    //private CMMenuBarDependencies cmMenuBarDependencies;
    //private CMToolBarDependencies cmToolBarDependencies;

    CMDependencyElementViews m_CMDependencyElementView = null;
    CMCombinationViews m_CMCombinationViews = null;
    JSplitPane bottomSplitPane; //fcastro_19102004

    public CMPanelDependencies(CMFrameView frame) {
        cmFrame = frame;
        frame.getKeyEventIntercepter().setM_CMPanelDependencies(this); //fcastro_13092004
        dependencyManager = cmFrame.getCmApplication().getSessionManager().getDependencyManager();
        structureManager = cmFrame.getCmApplication().getSessionManager().getStructureManager();
        elementManager = cmFrame.getCmApplication().getSessionManager().getElementManager();
        m_TestCaseManager = cmFrame.getCmApplication().getSessionManager().getTestCaseManager();
        try {
            initGUI();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setM_Structure(Structure p_Structure) {
        structure = p_Structure;
        m_CMDependencyElementView.setM_Structure(structure);
        m_CMCombinationViews.setM_Structure(structure);
        cmGridDependencies.setM_Structure(structure);
    }

    public Structure getM_Structure() {
        return cmGridDependencies.getM_Structure();
    }

    private void initGUI() throws Exception {
        //cmPopupMenuDependencies = new CMPopupMenuDependencies(this);
        //cmPopupMenuCombinations = new CMPopupMenuCombinations(this);
        setBorder(BorderFactory.createBevelBorder(1));
        setLayout(new BorderLayout());
        //LEFT BOTTOM COMPONENT
        m_CMDependencyElementView = new CMDependencyElementViews(this, cmFrame);
        //RIGHT BOTTOM COMPONENT
        m_CMCombinationViews = new CMCombinationViews(this, cmFrame);
        m_CMCombinationViews.setAutoResizeMode(0);
        //m_CMCombinationViews.setOpaque(true);
        m_CMCombinationViews.setBorder(null);
        //TOP COMPONENT
        cmGridDependencies = new CMGridDependencies(this, cmFrame, 0, 2, m_CMDependencyElementView,
            m_CMCombinationViews /*, cmPopupMenuDependencies*/);
        // TOP COMPONENT SCROLL PANE
        JScrollPane jScrollPane3 = new JScrollPane(cmGridDependencies);
        headerGridModel = new CMHeaderGridModelDependencies(cmGridDependencies);
        JSmartGridHeader m_GridHeader = new JSmartGridHeader(cmGridDependencies, JSmartGrid.HORIZONTAL,
            headerGridModel, null, new CMStyleModelHeader());
        m_GridHeader.setBackground(new Color(36, 38, 116));
        m_GridHeader.setSelectionBackgroundColor(new Color(36, 38, 116));
        m_GridHeader.setSelectionForegroundColor(new Color(252, 254, 252));
        m_GridHeader.setGridColor(new Color(196, 194, 196));
        m_GridHeader.setFont(new Font("Dialog", Font.PLAIN, 12));
        m_GridHeader.setForeground(new Color(252, 254, 252));
        m_GridHeader.setBorder(BorderFactory.createRaisedBevelBorder());
        m_GridHeader.setRowResizable(false);
        m_GridHeader.setColumnWidth(0, 150);
        m_GridHeader.setColumnWidth(1, 650);
        m_GridHeader.setColumnAutoResizeMode(JSmartGridHeader.AUTO_RESIZE_LAST);
        cmGridDependencies.setColumnHeader(m_GridHeader);


        //LEFT BOTTOM COMPONENT SCROLL PANE
        JScrollPane jScrollPane5 = new JScrollPane();
        jScrollPane5.setViewportView(m_CMDependencyElementView);
        //RIGHT BOTTOM COMPONENT SCROLL PANE
        JScrollPane jScrollPane4 = new JScrollPane();
        jScrollPane4.setViewportView(m_CMCombinationViews);
        jScrollPane4.setToolTipText("");
        jScrollPane4.setBorder(null);

        m_CMDependencyElementView.getViewport1().setScrollMode(JViewport.BLIT_SCROLL_MODE);
        m_CMCombinationViews.getViewport1().setScrollMode(JViewport.BLIT_SCROLL_MODE);

        jScrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //jScrollPane5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        JScrollBar jScrollBarLeft = jScrollPane5.getVerticalScrollBar();
        JScrollBar jScrollBarRight = jScrollPane4.getVerticalScrollBar();
        jScrollBarRight.getModel().addChangeListener(new ScrollBarSynchronizer(jScrollBarLeft));
        //Ccastedo begins 21-02-06
        jScrollPane5.getVerticalScrollBar().addAdjustmentListener(new CMAdjustmentListener(this,jScrollPane5,jScrollPane4));
        jScrollPane4.getVerticalScrollBar().addAdjustmentListener(new CMAdjustmentListener(this,jScrollPane5,jScrollPane4));
        //Ccastedo ends 21-02-06
        // BOTTOM COMPONENT SPLIT PANE
        JSplitPane jSplitPane3 = new JSplitPane();
        bottomSplitPane = jSplitPane3; //fcastro_19102004
        jSplitPane3.setOneTouchExpandable(true);
        jSplitPane3.setDividerSize(8);
        jSplitPane3.setDividerLocation(m_CMDependencyElementView.getM_Width() + jSplitPane3.getDividerSize());
        jSplitPane3.add(jScrollPane4, "right");
        jSplitPane3.add(jScrollPane5, "left");
        //OVERALL SPLIT PANE
        //JSplitPane jSplitPane2 = new JSplitPane();
        JSplitPane jSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jScrollPane3, jSplitPane3);
        jSplitPane2.setOrientation(0);
        jSplitPane2.setOneTouchExpandable(true);
        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setDividerSize(8);
        add(jSplitPane2, java.awt.BorderLayout.CENTER);
        //fcastro_19102004_begin
        //grueda11112004_begin
//        m_CMDependencyView.getViewport1().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
//        m_CMCombinationViews.getViewport1().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

        //grueda11112004_end
        //fcastro_19102004_end
        cmGridDependencies.getViewport1().setScrollMode(JViewport.BLIT_SCROLL_MODE);
        jScrollPane3.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jScrollPaneDependencies_mouseClicked(e);
                }
            });
        //jScrollPaneDependencyView.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane5.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jScrollPaneDependencyView_mouseClicked(e);
                }
            });
        //jScrollPaneCombinationViews.setBorder(BorderFactory.createLoweredBevelBorder());
        jScrollPane4.addMouseListener(
            new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    jScrollPaneCombinationViews_mouseClicked(e);
                }
            });
    }

    //fcastro_19102004_begin
    public void updateScrolls() {
        JScrollPane rightPane = (JScrollPane)bottomSplitPane.getRightComponent();
        JScrollBar barRight = rightPane.getVerticalScrollBar();
        JScrollPane leftPane = (JScrollPane)bottomSplitPane.getLeftComponent();
        barRight.setValue(leftPane.getVerticalScrollBar().getValue());
    }

    public int getRightHorizontalScrollValue() {
        JScrollPane rightPane = (JScrollPane)bottomSplitPane.getRightComponent();
        return rightPane.getHorizontalScrollBar().getValue();
    }

    public void setRightHorizontalScroll(int value) {
        JScrollPane rightPane = (JScrollPane)bottomSplitPane.getRightComponent();
        JScrollBar bar = rightPane.getHorizontalScrollBar();
        bar.setValue(value);
    }

    //fcastro_19102004_end
    public CMGridDependencies getCMGridDependencies() {
        return cmGridDependencies;
    }

    public CMDependencyElementViews getM_CMDependencyElementView() {
        return m_CMDependencyElementView;
    }

    public CMCombinationViews getM_CMCombinationViews() {
        return m_CMCombinationViews;
    }

    public void save() {
        //structureManager.saveStructure(structure);
    }


    public void changeDependencyCombinations(int index, Dependency dependency, Vector combinations) {
        if (cmGridDependencies.getSelectionModel().getLeadRow() == index) {
            this.m_CMCombinationViews.deleteAllCombinationViews();
            dependency.setLnkCombinations(combinations);
            this.m_CMCombinationViews.addAllCombinationViews(dependency);
        }
        else {
            dependency.setLnkCombinations(combinations);
            cmGridDependencies.changeSelection(index, 0, false, false);
        }
        m_CMCombinationViews.updateCommandos();
    }

    //integration_fcastro_17082004_end
    void jScrollPaneDependencies_mouseClicked(MouseEvent e) {
        if (e.getModifiers() == Event.META_MASK) {
        	/*
            cmFrame.getJPopupMenuItemDependencyGenerateCombinations().setEnabled(false);
            cmFrame.getJPopupMenuItemDependencyCreate().setEnabled(true);
            //cmPopupMenuDependencies.getJMenuItem_GenerateCombinations().setEnabled(false);
            //cmPopupMenuDependencies.getJMenuItem_CreateDependency().setEnabled(true);
            cmFrame.jPopupMenuDependency.show(e.getComponent(), e.getX(), e.getY());*/
            //cmPopupMenuDependencies.show(e.getComponent(), e.getX(), e.getY() );
        }
    }

    void jScrollPaneDependencyView_mouseClicked(MouseEvent e) {
        if (e.getModifiers() == Event.META_MASK) {
          //  cmFrame.jPopupMenuCombinations.show(e.getComponent(), e.getX(), e.getY());
            //cmPopupMenuCombinations.show(e.getComponent(), e.getX(), e.getY() );
        }
    }

    void jScrollPaneCombinationViews_mouseClicked(MouseEvent e) {
        if (e.getModifiers() == Event.META_MASK) {
          //  cmFrame.jPopupMenuCombinations.show(e.getComponent(), e.getX(), e.getY());
            //cmPopupMenuCombinations.show(e.getComponent(), e.getX(), e.getY() );
        }
    }


    //integration_fcastro_17082004_begin
    public void changeCombinationDescriptions(int index, Vector combinationDescriptions, Dependency dependency) {
        int numOfCombinations = dependency.getLnkCombinations().size();
        if (combinationDescriptions.size() == numOfCombinations) {
            Vector combinations = dependency.getLnkCombinations();
            for (int i = 0; i < numOfCombinations; i++) {
                Combination combination = (Combination)combinations.elementAt(i);
                String description = (String)combinationDescriptions.elementAt(i);
                combination.setDescription(new String(description));
            }
            this.cmGridDependencies.changeSelection(index, 0, false, false);
        }
    }



    public boolean isChangeOrderDependency(Vector oldOrder, Vector newOrder)
    {
        boolean result= true;
        for(int i=0;i<oldOrder.size();i++)
        {
            result =false;
			if(oldOrder.elementAt(i)!= newOrder.elementAt(i))
            {
                return true;
            }
        }
        return result;
    }
    public int getMaxIDDependency(Structure actualStructure) {        int maxId = -1;
        for (int i = 0; i < actualStructure.getLnkDependencies().size(); i++) {
            Dependency actualDependency = (Dependency)actualStructure.getLnkDependencies().elementAt(i);
            int idDependecy = actualDependency.getId();
            if (maxId < idDependecy)
                maxId = idDependecy;
        }
        return maxId;
    }
 //HCanedo_30112004_End
    /////////////////////////////////////////////////
    public CMFrameView getFrame() { return cmFrame; }

    public void bottomSplitPaneAncestorMoved(HierarchyEvent e) { }

	public DependencyManager getDependencyManager() {
		return dependencyManager;
	}

	public void setDependencyManager(DependencyManager dependencyManager) {
		this.dependencyManager = dependencyManager;
	}

	/**
	*@autor smoreno
	 * @param p_dependency
	 * @param p_combinations
	 */
	public void changeDependencyCombinations(Dependency p_dependency, Vector p_combinations) {
		// TODO Auto-generated method stub
		changeDependencyCombinations(getCMGridDependencies().getSelectionModel().getLeadRow(),p_dependency,p_combinations);
	}


}
