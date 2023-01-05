package simulator.view;

public class LawsInfo {
	private String key;
	private String value;
	private String description;
	
	public LawsInfo(String key, String value, String description) {
		this.key = key;
		this.value = value;
		this.description = description;
	}
	


	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
