/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.edit;

import java.io.File;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import model.util.CMDelegate;
import model.util.CMFilePathBean;
import model.util.CMNameBean;

/**
 * @author smoreno
 *
 */
public class CMChangeFilePathModelEdit extends AbstractUndoableEdit implements
		UndoableEdit {

	private CMFilePathBean filePathBean;
	private String filename;
	private String oldFilename;
	private String path;
	private String oldPath;
	private String oldFilePath;
	private CMNameBean nameBean;
	private CMDelegate delegate;
	private String oldName;


	/**
	 * @param p_bean
	 * @param p_newName
	 * @param p_newPath
	 * @param p_nameBean
	 * @param p_delegate
	 */
	public CMChangeFilePathModelEdit(CMFilePathBean p_bean, String p_newName, String p_newPath, CMNameBean p_nameBean, CMDelegate p_delegate) {
		// TODO Auto-generated constructor stub
		this.filePathBean = p_bean;
		this.oldFilePath = p_bean.getFilePath();
		this.filename = p_newName;
		this.oldFilename = p_bean.getFileName();
		this.nameBean  = p_nameBean;
		if (p_nameBean!=null)
			this.oldName = p_nameBean.getName();
		this.delegate = p_delegate;
		this.path = p_newPath;
		this.oldPath = p_bean.getPath();
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		if (delegate!=null){
			deleteFile(filePathBean.getFilePath());
			renameDir(path,oldPath);
		}
			filePathBean.setFileName(oldFilename);
			filePathBean.setPath(oldPath);
			if (nameBean!=null)
				nameBean.setName(oldName);
			if (delegate!=null)
				delegate.execute();
		
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		if (delegate!=null){
			deleteFile(oldFilePath);
			renameDir(oldPath,path);
		}
		filePathBean.setFileName(filename);
		filePathBean.setPath(path);
		if (nameBean!=null)
			nameBean.setName(filename);
		if (delegate!=null)
			delegate.execute();
	}
	private void renameDir(String p_oldPath, String p_newPath) {
		File oldFile = new File(p_oldPath);
		File newFile = new File(p_newPath);
	   if (newFile != null)
		   oldFile.renameTo(newFile);
	   if (oldFile.exists())
		   oldFile.delete();
	}
	private void deleteFile(String p_filePath) {
	    File file = new File(p_filePath);
	      if( file.exists()) {
	        file.delete();
	      }
	}
}
