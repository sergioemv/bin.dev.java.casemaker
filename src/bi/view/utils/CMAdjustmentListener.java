package bi.view.utils;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollPane;

import bi.view.dependencycombinationviews.CMPanelDependencies;
import bi.view.stdcombinationviews.CMElementAndStdCombinationViews;

public class CMAdjustmentListener implements AdjustmentListener {
	private JScrollPane leftPane = new JScrollPane(); 
    private JScrollPane rightPane = new JScrollPane();
    private CMPanelDependencies m_CMPanelDependencies=null;
    private CMElementAndStdCombinationViews m_ElementAndStdCombinationviews = null;
  
	
//    public CMAdjustmentListener(CMPanelDependencies p_dependencies, JScrollPane p_leftPane, JScrollPane p_rightPane) {
//    	this.leftPane = p_leftPane;
//		this.rightPane = p_rightPane;
//		m_CMPanelDependencies = p_dependencies;
//	}
//	public CMAdjustmentListener(CMElementAndStdCombinationViews p_ElementAndStdCombinationviews, JScrollPane p_leftPane, JScrollPane p_rightPane) {
//		this.leftPane = p_leftPane;
//		this.rightPane = p_rightPane;
//		this.m_ElementAndStdCombinationviews = p_ElementAndStdCombinationviews;
//	}
	public CMAdjustmentListener(Object  p_views, JScrollPane p_leftPane, JScrollPane p_rightPane) {
		this.leftPane = p_leftPane;
		this.rightPane = p_rightPane;
		if (p_views instanceof CMPanelDependencies)
			m_CMPanelDependencies = (CMPanelDependencies)p_views;
		else
			if (p_views instanceof CMElementAndStdCombinationViews)
				m_ElementAndStdCombinationviews = (CMElementAndStdCombinationViews)p_views;
	}
	// This method is called whenever the value of a scrollbar is changed,
    // either by the user or programmatically.
    public void adjustmentValueChanged(AdjustmentEvent evt) {
        Adjustable source = evt.getAdjustable();

        // getValueIsAdjusting() returns true if the user is currently
        // dragging the scrollbar's knob and has not picked a final value
        if (evt.getValueIsAdjusting()) {
        	rightPane.repaint();
        	leftPane.repaint();
            return;
        }

        // Determine which scrollbar fired the event
        int orient = source.getOrientation();
        if (orient == Adjustable.HORIZONTAL) {
            // Event from horizontal scrollbar
        } else {
            // Event from vertical scrollbar
        	if (source == leftPane.getVerticalScrollBar())
        	{
        		rightPane.getVerticalScrollBar().setValue(leftPane.getVerticalScrollBar().getValue());
        		rightPane.repaint();
        	}
        	else
        	{
        		leftPane.invalidate();
        		leftPane.getVerticalScrollBar().setValue(rightPane.getVerticalScrollBar().getValue());
        		leftPane.repaint();
        	}
        	if (m_CMPanelDependencies!=null)
        		m_CMPanelDependencies.repaint();
        	if (m_ElementAndStdCombinationviews!=null)
        		m_ElementAndStdCombinationviews.repaint();
        }

        // Determine the type of event
        int type = evt.getAdjustmentType();
        switch (type) {
          case AdjustmentEvent.UNIT_INCREMENT:
              // Scrollbar was increased by one unit
              break;
          case AdjustmentEvent.UNIT_DECREMENT:
              // Scrollbar was decreased by one unit
              break;
          case AdjustmentEvent.BLOCK_INCREMENT:
              // Scrollbar was increased by one block
              break;
          case AdjustmentEvent.BLOCK_DECREMENT:
              // Scrollbar was decreased by one block
              break;
          case AdjustmentEvent.TRACK:
              // The knob on the scrollbar was dragged
              break;
        }

        // Get current value
        int value = evt.getValue();
    }
}
