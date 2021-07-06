package view.selection;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import control.selection.MediaContainer;
import control.selection.SelectionMediaContainer;
import model.Marking;

public class MarkingCellRenderer implements ListCellRenderer<String> {
	public static final Color SELECTION_COLOR = new Color(179, 208, 255);
	
	private MediaContainer<?> mediaContainer;
	
	public MarkingCellRenderer (MediaContainer<?> mediaContainer) {
		this.mediaContainer = mediaContainer;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
			boolean isSelected, boolean cellHasFocus) {
		JLabel label = new JLabel(value);
		label.setOpaque(true);
		
		Color borderColor;
		Color bgColor;
		Color textColor;
		
		if (isSelected) {
			bgColor = SELECTION_COLOR;
			borderColor = Color.WHITE;
		} else if (cellHasFocus) {
			bgColor = Color.WHITE;
			borderColor = SELECTION_COLOR;
		} else {
			borderColor = Color.WHITE;
			bgColor = Color.WHITE;
		}
		
		Marking marking = this.mediaContainer.getMedias().get(index).getMarking();
		
		switch (marking) {
		case DONE:
			textColor = Color.GRAY;
			break;
		case UNFINISHED:
			textColor = Color.RED;
			break;
		default:
				textColor = Color.BLACK;
		}
		
		Border border = BorderFactory.createLineBorder(borderColor);
		label.setBorder(border);
		
		label.setBackground(bgColor);
		label.setForeground(textColor);
		return label;
	}

}
