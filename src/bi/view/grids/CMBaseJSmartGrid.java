package bi.view.grids;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import bi.view.cells.CMBaseCell;
import bi.view.cells.editors.CMBaseGridCellEditor;
import bi.view.cells.editors.CMGridCellEditorFactory;
import bi.view.cells.renderers.CMCellRendererFactory;
import bi.view.elementviews.CMElementViews;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.utils.CMBaseJComboBox;
import com.eliad.model.GenericGridModel;
import com.eliad.model.defaults.DefaultGridCellEditor;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.swing.GridEditingEvent;
import com.eliad.swing.GridEditingListener;
import com.eliad.swing.JSmartGrid;

public abstract class CMBaseJSmartGrid extends JSmartGrid {
	private Object previousEditor;
	private FocusListener componentFocusListener = new FocusAdapter()
	{
		public void focusGained(FocusEvent e) {
			//when a grid gains focus check if the focus is visible
			//if not select the default cell
			if (e.getSource() instanceof JSmartGrid)
			{
			int l_firstSelectedRow =((CMBaseJSmartGrid)e.getSource()).getFirstSelectedRow();
        	int l_firstSelectedColumn = ((CMBaseJSmartGrid)e.getSource()).getFirstSelectedColumn();

        	//provisory solution of focusLost in gridTDStructure, it affects only the combos in the grid
        	//it should be replaced with an editor for each Cell svonborries_15092006
        	if(e.getSource() instanceof CMGridTDStructure){
        		int column = ((CMGridTDStructure)e.getSource()).getColumnSelected();
        		int row = ((CMGridTDStructure)e.getSource()).getRowSelected();
        		((CMGridTDStructure)e.getSource()).changeSelection(row, column, false, false);
        		repaint();
        		return;
        	}

        	if ((l_firstSelectedRow==((CMBaseJSmartGrid)e.getSource()).getRowCount())||
        			((l_firstSelectedColumn==((CMBaseJSmartGrid)e.getSource()).getColumnCount())))
        			{
        				((CMBaseJSmartGrid)e.getSource()).changeSelection(getDefaultRow(),getDefaultColumn(),false,false);
        			}
        	else
        	{
        		//some grids does not show the current focused cell right
        		if (e.getSource() instanceof CMElementViews)
        			((CMBaseJSmartGrid)e.getSource()).changeSelection(l_firstSelectedRow,l_firstSelectedColumn,false,false);
			}

			}
			repaint();
		}
		public void focusLost(FocusEvent e) {
			Component cellEditor = null;
			if(getCurrentCellEditor()!=null)
			{
				cellEditor = ((DefaultGridCellEditor)getCurrentCellEditor()).getComponent();
			}

			if ((e.getSource() instanceof JTextField)||
			(e.getSource() instanceof CMBaseJComboBox))
			{
				stopCellEditing();
				if (e.getOppositeComponent()!=null)
					e.getOppositeComponent().requestFocus();
				   repaint();
				return;
			}
			if (e.getSource() instanceof JSmartGrid)
				if (e.getOppositeComponent() != cellEditor)
				{

					stopCellEditing();
//					System.out.println(e.getOppositeComponent());
//					if (e.getOppositeComponent()!=null)
//						e.getOppositeComponent().requestFocus();
//					   repaint();
					return;
				}

		   repaint();

		}

	};
	protected CMGridNavigationPolicy defaultGridNav=new CMDefaultGridNavigationPolicy(this);

	@SuppressWarnings("serial")
	private Action nextNavAction = new AbstractAction("next navigation"){

	public void actionPerformed(ActionEvent arg0) {
		int row=getLastSelectedRow();//getSelectionModel().getLeadRow();//this.getFirstSelectedRow();
    	int column=getLastSelectedColumn();//getSelectionModel().getLeadColumn();//this.getFirstSelectedColumn();
    	stopCellEditing();
    	getDefaultGridNav().goToNextCell(row,column);

	}};
	@SuppressWarnings("serial")
	private AbstractAction nullAction = new AbstractAction("null action")
	{

		public void actionPerformed(ActionEvent e) {
			// nada

		}};
	@SuppressWarnings("unused")
	private ActionListener defaultDownAction;

		public CMBaseJSmartGrid() {


	    this.getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
		this.getActionMap().put(nullAction .getValue(Action.NAME), nullAction);
		this.getActionMap().put(nextNavAction.getValue(Action.NAME), nextNavAction);
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
		        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), nextNavAction.getValue(Action.NAME));
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
		        KeyStroke.getKeyStroke('	',0), nextFocusAction.getValue(Action.NAME));
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
		        KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK|InputEvent.CTRL_DOWN_MASK), nullAction .getValue(Action.NAME));
		//this.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,KeyEvent.CTRL_MASK|InputEvent.CTRL_DOWN_MASK),nullAction .getValue(Action.NAME));

 //		hmendez_14102005_begin
     	//KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(new FocusChangeListener());
    	this.addGridEditingListener(gridEditingListener);
		this.addKeyListener(keyListener);
		this.addFocusListener(componentFocusListener);
		this.setGridNavigationPolicy(defaultGridNav);



	}


	/**
	 * @author smoreno
	 * 	default initializacion of the cell renderers
	 */
	protected void initializeCellRenderers()
	{
		if (getCellClasses()==null) return;
		for (Iterator i = getCellClasses().keySet().iterator();i.hasNext();)
		{
			Class cell = (Class)i.next();
			((DefaultStyleModel)this.getStyleModel()).setRenderer(cell,CMCellRendererFactory.INSTANCE.createRenderer(cell));
		}


	}
	/**
	 * @author smoreno
	 *  default initialization of the cell editors
	 */
	protected void initializeCellEditors()
	{
		if (getCellClasses() == null) return;
		for (Iterator i = getCellClasses().keySet().iterator();i.hasNext();)
		{
			Class cell = (Class)i.next();
			((DefaultStyleModel)this.getStyleModel()).setEditor(cell,CMGridCellEditorFactory.INSTANCE.createEditor(cell,(JComponent)getCellClasses().get(cell)));
		}
	}
	//
	/**
	 * @author smoreno
	 * get the list of cell classes that are included in the grid
	 * override this method to do the initialization of the renderers and the editors automatilcally
	 * @return
	 */
	protected abstract HashMap<Class,Component> getCellClasses() ;



//hmendez_14102005_end
@SuppressWarnings("serial")
private Action nextFocusAction = new AbstractAction("Move Focus Forwards") {
	        public void actionPerformed(ActionEvent evt) {
	        	((Component)evt.getSource()).transferFocus();

	        }
	    };






 public Object getCellObjectAt(int row, int column) {
	                if(this.getRowCount() > row && this.getColumnCount() > column
	                   && row >= 0 && column >= 0) {
	                  return this.getModel().getValueAt(row,column);
	                }
	                else {
	                  return null;
	                }
	              }


//hmendez_14102005_end
public void stopCellEditing() {
	    	if( isEditing() ) {
	    		  if (this.getCurrentCellEditor() != null) {
	    		    previousEditor = ((DefaultGridCellEditor)getCurrentCellEditor()).getComponent();
	    		    if (((DefaultGridCellEditor)getCurrentCellEditor()).getComponent() instanceof CMBaseJComboBox)
	    		    {
	    		    	((CMBaseJComboBox)((DefaultGridCellEditor)getCurrentCellEditor()).getComponent()).setRetainFocus(false);
	    		    }
	    		    this.getCurrentCellEditor().stopCellEditing();
	    	      }
	    	    }
	    }
 private KeyListener keyListener =new KeyAdapter()
		{
			@SuppressWarnings("static-access")
			public void keyPressed(KeyEvent e) {
				//to save the actions of the shocuts and the Al gr key

				if ((e.isAltDown())&&(((e.getKeyCode()>=e.VK_A)&&(e.getKeyCode()<=e.VK_Z)))){
					stopCellEditing();
					//return;
				}

				if (!(e.getSource() instanceof JTextField))
					if ((e.isControlDown())&&(((e.getKeyCode()>=e.VK_A)&&(e.getKeyCode()<=e.VK_Z)))){
						stopCellEditing();
					}
					if ((e.getSource() instanceof JTextField))
						if ((e.isControlDown())&&!(((e.getKeyCode()==e.VK_C)||(e.getKeyCode()<=e.VK_V)||(e.getKeyCode()<=e.VK_X)))){
							stopCellEditing();
						}
				Robot robot=null;
				try {
					robot = new Robot();
					if (e.getKeyCode() == KeyEvent.VK_DELETE)
					{

						if (!(e.getSource() instanceof JTextField))
							if (isEditing())
							{
								stopCellEditing();
								e.consume();
								robot.keyPress(KeyEvent.VK_DELETE);
							}


				    }
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}



		};

private GridEditingListener  gridEditingListener = new GridEditingListener(){



			private CellEditorListener editorListener = new CellEditorListener(){

				public void editingCanceled(ChangeEvent e) {
					 previousEditor = ((DefaultGridCellEditor)getCurrentCellEditor()).getComponent();
					Component text = ((DefaultGridCellEditor)getCurrentCellEditor()).getComponent();
				    if (text instanceof CMBaseJComboBox)
				    	((CMBaseJComboBox)text).setRetainFocus(false);
				}

				public void editingStopped(ChangeEvent e) {

				}

			};

			public void editingStarted(GridEditingEvent e) {
				   if( !isEnabled() )  return;
			      int row = e.getRow();
			      int column = e.getColumn();
			      Object editingObject= getCellObjectAt(row,column);
			      //set the correct cell for the cell editor
			      if( editingObject instanceof CMBaseCell) {
			          ((CMBaseGridCellEditor) getCurrentCellEditor()).setCell((CMBaseCell)getCellObjectAt(row,column));

			        	  if ((((CMBaseGridCellEditor) getCurrentCellEditor()).getComponent() instanceof JTextField)
					        	  && !(previousEditor instanceof CMBaseJComboBox))
					          {
					        	  //if is a text field select all the text
					        	  ((JTextField)((CMBaseGridCellEditor) getCurrentCellEditor()).getComponent()).selectAll();
					          }
			        	  previousEditor = ((CMBaseGridCellEditor) getCurrentCellEditor()).getComponent() ;



			       }

			      //focus stuff
				Component text=null;
				if (getCurrentCellEditor()!=null)
				{
					text = ((DefaultGridCellEditor)getCurrentCellEditor()).getComponent();
					List keyListeners = Arrays.asList((Object[])text.getKeyListeners());
					if (!keyListeners.contains(keyListener))
						text.addKeyListener(keyListener);
					List focusListeners = Arrays.asList((Object[])text.getFocusListeners());
					if (!focusListeners.contains(componentFocusListener))
						text.addFocusListener(componentFocusListener);
				    getCurrentCellEditor().addCellEditorListener(editorListener );

				    if (text instanceof CMBaseJComboBox)
				    {
				    	((CMBaseJComboBox)text).setRetainFocus(true);
				    	//text.addMouseListener(componentMouseListener);
				    }
				}
				}

			public void editingStopped(GridEditingEvent arg0) {

			}

			public void editingCanceled(GridEditingEvent arg0) {

			}};
public boolean isDeletePossible()
	{
				return (!isEditing() && (getCurrentCellEditor()==null));
	}

public void setValueAt(Object obj,int row, int column) {
    if(this.getRowCount() > row && this.getColumnCount() > column
       && row >= 0 && column >= 0 && obj != null) {
      super.setValueAt(obj,row,column);
    }
  }


		public CMGridNavigationPolicy getDefaultGridNav() {
			return defaultGridNav;
		}


		public void setGridNavigationPolicy(CMGridNavigationPolicy defaultGridNav) {
			this.defaultGridNav = defaultGridNav;
		}


		public boolean isCellEditable(int row, int column){
			return (((DefaultStyleModel)this.getStyleModel()).
					getEditor(((GenericGridModel)this.getModel()).getValueAt(row,column).getClass())!=null);
			}

		/**
		 *  gets the default row when the grid gains focus
		 * @return
		 */
		public int getDefaultRow()
		{	return 0;}
		/**
		 * gets the default column when the grid gains focus
		 * @return
		 */
		public int getDefaultColumn()
		{	return 0;}

		/**
		 * @author svonborries
		 *
		 */
		public void gainFocusWhenAintRowLeft(){
			if(getRowCount() == 0){
				getColumnHeader().requestFocus();
			}
		}

		/* (non-Javadoc)
		 * @see com.eliad.swing.JSmartGrid#editCellAt(int, int)
		 * method overriden because cause an exception in non-editable grids when the grid ain't elements
		 */
		@Override
		public boolean editCellAt(int arg0, int arg1) {
			//System.out.println("atributo1= " +arg0+ " atributo2= " + arg1);
			if(arg0 >=1 || arg1 >=1){
				return super.editCellAt(arg0, arg1);
			}
			return false;
		}
		public void selectCell(Object p_Model, Class type) {

			// search the model to find the cell with the model and the value
			for (int col = 0; col<getModel().getColumnCount();col++)
				for (int row = 0; row<getModel().getRowCount();row++)
					if (getValueAt(row,col) instanceof CMBaseCell)
						if (((CMBaseCell)getValueAt(row,col)).getModel().equals(p_Model))
							if (type==null) {
										changeSelection(row,col,false,false);
										return;
							}				
								else
									if (getValueAt(row,col).getClass().equals(type))
									{
										changeSelection(row,col,false,false);
										return;
									}
						}


		}
	
