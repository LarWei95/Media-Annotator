package model;

import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

public class ClassAnnotation extends BaseClassAnnotation {
	private Object classAnnotation;
	private boolean inBrackets;
	
	public ClassAnnotation (Object classAnnotation, boolean inBrackets) {
		this.classAnnotation = classAnnotation;
		this.inBrackets = inBrackets;
	}
	
	@Override
	public String getKeyString() {
		return "class";
	}

	@Override
	public String getValueString() {
		StringBuilder builder = new StringBuilder();
		
		if (this.inBrackets) {
			builder.append("\"");
		}
		
		builder.append(this.classAnnotation.toString());
		
		if (this.inBrackets) {
			builder.append("\"");
		}
		
		return builder.toString();
	}

	@Override
	public Jsonable getJsonable() {
		JsonObject obj = new JsonObject();
		
		obj.put(this.getKeyString(), Jsoner.escape(this.classAnnotation.toString()));
		return obj;
	}
}
