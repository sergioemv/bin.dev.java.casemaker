/*
 Copyright (c) ILOG 1995-2006.  All rights reserved.

 This source code is intended only as a supplement to ILOG's product.

 THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND,
 EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 */

import ilog.rules.archive.IlrJarArchiveLoader;
import ilog.rules.archive.IlrRulesetArchiveLoader;
import ilog.rules.engine.IlrBadContextException;
import ilog.rules.engine.IlrContext;
import ilog.rules.engine.IlrExceptionHandler;
import ilog.rules.engine.IlrParameterMap;
import ilog.rules.engine.IlrRuleset;
import ilog.rules.engine.IlrRulesetArchiveParser;
import ilog.rules.engine.IlrUndefinedMainTaskException;
import ilog.rules.engine.IlrUserRuntimeException;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.jar.JarInputStream;

public class RuleEngineRunner {
    private IlrRuleset ruleset;

    private IlrContext engine;

    private IlrParameterMap inputs = new IlrParameterMap();

    private IlrParameterMap outputs;

    /**
     * Loads the ruleset from the provided file
     * Sets a PrintWriter to send errors messages
     * @param the ruleset archive path
     * @throws Exception when parsing detected errors
     */
    public void loadRuleset(String rulesetPath) throws Exception {
	JarInputStream is = new JarInputStream(new FileInputStream(new File(
		rulesetPath)));

	IlrRulesetArchiveLoader rulesetloader = new IlrJarArchiveLoader(is);
	IlrRulesetArchiveParser rulesetparser = new IlrRulesetArchiveParser();

	ruleset = new IlrRuleset();
	rulesetparser.setRuleset(ruleset);

	if (rulesetparser.parseArchive(rulesetloader)) {
		ilog.rules.factory.IlrRulesetFactory factory = ruleset.makeFactory();
		java.io.FileWriter fw = new java.io.FileWriter("C:\\temp\\ruleset.xrl");
		ilog.rules.factory.IlrXmlRulesetWriter rswriter = new ilog.rules.factory.IlrXmlRulesetWriter(fw);
		rswriter.print(factory);
		fw.close();
		return;
	}
 

	String[] errors = rulesetparser.getErrors();

	if (errors != null) {
	    StringBuffer buf = new StringBuffer();
	    for (int i = 0; i < errors.length; i++) {
		buf.append(errors[i]).append('\n');
	    }
	    throw new Exception(buf.toString());
	}
    }

    /**
     * Prepares the engine with the loaded ruleset,
     * and sets exception handlers for the execution
     * @throws IlrBadContextException in case of mismatch
     * between provided (in this method) and requested (in the ruleset) context classes
     */
    public void buildEngine() throws IlrBadContextException {
	engine = new IlrContext(ruleset);
	// TODO refine default exception handler
	IlrExceptionHandler exceptionHandler = new IlrExceptionHandler() {
	    public boolean handleException(IlrUserRuntimeException ex) {
		if (ex.isInConditions())
		    return false;
		else
		    throw ex;
	    }
	};
	engine.setExceptionHandler(exceptionHandler);
	engine.setRuleflowExceptionHandler(exceptionHandler);
    }

    /**
     * Inserts all the objects of the collection into the engine working memory
     * @param the collection of objects to insert
     * @throws IlrUserRuntimeException when provided objects throw exceptions during their insertion
     */
    public void declareWorkingMemoryObjects(Collection objects)
	    throws IlrUserRuntimeException {
	if (objects == null)
	    return;
	Iterator it = objects.iterator();
	while (it.hasNext())
	    engine.insert(it.next());
    }

    /**
     * Runs the ruleset (ruleflow or rules) on the input parameters
     * and with the current working memory
     * @return the number of fired rules
     * @throws IlrUserRuntimeException, IlrUndefinedMainTaskException
     */
    public int executeRules() throws IlrUserRuntimeException,
	    IlrUndefinedMainTaskException {
	engine.setParameters(inputs);
	outputs = engine.execute();
	return outputs.getIntValue("ilog.rules.firedRulesCount");
    }

    /**
     * Gets the execution output results
     * @return the output parameter map
     */
    public IlrParameterMap getOutputResult() {
	return outputs;
    }

    /**
     * Resets the engine for execution of the same ruleset on other objects
     */
    public void reset() {
	engine.retractAll();
	engine.cleanRulesetVariables();
	engine.resetRuleflow();
    }

    /**
     * Cleans up the engine
     */
    public void end() {
	ruleset = null;
	if (engine != null) {
	    engine.end();
	    engine = null;
	}
    }

    /**
     * Runs the engine with the provided ruleset archive
     */
    public static void main(String[] args) {
        String rulesetPath = "ruleArchive1.jar";

        RuleEngineRunner runner = new RuleEngineRunner();

        try {
            // Load the ruleset
            runner.loadRuleset(rulesetPath);
            // Prepare an engine for this ruleset
            runner.buildEngine();

				        // TODO check input parameters
    	    // Feed the engine with input parameters
	//            	    runner.inputs.setParameter("customer", /* TODO assign a value */);
	            	    
            //	Feed the engine working memory
            //	TODO populate objects collection for working memory
            Collection objects = null;
            runner.declareWorkingMemoryObjects(objects);

            // Execute the rules
            runner.executeRules();
                        
                        // Display the result
            String[] outParameterNames = {
                        "customer",
                        }; 
 					
            IlrParameterMap results = runner.getOutputResult();
            for (int i = 0; i < outParameterNames.length; i++) {
              System.out.println("Name: " + outParameterNames[i] + ", Value: " + results.getObjectValue(outParameterNames[i]));				
            }					
            
            // Reset is necessary to execute the engine again with other objects
            runner.reset();

        } catch (IlrBadContextException bcException) {
            // TODO Process IlrBadContextException
            bcException.printStackTrace();
        } catch (IlrUserRuntimeException urException) {
            // TODO Process IlrUserRuntimeException
            urException.printStackTrace();
        } catch (IlrUndefinedMainTaskException umtException) {
            // TODO Process IlrUndefinedMainTaskException
            umtException.printStackTrace();
        } catch (Exception exception) {
            // TODO Process Exception
            exception.printStackTrace();
        } finally {
            runner.end();
        }
    }
};
