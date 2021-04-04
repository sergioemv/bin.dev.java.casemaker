package bi.view.testdataviews;

import model.StructureTestData;
import model.TDStructure;
import bi.view.cells.CMCellGroupHeaderTDStructure;
import bi.view.cells.CMCellHeaderTDStructureField;
import bi.view.cells.CMCellHeaderTDStructureFormat;
import bi.view.cells.CMCellHeaderTDStructureFormula;
import bi.view.cells.CMCellHeaderTDStructureGlobal;
import bi.view.cells.CMCellHeaderTDStructureKey;
import bi.view.cells.CMCellHeaderTDStructureLength;
import bi.view.cells.CMCellHeaderTDStructureName;
import bi.view.cells.CMCellHeaderTDStructureNewColumn;
import bi.view.cells.CMCellHeaderTDStructureObjectTypes;
import bi.view.cells.CMCellHeaderTDStructurePrefix;
import bi.view.cells.CMCellHeaderTDStructureSuffix;
import bi.view.cells.CMCellHeaderTDStructureType;
import bi.view.cells.CMCellHeaderTDStructureValue;

import com.eliad.model.AbstractGridModel;
import com.eliad.model.GenericGridModel;
import com.eliad.model.GridModel;
import com.eliad.swing.JSmartGrid;
/**
 * @author ccastedo 
 * 	
*/

@SuppressWarnings("serial")
public class CMHeaderGridTDStructure extends GenericGridModel {

      private String[] colNames;
      private JSmartGrid grid = new JSmartGrid();
      private CMCellGroupHeaderTDStructure cmCellGroupHeaderTDStructure;
     private int colCount = 0;
     
     private GridModel   columnHeaderModel_;
      public CMHeaderGridTDStructure(JSmartGrid jSmartGrid) {
       // super(jSmartGrid, JSmartGrid.HORIZONTAL);
    	  columnHeaderModel_ = new AbstractGridModel () {
    	        public int getRowCount() {
    	          return 1;
    	        }
    	        public int getColumnCount() {
    	          return colCount;
    	        }
    	        public Object getValueAt(int row, int column) {
    	          //return colNames[column];
    	        	if (column>cmCellGroupHeaderTDStructure.size())
    	      		  column = cmCellGroupHeaderTDStructure.size();
    	          return colNames[column];
    	        }
    	      };
        grid = jSmartGrid;
        initGUI();
      }
     public void initGUI() {
    	 
      }
     /* public int getRowCount() {
        return 1;
      }
      public int getColumnCount() {
        return colCount;
      }
      public Object getValueAt(int row, int column) {
    	  if (column>cmCellGroupHeaderTDStructure.size())
    		  column = cmCellGroupHeaderTDStructure.size();
        return colNames[column];
      }*/
      
      public CMCellGroupHeaderTDStructure getGroupHeaderTDStructure(){
    	  return cmCellGroupHeaderTDStructure;
      }
      @SuppressWarnings("unchecked")
	public void createTDStructureHeaderView(int i, TDStructure testData) {
    	  int sizeNCH = 0;
    	  if (testData != null){
    		  sizeNCH = sizeNewColumnsHeader(i,testData);
        	  cmCellGroupHeaderTDStructure = new CMCellGroupHeaderTDStructure(10+sizeNCH);
              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureKey(grid,testData)); 
              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureGlobal(grid,testData));

              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureField(grid,testData)); 
              CMCellHeaderTDStructureName newCMCellHeaderTDStructureName = new CMCellHeaderTDStructureName(grid,testData);
              newCMCellHeaderTDStructureName.setNumOfTable(i);
              cmCellGroupHeaderTDStructure.addElement(newCMCellHeaderTDStructureName);
            
              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureObjectTypes(grid,testData));

              
              if (sizeNCH > 0){          	
              	if ( sizeNCH> 0){
              		StructureTestData structureTestData = (StructureTestData)testData.getM_StructureTestData().elementAt(i);
                  	for (int j=0;j<sizeNCH;j++){

                  		String nameNewColumn = structureTestData.getNewColumnsHeader().elementAt(j).toString();
                  		CMCellHeaderTDStructureNewColumn newCMCellHeaderTDStructureNewColumn = new CMCellHeaderTDStructureNewColumn(grid,testData);
                  		newCMCellHeaderTDStructureNewColumn.setName(nameNewColumn);
                  		cmCellGroupHeaderTDStructure.addElement(newCMCellHeaderTDStructureNewColumn);
                  	}
                  }
              }

              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureType(grid,testData));
              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureLength(grid,testData));
              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructurePrefix(grid,testData));
              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureSuffix(grid,testData));
              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureFormat(grid,testData));
              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureFormula(grid,testData));
              cmCellGroupHeaderTDStructure.addElement(new CMCellHeaderTDStructureValue(grid,testData));
             
              colCount = cmCellGroupHeaderTDStructure.size();
              colNames = new String[cmCellGroupHeaderTDStructure.size()];
              for (int j = 0; j<cmCellGroupHeaderTDStructure.size();j++){
            	  colNames[j] = cmCellGroupHeaderTDStructure.elementAt(j).toString();    
              }
    	  }   	              
             
          
    	 
    }
      
      public int sizeNewColumnsHeader(int index, TDStructure testData){
      	int sizeNCH = 0;
      	if (testData != null){
      		int  sizestructureTestData = testData.getM_StructureTestData().size();
      		if (sizestructureTestData > 0 && index<sizestructureTestData){      			
      			StructureTestData structureTestData = (StructureTestData)testData.getM_StructureTestData().elementAt(index);
          		if (structureTestData != null)
          			if (structureTestData.getNewColumnsHeader() != null)
          	        	sizeNCH = structureTestData.getNewColumnsHeader().size();          		
      		}
      	}
          return sizeNCH;
      }
      
      public void setColumnCount(int i){
    	  colCount = 0;
    	  
      }
      
      public GridModel getColumnHeaderModel() {
    	    return columnHeaderModel_;
    	  }
}
