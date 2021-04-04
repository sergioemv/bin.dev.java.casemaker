package bi.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;



public class AllPairsCombinationManager implements IAllPairsCombinationManager {

    private String[] neededvalues;
    private String[] vars;
    private Hashtable<String,List> labelsHt = new Hashtable<String,List>();
    private ArrayList labelsArray;
    private Hashtable<String,List> lists = new Hashtable<String,List>();
    private Hashtable<String,Integer> listorderHt = new Hashtable<String,Integer>();
    private ArrayList listorderArray = new ArrayList();
    private Hashtable<String,Hashtable> pairs = new Hashtable<String,Hashtable>();
    private Hashtable pairscases = new Hashtable();
    private String[] zeros = {"","0","00","000"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    private String slug = ""; //$NON-NLS-1$
    private int count = 1;
    private ArrayList<List> allPairsCombinations = new ArrayList<List>();


  @SuppressWarnings("unchecked")
public ArrayList generateAllPairsCombinations(ArrayList p_UseCaseData){
    maketables(p_UseCaseData, "tables"); //# read the data file and fill the arrays with each variable. //$NON-NLS-1$
    populate(); //# make the checklists for the pairs.
      //# This loop creates the "slug" which is the blank test case filled in by the recursive FINDNEXT routine.
    StringBuffer sBuffer = new StringBuffer();
    for( int c  = 0; c < vars.length; c++){
	  sBuffer.append("x\t"); //$NON-NLS-1$
    }
    sBuffer.deleteCharAt(sBuffer.length()-1);
    slug = sBuffer.toString();


	//# print first line of the output table.
	//#
	Logger.getLogger(this.getClass()).info("TEST CASES"); //$NON-NLS-1$
	Logger.getLogger(this.getClass()).info("case\t"); //$NON-NLS-1$
	labelsArray = gettable1("tables","labels"); //$NON-NLS-1$ //$NON-NLS-2$
	//ArrayList originalLabelsArray = (ArrayList) labelsArray.clone();	
    ArrayList originalLabelsArray = new ArrayList();
    originalLabelsArray.addAll(labelsArray);
    Collections.sort(originalLabelsArray,new Comparator() {
					public int compare(Object o1, Object o2) {
						String s1 = (String) o1;
						String s2 = (String) o2;
                        Integer originalposition1 =   listorderHt.get(s1);
                        Integer originalposition2 =   listorderHt.get(s2);

						if (originalposition1.intValue() > originalposition2.intValue()) {
							return 1;
						}
						else if (originalposition1.intValue() < originalposition2.intValue()) {
							return -1;
						}
						else {
							return 0;
						}
					}
				});
    for( int i = 0; i < originalLabelsArray.size(); i++){
    	Logger.getLogger(this.getClass()).info(originalLabelsArray.get(i));
    	Logger.getLogger(this.getClass()).info("\t"); //$NON-NLS-1$
    }
    Logger.getLogger(this.getClass()).info("pairings"); //$NON-NLS-1$

	//# find each test case, then show the status of all the pairings
	//#
	while(more())
	{
        neededvalues = new String[vars.length];
	    String casef = findnext(slug);
        StringBuffer sBuffer2 = new StringBuffer();
        sBuffer2.append(count);
        sBuffer2.append("\t"); //$NON-NLS-1$

        String combinationString = readable(casef);
        sBuffer2.append(combinationString);
        sBuffer2.append(score(casef));
        Logger.getLogger(this.getClass()).info(sBuffer2.toString());
        checkin(casef, count++);
	}
	status();
    return allPairsCombinations;
  }


//# This routine counts the unique pairings in a test case.
//#

private int score(String p_Case)
{
    int score = 0;
    String patternStr = "\t"; //$NON-NLS-1$
    String[] casevalues = p_Case.split(patternStr);
    int c = 0;
    int v = 0;

      for( c = 0; c < vars.length-1; c++) {
        for( v = c+1; v < vars.length; v++){
          StringBuffer cvIndexBuffer = new StringBuffer();
          cvIndexBuffer.append(c);
          cvIndexBuffer.append("-"); //$NON-NLS-1$
          cvIndexBuffer.append(v);

          StringBuffer caseValuesIndexBuffer = new StringBuffer();
          caseValuesIndexBuffer.append(casevalues[c]);
          caseValuesIndexBuffer.append("-"); //$NON-NLS-1$
          caseValuesIndexBuffer.append(casevalues[v]);
          Hashtable h1 =  pairs.get(cvIndexBuffer.toString());
          Integer caseValue = (Integer) h1.get(caseValuesIndexBuffer.toString());
          if( caseValue.intValue() == 0 ) {
            score++;
          }
        }
      }
      return score;
}

//# This routine records all the new pairings in a test case in the checklists.
//#
@SuppressWarnings("unchecked")
private void checkin(String p_Case, int p_Count){
    int c = 0;
    int v = 0;
    int casenumber = p_Count;

    String[] casevalues = p_Case.split("\t"); //$NON-NLS-1$

      for( c = 0; c < vars.length-1; c++) {
        for( v = c + 1; v < vars.length; v++) {
          StringBuffer cvIndex = new StringBuffer();
          cvIndex.append(c);
          cvIndex.append("-"); //$NON-NLS-1$
          cvIndex.append(v);
          StringBuffer caseValuesIndex = new StringBuffer();
          caseValuesIndex.append(casevalues[c]);
          caseValuesIndex.append("-"); //$NON-NLS-1$
          caseValuesIndex.append(casevalues[v]);
          Hashtable<String,Integer> h1 =  pairs.get(cvIndex.toString());
          Integer pairValue = (Integer) h1.get(caseValuesIndex.toString());
          h1.put(caseValuesIndex.toString(), new Integer(pairValue.intValue()+1));

          Hashtable h2 = (Hashtable) pairscases.get(cvIndex.toString());
          if( h2 == null) {
            h2 = new Hashtable();
            pairscases.put(cvIndex.toString(), h2);
          }
          ArrayList<Integer> arrayOfCaseNumbers = (ArrayList<Integer>) h2.get(caseValuesIndex.toString());
          if( arrayOfCaseNumbers == null) {
            arrayOfCaseNumbers = new ArrayList<Integer>();
            h2.put(caseValuesIndex.toString(), arrayOfCaseNumbers);
          }
          arrayOfCaseNumbers.add(new Integer(casenumber));
        }
      }
}

//# This routine creates the checklists of pairs.
//#

private void populate() {
      int c = 0;
      int v = 0;
      int x = 0;
      int y = 0;


      for( c = 0; c < vars.length-1; c++) {
             for( v = c + 1; v < vars.length; v++){
               Hashtable<String,Integer> h = new Hashtable<String,Integer>();
               Integer tempInteger1 = new Integer(vars[c]);
               for( x = 0; x < tempInteger1.intValue(); x++){
                 Integer tempInteger2 = new Integer(vars[v]);
                 for( y = 0; y < tempInteger2.intValue(); y++){
                   StringBuffer xyIndex = new StringBuffer();
                   xyIndex.append(x);
                   xyIndex.append("-"); //$NON-NLS-1$
                   xyIndex.append(y);

                   StringBuffer cvIndex = new StringBuffer();
                   cvIndex.append(c);
                   cvIndex.append("-"); //$NON-NLS-1$
                   cvIndex.append(v);

                   h.put(xyIndex.toString(), new Integer(0));
                   pairs.put(cvIndex.toString(), h);
                 }
               }
             }
           }
}

//# This recursive routine walks through all the values of all the variables, trying to construct
//# a test case with the highest number of unique pairings.
private String findnext(String p_Slug){

      int c = 0;
      int v = 0;
      int x = 0;
      int y = 0;
      String caseSlug = p_Slug;
      String[] casevalues = caseSlug.split("\t"); //$NON-NLS-1$
      int[] scores;
      String[] scorestrings;
      String best = "x"; //$NON-NLS-1$


	//# find the unfinished part of the test case.
    for( c = 0; c < vars.length; c++){
            if( casevalues[c].equals("x")) { //$NON-NLS-1$
              break;
            }
    }
    if( c == vars.length) {
        return caseSlug;
    }

	//# but if no part is unfinished, then we're done.
	//# let's walk through the values for the particular variable we have to choose.
    Integer integerTemp1 = new Integer(vars[c]);
    scorestrings = new String[integerTemp1.intValue()];
    for( x = 0; x < integerTemp1.intValue(); x++){
		//# let's check the current variable paired against the all the other values.
          scores = new int[vars.length];
		  for( v = 0; v < vars.length; v++){
				//# but don't check it against itself.
				if( v == c) {
				  scores[v] = 0;
				  continue;
				}
				//# for any variable we've already chosen, we already know the pairing status
				//# and we just add that to the score.
				if( v < c) {
						  StringBuffer caseValuesIndex = new StringBuffer();
						  caseValuesIndex.append(casevalues[v]);
						  caseValuesIndex.append("-"); //$NON-NLS-1$
						  caseValuesIndex.append(x);

						  StringBuffer pairsIndex = new StringBuffer();
						  pairsIndex.append(v);
                          pairsIndex.append("-"); //$NON-NLS-1$
                          pairsIndex.append(c);

						  Hashtable h = (Hashtable) pairs.get(pairsIndex.toString());
                          Integer temp2 = (Integer) h.get(caseValuesIndex.toString());


						  scores[v] = temp2.intValue();
				}
				//# for the variables we haven't yet chosen, we walk through those values and see what the best pairing score will be.
				else {
					best = "x"; //$NON-NLS-1$
					Integer tempInteger3 = new Integer(vars[v]);
					for( y = 0; y < tempInteger3.intValue(); y++)
					{
						StringBuffer cvIndex = new StringBuffer();
		 				cvIndex.append(c);
                        cvIndex.append("-"); //$NON-NLS-1$
                        cvIndex.append(v);

						StringBuffer xyIndex = new StringBuffer();
						xyIndex.append(x);
						xyIndex.append("-"); //$NON-NLS-1$
						xyIndex.append(y);
						Hashtable h = (Hashtable) pairs.get(cvIndex.toString());
						Integer caseValue4 = (Integer) h.get(xyIndex.toString());
                        int bestIntegerValue;
                        if( best.equals("x") ) { //$NON-NLS-1$
                          bestIntegerValue = 1;
                        }
                        else{
                          bestIntegerValue = Integer.parseInt(best);
                        }

                        Integer bestInteger1 = Integer.getInteger(best);
                        if( bestInteger1 == null) {
                          bestInteger1 = new Integer(0);
                        }
						if( best.equals("x") || ( caseValue4.intValue() < bestIntegerValue) ) { //$NON-NLS-1$
							best = String.valueOf( caseValue4.intValue());
						}
					}
                    if( best.equals("x") ) { //$NON-NLS-1$
					  scores[v] = 0;
                    }
                    else {
                      scores[v] = Integer.parseInt(best) + 0;
                    }
				}
          }


			//# now create a sorted string of scores for the value ($x) of current variable ($c) vs. values ($y) of each other variable ($v)
			 Arrays.sort(scores);

			 int sizeOfScores = scores.length;
			 StringBuffer scoreStringsBuffer = new StringBuffer();
			 for(int i = 0; i < sizeOfScores; i++)
			   {
				int score = scores[i];
                StringBuffer scoreBuffer = new StringBuffer();
                scoreBuffer.append(score);

				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append(zeros[4-scoreBuffer.length()]);
				sBuffer.append(score);
				sBuffer.append("\t"); //$NON-NLS-1$
				scoreStringsBuffer.append(sBuffer.toString());
			}
			int sLength = scoreStringsBuffer.length();
			scoreStringsBuffer.deleteCharAt(sLength-1);
            StringBuffer xBuffer = new StringBuffer();
            xBuffer.append(x);

			scoreStringsBuffer.append(":"); //$NON-NLS-1$
			scoreStringsBuffer.append(zeros[4-xBuffer.length()]);
			scoreStringsBuffer.append(x);
			scorestrings[x] = scoreStringsBuffer.toString();
		  }


	//# the scores for each choice are now in a set of strings of the form {score}:{choice}.
	//# the next step is to sort the scorestrings, pick the best, and record that choice...
      Arrays.sort(scorestrings);
	 String[] sortedscorestrings = scorestrings;

     String[] resultOfSplit_1 = ((String) sortedscorestrings[0]).split(":"); //$NON-NLS-1$
     int intResultOfSplit_1 = Integer.parseInt(resultOfSplit_1[1]);
     int result = intResultOfSplit_1 + 0;
     casevalues[c] = String.valueOf(result);
	//# this monstrousity of a line of code records whether the best choice is a needed value or not. If the best choice
	//# results in no unique pairings, then we call it "N" meaning it's the best choice, but really doesn't matter.
	 String[] resultOfSplit_2 = ((String) sortedscorestrings[0]).split(":"); //$NON-NLS-1$
     String[] resultOfSplit_3 = resultOfSplit_2[0].split("\t"); //$NON-NLS-1$
     Arrays.sort(resultOfSplit_3);
	 String[] sortedResultOfSplit_3 = resultOfSplit_3;
     String tempString = sortedResultOfSplit_3[1];
	 if( Integer.parseInt(tempString) == 0) {
	   neededvalues[c] = "Y"; //$NON-NLS-1$
	 }
	 else{
	   neededvalues[c] = "N"; //$NON-NLS-1$
	 }

	//# now construct the test case string and call findnext again.
    caseSlug = ""; //$NON-NLS-1$
    StringBuffer caseBuffer = new StringBuffer();

    for( int i = 0; i < casevalues.length; i++){
        String casevalue = casevalues[i];
        caseBuffer.append(casevalue);
        caseBuffer.append("\t"); //$NON-NLS-1$
	}
    caseBuffer.deleteCharAt(caseBuffer.length()-1);
    caseSlug = caseBuffer.toString();
	//# after the recursion bottoms out, it will unwind via this return statement.
	return findnext(caseSlug);
    }

	private void status() {
		int c = 0;
		int v = 0;
		int x = 0;
		int y = 0;

		Logger.getLogger(this.getClass()).info("\nPAIRING DETAILS\n"); //$NON-NLS-1$
		Logger.getLogger(this.getClass()).info("var1\tvar2\tvalue1\tvalue2\tappearances\tcases\n"); //$NON-NLS-1$

		  for( c = 0; c < vars.length-1; c++){
			for(v = c+1; v < vars.length; v++){
			  Integer tempInteger1 = new Integer(vars[c]);
			  for( x = 0; x < tempInteger1.intValue(); x++) {
				Integer tempInteger2 = new Integer(vars[v]);
				for( y = 0; y < tempInteger2.intValue(); y++){
				  StringBuffer sBuffer = new StringBuffer();
				  sBuffer.append(labelsArray.get(c));
				  sBuffer.append("\t"); //$NON-NLS-1$
				  sBuffer.append(labelsArray.get(v));
				  sBuffer.append("\t"); //$NON-NLS-1$
				  ArrayList array1 = gettable2("tables",c); //$NON-NLS-1$
				  sBuffer.append(array1.get(x));
				  sBuffer.append("\t"); //$NON-NLS-1$
				  ArrayList array2 = gettable2("tables",v); //$NON-NLS-1$
				  sBuffer.append(array2.get(y));
				  sBuffer.append("\t"); //$NON-NLS-1$
				  StringBuffer cvIndexBuffer = new StringBuffer();
				  cvIndexBuffer.append(c);
                  cvIndexBuffer.append("-"); //$NON-NLS-1$
                  cvIndexBuffer.append(v);

				  Hashtable h1 = (Hashtable) pairs.get(cvIndexBuffer.toString());

				  StringBuffer xyIndexBuffer = new StringBuffer();
				  xyIndexBuffer.append(x);
				  xyIndexBuffer.append("-"); //$NON-NLS-1$
				  xyIndexBuffer.append(y);

				  sBuffer.append( h1.get(xyIndexBuffer.toString()));
				  sBuffer.append("\t"); //$NON-NLS-1$
				  Logger.getLogger(this.getClass()).info(sBuffer.toString());

				  String comma = ""; //$NON-NLS-1$
				  Hashtable h2 = (Hashtable) pairscases.get(cvIndexBuffer.toString());
				  ArrayList array3 = (ArrayList) h2.get(xyIndexBuffer.toString());
				  for( int i = 0; i < array3.size(); i++){
					  Logger.getLogger(this.getClass()).info(comma);
					  Logger.getLogger(this.getClass()).info(array3.get(i));
					comma = ", "; //$NON-NLS-1$
				  }
				  Logger.getLogger(this.getClass()).info("\n"); //$NON-NLS-1$
				}
			  }
			}
		  }
	}

	private ArrayList gettable1(String p_TableName, String p_Index){
		  String tablename = p_TableName;
		  if( p_Index.equals("labels")) { //$NON-NLS-1$
			return (ArrayList) labelsHt.get(tablename);
		  }
		  else{
			return null;
		  }
	}


	private ArrayList gettable2(String p_TableName, int p_Index){
		  String tablename = p_TableName;
		  int index = p_Index;

		  ArrayList array = (ArrayList) labelsHt.get(tablename);
		  return (ArrayList) lists.get(array.get(index));
	}

	private boolean more(){
		  int c = 0;
		  int v = 0;
		  int x = 0;
		  int y = 0;
		  for( c = 0; c < vars.length-1; c++){
			for( v = c+1; v < vars.length; v++){
              Integer tempInteger1 = new Integer(vars[c]);
			  for( x = 0; x < tempInteger1.intValue(); x++){
                Integer tempInteger2 = new Integer(vars[v]);
				for( y = 0; y < tempInteger2.intValue(); y++){
				  StringBuffer cvIndex = new StringBuffer();
				  StringBuffer xyIndex = new StringBuffer();
				  cvIndex.append(c);
                  cvIndex.append("-"); //$NON-NLS-1$
                  cvIndex.append(v);

				  xyIndex.append(x);
				  xyIndex.append("-"); //$NON-NLS-1$
				  xyIndex.append(y);

				  Hashtable h = (Hashtable) pairs.get(cvIndex.toString());
 				  Integer tempValue = (Integer) h.get(xyIndex.toString());
				  if( tempValue.intValue() == 0 ) {
					return true;
				  }
				}
			  }
			}
		  }
		  return false;
	}

	private String readable(String p_Case){
		String newcase = ""; //$NON-NLS-1$
		int t = 0;
		String[] list = p_Case.split("\t"); //$NON-NLS-1$
		StringBuffer newCaseBuffer = new StringBuffer();
        StringBuffer valueBuffer = null;
        ArrayList<String> combination = new ArrayList<String>();
		for( t = 0; t < list.length; t++){
            valueBuffer = new StringBuffer();
            Integer tempInteger10 = (Integer) listorderArray.get(t);
			if( neededvalues[tempInteger10.intValue()].equals("N")) { //$NON-NLS-1$
			  newCaseBuffer.append(newcase);
			  newCaseBuffer.append("~"); //$NON-NLS-1$

              valueBuffer.append(newcase);
			}
			ArrayList array1 = gettable2("tables", tempInteger10.intValue()); //$NON-NLS-1$
            String listIndex = list[tempInteger10.intValue()];
            Integer tempInteger1 = new Integer(listIndex);
			newCaseBuffer.append(array1.get(tempInteger1.intValue()));

            valueBuffer.append(array1.get(tempInteger1.intValue()));
            combination.add(valueBuffer.toString());

			newCaseBuffer.append("\t"); //$NON-NLS-1$
		}
        allPairsCombinations.add(combination);
		newcase = newCaseBuffer.toString();
    	return newcase;
	}

	 @SuppressWarnings("unchecked")
	private void maketables(ArrayList p_UseCaseData, String p_TableName){
		String tablename = p_TableName;
		ArrayList data = p_UseCaseData;
		int index = 0;
		int l_count = 0;

        labelsHt.put(tablename, new ArrayList());

		String label =  (String) data.get(0);

		String[] labelSplits = label.split("\t"); //$NON-NLS-1$
		for( int i = 0; i < labelSplits.length; i++) {
		  StringBuffer splitBuffer = new StringBuffer(labelSplits[i]);
		  listorderHt.put(splitBuffer.toString(), new Integer(l_count++));
		  ArrayList<String> arrayOfLabels = (ArrayList<String>) labelsHt.get(tablename);
		  arrayOfLabels.add(splitBuffer.toString());

          lists.put(splitBuffer.toString(), new ArrayList());
		}

		index = 0;
		int i = 1;

		  while(i < data.size()) {
			index = 0;
			String values = (String) data.get(i);
            String[] splitValues = values.split("\t"); //$NON-NLS-1$
			for(int j = 0; j < splitValues.length; j++) {
			  if( !splitValues[j].equals("")){ //$NON-NLS-1$
				ArrayList labelArray = (ArrayList) labelsHt.get(tablename);
				String labelElement = (String) labelArray.get(index);

				ArrayList<String> arrayOfElements = (ArrayList<String>) lists.get(labelElement);
				arrayOfElements.add(splitValues[j]);
			  }
              index++;

			}
			i++;
		  }

		//# reorder the variable lists by size, because the allpairs algorithm works better that way.
		ArrayList tempArray = (ArrayList) labelsHt.get(tablename);

		Collections.sort(tempArray, new Comparator() {
					public int compare(Object o1, Object o2) {
						String s1 = (String) o1;
						String s2 = (String) o2;
                        ArrayList elements1 = (ArrayList) lists.get(s1);
                        ArrayList elements2 = (ArrayList) lists.get(s2);
                        Integer numOfElements1 = new Integer(elements1.size());
                        Integer numOfElements2 = new Integer(elements2.size());

						if (numOfElements1.intValue() < numOfElements2.intValue()) {
							return 1;
						}
						else if (numOfElements1.intValue() > numOfElements2.intValue()) {
							return -1;
						}
						else {
							return 0;
						}
					}
				});

		  index = 0;
          for( i = 0; i < tempArray.size(); i++) {
            listorderArray.add(new Integer(0));
          }

		  for( i = 0; i < tempArray.size(); i++){
            Integer listOrderIndex  = (Integer) listorderHt.get(tempArray.get(i));
            listorderArray.set(listOrderIndex.intValue(), new Integer(index++));
		  }
	      vars = new String[tempArray.size()];
		  for( index = 0; index < tempArray.size(); index++){
			ArrayList array2 = (ArrayList) lists.get(tempArray.get(index));
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(array2.size());
			vars[index] = sBuffer.toString();
		  }
	}
}