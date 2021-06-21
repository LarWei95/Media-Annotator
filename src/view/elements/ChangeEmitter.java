package view.elements;

public interface ChangeEmitter {
	public void updateOnForwardedChange ();
	
	public default void forwardChange () {
		ChangeEmitter emitter = this.getSuperChangeEmitter();
		this.updateOnForwardedChange();
		
		if (emitter != null) {
			emitter.forwardChange();
		}
	}
	
	public ChangeEmitter getSuperChangeEmitter ();
	
	public void setSuperChangeEmitter (ChangeEmitter changeEmitter);
}
