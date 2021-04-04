/**
 * 
 */
package bi.view.cells.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;

import com.eliad.model.GridContext;
import com.eliad.model.defaults.DefaultGridCellRenderer;

public class CMCellDependencyRenderer extends DefaultGridCellRenderer {
  public CMCellDependencyRenderer() {
  }

  public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
        Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
          this.setHorizontalAlignment(JLabel.LEFT);
          //c.setBackground(new Color(235, 235, 228));
          //this.setBackground(new Color(235,235,228));
          if( !isSelected) {
            this.setBackground(new Color(235,235,228));
          }
          return c;
      }
}