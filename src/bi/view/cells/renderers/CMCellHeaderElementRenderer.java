package bi.view.cells.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import bi.view.cells.CMCellElement;
import bi.view.utils.CMDnDJSmartGrid;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;

public class CMCellHeaderElementRenderer extends DefaultGridCellRenderer{

	boolean isTargetCell = false;
	private boolean isSourceCell = false;
	public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
		Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
		if (context.getComponent() instanceof CMDnDJSmartGrid)
		{
			Object dropppedValue = ((CMDnDJSmartGrid)context.getComponent()).getDropTargetCell();
			int  draggedRow = ((CMDnDJSmartGrid)context.getComponent()).getDraggedRow();
			int  draggedCol = ((CMDnDJSmartGrid)context.getComponent()).getDraggedCol();
			Object draggedValue = null;
			if (draggedCol!=-1 && draggedRow!=-1)
				draggedValue = ((CMDnDJSmartGrid)context.getComponent()).getValueAt(draggedRow,draggedCol);

			if ((value instanceof CMCellElement))
			{
				if (dropppedValue instanceof CMCellElement){
			    isTargetCell = ((CMCellElement)value).getElement() == ((CMCellElement)dropppedValue).getElement();
				}
				else
					isTargetCell = false;
				if (draggedValue instanceof CMCellElement)
				isSourceCell  = 	((CMCellElement)value).getElement() == ((CMCellElement)draggedValue).getElement();
				else
					isSourceCell = false;
			}


		}
		if (isTargetCell)
		{
			setBackground(Color.GRAY);
	        setForeground(Color.WHITE);
	        setFont(new Font("Dialog",Font.BOLD,13));
	        setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
	        setHorizontalAlignment(JLabel.LEFT);
	        return c;
		}
		if (isSourceCell)
		{
			setBackground(new Color(36,38,116));
	        setForeground(Color.WHITE);
	        setFont(new Font("Dialog",Font.BOLD,13));
	        setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
	        setHorizontalAlignment(JLabel.LEFT);
	        return c;
		}
		setBackground(new Color(36,38,116));
		setForeground(new Color(252,254,252));
        setFont(new Font("Dialog",Font.PLAIN,12));
		setHorizontalAlignment(JLabel.LEFT);
		 //$NON-NLS-1$

        if( !isSelected)

        		setBorder(BorderFactory.createRaisedBevelBorder());


//
   //      context.getComponent().repaint();
        return c;
	}

}
