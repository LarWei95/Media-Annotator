package view.annotation.types;

import view.ChangeEmitter;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import control.ViewAnnotationLink;
import model.annotation.Annotation;
import model.annotation.BoxAnnotation;
import model.annotation.MapClassAnnotation;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

class CoordinateChangeListener implements PropertyChangeListener {
	private BoxAnnotationPanel panel;
	protected boolean active;
	
	public CoordinateChangeListener (BoxAnnotationPanel panel) {
		this.panel = panel;
		this.active = true;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (this.active) {
			this.panel.forwardChange();
		}
	}
	
}

public class BoxAnnotationPanel extends AnnotationPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -369267743186640621L;
	private MapClassAnnotationPanel annotationPanel;
	private JFormattedTextField xminField;
	private JFormattedTextField yminField;
	private JFormattedTextField xmaxField;
	private JFormattedTextField ymaxField;
	
	private NumberFormat xminFieldFormat;
	private NumberFormat yminFieldFormat;
	private NumberFormat xmaxFieldFormat;
	private NumberFormat ymaxFieldFormat;
	
	private CoordinateChangeListener xminListener;
	private CoordinateChangeListener yminListener;
	private CoordinateChangeListener xmaxListener;
	private CoordinateChangeListener ymaxListener;
	
	private BoxAnnotation boxAnnotation;

	public BoxAnnotationPanel(ChangeEmitter changeEmitter, ViewAnnotationLink viewAnnotationLink) {
		super(changeEmitter, viewAnnotationLink);
		this.annotationPanel = new MapClassAnnotationPanel(this, viewAnnotationLink, MappableAnnotationPanel.TYPE_BASECLASS);
		this.boxAnnotation = null;
		
		this.xminFieldFormat = NumberFormat.getNumberInstance();
		this.yminFieldFormat = NumberFormat.getNumberInstance();
		this.xmaxFieldFormat = NumberFormat.getNumberInstance();
		this.ymaxFieldFormat = NumberFormat.getNumberInstance();
		
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel xLabel = new JLabel("X");
		GridBagConstraints gbc_xLabel = new GridBagConstraints();
		gbc_xLabel.insets = new Insets(0, 0, 5, 5);
		gbc_xLabel.gridx = 1;
		gbc_xLabel.gridy = 0;
		add(xLabel, gbc_xLabel);
		
		JLabel yLabel = new JLabel("Y");
		GridBagConstraints gbc_yLabel = new GridBagConstraints();
		gbc_yLabel.insets = new Insets(0, 0, 5, 0);
		gbc_yLabel.gridx = 2;
		gbc_yLabel.gridy = 0;
		add(yLabel, gbc_yLabel);
		
		JLabel minLabel = new JLabel("Minimum:");
		GridBagConstraints gbc_minLabel = new GridBagConstraints();
		gbc_minLabel.anchor = GridBagConstraints.EAST;
		gbc_minLabel.insets = new Insets(0, 0, 5, 5);
		gbc_minLabel.gridx = 0;
		gbc_minLabel.gridy = 1;
		add(minLabel, gbc_minLabel);
		
		this.xminField = new JFormattedTextField(this.xminFieldFormat);
		this.xminListener = new CoordinateChangeListener(this);
		this.xminField.addPropertyChangeListener(this.xminListener);
		GridBagConstraints gbc_xminField = new GridBagConstraints();
		gbc_xminField.insets = new Insets(0, 0, 5, 5);
		gbc_xminField.fill = GridBagConstraints.HORIZONTAL;
		gbc_xminField.gridx = 1;
		gbc_xminField.gridy = 1;
		add(this.xminField, gbc_xminField);
		
		this.yminField = new JFormattedTextField(this.yminFieldFormat);
		this.yminListener = new CoordinateChangeListener(this);
		this.yminField.addPropertyChangeListener(this.yminListener);
		GridBagConstraints gbc_yminField = new GridBagConstraints();
		gbc_yminField.insets = new Insets(0, 0, 5, 0);
		gbc_yminField.fill = GridBagConstraints.HORIZONTAL;
		gbc_yminField.gridx = 2;
		gbc_yminField.gridy = 1;
		add(this.yminField, gbc_yminField);
		
		JLabel maxLabel = new JLabel("Maximum:");
		GridBagConstraints gbc_maxLabel = new GridBagConstraints();
		gbc_maxLabel.anchor = GridBagConstraints.EAST;
		gbc_maxLabel.insets = new Insets(0, 0, 5, 5);
		gbc_maxLabel.gridx = 0;
		gbc_maxLabel.gridy = 2;
		add(maxLabel, gbc_maxLabel);
		
		this.xmaxField = new JFormattedTextField(this.xmaxFieldFormat);
		this.xmaxListener = new CoordinateChangeListener(this);
		this.xmaxField.addPropertyChangeListener(this.xmaxListener);
		GridBagConstraints gbc_xmaxField = new GridBagConstraints();
		gbc_xmaxField.insets = new Insets(0, 0, 5, 5);
		gbc_xmaxField.fill = GridBagConstraints.HORIZONTAL;
		gbc_xmaxField.gridx = 1;
		gbc_xmaxField.gridy = 2;
		add(this.xmaxField, gbc_xmaxField);
		
		this.ymaxField = new JFormattedTextField(this.ymaxFieldFormat);
		this.ymaxListener = new CoordinateChangeListener(this);
		this.ymaxField.addPropertyChangeListener(this.ymaxListener);
		GridBagConstraints gbc_ymaxField = new GridBagConstraints();
		gbc_ymaxField.insets = new Insets(0, 0, 5, 0);
		gbc_ymaxField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ymaxField.gridx = 2;
		gbc_ymaxField.gridy = 2;
		add(this.ymaxField, gbc_ymaxField);
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		
		BoxAnnotationPanel.setNumberFormatSpecifics(this.xminFieldFormat, this.xminField);
		BoxAnnotationPanel.setNumberFormatSpecifics(this.xminFieldFormat, this.yminField);
		BoxAnnotationPanel.setNumberFormatSpecifics(this.xminFieldFormat, this.xmaxField);
		BoxAnnotationPanel.setNumberFormatSpecifics(this.xminFieldFormat, this.ymaxField);
		
		add(this.annotationPanel, gbc_panel);
		// TODO Auto-generated constructor stub
	}

	private static void setNumberFormatSpecifics (NumberFormat numberFormat, JFormattedTextField field) {
		numberFormat.setMinimumFractionDigits(0);
		numberFormat.setMaximumFractionDigits(0);
		field.setValue(0);
	}
	
	public void setFieldListenersActive (boolean active) {
		this.xminListener.active = active;
		this.yminListener.active = active;
		this.xmaxListener.active = active;
		this.ymaxListener.active = active;
	}
	
	public void setCoordinates (int xmin, int ymin, int xmax, int ymax) {
		this.setFieldListenersActive(false);
		
		this.xminField.setValue(xmin);
		this.yminField.setValue(ymin);
		this.xmaxField.setValue(xmax);
		this.ymaxField.setValue(ymax);
		
		this.setFieldListenersActive(true);
		this.forwardChange();
	}
	
	@Override
	public void updateOnForwardedChange() {
		MapClassAnnotation baseClassAnnotation = (MapClassAnnotation)this.annotationPanel.getAnnotation();
		
		if (baseClassAnnotation != null) {
			int xmin = -1;
			int ymin = -1;
			int xmax = -1;
			int ymax = -1;
			
			boolean xminOk = false;
			boolean yminOk = false;
			boolean xmaxOk = false;
			boolean ymaxOk = false;
			
			try {
				xmin = Integer.valueOf(xminField.getText().replace(".", ""));
				xminOk = true;
			} catch (NumberFormatException e) {
				
			}
			
			try {
				ymin = Integer.valueOf(yminField.getText().replace(".", ""));
				yminOk = true;
			} catch (NumberFormatException e) {
				
			}
			
			try {
				xmax = Integer.valueOf(xmaxField.getText().replace(".", ""));
				xmaxOk = true;
			} catch (NumberFormatException e) {
				
			}
			
			try {
				ymax = Integer.valueOf(ymaxField.getText().replace(".", ""));
				ymaxOk = true;
			} catch (NumberFormatException e) {
				
			}
				
			if (xminOk && yminOk && xmaxOk && ymaxOk) {
				this.boxAnnotation = new BoxAnnotation(xmin, ymin, xmax, ymax, baseClassAnnotation);
			}
		}
	}

	@Override
	public Annotation getAnnotation() {
		return this.boxAnnotation;
	}

	@Override
	public void fillActivePanelContainer(ActivePanelContainer activePanelContainer) {
		activePanelContainer.setBoxAnnotationPanel(this);
		this.annotationPanel.fillActivePanelContainer(activePanelContainer);
	}

	@Override
	public void setAnnotation(Annotation annotation) {
		BoxAnnotation anno = (BoxAnnotation) annotation;
		
		this.setFieldListenersActive(false);
		this.xminField.setValue(anno.getStartX());
		this.yminField.setValue(anno.getStartY());
		this.xmaxField.setValue(anno.getEndX());
		this.ymaxField.setValue(anno.getEndY());
		this.setFieldListenersActive(true);
		
		this.annotationPanel.setAnnotation(anno.getMapClassAnnotation());
		
		this.forwardChange();
	}

	@Override
	public void clear() {
		this.annotationPanel.clear();
	}
}
