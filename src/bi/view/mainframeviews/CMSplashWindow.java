/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package  bi.view.mainframeviews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;

import model.License;
import bi.controller.LicenseManager;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;



public class CMSplashWindow extends Window {

   int width = 514, height = 400;

    Image tile = null;
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    String client      = "HSH-NORDBANK"; //$NON-NLS-1$
    String clientLabel = CMMessages.getString("LABEL_LICENSED_FOR"); //$NON-NLS-1$
    String serialNumberLabel = CMMessages.getString("LABEL_SERIAL_NUMBER"); //$NON-NLS-1$
    String serialNumber = "12c43-a1957-dj7BF-Fhcb2-3k20"; //$NON-NLS-1$
    String developedBy = CMMessages.getString("LABEL_DEVELOPED_BY"); //$NON-NLS-1$
    String product =  " CaseMaker "+model.BusinessRules.APPLICATIONVERSION+"."+model.BusinessRules.BUILDDATE+"."+model.BusinessRules.BUILDNUMBER;
    String company = CMMessages.getString("LABEL_COMPANY"); //$NON-NLS-1$
    String m_InstallationDirectory = null;

    CMSplashWindow()
    {
       super(new Frame());
       setVisible(false) ;
       LicenseManager LM = new LicenseManager();
       License license = LM.getExistingLicense(m_InstallationDirectory);
       this.client = this.getExistingClientName(license);
       this.serialNumber = this.getExistingSerialNumber(license);
       charge();
    }

    public CMSplashWindow(String p_InstallationDirectory)
    {
       super(new Frame());
       setVisible(false) ;
       m_InstallationDirectory = p_InstallationDirectory;
       LicenseManager LM = new LicenseManager();
       if (LM.getClientType().equals("Stand-Alone"))
       {	   
         License license = LM.getExistingLicense(m_InstallationDirectory);
         if( license != null) {
		     this.client = this.getExistingClientName(license);
		     this.serialNumber = this.getExistingSerialNumber(license);
         }
         else {
           client = "UNKNOWN-CLIENT"; //$NON-NLS-1$
           serialNumber = "unknown"; //$NON-NLS-1$
         }
       }  
       else
       {
           client = "NetClient";
           serialNumber = "SERVER";    	   
       } 	 
       charge();
    }
    public String getExistingClientName(License p_License) {
      int clientIdLength = p_License.getM_ClientId().length();
	  String licenseValue = p_License.getM_LicenseValue();

      StringBuffer clientName = new StringBuffer();
      for( int i = 0; i < clientIdLength; i++) {
        if( licenseValue != null &&  i < licenseValue.length() )
        {
          char charValue = licenseValue.charAt(i);
          clientName.append(charValue);
        }
      }
      return clientName.toString();
    }

    public String getExistingSerialNumber(License p_License) {
      int clientIdLength = p_License.getM_ClientId().length();
	  String licenseValue = p_License.getM_LicenseValue();

      StringBuffer serialNumber = new StringBuffer();
      for( int i = clientIdLength; i < licenseValue.length(); i++) {
        serialNumber.append(licenseValue.charAt(i));
      }

      return serialNumber.toString();
    }
    private void charge()
    {
          // Lecture de l'icone
          MediaTracker trk = new MediaTracker(this) ;
          //Image image = (Image) CMFrameView.class.getRessource(s);
          java.net.URL url = CMIcon.ABOUT.getURL();

          if( url != null) {
			  tile = toolkit.getImage( url);
				  if (tile != null) {
					  trk.addImage(tile, 0);
					  try   {    trk.waitForID(0) ;  }
					  catch ( InterruptedException e) {  }

					  setSize(tile.getWidth(this), tile.getHeight(this)) ;
				  }
		  }
          else {
            tile = new java.awt.image.BufferedImage(513,303,java.awt.image.BufferedImage.TYPE_3BYTE_BGR);
            trk.addImage(tile,0);
					  try   {    trk.waitForID(0) ;  }
					  catch ( InterruptedException e) {  }

					  setSize(tile.getWidth(this), tile.getHeight(this)) ;
          }
    }

    public void run( ) {
          init() ;
    }
    public void init( )
    {
		m_ProgressDrawer = new ProgressDrawer(this,10,245,395,5);//fcastro_20092004
        Dimension dS = Toolkit.getDefaultToolkit().getScreenSize() ;
        Dimension dL = getSize() ;

        if (dL.width > dS.width) dL.width = dS.width ;
        if (dL.height > dS.height) dL.height = dS.height ;

        setLocation ( (dS.width - dL.width) / 2 , (dS.height - dL.height) / 2) ;
        if(tile != null)
            setVisible(true) ;
    }


    public void hide(int i) {
        try {
            Thread.sleep(i * 1000) ;
        }
        catch (Exception e) { }

        setVisible(false) ;
    }

    public void paint(Graphics g)
    {
          int width = getSize().width, height = getSize().height, x = 0, y = 0;

          if(tile != null)
          g.drawImage(tile, 0, 0, getBackground(), this);
          g.setColor(new Color(0xD0D0D0)) ;
          if( tile instanceof java.awt.image.BufferedImage) {
            g.fill3DRect(0, 0, 513, 303, true);
          }
          g.setColor(new Color(0x0)) ;
          g.draw3DRect(0, 0, 513, 303, true);

          Font f = this.getFont() ;
          g.setFont(new Font("Arial",2,8)); //$NON-NLS-1$

          if( !(tile instanceof java.awt.image.BufferedImage)) {
			  int Y = height-72 ;
			  g.setFont(f) ;
			  if (client != null) {
				g.drawString(clientLabel, 8, Y);
                Color color = g.getColor();
                if( client.indexOf("UNKNOWN") >= 0) { //$NON-NLS-1$
                  g.setColor(Color.red);
                }
				g.drawString(client, 85, Y);
				g.setColor(color);
			  }
			  if (serialNumberLabel != null) {
				g.drawString(serialNumberLabel, 235, Y) ;
                Color color = g.getColor();
                if( serialNumber.indexOf("unknown") >= 0) { //$NON-NLS-1$
                  g.setColor(Color.red);
                }
				g.drawString(serialNumber, 325, Y) ;
                g.setColor(color);
			  }
          }
          else{
              g.setFont(new Font("Sans Serif",1, 12)) ; //$NON-NLS-1$
              int Y = 120;
              g.drawString(product, 8, Y);
              Y += 22;
              g.drawString(company, 8, Y);
              Y += 22;
              g.drawString(developedBy, 8, Y);
              Y += 22;
              g.setFont(f) ;
			  if (client != null) {
				g.drawString(clientLabel, 8, Y) ;
				g.drawString(client, 85, Y) ;
			  }
			  if (serialNumberLabel != null) {
				g.drawString(serialNumberLabel, 235, Y) ;
				g.drawString(serialNumber, 325, Y) ;
			  }

          }

    }
    //fcastro_20092004_begin
    public void startProgressDrawer (){
        this.m_ProgressDrawer.start();
    }

    public void updateProgress(){
        this.m_ProgressDrawer.updateWaitTime();
    }

	private ProgressDrawer m_ProgressDrawer = null;

    public class ProgressDrawer extends Thread{

        private int widthPainted = 1;
        private int widthToPaint = 0;
        private int startXPosition = 0;
        private int startYPosition = 0;
        private int waitTime = 50;
        private int barHeigth = 1;
		private CMSplashWindow m_CMSplashWindow = null;


        public ProgressDrawer(CMSplashWindow splash,int x,int y,int widthToPaint,int barHeigth){
            super();
            this.m_CMSplashWindow = splash;
            this.widthToPaint = widthToPaint;
            this.startXPosition = x;
            this.startYPosition = y;
			this.barHeigth = barHeigth;
        }
        public void run(){
            try{
				Graphics g = this.m_CMSplashWindow.getGraphics();

            	g.setColor(new Color(187,213,242));
				while(widthPainted < widthToPaint){
                	Thread.sleep(waitTime);
					g.fillRect(startXPosition,startYPosition,widthPainted,barHeigth);
					widthPainted++;

            }
            }catch(Exception e){
            }
        }
        public void setWaitTime(int time){
            this.waitTime = time;
        }

        public void updateWaitTime(){
            if(this.isAlive()&&widthPainted<widthToPaint){ //continue painting the whole area with the same thread
            	widthToPaint =this.m_CMSplashWindow.getWidth()-20;
               	int leftToPaint = widthToPaint - widthPainted;
               	Double d = new Double(1900/leftToPaint);
               	int newWaitTime = d.intValue()-1;
               	waitTime = newWaitTime;
            }
            else{//use another thread for remaining area to be painted
            	ProgressDrawer finisher = new ProgressDrawer(this.m_CMSplashWindow,startXPosition+widthToPaint -1,this.startYPosition,100,5);
                finisher.setWaitTime(19);
                finisher.start();
            }
        }
    }

    public String getM_InstallationDirectory(){
      return m_InstallationDirectory;
    }

    public void setM_InstallationDirectory(String p_InstallationDirectory) {
      m_InstallationDirectory = p_InstallationDirectory;
    }
    //fcastro_20092004_end
}
