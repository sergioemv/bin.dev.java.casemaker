package bi.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import bi.view.lang.CMMessages;
import bi.view.utils.CMFileFilter;
public class BREditorManager {
    public BREditorManager() {

    }

    public File selectBusinessRuleFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(CMFileFilter.TXT.getFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fileChooser.showOpenDialog(null);
        File selectedFile = null;
        if (returnVal == 0) {
            selectedFile = fileChooser.getSelectedFile();
            //grueda12092004_begin
            String filePath= WorkspaceManager.getInstance().findCorrectPath(selectedFile);
            filePath = filePath.replace('\\','/');
            //grueda12092004_end
            int pathLength = filePath.length();
            String fileExtension=filePath.substring(pathLength - 3);
            if(fileExtension.equals("txt")&&selectedFile.canRead()){
                return selectedFile;
            }
            else{
				JOptionPane.showMessageDialog(null,CMMessages.getString("LABEL_BAD_BR_FILE"),CMMessages.getString("TITLE_BAD_BR_FILE"),JOptionPane.ERROR_MESSAGE);
                return null;
			}
		}
        else{
            return null;
        }

    }

    public String readContentFromFile(File brFile) {
        FileReader fileReader = null;
        String line=null;
		try{
			fileReader = new FileReader(brFile);
//hcanedo_21102004_begin
        }catch (java.io.FileNotFoundException e){return "";}
//hcanedo_21102004_end
        BufferedReader bufReader = new BufferedReader(fileReader);
        StringBuffer fileContent = new StringBuffer();
        try{
			line = bufReader.readLine();
        }catch (java.io.IOException e){Logger.getLogger(this.getClass()).error("Exception reading file's Buffered Reader");}
		while(line!=null){
            fileContent.append(line+"\n");
            try{
				line = bufReader.readLine();
        	}catch (java.io.IOException e){Logger.getLogger(this.getClass()).error("Exception reading file's Buffered Reader");}
        }
        try{
			bufReader.close();
    	    fileReader.close();
        }catch (java.io.IOException e){Logger.getLogger(this.getClass()).error("Exception reading file's Buffered Reader");}
		return fileContent.toString();

    }

    public void saveStringToFile(String text,File selectedFile){
           try{
    	        FileWriter writer = new FileWriter(selectedFile);
				for(int i=0;i<text.length();i++){
                        char c=text.charAt(i);
                        if(c==13||c==10){
							writer.write(System.getProperty("line.separator"));
                        }
                        else{
                        	writer.write(c);
                        }
                    }

            		writer.close();
        	}catch (java.io.IOException e){
               //grueda30122004_begin
        		Logger.getLogger(this.getClass()).error(e);
               //grueda30122004_end
              }
        }

    public boolean isTextModified(){ return textModified; }

    public void setTextModified(boolean textModified){ this.textModified = textModified; }



    public boolean isChecked(){
            return checked;
        }

    public void setChecked(boolean checked){
            this.checked = checked;
        }

    private boolean textModified=false;
    private boolean checked=false;


}
