package bi.view.businessrulesviews.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class CMLineNumberScrollPane extends JScrollPane {

	protected JTextPane lineNumbers;
	protected int numLines;
	protected int numDigits;
	int fontWidth;
	JPanel m_contentPane;
	private CMCustomJTextPane m_CMCustomJTextPane;

	public CMLineNumberScrollPane(CMCustomJTextPane pane) {
		super();
		m_CMCustomJTextPane = pane;
		fontWidth = getFontMetrics(m_CMCustomJTextPane.getFont()).stringWidth("9");
		lineNumbers = new JTextPane() {
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.width = (numDigits * fontWidth) + 1;
				return d;
			}

			public boolean getScrollableTracksViewportWidth() {
				return false;
			}

			@Override
			public void scrollRectToVisible(Rectangle aRect) {
				//override to avoid scrolling inside line numbers.
				//do nothing
			}
			
		};
		lineNumbers.setText("1");
		lineNumbers.setEditable(false);
		lineNumbers.setEnabled(false);
		lineNumbers.setFont(new Font("Dialog", Font.PLAIN, 12));
		numLines = 1;
		numDigits = 1;
		m_contentPane = new JPanel() {
			/*
			 * public void paint(Graphics g) { // paint a thin gray line down
			 * right-hand side of line numbering super.paint(g); Dimension d =
			 * getSize(); int w = d.width - 3; int h = d.height - 1;
			 * g.setColor(Color.gray); g.drawLine(w, 0, w, h); }
			 */

			public Dimension getPreferredSize() {
				Dimension d = lineNumbers.getPreferredSize();
				d.width += 10;
				return d;
			}
		};
		m_contentPane.setBackground(Color.white);
		m_contentPane.setLayout(new BoxLayout(m_contentPane, BoxLayout.X_AXIS));
		m_contentPane.add(lineNumbers);
		setRowHeaderView(m_contentPane);

	}

	


	public void highlightLine(int line) {
		int start = 0;
		String s = m_CMCustomJTextPane.getText() + "\n";
		for (int i = 0; i < line - 1; i++)
			start = s.indexOf("\n", start) + 1;
		int end = s.indexOf("\n", start);

		m_CMCustomJTextPane.highlight(start, end);
	}


	protected void updateLineNumbers() {
		String s = m_CMCustomJTextPane.getText();
		int count = 1;

		// count number of lines in document
		int index = s.indexOf("\n");
		while (index >= 0) {
			count++;
			index = s.indexOf("\n", index + 1);
		}
		if (count == numLines)
			return;

		// compute index into line numbers text string
		String l = lineNumbers.getText() + "\n";
		int digits = ("" + count).length();
		int spot = 0;
		int nine = 9;
		for (int i = 2; i <= digits; i++) {
			spot += i * nine;
			nine *= 10;
		}
		int ten = nine / 9;
		spot += (digits + 1) * (count - ten + 1);

		// update line numbers text string
		int maxSpot = l.length();
		String newL;
		if (spot < maxSpot) {
			// eliminate extra line numbers
			newL = l.substring(0, spot - 1);
		} else {
			// append additional line numbers
			StringBuffer sb = new StringBuffer(spot);
			sb.append(l);
			for (int i = numLines + 1; i < count; i++) {
				sb.append(i);
				sb.append("\n");
			}
			sb.append(count);
			newL = sb.toString();
		}
		numLines = count;
		numDigits = digits;
		lineNumbers.setText(newL);
	}

	public void updateAfterChangeFont(Font font) {
		fontWidth = getFontMetrics(m_CMCustomJTextPane.getFont()).stringWidth("9");
		lineNumbers.setFont(font);
		/*
		 * lineNumbers = new JTextPane() { public Dimension getPreferredSize() {
		 * Dimension d = super.getPreferredSize(); d.width = numDigits *
		 * fontWidth; return d; } }; lineNumbers.setText("1");
		 * lineNumbers.setEditable(false); lineNumbers.setFont(font); numLines =
		 * 1; numDigits = 1; m_contentPane = new JPanel() {
		 * 
		 * public Dimension getPreferredSize() { Dimension d =
		 * lineNumbers.getPreferredSize(); d.width += 10; return d; } };
		 * m_contentPane.setBackground(Color.white); m_contentPane.setLayout(new
		 * BoxLayout(m_contentPane, BoxLayout.X_AXIS));
		 * m_contentPane.add(lineNumbers); setRowHeaderView(m_contentPane);
		 * updateLineNumbers();
		 */
	}
}
