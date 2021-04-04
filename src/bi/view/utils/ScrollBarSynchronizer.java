/*******************************************************************************
Developed by BUSINESS SOFTWARE INNOVATIONS. .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/


package	 bi.view.utils;


import	javax.swing.JScrollBar;
import	javax.swing.BoundedRangeModel;
import	javax.swing.event.ChangeListener;
import	javax.swing.event.ChangeEvent;

/**
 * This class is used for synchronizing two JScrollBars.
 * <p>
 * If (maximum - minimum) of the scroll bars don't match, the other scrollbar
 * will be adjusted proportionally.
 *
 */
public class ScrollBarSynchronizer
	implements	ChangeListener
{
	private		JScrollBar		msbScrollBar;

	/**
	 * Constructor.
	 *
	 * @param	destScroll	ScrollBar to synchronize
	 */
	public ScrollBarSynchronizer( JScrollBar destScroll )
	{
		msbScrollBar = destScroll;
	}

	public void stateChanged( ChangeEvent e )
	{
		BoundedRangeModel	sourceScroll = (BoundedRangeModel)e.getSource();
        int iSVal = sourceScroll.getValue();
        msbScrollBar.setValue( iSVal);
		/* OLD:
		int	iSMin	= sourceScroll.getMinimum();
		int iSMax	= sourceScroll.getMaximum();
		int	iSDiff	= iSMax - iSMin;
		int	iSVal	= sourceScroll.getValue();

		int	iDMin	= msbScrollBar.getMinimum();
		int iDMax	= msbScrollBar.getMaximum();
		int	iDDiff	= iDMax - iDMin;
		int	iDVal = 0;

		if( iSDiff == iDDiff ) {
			iDVal	= iSVal;
        }
		else {
            if( iSDiff > 0) {
			  iDVal	= (iDDiff * iSVal) / iSDiff;
            }
        }
		
		msbScrollBar.setValue( iDMin + iDVal);
        */
	}
}
