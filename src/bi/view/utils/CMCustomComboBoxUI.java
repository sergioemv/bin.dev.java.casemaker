package bi.view.utils;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

public class CMCustomComboBoxUI extends MetalComboBoxUI {
	 @SuppressWarnings("serial")
	protected ComboPopup createPopup() {
		    BasicComboPopup popup = new BasicComboPopup(comboBox) {
		      public int getLargestStringSize(){
		    	  FontMetrics fm = comboBox.getFontMetrics(comboBox.getFont());
		    	  int size =0;
		    	  for(int i=0; i<comboBox .getItemCount(); i++){
		    		Object obj=comboBox.getItemAt(i);
		    		 String sds = obj.toString();
		    	  if(size< fm.stringWidth(sds))
		    	  	size = fm.stringWidth(sds);
		    	  }
		    	  return size+5;
		      }
		      public void show() {
		        Dimension popupSize = ((CMBaseJComboBox)comboBox).getPopupSize();
		        popupSize.setSize( popupSize.width,
		          getPopupHeightForRowCount( comboBox.getMaximumRowCount() ) );
		        int size1=getLargestStringSize();
		        int size2=popupSize.width;
		        if(size1 >size2){
		        	size2=size1;
		        	if(comboBox.getBounds().width > size2){
		        		size2=comboBox.getBounds().width;
		        	}
		        }
		        Rectangle popupBounds = computePopupBounds( 0,
		          comboBox.getBounds().height, size2, popupSize.height);
		        scroller.setMaximumSize( popupBounds.getSize() );
		        scroller.setPreferredSize( popupBounds.getSize() );
		        scroller.setMinimumSize( popupBounds.getSize() );
		        scroller.getVerticalScrollBar().setVisible(true);
		        list.invalidate();
		        int selectedIndex = comboBox.getSelectedIndex();
		        if ( selectedIndex == -1 ) {
		          list.clearSelection();
		        } else {
		          list.setSelectedIndex( selectedIndex );
		        }
		        list.ensureIndexIsVisible( list.getSelectedIndex() );
		     
		        setLightWeightPopupEnabled( comboBox.isLightWeightPopupEnabled() );
		 
		        show( comboBox, popupBounds.x, popupBounds.y );
		      }
		    };
		    popup.getAccessibleContext().setAccessibleParent(comboBox);
		    return popup;
		  }
}

