package model.brimport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bi.view.businessrulesviews.brimport.utilsimporter.CMBRStringUtils;

import model.BRulesReference;

/**
 * This class represents a NAry operation.
 * 
 * @author portiz
 * @since 2007/10/29
 */
public class NAryCondition extends Condition {

	// logical operator that works for all the conditions
	private BRKeyword logicalOperator = BRKeyword.AND;

	// list of conditions
	private ArrayList<Condition> _listOfConditions = new ArrayList<Condition>();

	public ArrayList<Condition> getListOfConditions() {
		return _listOfConditions;
	}

	/**
	 * Add a condition to the list
	 * 
	 * @param __cond
	 */
	public void addCondition(Condition __cond) {
		// if it's nary, the operator is OR and _listOfConditions.size()==1,
		// there is a possibility that the
		if (__cond instanceof NAryCondition
				&& this.logicalOperator == BRKeyword.OR
				&& this._listOfConditions.size() == 1) {
			NAryCondition condN = (NAryCondition) __cond;
			NAryCondition localNaryCond = this.getTheFirstCondition();
			
			if (localNaryCond.hasAtLeastTwoLogicalOp() && condN.hasAtLeastTwoLogicalOp() )
				compareAndDecideInsertion(localNaryCond, condN);
			else
				_listOfConditions.add(__cond); // it's another thing
			/*if (localNaryCond.allConditionAreLogicalOp()
					&& condN.allConditionAreLogicalOp()) {
				compareAndDecideInsertion(localNaryCond, condN);
			} else
				_listOfConditions.add(__cond); // it's another thing
*/

		} else
			_listOfConditions.add(__cond);
	}

	
	private void compareAndDecideInsertion(NAryCondition __local,NAryCondition __foreign) {
		// at this point, i know that all parameters have conditional (something
		// with >,>=,...)
		// must verify if are two conditions, for takin 100% sure that is a
		// wrong comparison
		if (__local.getListOfConditions().size() == 2
				&& __foreign.getListOfConditions().size() == 2) {

			// Simple condition for auxiliar (for casting)
			SimpleCondition simpleAux, simpleAux2;
			String SecondPart1, SecondPart2;
			String sign1, sign2;
			String strHypothesis1, strHypothesis2;
			NAryCondition Ncond1=null,Ncond2=null;
			
			//verify if the nary are the requierd
			if (__local.countLogicalOp()==2 && __local.allConditionAreLogicalOp())
				Ncond1=__local;
			else
				Ncond1=__local.selectSpectedTwoCond();
			
			if (__foreign.countLogicalOp()==2 && __foreign.allConditionAreLogicalOp())
				Ncond2=__foreign;
			else
				Ncond2=__foreign.selectSpectedTwoCond();
			
			//if one fails, abort mision
			if (Ncond1==null || Ncond2==null )
				_listOfConditions.add(__foreign); // must be other thing
			// get the conditions
			simpleAux = (SimpleCondition) Ncond1.getListOfConditions().get(0);
			simpleAux2 = (SimpleCondition) Ncond1.getListOfConditions().get(1);

			// get the first part
			String strCondition = CMBRStringUtils.getConditionFirstPart(simpleAux.getIdentifier());
		
			// get the second part's
			SecondPart1 = CMBRStringUtils.getConditionSecondPart(simpleAux.getIdentifier());
			SecondPart2 = CMBRStringUtils.getConditionSecondPart(simpleAux2.getIdentifier());

			//horrible patch :(
			/*if (SecondPart2.equals(SecondPart1)){
				simpleAux2 = (SimpleCondition) Ncond2.getListOfConditions().get(0);
				SecondPart2 = CMBRStringUtils.getConditionSecondPart(simpleAux2.getIdentifier());
			}*/
		
			//get and invert signs
			sign1 = CMBRStringUtils.getConditionPart(simpleAux.getIdentifier());
			sign2 = CMBRStringUtils.getConditionPart(simpleAux2.getIdentifier());
			
			// forms the hypotesis
			strHypothesis1 = strCondition + " " + sign1 + " " + SecondPart2;
			strHypothesis2 = strCondition + " " + sign2 + " " + SecondPart1;

			// Search in the foreign NAryCondition if the hypotesis exists
			if (!(Ncond2.existCondition(strHypothesis1) && Ncond2.existCondition(strHypothesis2)))
				_listOfConditions.add(__foreign); // must be other thing
			else{
				//hipgotesis was correct, the expressions must be deleted from the list of natives
				//__nativeList.remove(strHypothesis1);
				//__nativeList.remove(strHypothesis1);
			}
				
		} else
			_listOfConditions.add(__foreign); // must be other thing
	}
	
	
/*	private void compareAndDecideInsertion(NAryCondition __local,NAryCondition __foreign) {
		// at this point, i know that all parameters have conditional (something
		// with >,>=,...)
		// must verify if are two conditions, for takin 100% sure that is a
		// wrong comparison
		if (__local.getListOfConditions().size() == 2
				&& __foreign.getListOfConditions().size() == 2) {

			// Simple condition for auxiliar (for casting)
			SimpleCondition simpleAux, simpleAux2;
			String SecondPart1, SecondPart2;
			String sign1, sign2;
			String strHypothesis1, strHypothesis2;

			// get the conditions
			simpleAux = (SimpleCondition) __local.getListOfConditions().get(0);
			simpleAux2 = (SimpleCondition) __local.getListOfConditions().get(1);

			// get the first part
			String strCondition = CMBRStringUtils.getConditionFirstPart(simpleAux.getIdentifier());

			// get and invert signs
			sign1 = CMBRStringUtils.getConditionPart(simpleAux.getIdentifier());
			sign2 = CMBRStringUtils
					.getConditionPart(simpleAux2.getIdentifier());

			// get the second part's
			SecondPart1 = CMBRStringUtils.getConditionSecondPart(simpleAux
					.getIdentifier());
			SecondPart2 = CMBRStringUtils.getConditionSecondPart(simpleAux2
					.getIdentifier());

			// forms the hypotesis
			strHypothesis1 = strCondition + " " + sign1 + " " + SecondPart2;
			strHypothesis2 = strCondition + " " + sign2 + " " + SecondPart1;

			// Search in the foreign NAryCondition if the hypotesis exists
			if (!(__foreign.existCondition(strHypothesis1) && __foreign
					.existCondition(strHypothesis2)))
				_listOfConditions.add(__foreign); // must be other thing
			else{
				//hipgotesis was correct, the expressions must be deleted from the list of natives
				//__nativeList.remove(strHypothesis1);
				//__nativeList.remove(strHypothesis1);
			}
				
		} else
			_listOfConditions.add(__foreign); // must be other thing
	}*/

	private NAryCondition selectSpectedTwoCond() {
		NAryCondition res=null;
		for (Condition cond : _listOfConditions) {
			if (cond instanceof NAryCondition){
				res=(NAryCondition)cond;
				if (res.countLogicalOp()==2 && res.allConditionAreLogicalOp())
					return res;
				else
					res=null;
			}
		}
		return res;
	}

	/**
	 * Get the first condition in the list.
	 * 
	 * @return In exists (and it's NAryCondition), returns the NAryCondition;
	 *         else null
	 */
	private NAryCondition getTheFirstCondition() {
		if (_listOfConditions.size() == 0)
			return null;
		if (_listOfConditions.get(0) instanceof NAryCondition)
			return (NAryCondition) _listOfConditions.get(0);
		else
			return null;
	}

	/*
	 * public void addCondition(Condition __cond){
	 * _listOfConditions.add(__cond); }
	 */

	public boolean allConditionAreLogicalOp() {
		for (Condition cond : this._listOfConditions) {
			if (cond instanceof SimpleCondition) {
				if (!hasConditionals((SimpleCondition) cond))
					return false;
			} else
				return false;
		}
		return true;
	}

	public Integer countLogicalOp() {
		Integer count=0;
		for (Condition cond : this._listOfConditions) {
			if (cond instanceof SimpleCondition) {
				if (hasConditionals((SimpleCondition) cond))
					count++;
			}else{
				if (cond instanceof NAryCondition) {
					count=count + ((NAryCondition)cond).countLogicalOp();
				}
			}
		}
		return count;
	}
	
	public boolean hasAtLeastTwoLogicalOp() {
		Integer count=0;
		for (Condition cond : this._listOfConditions) {
			if (cond instanceof SimpleCondition) {
				if (hasConditionals((SimpleCondition) cond))
					count++;
			}else{
				if (cond instanceof NAryCondition) {
					count+= ((NAryCondition)cond).countLogicalOp();
				}
			}
		}
		return count >=2;
	}

	
	
	private boolean existCondition(SimpleCondition __simpleCond) {
		for (Condition cond : _listOfConditions) {
			if (cond instanceof SimpleCondition) {
				SimpleCondition condAux = (SimpleCondition) cond;
				if (condAux.getIdentifier().trim().equals(
						__simpleCond.getIdentifier().trim()))
					return true;
			}
		}
		return false;
	}

	private boolean existCondition(String __strSimpleCond) {
		for (Condition cond : _listOfConditions) {
			if (cond instanceof SimpleCondition) {
				SimpleCondition condAux = (SimpleCondition) cond;
				if (condAux.getIdentifier().trim().equals(
						__strSimpleCond.trim()))
					return true;
			}
		}
		return false;
	}

	private String invertCondition(String __strCad) {
		if (__strCad.indexOf("<") != -1)
			return __strCad.replace("<", ">");
		else
			return __strCad.replace(">", "<");
	}

	private boolean hasConditionals(String __strLine) {
		return (__strLine.indexOf("<") != -1)
				|| (__strLine.indexOf("<=") != -1)
				|| (__strLine.indexOf(">") != -1)
				|| (__strLine.indexOf(">=") != -1);
	}

	private boolean hasConditionals(SimpleCondition __condLine) {
		return hasConditionals(__condLine.getIdentifier());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		// calculate the tabs necesary
		int count = -1;
		IFPart temp = this.getIfPart();
		while (temp != null) {
			count++;
			temp = temp.getParentIfPart();
		}
		char[] tabarray = new char[count];
		Arrays.fill(tabarray, '\t');

		boolean bIsFirst = true;
		for (Condition cond : _listOfConditions) {
			// verfy my parents
			if (cond.getIfPart() != this.getIfPart())
				cond.setIfPart(getIfPart());

			if (cond instanceof SimpleCondition) {
				if (bIsFirst) {
					builder.append((SimpleCondition) cond);
					bIsFirst = false;
				} else {
					builder.append(" "
							+ getLogicalOperator().get(getLanguaje()) + "\n");
					builder.append(String.copyValueOf(tabarray) + "    ");
					builder.append((SimpleCondition) cond);
				}
			} else {
				if (bIsFirst) {
					builder.append(cond);
					bIsFirst = false;
				} else {
					builder.append(" "
							+ getLogicalOperator().get(getLanguaje()) + "\n");
					builder.append(String.copyValueOf(tabarray) + "    ");
					builder.append(cond);
				}
			}

		}

		return builder.toString();
	}

	/**
	 * Sets the logical operatos
	 * 
	 * @return
	 */
	public BRKeyword getLogicalOperator() {
		return logicalOperator;
	}

	public void setLogicalOperator(BRKeyword logicalOperator) throws Exception {
		if (!logicalOperator.isLogicalOperator())
			throw new Exception("The operator is not a valid logical operator");
		this.logicalOperator = logicalOperator;
	}

	public Set<String> getInvalidIdentifiers() {
		Set<String> ids = new HashSet<String>();

		for (Condition cond : _listOfConditions) {
			// verfy my parents
			if (cond.getIfPart() != this.getIfPart())
				cond.setIfPart(getIfPart());
			ids.addAll(cond.getInvalidIdentifiers());
		}
		return ids;
	}

}
