package model;

public enum Marking {
	NONE,
	UNFINISHED,
	DONE;
	
	public static final String NONE_STRING = "none";
	public static final String UNFINISHED_STRING = "unfinished";
	public static final String DONE_STRING = "done";
	
	public static Marking ofString (String string) {
		string = string.toLowerCase();
		
		switch (string) {
		case Marking.NONE_STRING:
			return Marking.NONE;
		case Marking.UNFINISHED_STRING:
			return Marking.UNFINISHED;
		case Marking.DONE_STRING:
			return Marking.DONE;
		default:
				throw new IllegalArgumentException("Unrecognized marking: "+string);
		}
	}
	
	public String getString () {
		switch (this) {
		case UNFINISHED:
			return UNFINISHED_STRING;
		case DONE:
			return DONE_STRING;
		default:
			return NONE_STRING;
		}
	}
	
}
