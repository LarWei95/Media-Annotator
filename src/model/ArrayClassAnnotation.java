package model;

import java.util.ArrayList;
import java.util.Collection;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.Jsonable;

import view.elements.MappablePanel;

public class ArrayClassAnnotation extends BaseClassAnnotation {
	
	private ArrayList<Annotation> classes;
	
	public ArrayClassAnnotation (Collection<Annotation> classes) {
		this.classes = new ArrayList<Annotation>(classes);
	}
	
	@Override
	public String getKeyString() {
		return "arrayclass";
	}

	@Override
	public String getValueString() {
		StringBuilder builder = new StringBuilder("[");
		
		int count = this.classes.size();
		int lastIndex = count - 1;
		Annotation current;
		
		for (int i = 0; i < count; i++) {
			current = this.classes.get(i);
			
			builder.append(current.getValueString());
			
			if (i != lastIndex) {
				builder.append(",");
			}
		}
		
		builder.append("]");
		return builder.toString();
	}

	@Override
	public Jsonable getJsonable() {
		JsonArray array = new JsonArray();
		
		for (Annotation anno: this.classes) {
			array.add(anno.getJsonable());
		}
		
		return array;
		
	}
}
