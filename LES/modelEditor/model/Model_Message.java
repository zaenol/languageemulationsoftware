package modelEditor.model;

public class Model_Message {
	private String originalMessage;
	
	private boolean postOriginalMessage=false;
	
	public Model_Message(String message){
		originalMessage = message+"";
	}
	
	public String getOriginalMessage(){
		return originalMessage;
	}
	
	public String[] getMessageToSend(){
		String[] messages = {originalMessage};
		return messages;
	}

	public boolean isPostOriginalMessage() {
		return postOriginalMessage;
	}

	public void setPostOriginalMessage(boolean postOriginalMessage) {
		this.postOriginalMessage = postOriginalMessage;
	}

	
}
