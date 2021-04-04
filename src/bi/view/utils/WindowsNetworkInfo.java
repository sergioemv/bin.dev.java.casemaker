package bi.view.utils;
 import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


public class WindowsNetworkInfo extends NetworkInfo
{
    public static final String IPCONFIG_COMMAND = "ipconfig /all";

    public Vector parseMacAddress() throws ParseException
    {
        // run command
    	Vector macAddressCandidates= new Vector(0);
        String ipConfigResponse = null;
        try {
            ipConfigResponse = runConsoleCommand(IPCONFIG_COMMAND);
            Logger.getLogger(this.getClass()).info(ipConfigResponse);
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new ParseException(e.getMessage(), 0);
        }

        // get localhost address
        String localHost =  getLocalHost();

        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(ipConfigResponse, "\n");
        String lastMacAddress = null;
        while(tokenizer.hasMoreTokens())
        {
            String line = tokenizer.nextToken().trim();

            // see if line contains IP address, this means stop if we've already seen a MAC address
            /*if(line.endsWith(localHost) && lastMacAddress != null) {
                return lastMacAddress;
            }*/

            // see if line might contain a MAC address
            int macAddressPosition = line.indexOf(":");
            if(macAddressPosition <= 0) {
                continue;
            }

            // trim the line and see if it matches the pattern
            String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
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
        ParseException ex = new ParseException("Cannot read MAC address from [" + ipConfigResponse + "]", 0);
        ex.printStackTrace();
        throw ex;
        }
    }

    private static boolean isMacAddress(String macAddressCandidate)
    {
        Pattern macPattern = Pattern.compile("[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}-[0-9a-fA-F]{2}");
        Matcher m = macPattern.matcher(macAddressCandidate);
        return m.matches();
    }

}
