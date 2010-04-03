package NLP;

public class StringObject{
	private String value = "";
	
	public StringObject(String string){
		value = string;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		
	}
	public boolean equals(Object otherString){
		return value.equals(otherString);
	}
	public String toString(){
		return value;
	}
	public void append(String otherString){
		value+=otherString;
	}
	public void append(StringObject otherString){
		append(otherString.getValue());
	}
	
}
