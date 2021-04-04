package bi.view.elementviews;


import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import bi.view.actions.CMAction;
import bi.view.cells.CMCellElement;
import bi.view.cells.CMCellElementDescription;
import bi.view.cells.CMCellElementGuiObject;
import bi.view.cells.CMCellElementName;
import bi.view.cells.CMCellEquivalenceClass;
import bi.view.cells.CMCellEquivalenceClassDescription;
import bi.view.cells.CMCellEquivalenceClassEffects;
import bi.view.cells.CMCellEquivalenceClassName;
import bi.view.cells.CMCellEquivalenceClassState;
import bi.view.cells.CMCellEquivalenceClassValue;
import bi.view.cells.headers.CMCellHeaderElementDescription;
import bi.view.cells.headers.CMCellHeaderElementGuiObject;
import bi.view.cells.headers.CMCellHeaderElementName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassDescription;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassEffects;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassName;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassState;
import bi.view.cells.headers.CMCellHeaderEquivalenceClassValue;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMFrameView;

public class CMGridElementsKeyEventIntercepter{
	 private CMGridElements m_CMGridElements;
	
	 //CCastedo begins 03-10-05    
	 
	    private Object  editingObject = null;
	    private CMFrameView m_FrameView=null;
	    //CCastedo ends 03-10-05
	public CMGridElementsKeyEventIntercepter(CMGridElements p_gridElements, CMFrameView p_frameView) {
		 m_CMGridElements=p_gridElements;	
		 m_FrameView = p_frameView;
	}

	

	public boolean dispatchKeyEvent(KeyEvent e) {
		
//      CCastedo begin 04-10-05
		int row = m_CMGridElements.getFirstSelectedRow();
        int column = m_CMGridElements.getFirstSelectedColumn();
        editingObject = m_CMGridElements.getCellObjectAt(row,column);
        
        if(e.getKeyCode()==KeyEvent.VK_N && e.isControlDown()){      	
            
            if (editingObject == null){
            	
            	CMAction.ELEMENT_CREATE.getAction().actionPerformed(null);
            }
            if(editingObject instanceof CMCellHeaderElementName || editingObject instanceof CMCellHeaderElementDescription
            		|| editingObject instanceof CMCellHeaderElementGuiObject || editingObject instanceof CMCellElementName
            		|| editingObject instanceof CMCellElementDescription 
            ){            
            	newElement(row,column);
            	return true;
            }            
            if(editingObject instanceof CMCellHeaderEquivalenceClassDescription || editingObject instanceof CMCellHeaderEquivalenceClassEffects
            		|| editingObject instanceof CMCellHeaderEquivalenceClassName || editingObject instanceof CMCellHeaderEquivalenceClassState
            		|| editingObject instanceof CMCellHeaderEquivalenceClassValue || editingObject instanceof CMCellEquivalenceClassDescription 
            		|| editingObject instanceof CMCellEquivalenceClassName || editingObject instanceof CMCellEquivalenceClassState
            		|| editingObject instanceof CMCellEquivalenceClassValue
            ){            	
            	CMAction.EQUIVALENCECLASS_CREATE.getAction().actionPerformed(null);      
            	return true;
            } 
            if(editingObject instanceof CMCellEquivalenceClassEffects){
            	CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.getAction().actionPerformed(null);
            	return true;
            }
        	
        }
        
        //hmendez_15112005_begin        
        //else if(e.getKeyCode()==KeyEvent.VK_INSERT && e.isControlDown()){
        else if(e.getKeyCode()==KeyEvent.VK_INSERT){
        //hmendez_15112005_begin        	
        	if(editingObject instanceof CMCellHeaderElementName || editingObject instanceof CMCellHeaderElementDescription
            		|| editingObject instanceof CMCellHeaderElementGuiObject /*|| editingObject instanceof CMCellElementName
            		|| editingObject instanceof CMCellElementDescription */
            ){            	
        		CMAction.ELEMENT_INSERT.getAction().actionPerformed(null);
        		return true;
            }   
        	
        	
        }
        else if((e.getKeyCode()==KeyEvent.VK_DELETE)&&
        		(m_CMGridElements.isDeletePossible()))
        {
        	
        	if(editingObject instanceof CMCellHeaderElementName || editingObject instanceof CMCellHeaderElementDescription
            		|| editingObject instanceof CMCellHeaderElementGuiObject || editingObject instanceof CMCellElementName
            		|| editingObject instanceof CMCellElementDescription 
            ){            	
        		CMAction.ELEMENT_DELETE.getAction().actionPerformed(null);
        		if (m_CMGridElements.getStructure().getLnkElements().size()==0){
        			m_FrameView.getContentTabbedPane().requestFocus();
        		}
        		return true;
            }   
        	if(editingObject instanceof CMCellEquivalenceClassDescription || editingObject instanceof CMCellEquivalenceClassEffects
            		|| editingObject instanceof CMCellEquivalenceClassName || editingObject instanceof CMCellEquivalenceClassState
            		|| editingObject instanceof CMCellEquivalenceClassValue || editingObject instanceof CMCellEquivalenceClassDescription 
            		|| editingObject instanceof CMCellEquivalenceClassName 
            		|| editingObject instanceof CMCellEquivalenceClassValue
            ){            	
        		CMAction.EQUIVALENCECLASS_DELETE.getAction().actionPerformed(null);
        		return true;
            } 
        	
        }
        else if(e.isShiftDown()){ 
        	if (e.getKeyCode()==KeyEvent.VK_F10)
        	{
        		showpopup(e);
        	    return true;
        	}
        }
        //CCastedo ends 04-10-05
        //hmendez_10112005_begin
        else if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Z){
        	/*The error happens when the cell is editable. 
        	 * In elementview -> CMCellElement,CMCellEquivalenceClass are editables 
        	 */
//        	boolean isEditing=false;
//        	if (editingObject instanceof CMCellElement){
//        		if (((CMCellElement)editingObject).getGrid().isEditing()){
//        			isEditing=true;
//        		}
//        	}
//        	else{
//        			if (editingObject instanceof CMCellEquivalenceClass)
//        				if (((CMCellEquivalenceClass)editingObject).getGrid().isEditing()){
//        					isEditing=true;
//        					
//        				}
//        	}
//        	if (!isEditing){
        		if ((editingObject instanceof CMCellElement)||(editingObject instanceof CMCellEquivalenceClass))
          		  if (CMAction.UNDO.isEnabled())
              	  {
          			CMAction.UNDO.getAction().actionPerformed(null);
              	      return true;
              	  }
        //	}
//        	else
//        		((CMBaseJSmartGrid)editingObject).stopCellEditing();
            
        }
        else if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Y){
        	/*The error happens when the cell is editable. 
        	 * In elementview -> CMCellElement,CMCellEquivalenceClass are editables 
        	 */
            if ((editingObject instanceof CMCellElement)||(editingObject instanceof CMCellEquivalenceClass))        	
        	  if (CMAction.REDO.isEnabled())
        	  {
        		  CMAction.REDO.getAction().actionPerformed(null);
                  return true;              
        	  }
        }        
        //hmendez_10112005_end
		return false;
	}

		
	public void newElement(int row, int column){
		
		int sizeEC =m_CMGridElements.getSelectedElement(row,column).getEquivalenceClasses().size();
			//m_CMGridElements.getM_Structure().getLnkElements().t_CMCombinationViews().getColumnCount();
        if (sizeEC == 0){
    	
    		if (!(CMAction.ELEMENT_CREATE.isEnabled()))
    			return;
    		int confirmation = JOptionPane.showConfirmDialog(m_FrameView,CMMessages.getString("WANTS_TO_CREATE_ELEMENT_OR_EC"),CMMessages.getString("TITLE_CREATE_ELEMENT_OR_EC"),JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
            if( confirmation == JOptionPane.YES_OPTION) {
            	CMAction.ELEMENT_CREATE.getAction().actionPerformed(null);
            }
            else
            	if (confirmation == JOptionPane.NO_OPTION){
            		CMAction.EQUIVALENCECLASS_CREATE.getAction().actionPerformed(null);
            	}
            	else
            		if(confirmation == JOptionPane.CANCEL_OPTION)
            			;
    	}
        else
        	CMAction.ELEMENT_CREATE.getAction().actionPerformed(null);
	}
	
	public void showpopup(KeyEvent e){		
       
		int row = m_CMGridElements.getSelectionModel().getLeadRow();
		int column = m_CMGridElements.getSelectionModel().getLeadColumn();
		
		int r = m_CMGridElements.getRowPosition(row);
		int c = m_CMGridElements.getColumnPosition(column);
		
		editingObject = m_CMGridElements.getCellObjectAt(row,column);
		if(editingObject instanceof CMCellHeaderElementName || editingObject instanceof CMCellHeaderElementDescription
        		|| editingObject instanceof CMCellHeaderElementGuiObject 
        ){ 
			m_FrameView.setStateElementHeaderSelected();			
		}
		else if( editingObject instanceof CMCellElementName || editingObject instanceof CMCellElementGuiObject || editingObject instanceof CMCellElementDescription) {
		       
			m_FrameView.setStateElementContentSelected();
		}
		else if(editingObject instanceof CMCellHeaderEquivalenceClassDescription || editingObject instanceof CMCellHeaderEquivalenceClassEffects
        		|| editingObject instanceof CMCellHeaderEquivalenceClassName || editingObject instanceof CMCellHeaderEquivalenceClassState
        		|| editingObject instanceof CMCellHeaderEquivalenceClassValue
        ){   
			m_FrameView.setStateEquivalenceClassHeaderSelected();
		}
		else if(editingObject instanceof CMCellEquivalenceClassDescription
        		|| editingObject instanceof CMCellEquivalenceClassName || editingObject instanceof CMCellEquivalenceClassState
        		|| editingObject instanceof CMCellEquivalenceClassValue)
		{
			m_FrameView.setStateEquivalenceClassContentSelected();
			
		}
		else if(editingObject instanceof CMCellEquivalenceClassEffects){
	
			m_FrameView.setStateEffectsContentSelected(); 
		}
		m_FrameView.getPopupMenuElements().show(m_CMGridElements, c, r);		
	}
}
