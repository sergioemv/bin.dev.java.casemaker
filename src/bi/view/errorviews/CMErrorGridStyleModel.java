package bi.view.errorviews;
import com.eliad.model.defaults.DefaultStyleModel;
import com.eliad.model.defaults.DefaultGridCellRenderer;
import com.eliad.model.GridContext;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Color;

  /////////////////////////////Style Model /////////////////////////////////////

  public class CMErrorGridStyleModel extends DefaultStyleModel {
    public CMErrorGridStyleModel() {
      this.setRenderer(String.class, new CMCellRendererDependencies());
    }

    public class CMCellRendererDependencies extends DefaultGridCellRenderer {
      public CMCellRendererDependencies() {
      }

      public Component getComponent(Object value, boolean isSelected, boolean hasFocus, boolean isEditable, int row, int column, GridContext context) {
            Component c = super.getComponent(value, isSelected, hasFocus, isEditable, row, column, context);
              this.setHorizontalAlignment(JLabel.LEFT);
              if( !isSelected) {
                  this.setBackground(new Color(235,235,228));
              }
              return c;
          }
    }
  }
