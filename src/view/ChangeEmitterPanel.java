package view;

import javax.swing.JPanel;

public abstract class ChangeEmitterPanel extends JPanel implements ChangeEmitter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1694020342657889478L;
	private ChangeEmitter changeEmitter;
	/**
	 * Create the panel.
	 */
	public ChangeEmitterPanel(ChangeEmitter changeEmitter) {
		this.changeEmitter = changeEmitter;
	}
	
	@Override
	public ChangeEmitter getSuperChangeEmitter () {
		return this.changeEmitter;
	}
	
	@Override
	public void setSuperChangeEmitter (ChangeEmitter changeEmitter) {
		this.changeEmitter = changeEmitter;
	}
}
