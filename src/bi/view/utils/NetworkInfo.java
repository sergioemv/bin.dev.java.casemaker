package bi.view.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
 
public abstract class NetworkInfo
{
    private static final String LOCALHOST = "localhost";
    public static final String NSLOOKUP_CMD     = "nslookup";
 
    public abstract Vector parseMacAddress() throws ParseException;
 
    /** Not too sure of the ramifications here, but it just doesn't seem right */
    public String parseDomain()
        throws ParseException
    {
        return parseDomain(LOCALHOST);
    }
 	private final static String deleteSpecialCharInMACAddress(String macAddress)
 	{
    	StringBuffer newMacAddress= new StringBuffer();
      	for(int i=0; i< macAddress.length();i++)
      	{
			char cc=macAddress.charAt(i);
            if(Character.isLetterOrDigit(cc))
            {
                newMacAddress.append(cc);
            }
      	}
      	return newMacAddress.toString();
 	}
    /** Universal entry for retrieving MAC Address */
    public final static Vector getMacAddress()
    {
    	Vector macCandidates= new Vector(0);
        try {
            NetworkInfo info = getNetworkInfo();
            Vector mac = info.parseMacAddress();
            for(int i=0;i<mac.size();i++){
            	macCandidates.addElement(deleteSpecialCharInMACAddress(mac.elementAt(i).toString()));
            }
            return macCandidates;
        } catch(ParseException ex) {
            return new Vector();
            /*ex.printStackTrace();
            throw new IOException(ex.getMessage());*/
        }
        catch(IOException ex){
        	Logger.getLogger(CMFormatFactory.class).error(ex.getMessage());
            return new Vector();
        }
    }
 
    /** Universal entry for retrieving domain info */
    public final static String  getNetworkDomain() throws IOException
    {
        try {
            NetworkInfo info = getNetworkInfo();
            String domain = info.parseDomain();
            return domain;
        } catch(ParseException ex) {
            ex.printStackTrace();
            throw new IOException(ex.getMessage());
        }
    }
 
    protected String parseDomain(String hostname) throws ParseException
    {
        // get the address of the host we are looking for - verification
        java.net.InetAddress addy = null;
        try {
            addy = java.net.InetAddress.getByName(hostname);
        } catch ( UnknownHostException e ) {
            e.printStackTrace();
            throw new ParseException(e.getMessage(),0);
        }
 
        // back out to the hostname - just validating
        hostname = addy.getCanonicalHostName();
        String nslookupCommand = NSLOOKUP_CMD + " " + hostname;
 
        // run the lookup command
        String nslookupResponse = null;
        try {
            nslookupResponse = runConsoleCommand(nslookupCommand);
        } catch ( IOException e ) {
            e.printStackTrace();
            throw new ParseException(e.getMessage(), 0);
        }
 
        StringTokenizer tokeit = new StringTokenizer(nslookupResponse,"\n",false);
        while( tokeit.hasMoreTokens() ) {
            String line = tokeit.nextToken();
            if( line.startsWith("Name:") ) {
                line = line.substring(line.indexOf(":") + 1);
                line = line.trim();
                if( isDomain(line, hostname) ) {
                    line = line.substring(hostname.length()+1);
                    return line;
                }
            }
        }
 
        return "n.a.";
    }
 
    private static boolean isDomain(String domainCandidate, String hostname)
    {
        Pattern domainPattern = Pattern.compile("[\\w-]+\\.[\\w-]+\\.[\\w-]+\\.[\\w-]+");
        Matcher m = domainPattern.matcher(domainCandidate);
        return m.matches() && domainCandidate.startsWith(hostname);
    }
 
    protected String  runConsoleCommand(String command)  throws IOException
    {
        Process p = Runtime.getRuntime().exec(command);
        InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
 
        StringBuffer buffer= new StringBuffer();
        for (;;) {
            int c = stdoutStream.read();
            if (c == -1) break;
            buffer.append((char)c);
        }
        String outputText = buffer.toString();
 
        stdoutStream.close();
 
        return outputText;
    }

    private static NetworkInfo getNetworkInfo() throws IOException
    {
        String os = System.getProperty("os.name");
 
        if(os.startsWith("Windows")) {
            return new WindowsNetworkInfo();
        } else if(os.startsWith("Linux")) {
            return new LinuxNetworkInfo();
        } else {
            throw new IOException("unknown operating system: " + os);
        }
    }

    protected String getLocalHost() throws ParseException
    {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch ( java.net.UnknownHostException e ) {
            e.printStackTrace();
            throw new ParseException(e.getMessage(), 0);
        }
    }

    public static  String  getLocalHostName()
    {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();//.getHostAddress();
        } catch ( java.net.UnknownHostException e ) {
            return "empty";
            /*e.printStackTrace();
            throw new ParseException(e.getMessage(), 0);*/
        }
    }
}

