package model;

public enum MediaType {
	IMAGE(MediaType.IMAGE_STRING);
	
	
	public static final String IMAGE_STRING = "image";
	private final String label;
	
	private MediaType(String label) {
		this.label = label;
	}
	
	public String getLabel () {
		return this.label;
	}
	
	public static MediaType ofString (String str) {
		switch (str) {
		case "image":
			return MediaType.IMAGE;
			default:
				throw new IllegalArgumentException("Unrecognized type: "+str);
		}
	}
}
