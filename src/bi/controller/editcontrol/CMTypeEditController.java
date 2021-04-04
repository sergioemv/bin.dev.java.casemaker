package bi.controller.editcontrol;

import javax.swing.undo.UndoableEdit;
import model.ITypeData;
import model.Type;
import model.edit.CMModelEditFactory;
import model.util.CMTypeBean;
import bi.view.cells.CMCellTDStructureClassState;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.utils.CMTypeView;
/**
 * @author ccastedo
 *
 */
public class CMTypeEditController extends CMDefaultEditController{

	private CMTypeView typeView;
	private CMTypeBean typeBean;
	
	public UndoableEdit applyChanges(){
		getWarningMessages().clear();
		CMCompoundEdit ce = new CMCompoundEdit();
		if ((getTypeBean()==null)||(getTypeView()==null))
			return ce;
		ce.addEdit(new CMComponentAwareEdit());
		ce.addEdit(changeType());
		
		return ce;
	}

	private UndoableEdit changeType() {
		CMCompoundEdit ce = new CMCompoundEdit();
		if ((getTypeBean()==null))
			return ce;
		//check if the new type can apply to the typebean
		boolean apply = true;
		int newType = getTypeView().getSelectedType();		  
		
		if((apply)&&(newType!=getTypeBean().getType().getTypeIndex())){
		  	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeModelEdit((ITypeData)getTypeBean(), Type.values()[newType]));
		  	((ITypeData)getTypeBean()).setType(newType);	
			ce.addEdit(CMModelEditFactory.INSTANCE.createChangeLengthInTypeDataModelEdit((ITypeData)getTypeBean(),CMCellTDStructureClassState.getDefaultValue(getTypeBean().getTypeName())));
			((ITypeData)getTypeBean()).setLength(CMCellTDStructureClassState.getDefaultValue(getTypeBean().getTypeName()));
		}
		
		return ce;
	}

	

	public CMTypeView getTypeView() {
		return typeView;
	}

	public void setTypeView(CMTypeView typeView) {
		this.typeView = typeView;
		if (getTypeBean()!=null)
			this.typeView.setType(getTypeBean().getType().getTypeIndex());
	}

	public CMTypeBean getTypeBean() {
		return typeBean;
	}

	public void setTypeBean(CMTypeBean typeBean) {
		this.typeBean = typeBean;
		if (getTypeView() != null)
			getTypeView().setType(this.typeBean.getTypeIndex());
	}
}
