package view.annotation.types;

public class ActivePanelContainer {
	private BoxAnnotationPanel boxAnnotationPanel;
	private ClassAnnotationPanel classAnnotationPanel;
	
	public ActivePanelContainer () {
		this.reset();
	}
	
	public final void reset () {
		this.boxAnnotationPanel = null;
		this.classAnnotationPanel = null;
	}
	
	public BoxAnnotationPanel getBoxAnnotationPanel () {
		return this.boxAnnotationPanel;
	}
	
	public void setBoxAnnotationPanel (BoxAnnotationPanel boxAnnotationPanel) {
		this.boxAnnotationPanel = boxAnnotationPanel;
	}
	
	public ClassAnnotationPanel getClassAnnotationPanel () {
		return this.classAnnotationPanel;
	}
	
	public void setClassAnnotationPanel (ClassAnnotationPanel classAnnotationPanel) {
		this.classAnnotationPanel = classAnnotationPanel;
	}
}
