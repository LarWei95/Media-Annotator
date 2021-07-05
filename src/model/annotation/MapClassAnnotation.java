package model.annotation;

import java.util.HashMap;
import java.util.Map;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

public class MapClassAnnotation extends BaseClassAnnotation {

	private HashMap<String, Annotation> classes;
	
	public MapClassAnnotation (Map<String, Annotation> classes) {
		this.classes = new HashMap<String, Annotation>(classes);
	}
	
	@Override
	public Annotation copy() {
		HashMap<String, Annotation> copiedClasses = new HashMap<String, Annotation>(this.classes.size());
		
		for (String str: this.classes.keySet()) {
			copiedClasses.put(str, this.classes.get(str).copy());
		}
		
		return new MapClassAnnotation(copiedClasses);
	}
	
	@Override
	public String getKeyString() {
		return "mapclass";
	}

	public HashMap<String, Annotation> getClasses () {
		return this.classes;
	}
	
	@Override
	public String getValueString() {
		StringBuilder builder = new StringBuilder("{");
		
		int count = this.classes.size();
		int lastIndex = count - 1;
		int i = 0;
		
		Annotation current;
		
		for (String key: this.classes.keySet()) {
			current = this.classes.get(key);
			
			if (current != null) {
				builder.append("\"");
				builder.append(key);
				builder.append("\":");
				
				builder.append(current.getValueString());
				
				if (i != lastIndex) {
					builder.append(",");
				}
			}
			
			i += 1;
		}
		
		
		builder.append("}");
		return builder.toString();
	}


	@Override
	public Jsonable getJsonable() {
		JsonObject obj = new JsonObject();
		
		Annotation current;
		
		for (String key: this.classes.keySet()) {
			current = this.classes.get(key);
			
			obj.put(Jsoner.escape(key), current.getJsonable());
		}
		
		return obj;
	}


	
}
