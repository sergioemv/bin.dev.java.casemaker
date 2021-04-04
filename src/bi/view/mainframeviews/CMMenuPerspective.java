package bi.view.mainframeviews;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;

import com.jidesoft.action.CommandBar;
import com.jidesoft.swing.JideMenu;

public enum CMMenuPerspective {
	DEFAULT,
	BUSINESS_RULES,
	TDSTRUCTURE,
	TEST_DATA,
	TEST_DATASET,
	TEST_DATASET_REPORTS,
	VARIABLES,
	RESULT_COMPARISON,
	ERROR,
	ELEMENT_EQUIVALENCECLASS,
	EFFECTS,
	DEPENDENCY_COMBINATION,
	TEST_CASES,
	STD_COMBINATION;
	private List<JMenu> menus;
	private List<CommandBar> commandBars;

	public List<JMenu> getMenus(){
		if (menus == null){
		List<JMenu> list = new ArrayList<JMenu>();
		switch (this) {

		case BUSINESS_RULES:
			list.add(CMMainCommandBarFactory.createMenuBusinessRules());
			break;
		case TDSTRUCTURE:
			list.add(CMMainCommandBarFactory.createMenuStructure());
			list.add(CMMainCommandBarFactory.createMenuFormulas());
			break;
		case TEST_DATA:
			list.add(CMMainCommandBarFactory.createMenuTestDataCombination());
			list.add(CMMainCommandBarFactory.createMenuFormulas());
			break;
		case TEST_DATASET:
			list.add(CMMainCommandBarFactory.createMenuTestDataSet());
			break;
		case TEST_DATASET_REPORTS:
			list.add(CMMainCommandBarFactory.createMenuTestDataSetReports());
		case VARIABLES:
			list.add(CMMainCommandBarFactory.createMenuVariables());
			break;
		case RESULT_COMPARISON:
			list.add(CMMainCommandBarFactory.createMenuResultComparison());
			break;
		case ERROR:
			list.add(CMMainCommandBarFactory.createMenuError());
			break;
		case ELEMENT_EQUIVALENCECLASS:
			list.add(CMMainCommandBarFactory.createMenuElement());
			list.add(CMMainCommandBarFactory.createMenuEquivalenceClass());
			break;
		case EFFECTS:
			list.add(CMMainCommandBarFactory.createMenuEffects());
			break;
		case DEPENDENCY_COMBINATION:
			list.add(CMMainCommandBarFactory.createMenuDependency());
			list.add(CMMainCommandBarFactory.createMenuCombination());
			break;
		case TEST_CASES:
			list.add(CMMainCommandBarFactory.createMenuTestCase());;
			break;
		case STD_COMBINATION:
			list.add(CMMainCommandBarFactory.createMenuStdCombination());
			break;
		default:
			break;
		}

		menus = list ;
	}
	return menus;

}
	public List<CommandBar> getCommandBars(){
		commandBars = null;
		if (commandBars == null){
			List<CommandBar> list = new ArrayList<CommandBar>();
			switch (this) {
			case DEFAULT:
				list.add(CMMainCommandBarFactory.createMainMenuBar(CMApplication.frame));
				list.add(CMMainCommandBarFactory.createToolBarStandart());
				list.add(CMMainCommandBarFactory.createToolBarHelp());
				break;
			case BUSINESS_RULES:
				list.add(CMMainCommandBarFactory.createToolBarBusinessRules());
			break;
			case TDSTRUCTURE:
				list.add(CMMainCommandBarFactory.createToolBarStructure());
				list.add(CMMainCommandBarFactory.createToolBarFields());
				list.add(CMMainCommandBarFactory.createToolBarFormulasAndVariables());
			break;
			case TEST_DATA:
				list.add(CMMainCommandBarFactory.createToolBarTestData());
				list.add(CMMainCommandBarFactory.createToolBarFormulasAndVariables());
				break;
			case TEST_DATASET:
				list.add(CMMainCommandBarFactory.createToolBarTestDataSet());
				break;
			case TEST_DATASET_REPORTS:
				list.add(CMMainCommandBarFactory.createToolBarTestDataSetReports());
				break;
			case VARIABLES:
				list.add(CMMainCommandBarFactory.createToolBarVariables());
				break;
			case RESULT_COMPARISON:
				list.add(CMMainCommandBarFactory.createToolBarResultComparison());
				break;
			case ERROR:
				list.add(CMMainCommandBarFactory.createToolBarErrors());
				break;
			case ELEMENT_EQUIVALENCECLASS:
				list.add(CMMainCommandBarFactory.createToolBarElements());
				break;
			case EFFECTS:
				list.add(CMMainCommandBarFactory.createToolBarCauseEffects());
				break;
			case DEPENDENCY_COMBINATION:
				list.add(CMMainCommandBarFactory.createToolBarDependenciesCombinations());
				break;
			case TEST_CASES:
				list.add(CMMainCommandBarFactory.createToolBarTestCases());
				break;
			case STD_COMBINATION:
				list.add(CMMainCommandBarFactory.createToolBarStdCombinations());
				break;
			default:
				break;
			}
			commandBars = list;
		}
		return commandBars;
	}

	public List<String> getCommandBarNames(){
		List<String> list = new ArrayList<String>();
		for (CommandBar commandBar : getCommandBars())
			list.add(commandBar.getTitle());
		return list;
	}
}