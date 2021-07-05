package model.annotation;

import com.github.cliftonlabs.json_simple.Jsonable;

public abstract class Annotation {
	public abstract String getKeyString ();
	
	public abstract String getValueString ();
	
	public abstract Jsonable getJsonable ();
	
	public abstract Annotation copy();
}
