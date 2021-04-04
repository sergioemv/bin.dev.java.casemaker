/**
 * 12/02/2007
 * svonborries
 */
package bi.controller.testdata.format;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import model.BusinessRules;
import bi.view.lang.CMMessages;
import bi.view.utils.CMFormatFactory;
import bi.view.utils.testdata.CMPanelAssignFormat;

/**
 * @author svonborries
 *
 */
public class CMAssignFormatEditController {
	
	private CMPanelAssignFormat panelAssignFormat;
	private String pattern;
	private String formatResult;
	private String decimalSeparator;
	private String ejem;

	public CMAssignFormatEditController(){
		
	}
	/**
	 * @return the panelAssignFormat
	 * 12/02/2007
	 * svonborries
	 */
	public CMPanelAssignFormat getPanelAssignFormat() {
		if(panelAssignFormat == null){
			panelAssignFormat = new CMPanelAssignFormat();
			panelAssignFormat.getJListCategory().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					jListCategoryActionPerformed();
				}
			});
			
			panelAssignFormat.getJListFormat().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					jListFormatActionPerformed();
				}
			});
			
			panelAssignFormat.getJSpinnerDecimal().addChangeListener(
		            new ChangeListener() {
		                public void stateChanged(ChangeEvent e) { jSpinnerDecimalChangeEvent(); }
		            });
			
			panelAssignFormat.getJComboBoxSymbol().addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					jComboBoxSymbolActionPerformed();
				}
			});
			panelAssignFormat.getJListCategory().setListData(BusinessRules.TESTDATA_FORMAT_CATEGORY);
			panelAssignFormat.getJListCategory().setSelectedIndex(0);
		}
		return panelAssignFormat;
	}
	

	private void jListCategoryActionPerformed() {
		int index = panelAssignFormat.getJListCategory().getSelectedIndex();
		switch (index) {
		case 0: 
			panelAssignFormat.getJPanelFormat().setVisible(true);
			panelAssignFormat.getJListFormat().setVisible(true);
			panelAssignFormat.getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_DATE);
			panelAssignFormat.getJComboBoxSymbol().setVisible(false);
			panelAssignFormat.getJLabelSymbol().setVisible(false);
			panelAssignFormat.getJLabelDecimal().setVisible(false);
			panelAssignFormat.getJSpinnerDecimal().setVisible(false);
			panelAssignFormat.getJTextFieldExample().setVisible(true);
			panelAssignFormat.getJTextAreaDescription().setText(CMMessages.getString("TESTDATA_DATE_AND_TIME_FORMAT_DESCRIPTION"));
			panelAssignFormat.getJListFormat().setSelectedIndex(0);
			break;
		case 1: 
			panelAssignFormat.getJPanelFormat().setVisible(true);
			panelAssignFormat.getJListFormat().setVisible(true);
			panelAssignFormat.getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNE);
			panelAssignFormat.getJComboBoxSymbol().setVisible(true);
			panelAssignFormat.getJLabelSymbol().setVisible(true);
			panelAssignFormat.getJLabelDecimal().setVisible(true);
			panelAssignFormat.getJSpinnerDecimal().setVisible(true);
			panelAssignFormat.getJTextFieldExample().setVisible(true);
			panelAssignFormat.getJTextAreaDescription().setText(CMMessages.getString("TESTDATA_MONEY_FORMAT_DESCRIPTION"));
			panelAssignFormat.getJListFormat().setSelectedIndex(0);
			break;
		case 2:
			panelAssignFormat.getJPanelFormat().setVisible(true);
			panelAssignFormat.getJListFormat().setVisible(true);
			panelAssignFormat.getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_NUMERIC);
			panelAssignFormat.getJComboBoxSymbol().setVisible(false);
			panelAssignFormat.getJLabelSymbol().setVisible(false);
			panelAssignFormat.getJLabelDecimal().setVisible(true);
			panelAssignFormat.getJSpinnerDecimal().setVisible(true);
			panelAssignFormat.getJTextFieldExample().setVisible(true);
			panelAssignFormat.getJTextAreaDescription().setText(CMMessages.getString("TESTDATA_NUMERIC_FORMAT_DESCRIPTION"));
			panelAssignFormat.getJListFormat().setSelectedIndex(0);
			break;
		case 3:
			panelAssignFormat.getJPanelFormat().setVisible(true);
			panelAssignFormat.getJListFormat().setVisible(true);
			panelAssignFormat.getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_PERCENTAGE);
			panelAssignFormat.getJComboBoxSymbol().setVisible(false);
			panelAssignFormat.getJLabelSymbol().setVisible(false);
			panelAssignFormat.getJLabelDecimal().setVisible(true);
			panelAssignFormat.getJSpinnerDecimal().setVisible(true);
			panelAssignFormat.getJTextFieldExample().setVisible(true);
			panelAssignFormat.getJTextAreaDescription().setText(CMMessages.getString("TESTDATA_PERCENTAGE_FORMAT_DESCRIPTION"));
			panelAssignFormat.getJListFormat().setSelectedIndex(0);
			break;	
		case 4:
			panelAssignFormat.getJPanelFormat().setVisible(false);
			panelAssignFormat.getJListFormat().setVisible(false);
			//panelAssignFormat.getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_PERCENTAGE);
			panelAssignFormat.getJComboBoxSymbol().setVisible(false);
			panelAssignFormat.getJLabelSymbol().setVisible(false);
			panelAssignFormat.getJLabelDecimal().setVisible(false);
			panelAssignFormat.getJSpinnerDecimal().setVisible(false);
			panelAssignFormat.getJTextFieldExample().setVisible(false);
			panelAssignFormat.getJTextAreaDescription().setText(CMMessages.getString("TESTDATA_TEXT_FORMAT_DESCRIPTION"));
			//panelAssignFormat.getJListFormat().setSelectedIndex(0);
			formatResult = "Text";
			break;
		default:
			break;
		}
	}
	
	private void jSpinnerDecimalChangeEvent() {
		SpinnerModel numberModel = getPanelAssignFormat().getJSpinnerDecimal().getModel();
        if (numberModel instanceof SpinnerNumberModel) {
            if (getPanelAssignFormat().getJListCategory().getSelectedIndex() == 3)
                createExamplePercent(getPanelAssignFormat().getJSpinnerDecimal().getValue().toString());
            if (getPanelAssignFormat().getJListCategory().getSelectedIndex() == 2)
                createExampleNum(getPanelAssignFormat().getJSpinnerDecimal().getValue().toString());
            if (getPanelAssignFormat().getJListCategory().getSelectedIndex() == 1)
                createExampleMoney(getPanelAssignFormat().getJSpinnerDecimal().getValue().toString());
        }
	}
	

	private void jListFormatActionPerformed() {
		int index = getPanelAssignFormat().getJListCategory().getSelectedIndex();
        if (getPanelAssignFormat().getJListFormat().getSelectedIndex() > -1)
            if (index == 0) {
            	getPanelAssignFormat().getJTextFieldExample().setText(BusinessRules.FORMULAS_FORMAT_DATE_EXAMPLE
            			[getPanelAssignFormat().getJListFormat().getSelectedIndex()]);
                formatResult = getPanelAssignFormat().getJListFormat().getSelectedValue().toString();
            }
            else {
                if (index == 1) {
                   // int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,jListFormat.getSelectedValue().toString());
        			if(isVisualFormatter(getPanelAssignFormat().getJListFormat().getSelectedValue().toString()))
        			{
            			decimalSeparator=",";
        			}
        			else{
            			decimalSeparator=".";
        			}
        			getPanelAssignFormat().getJSpinnerDecimal().setValue(calculeDecimals());
                    ejem = reemplaceX(getPanelAssignFormat().getJListFormat().getSelectedValue().toString());
                    getPanelAssignFormat().getJTextFieldExample().setText(ejem);
                    formatResult = getPanelAssignFormat().getJListFormat().getSelectedValue().toString();
                }
                else {
                    if (index == 2) {
                       // int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,jListFormat.getSelectedValue().toString());
        				if(isVisualFormatter(getPanelAssignFormat().getJListFormat().getSelectedValue().toString()))
        				{
            				decimalSeparator=",";
        				}
        				else{
            				decimalSeparator=".";
        				}
        				getPanelAssignFormat().getJSpinnerDecimal().setValue(calculeDecimals());
                        ejem = reemplaceX(getPanelAssignFormat().getJListFormat().getSelectedValue().toString());
                        getPanelAssignFormat().getJTextFieldExample().setText(ejem);
                        formatResult = getPanelAssignFormat().getJListFormat().getSelectedValue().toString();
                    }
                    else {
                        if (index == 3) {
                           // int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,jListFormat.getSelectedValue().toString());
                        	if(isVisualFormatter(getPanelAssignFormat().getJListFormat().getSelectedValue().toString()))
            				{
                				decimalSeparator=",";
            				}
            				else{
                				decimalSeparator=".";
            				}
                        	getPanelAssignFormat().getJSpinnerDecimal().setValue(calculeDecimals());
                            //ejem = reemplaceX(jListFormat.getSelectedValue().toString());
                        	getPanelAssignFormat().getJTextFieldExample().setText(getPanelAssignFormat().getJListFormat().getSelectedValue().toString());
                            formatResult = getPanelAssignFormat().getJListFormat().getSelectedValue().toString();
                        }
                    }
                }
            }
	}


	/**
	 * @return the pattern
	 * 13/02/2007
	 * svonborries
	 */
	public String getPattern() {
		if(pattern == null){
			pattern = "";
		}
		return pattern;
	}


	/**
	 * @param pattern the pattern to set
	 * 13/02/2007
	 * svonborries
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	@SuppressWarnings("unchecked")
	private  boolean isVisualFormatter(String pattern)
    {
     	if(pattern.indexOf("%")>0 && pattern.indexOf(",")>0){
    		return true;
    	}
    	else if(pattern.indexOf("%")>0&& pattern.indexOf(",") < 0){
    		return false;
    	}
    	
        int indexVisualPattern=Arrays.binarySearch(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS,pattern);
        if(indexVisualPattern>=0)
        {
            return true;
        }
        else
        {
            Vector visualFormaters= new Vector(Arrays.asList(BusinessRules.TESTDATA_FORMAT_VISUAL_FORMATTERS));
			boolean result=false;
            for(int i=0; i< visualFormaters.size();i++)
            {
                String formatvisual=visualFormaters.elementAt(i).toString();

                if(!result &&(pattern.startsWith(formatvisual)|| formatvisual.startsWith(pattern))){
                    result=true;//return true;
                }
                else{
                    if(!result)
                    result= ismodificVisualFormula(formatvisual,pattern);
                }
            }

            return result;
        }
    }
	
	private  boolean ismodificVisualFormula(String visual, String pattern)
    {
        try{
        int lengthVisual= visual.length();
        Logger.getLogger(this.getClass()).debug(lengthVisual);
        int lengthPattern=pattern.length();
        Logger.getLogger(this.getClass()).debug(lengthPattern);
		int indexVisual=0;
        int indexPattern=0;
        char ccVisual= visual.charAt(0);
        char ccPattern=pattern.charAt(0);
        boolean resultFinal=false;
        while(!resultFinal){
        	Logger.getLogger(this.getClass()).debug(indexVisual);
        	Logger.getLogger(this.getClass()).debug(indexPattern);
			if(ccVisual== ccPattern){

                if(indexVisual<lengthVisual)
             			ccVisual= visual.charAt(indexVisual);
                if(indexPattern<lengthPattern)
             			ccPattern=pattern.charAt(indexPattern);
                indexVisual++;
        	    indexPattern++;
            }
            else{
                if(/*ccVisual=='.'||*/ ccVisual==','|| ccVisual=='#')
                {

                	if(indexVisual<lengthVisual)
             			ccVisual= visual.charAt(indexVisual);
                	indexVisual++;
                }
                else
                if(/*ccPattern=='.'|| ccPattern==','||*/ ccPattern=='#')
                {

          		      if(indexPattern<lengthPattern)
             			ccPattern=pattern.charAt(indexPattern);
          			indexPattern++;
                }
                else
                    return false;
            }
            if(indexVisual==lengthVisual && indexPattern==lengthPattern)
                return true;
            else
                if(indexVisual>lengthVisual || indexPattern>lengthPattern)
                	return false;
        }

        return ccPattern==ccVisual;
        }
        catch(Exception ex)
        {
            return false;
        }
    }

	public Object calculeDecimals() {
        String cantDecimals = getPanelAssignFormat().getJListFormat().getSelectedValue().toString().trim();
        cantDecimals = CMFormatFactory.clearMoneyPercentSign(cantDecimals).trim();
        if (cantDecimals == null)
            return new Integer(0);
        else {
            if (cantDecimals.indexOf(decimalSeparator) == -1)
                return new Integer(0);
            else {
                return new Integer(cantDecimals.length() - cantDecimals.indexOf(decimalSeparator) - 1);
            }
        }
    }
	
	public String reemplaceX(String p_ej) {
        String newstring = new String(p_ej);
        newstring = newstring.replace('#', '1');
        return newstring;
    }
	
	public void createExamplePercent(String cant) {
    	String entero = ((String)getPanelAssignFormat().getJListFormat().getSelectedValue());
        if (entero.indexOf(decimalSeparator) != -1)
            entero = entero.substring(0, entero.indexOf(decimalSeparator));
        int cantidad = Integer.parseInt(cant);
        StringBuffer example = new StringBuffer(entero); //$NON-NLS-1$
        if (cantidad > 0)
            example.append(decimalSeparator);
        for (int i = 0; i < cantidad; i++)
            example.append("#"); //$NON-NLS-1$
        example.append("%"); //$NON-NLS-1$
        formatResult = example.toString();
        getPanelAssignFormat().getJTextFieldExample().setText(example.toString());
    }
	
	 public void createExampleNum(String cant) {
	        String entero = ((String)getPanelAssignFormat().getJListFormat().getSelectedValue());
	        if (entero == null) {
	            return;
	        }
	        if (entero.indexOf(decimalSeparator) != -1)
	            entero = entero.substring(0, entero.indexOf(decimalSeparator));
	        int cantidad = Integer.parseInt(cant);
	        StringBuffer example = new StringBuffer(entero);
	        if (cantidad > 0)
	            example.append(decimalSeparator);
	        if (getPanelAssignFormat().getJListFormat().getSelectedIndex() == 1 || getPanelAssignFormat().getJListFormat().getSelectedIndex() == 4) {
	            for (int i = 0; i < cantidad; i++)
	                example.append("0"); //$NON-NLS-1$
	        }
	        else if (getPanelAssignFormat().getJListFormat().getSelectedIndex() == 2 || getPanelAssignFormat().getJListFormat().getSelectedIndex() == 5) {
	            for (int i = 0; i < cantidad; i++)
	                example.append("0"); //$NON-NLS-1$
	        }
	        else {
	            for (int i = 0; i < cantidad; i++)
	                example.append("#"); //$NON-NLS-1$
	        }
	        formatResult = example.toString();
	        getPanelAssignFormat().getJTextFieldExample().setText(example.toString());
	    }

	    public void createExampleMoney(String cant) {
	        String entero = ((String)getPanelAssignFormat().getJListFormat().getSelectedValue());
	        if (entero.indexOf(decimalSeparator) != -1)
	            entero = entero.substring(0, entero.indexOf(decimalSeparator));
	        int cantidad = Integer.parseInt(cant);
	        StringBuffer example = new StringBuffer(entero); //$NON-NLS-1$
	        if (cantidad > 0)
	            example.append(decimalSeparator);
	        for (int i = 0; i < cantidad; i++)
	            example.append("#"); //$NON-NLS-1$
	        if (getPanelAssignFormat().getJListFormat().getSelectedIndex() == 0 || getPanelAssignFormat().getJListFormat().getSelectedIndex() == 2) {
	            example.append(" " + getPanelAssignFormat().getJComboBoxSymbol().getSelectedItem().toString()); //$NON-NLS-1$
	        }
	 /*       if (jListFormat.getSelectedIndex() == 1) {
	            example.insert(0, jComboBoxSimbol.getSelectedItem().toString());
	        }*/
	        if (getPanelAssignFormat().getJListFormat().getSelectedIndex() == 1 || getPanelAssignFormat().getJListFormat().getSelectedIndex() == 3) {
	            example.append(getPanelAssignFormat().getJComboBoxSymbol().getSelectedItem().toString());
	        }
	        formatResult = example.toString();
	        getPanelAssignFormat().getJTextFieldExample().setText(example.toString());
	    }


		/**
		 * @return the formatResult
		 * 14/02/2007
		 * svonborries
		 */
		public String getFormatResult() {
			return formatResult;
		}
		

		private void jComboBoxSymbolActionPerformed() {
			int index = getPanelAssignFormat().getJComboBoxSymbol().getSelectedIndex();
	        if (index == 0) {
	        	getPanelAssignFormat().getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNE);
	        	getPanelAssignFormat().getJListFormat().setSelectedIndex(0);
	        }
	        else {
	            if (index == 1) {
	            	getPanelAssignFormat().getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGND);
	            	getPanelAssignFormat().getJListFormat().setSelectedIndex(0);
	            }
	            else {
	                if (index == 2) {
	                	getPanelAssignFormat().getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNC);
	                	getPanelAssignFormat().getJListFormat().setSelectedIndex(0);
	                }
	                else {
	                    if (index == 3) {
	                    	getPanelAssignFormat().getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNI);
	                    	getPanelAssignFormat().getJListFormat().setSelectedIndex(0);
	                    }
	                    else {
	                        if (index == 4) {
	                        	getPanelAssignFormat().getJListFormat().setListData(BusinessRules.TESTDATA_FORMAT_MONEYSIGNP);
	                        	getPanelAssignFormat().getJListFormat().setSelectedIndex(0);
	                        }
	                    }
	                }
	            }
	        }
		}
	
	
}
