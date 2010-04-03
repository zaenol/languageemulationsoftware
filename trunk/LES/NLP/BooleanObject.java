package NLP;

public class BooleanObject{
	private Boolean value = false;
	
	public BooleanObject(boolean bool){
		this.value = new Boolean(bool);
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = new Boolean(value);
		
	}
	public boolean equals(boolean bool){
		return value.equals(bool);
	}
	public String toString(){
		return value+"";
	}
	
	
}
