package bi.view.utils;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicMenuItemUI;
import javax.swing.text.View;

public class CMBaseJMenuItemUI extends BasicMenuItemUI {
	

	private String acceleratorDelimiter = "+";
	 static Rectangle zeroRect = new Rectangle(0,0,0,0);
	    static Rectangle iconRect = new Rectangle();
	    static Rectangle textRect = new Rectangle();
	    static Rectangle acceleratorRect = new Rectangle();
	    static Rectangle checkIconRect = new Rectangle();
	    static Rectangle arrowIconRect = new Rectangle();
	    static Rectangle viewRect = new Rectangle(Short.MAX_VALUE, Short.MAX_VALUE);
	    static Rectangle r = new Rectangle();
	    static final String MAX_TEXT_WIDTH =  "maxTextWidth";
	    static final String MAX_ACC_WIDTH  =  "maxAccWidth";
	    static final String CONTROL_TEXT = "Ctrl";
	    static final String INSERT_TEXT = "Ins";
	    static final String DEL_TEXT = "Del";
	    static final String CONTROL_SHIFT_TEXT = "Ctrl+Shift";
	    
	public CMBaseJMenuItemUI() {
		super();
		// TODO Auto-generated constructor stub
	}
protected Dimension getPreferredMenuItemSize(JComponent c, Icon checkIcon, Icon arrowIcon, int defaultTextIconGap) {
	JMenuItem b = (JMenuItem) c;
    Icon icon = (Icon) b.getIcon(); 
    String text = b.getText();
    KeyStroke accelerator =  b.getAccelerator();
    String acceleratorText = "";

    if (accelerator != null) {
        int modifiers = accelerator.getModifiers();
        if (modifiers > 0) {
        	if (modifiers ==130)
        		acceleratorText = CONTROL_TEXT;
        	if (modifiers ==195)
        		acceleratorText = CONTROL_SHIFT_TEXT;
        	else
        		acceleratorText = KeyEvent.getKeyModifiersText(modifiers);

            acceleratorText += acceleratorDelimiter ;
      }
        int keyCode = accelerator.getKeyCode();
        if (keyCode != 0) {
        	if (keyCode == 155)
      		    acceleratorText += INSERT_TEXT;  
        	else
        	if (keyCode == (int)KeyEvent.VK_DELETE)
        		acceleratorText+= DEL_TEXT;
      	   else           		   
             acceleratorText += KeyEvent.getKeyText(keyCode);
        } else {
            acceleratorText += accelerator.getKeyChar();
        }
    }

    Font font = b.getFont();
    FontMetrics fm = b.getToolkit().getFontMetrics(font);
    FontMetrics fmAccel = b.getToolkit().getFontMetrics( acceleratorFont );

    resetRects();
    
    layoutMenuItem(
              fm, text, fmAccel, acceleratorText, icon, checkIcon, arrowIcon,
              b.getVerticalAlignment(), b.getHorizontalAlignment(),
              b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
              viewRect, iconRect, textRect, acceleratorRect, checkIconRect, arrowIconRect,
              text == null ? 0 : defaultTextIconGap,
              defaultTextIconGap
              );
    // find the union of the icon and text rects
    r.setBounds(textRect);
    r = SwingUtilities.computeUnion(iconRect.x,
                                    iconRect.y,
                                    iconRect.width,
                                    iconRect.height,
                                    r);
    //   r = iconRect.union(textRect);


// To make the accelerator texts appear in a column, find the widest MenuItem text
// and the widest accelerator text.

//Get the parent, which stores the information.
Container parent = menuItem.getParent();

//Check the parent, and see that it is not a top-level menu.
    if (parent != null && parent instanceof JComponent && 
    !(menuItem instanceof JMenu && ((JMenu) menuItem).isTopLevelMenu())) {
    JComponent p = (JComponent) parent;
    
    //Get widest text so far from parent, if no one exists null is returned.
    Integer maxTextWidth = (Integer) p.getClientProperty(MAX_TEXT_WIDTH);
    Integer maxAccWidth = (Integer) p.getClientProperty(MAX_ACC_WIDTH);
    
    int maxTextValue = maxTextWidth!=null ? maxTextWidth.intValue() : 0;
    int maxAccValue = maxAccWidth!=null ? maxAccWidth.intValue() : 0;
    
    //Compare the text widths, and adjust the r.width to the widest.
    if (r.width < maxTextValue) {
	r.width = maxTextValue;
    } else {
	p.putClientProperty(MAX_TEXT_WIDTH, new Integer(r.width) );
    }
    
  //Compare the accelarator widths.
    if (acceleratorRect.width > maxAccValue) {
	maxAccValue = acceleratorRect.width;
	p.putClientProperty(MAX_ACC_WIDTH, new Integer(acceleratorRect.width) );
    }
    
    //Add on the widest accelerator 
    r.width += maxAccValue;
    r.width += defaultTextIconGap;
    
}

if( useCheckAndArrow() ) {
    // Add in the checkIcon
    r.width += checkIconRect.width;
    r.width += defaultTextIconGap;

    // Add in the arrowIcon
    r.width += defaultTextIconGap;
    r.width += arrowIconRect.width;
    }	

r.width += 2*defaultTextIconGap;

    Insets insets = b.getInsets();
    if(insets != null) {
        r.width += insets.left + insets.right;
        r.height += insets.top + insets.bottom;
    }

    // if the width is even, bump it up one. This is critical
    // for the focus dash line to draw properly
    if(r.width%2 == 0) {
        r.width++;
    }

    // if the height is even, bump it up one. This is critical
    // for the text to center properly
    if(r.height%2 == 0) {
        r.height++;
    }

return r.getSize();
}

protected void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap) {
	// TODO Auto-generated method stub
	   JMenuItem b = (JMenuItem) c;
       ButtonModel model = b.getModel();

       //   Dimension size = b.getSize();
       int menuWidth = b.getWidth();
       int menuHeight = b.getHeight();
       Insets i = c.getInsets();
	
       resetRects();

       viewRect.setBounds( 0, 0, menuWidth, menuHeight );

       viewRect.x += i.left;
       viewRect.y += i.top;
       viewRect.width -= (i.right + viewRect.x);
       viewRect.height -= (i.bottom + viewRect.y);


       Font holdf = g.getFont();
       Font f = c.getFont();
       g.setFont( f );
       FontMetrics fm = g.getFontMetrics( f );
       FontMetrics fmAccel = g.getFontMetrics( acceleratorFont );

       // get Accelerator text
       KeyStroke accelerator =  b.getAccelerator();
       String acceleratorText = "";
       if (accelerator != null) {
    	  
           int modifiers = accelerator.getModifiers();
           if (modifiers > 0) {
        		if (modifiers == 130)
            		acceleratorText = CONTROL_TEXT;
        		if (modifiers ==195)
            		acceleratorText = CONTROL_SHIFT_TEXT;
            	else
            		acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
        		    
               //acceleratorText += "-";
               acceleratorText += acceleratorDelimiter;
	       }

           int keyCode = accelerator.getKeyCode();
           if (keyCode != 0) {
               //hmendez_15112005_begin        	   
        	   if (keyCode == 155)
        		 acceleratorText += INSERT_TEXT;
        	   else
        	   if (keyCode == (int)KeyEvent.VK_DELETE)
              		acceleratorText += DEL_TEXT;
        	   else
        		 
               //hmendez_15112005_end        		   
                 acceleratorText += KeyEvent.getKeyText(keyCode);
           } else {
               acceleratorText += accelerator.getKeyChar();
           }
       }
       
       // layout the text and icon
       String text = layoutMenuItem(
           fm, b.getText(), fmAccel, acceleratorText, b.getIcon(),
           checkIcon, arrowIcon,
           b.getVerticalAlignment(), b.getHorizontalAlignment(),
           b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
           viewRect, iconRect, textRect, acceleratorRect, 
           checkIconRect, arrowIconRect,
           b.getText() == null ? 0 : defaultTextIconGap,
           defaultTextIconGap
       );
         
       // Paint background
	paintBackground(g, b, background);

       Color holdc = g.getColor();

       // Paint the Check
       if (checkIcon != null) {
           if(model.isArmed() || (c instanceof JMenu && model.isSelected())) {
               g.setColor(foreground);
           } else {
               g.setColor(holdc);
           }
           if( useCheckAndArrow() )
		checkIcon.paintIcon(c, g, checkIconRect.x, checkIconRect.y);
           g.setColor(holdc);
       }

       // Paint the Icon
       if(b.getIcon() != null) { 
           Icon icon;
           if(!model.isEnabled()) {
               icon = (Icon) b.getDisabledIcon();
           } else if(model.isPressed() && model.isArmed()) {
               icon = (Icon) b.getPressedIcon();
               if(icon == null) {
                   // Use default icon
                   icon = (Icon) b.getIcon();
               } 
           } else {
               icon = (Icon) b.getIcon();
           }
             
           if (icon!=null)   
               icon.paintIcon(c, g, iconRect.x, iconRect.y);
       }

       // Draw the Text
       if(text != null) {
	    View v = (View) c.getClientProperty(BasicHTML.propertyKey);
	    if (v != null) {
		v.paint(g, textRect);
	    } else {
		paintText(g, b, textRect, text);
	    }
	}
	
       // Draw the Accelerator Text
       if(acceleratorText != null && !acceleratorText.equals("")) {

	  //Get the maxAccWidth from the parent to calculate the offset.
	  int accOffset = 0;
	  Container parent = menuItem.getParent();
	  if (parent != null && parent instanceof JComponent) {
	    JComponent p = (JComponent) parent;
	    Integer maxValueInt = (Integer) p.getClientProperty(MAX_ACC_WIDTH);
	    int maxValue = maxValueInt != null ?
               maxValueInt.intValue() : acceleratorRect.width;

	    //Calculate the offset, with which the accelerator texts will be drawn with.
	    accOffset = maxValue - acceleratorRect.width;
	  }
	  
	  g.setFont( acceleratorFont );
           if(!model.isEnabled()) {
               // *** paint the acceleratorText disabled
	      if ( disabledForeground != null )
		  {
                 g.setColor( disabledForeground );
                 BasicGraphicsUtils.drawString(g,acceleratorText,0,
                                               acceleratorRect.x - accOffset, 
                                               acceleratorRect.y + fmAccel.getAscent());
               }
               else
               {
                 g.setColor(b.getBackground().brighter());
                 BasicGraphicsUtils.drawString(g,acceleratorText,0,
                                               acceleratorRect.x - accOffset, 
						acceleratorRect.y + fmAccel.getAscent());
                 g.setColor(b.getBackground().darker());
                 BasicGraphicsUtils.drawString(g,acceleratorText,0,
                                               acceleratorRect.x - accOffset - 1, 
						acceleratorRect.y + fmAccel.getAscent() - 1);
               }
           } else {
               // *** paint the acceleratorText normally
               if (model.isArmed()|| (c instanceof JMenu && model.isSelected())) {
                   g.setColor( acceleratorSelectionForeground );
               } else {
                   g.setColor( acceleratorForeground );
               }
               BasicGraphicsUtils.drawString(g,acceleratorText, 0,
                                             acceleratorRect.x - accOffset,
                                             acceleratorRect.y + fmAccel.getAscent());
           }
       }

       // Paint the Arrow
       if (arrowIcon != null) {
           if(model.isArmed() || (c instanceof JMenu &&model.isSelected()))
               g.setColor(foreground);
           if(useCheckAndArrow())
               arrowIcon.paintIcon(c, g, arrowIconRect.x, arrowIconRect.y);
       }
       g.setColor(holdc);
       g.setFont(holdf);
   }


private void resetRects() {
    iconRect.setBounds(zeroRect);
    textRect.setBounds(zeroRect);
    acceleratorRect.setBounds(zeroRect);
    checkIconRect.setBounds(zeroRect);
    arrowIconRect.setBounds(zeroRect);
    viewRect.setBounds(0,0,Short.MAX_VALUE, Short.MAX_VALUE);
    r.setBounds(zeroRect);
}
private String layoutMenuItem(
        FontMetrics fm,
        String text,
        FontMetrics fmAccel,
        String acceleratorText,
        Icon icon,
        Icon checkIcon,
        Icon arrowIcon,
        int verticalAlignment,
        int horizontalAlignment,
        int verticalTextPosition,
        int horizontalTextPosition,
        Rectangle viewRect, 
        Rectangle iconRect, 
        Rectangle textRect,
        Rectangle acceleratorRect,
        Rectangle checkIconRect, 
        Rectangle arrowIconRect, 
        int textIconGap,
        int menuItemGap
        )
    {

        SwingUtilities.layoutCompoundLabel(
                            menuItem, fm, text, icon, verticalAlignment, 
                            horizontalAlignment, verticalTextPosition, 
                            horizontalTextPosition, viewRect, iconRect, textRect, 
                            textIconGap);

        /* Initialize the acceelratorText bounds rectangle textRect.  If a null 
         * or and empty String was specified we substitute "" here 
         * and use 0,0,0,0 for acceleratorTextRect.
         */
        if( (acceleratorText == null) || acceleratorText.equals("") ) {
            acceleratorRect.width = acceleratorRect.height = 0;
            acceleratorText = "";
        }
        else {
            acceleratorRect.width = SwingUtilities.computeStringWidth( fmAccel, acceleratorText );
            acceleratorRect.height = fmAccel.getHeight();
        }

        /* Initialize the checkIcon bounds rectangle's width & height.
         */

	if( useCheckAndArrow()) {
	    if (checkIcon != null) {
		checkIconRect.width = checkIcon.getIconWidth();
		checkIconRect.height = checkIcon.getIconHeight();
	    } 
	    else {
		checkIconRect.width = checkIconRect.height = 0;
	    }
	    
	    /* Initialize the arrowIcon bounds rectangle width & height.
	     */
	    
	    if (arrowIcon != null) {
		arrowIconRect.width = arrowIcon.getIconWidth();
		arrowIconRect.height = arrowIcon.getIconHeight();
	    } else {
		arrowIconRect.width = arrowIconRect.height = 0;
	    }
        }

        Rectangle labelRect = iconRect.union(textRect);
        if(menuItem.getComponentOrientation().isLeftToRight())
        	//BasicGraphicsUtils.isLeftToRight(menuItem) ) {
        {
        	textRect.x += menuItemGap;
            iconRect.x += menuItemGap;

            // Position the Accelerator text rect
            acceleratorRect.x = viewRect.x + viewRect.width - arrowIconRect.width 
                             - menuItemGap - acceleratorRect.width;
            
            // Position the Check and Arrow Icons 
            if (useCheckAndArrow()) {
                checkIconRect.x = viewRect.x + menuItemGap;
                textRect.x += menuItemGap + checkIconRect.width;
                iconRect.x += menuItemGap + checkIconRect.width;
                arrowIconRect.x = viewRect.x + viewRect.width - menuItemGap
                                  - arrowIconRect.width;
            }
        } else {
            textRect.x -= menuItemGap;
            iconRect.x -= menuItemGap;

            // Position the Accelerator text rect
            acceleratorRect.x = viewRect.x + arrowIconRect.width + menuItemGap;

            // Position the Check and Arrow Icons 
            if (useCheckAndArrow()) {
                checkIconRect.x = viewRect.x + viewRect.width - menuItemGap
                                  - checkIconRect.width;
                textRect.x -= menuItemGap + checkIconRect.width;
                iconRect.x -= menuItemGap + checkIconRect.width;      
                arrowIconRect.x = viewRect.x + menuItemGap;
            }
        }

        // Align the accelertor text and the check and arrow icons vertically
        // with the center of the label rect.  
        acceleratorRect.y = labelRect.y + (labelRect.height/2) - (acceleratorRect.height/2);
        if( useCheckAndArrow() ) {
            arrowIconRect.y = labelRect.y + (labelRect.height/2) - (arrowIconRect.height/2);
            checkIconRect.y = labelRect.y + (labelRect.height/2) - (checkIconRect.height/2);
        }

  
        
        return text;
    }
private boolean useCheckAndArrow(){
	boolean b = true;
	if((menuItem instanceof JMenu) &&
	   (((JMenu)menuItem).isTopLevelMenu())) {
	    b = false;
	}
	return b;
    }
   
}
