package bi.view.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GetEnvironmentVariables {
    protected GetEnvironmentVariables() {
        try {
            envProps = getEnvProperties();
        }
        catch (Exception ex) {
        	Logger.getLogger(CMFormatFactory.class).error("System can't support Environment Variables");
        }
    }

    public static GetEnvironmentVariables getInstance() {
        if (instance == null) {
            instance = new bi.view.utils.GetEnvironmentVariables();
        }
        return instance;
    }

    public String getEnviromentVariable(String key) {
        if (envProps != null)
            return envProps.getProperty(key);
        else
            return null;
    }

    public String getSystemRootVariable() {
        if (envProps != null) {
            String systemRoot = envProps.getProperty("SystemRoot");
            File file = new File(systemRoot);
            if (file.exists())
                return envProps.getProperty("SystemRoot");
            else
                return null;
        }
        else
            return null;
    }

    private Properties getEnvProperties() throws Exception {
        Properties envProps = new Properties();
        // get runtime ...
        Runtime r = Runtime.getRuntime();
        // start new shell with /c switch and parameters( only work around for win...)
        // for linux/unix... get OS type and invoke Shell accordingly...
        Process p = r.exec("cmd /c set>temp.env");
        // Give some time for above process to finish, say 0.5 sec
        Thread.sleep(2000);
        // load property with this temp file...
        //envProps.load(in);
        // this method will eliminate all '\' chars and
        // will throw an error if an invalid escape
        // sequence will be found
        // do not relay on Properties.load() and instead simply pars the output
        // using this
        FileInputStream in = new FileInputStream("temp.env");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = br.readLine()) != null) {
            int index = -1;
            if ((index = line.indexOf("=")) > -1) {
                String key = line.substring(0, index).trim();
                String value = line.substring(index + 1).trim();
                envProps.setProperty(key, value);
            } else {
                envProps.setProperty(line, "");
            }
        }
        in.close();
        // clean up the mesh....
        new File("temp.env").delete();
        // return the environment properties...
        return envProps;
    }

    /**
     * @link
     * @shapeType PatternLink
     * @pattern Singleton
     * @supplierRole Singleton factory
     */
    Properties envProps = null; // = env.getEnvProperties();

    /*# private CMFormatFactory _cmFormatFactory; */

    private static GetEnvironmentVariables instance = null;
}
