package bi.view.businessrulesviews.brimport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import model.brimport.BRLanguaje;

public class CMBRTextImporter implements ICMBRImporter {

	public String doImport(File p_importFile,File p_importFileBom,Map<String, Object> aditionalOptions) throws IOException {
		if(p_importFile!=null){
            String fileContent = readContentFromFile(p_importFile);
            return fileContent;
		}
        return "";
           
	}
		public String readContentFromFile(File brFile) throws IOException {
	        FileReader fileReader = null;
	        String line=null;
	        fileReader = new FileReader(brFile);
	        BufferedReader bufReader = new BufferedReader(fileReader);
	        StringBuffer fileContent = new StringBuffer();
			line = bufReader.readLine();
			while(line!=null){
	            fileContent.append(line+"\n");
					line = bufReader.readLine();
	        }
				bufReader.close();
	    	    fileReader.close();
			return fileContent.toString();

	    }
		public void setLanguaje(BRLanguaje languaje) {
			// do nothing ....
			
		}
		public String getWarningMessages() {
			// TODO Auto-generated method stub
			return "";
		}
}