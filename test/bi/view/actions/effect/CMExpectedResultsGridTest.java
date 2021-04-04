package bi.view.actions.effect;

import java.awt.Robot;

import org.apache.log4j.BasicConfigurator;

import junit.framework.TestCase;

/**
 * @author smoreno
 *
 */
public class CMExpectedResultsGridTest extends TestCase {



	private CMExpectedResultsDialogPanel dp;
	private Robot robot;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		BasicConfigurator.configure();
		 dp = new CMExpectedResultsDialogPanel();


		super.setUp();
	}



	/*
	 * Test method for 'bi.view.actions.effect.CMExpectedResultsGrid.deleteSelectedRow()'
	 */
	public void testDeleteSelectedRow() {

		dp.getJButtonDelete().doClick();
		dp.getJButtonAdd().doClick();
		assertEquals(1,dp.getGridExpectedResults().getRowCount()); //$NON-NLS-1$
		dp.getJButtonDelete().doClick();
		assertEquals(0,dp.getGridExpectedResults().getRowCount()); //$NON-NLS-1$
		dp.getJButtonDelete().doClick();
		assertEquals(0,dp.getGridExpectedResults().getRowCount()); //$NON-NLS-1$

	}

}
