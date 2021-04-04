package bi.view.utils;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;
 
public class LinuxNetworkInfo extends NetworkInfo
{
    public static final String IPCONFIG_COMMAND = "ifconfig";
 
    public Vector parseMacAddress() throws ParseException
    {
    	Vector macAddressCandidates= new Vector(0);
        String ipConfigResponse = null;
        try {
            ipConfigResponse = runConsoleCommand(IPCONFIG_COMMAND);
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new ParseException(e.getMessage(), 0);
        }
        String localHost = null;
        try {
            localHost = java.net.InetAddress.getLocalHost().getHostAddress();
        } catch(java.net.UnknownHostException ex) {
            ex.printStackTrace();
           // throw new ParseException(ex.getMessage(), 0);
        }
 
        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(ipConfigResponse, "\n");
        String lastMacAddress = null;
 
        while(tokenizer.hasMoreTokens()) {
            String line = tokenizer.nextToken().trim();
            boolean containsLocalHost = line.indexOf(localHost) >= 0;
 
           /* // see if line contains IP address
            if(/*containsLocalHost &&/ lastMacAddress != null) {
            	macAddressCandidates.addElement(lastMacAddress);//return lastMacAddress;
            }*/
            
 
            // see if line contains MAC address
            int macAddressPosition = line.indexOf("HWaddr");
            if(macAddressPosition <= 0) {
                continue;
            }
 
            String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
            if(isMacAddress(macAddressCandidate)) {
                lastMacAddress = macAddressCandidate;
                macAddressCandidates.addElement(lastMacAddress);
                continue;
            }
        }
        if(macAddressCandidates.size()>0){
        	return macAddressCandidates;
        }
        else{
        	ParseException ex = new ParseException("cannot read MAC address for " + localHost + " from [" + ipConfigResponse + "]", 0);
        	ex.printStackTrace();
        	throw ex;
        }
    }
 
    public String  parseDomain(String hostname) throws ParseException
    {
        return "";
    }
 
    private final boolean isMacAddress(String macAddressCandidate)
    {
        if(macAddressCandidate.length() != 17)
            return false;
        return true;
    }
}

